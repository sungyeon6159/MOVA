/**
 *<pre>
 * com.assemble.mova.controller
 * Class Name : LoginController.java
 * Description : 
 * Modification Information
 * 
 *   수정일      수정자              수정내용
 *  ---------   ---------   -------------------------------
 *  2019-12-23           최초생성
 *
 * @author 개발프레임웍크 실행환경 ASSEMBLE
 * @since 2019-12-11 
 * @version 1.0
 * 
 *
 *  Copyright (C) by ASSEMBLE All right reserved.
 * </pre>
 */
package com.assemble.mova.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.assemble.mova.vo.NaMovieVO;

public class NaSearchListController implements Initializable {

	@FXML
	private Button search1, searchBtn, refreshBtn;

	@FXML
	private WebView wImg01, wImg02, wImg03, wImg04, wImg05;

	@FXML
	private VBox vboxBtn01, vboxBtn02, vboxBtn03, vboxBtn04, vboxBtn05;

	@FXML
	private TextField searchTitle;

	@FXML
	private Label sTitle01, sTitle02, sTitle03, sTitle04, sTitle05, loginId;

	@FXML
	private Label avgScore1, avgScore2, avgScore3, avgScore4, avgScore5;

	@FXML
	private Label director1, director2, director3, director4, director5;

	static Logger log = Logger.getLogger(NaSearchListController.class);
	public static String movieCode;
	public static StringBuilder sb;
	public static String str, receiveMsg;
	String query;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginId.setText(LoginController.memName + " 님");

		VBox[] arrVbox = { vboxBtn01, vboxBtn02, vboxBtn03, vboxBtn04, vboxBtn05 };
		for (int i = 0; i < arrVbox.length; i++) {
			arrVbox[i].setVisible(false);
		}
	}

	
	/** 
	 * 
	 * @category 네이버 영화 검색 API
 	 * @author 김주희
	 * @param  e
	 * 
	 */
	
	public void searchResult(ActionEvent e) {

		String clientId = "ftwTVx8KNPzDR27DExC_";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "1SchHKnVP5";// 애플리케이션 클라이언트 시크릿값";\
		int start = 1; // 검색 시작점
		int display = 5; // 검색결과갯수. 최대100개
		query = searchTitle.getText(); // 검색어 입력
		// String country = "EN"; //국가별
		// String yearto = "";// 제작년도 최소
		// String yearfrom = ""; // 제작년도 최대

		try {
			String encodeQuery = URLEncoder.encode(query, "utf-8");

			String apiURL = "https://openapi.naver.com/v1/search/movie.json?" + "query=" + encodeQuery + "&start="
					+ start + "&display=" + display;

			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				InputStreamReader tmp = new InputStreamReader(con.getInputStream(), "UTF-8");
				BufferedReader reader = new BufferedReader(tmp);
				StringBuffer buffer = new StringBuffer();

				while ((str = reader.readLine()) != null) {
					buffer.append(str);
					receiveMsg = buffer.toString();

				}

				NaMovieVO vo;
				List<NaMovieVO> movieList = new ArrayList<NaMovieVO>();
				try {
					JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("items");
					for (int i = 0; i < jarray.length(); i++) {
						JSONObject jObject = jarray.getJSONObject(i);
						vo = new NaMovieVO(jObject.optString("subtitle"), jObject.optString("link"),
								jObject.optString("image"), jObject.optString("pubDate"), jObject.optString("director"),
								jObject.optString("actor"), jObject.optString("userRating"));
						movieList.add(vo);
						// System.out.println(vo.toString());

					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				// 작업 진행하시오

				VBox[] arrVbox = { vboxBtn01, vboxBtn02, vboxBtn03, vboxBtn04, vboxBtn05 };
				WebView[] arrImage = { wImg01, wImg02, wImg03, wImg04, wImg05 };
				Label[] arrLabel = { sTitle01, sTitle02, sTitle03, sTitle04, sTitle05 };
				Label[] arrAvgLabel = { avgScore1, avgScore2, avgScore3, avgScore4, avgScore5 };
				Label[] arrDirector = { director1, director2, director3, director4, director5 };

				String Jennie = "http://blogfiles.naver.net/MjAxODA2MjhfMzcg/MDAxNTMwMTc2NzI0MzI3.5qgxjUTcLSJiu5hvWT5nqhqd0vOkEAVe9z_hOeoyIt4g.hLiytnDzmvB7T2qXIRErShdRMo2zgfQ6pacfdrzvt20g.JPEG.shflower92/IMG_3624.jpg";
				for (int i = 0; i < movieList.size(); i++) {

					arrVbox[i].setVisible(true);

					if (movieList.get(i).getSubtitle().equals("")) {

						arrLabel[i].setText("제목: 미기입");
					} else {

						arrLabel[i].setText(movieList.get(i).getSubtitle());
					}

					if (movieList.get(i).getImage().equals("")) {
						arrImage[i].getEngine().load(Jennie);

					} else {
						arrImage[i].getEngine().load(movieList.get(i).getImage());
					}

					if (movieList.get(i).getDirector().equals("")) {
						arrDirector[i].setText("감독: 미기입");
					} else {

						arrDirector[i].setText("감독: " + movieList.get(i).getDirector());
					}

					if (movieList.get(i).getUserRating().equals("")) {
						arrAvgLabel[i].setText("평점: 0");
					} else {
						arrAvgLabel[i].setText("평점: " + movieList.get(i).getUserRating());
					}
				}

				for (int i = 0; i < movieList.size(); i++) {
					System.out.println(movieList.get(i).getSubtitle());
					System.out.println(movieList.get(i).getUserRating());
					System.out.println(movieList.get(i).getImage());
					System.out.println(movieList.get(i).getDirector());
				}

				reader.close();
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				br.close();
			}

		} catch (Exception e1) {

			log.debug(e1.getMessage());
		}

	}

	
	
	/** 
	 * 
	 * @category 새로고침
 	 * @author 김주희
	 *@param  e
	 * 
	 */
	public void refreshPage(ActionEvent e) {
		Parent search;
		try {
			search = FXMLLoader.load(getClass().getResource("../view/NaSearchListFX.fxml"));
			Scene scene = new Scene(search);
			Stage primaryStage = (Stage) refreshBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	
	
	/** 
	 * 
	 * @category 메인페이지 이동
 	 * @author 김주희
	 *@param  e
	 * 
	 */
	public void searchPage(ActionEvent e) {
		Parent search;
		try {
			search = FXMLLoader.load(getClass().getResource("../view/SearchFX.fxml"));
			Scene scene = new Scene(search);
			Stage primaryStage = (Stage) search1.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
