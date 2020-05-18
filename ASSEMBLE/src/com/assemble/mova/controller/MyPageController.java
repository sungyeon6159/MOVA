package com.assemble.mova.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.assemble.mova.dao.MovieDao;
import com.assemble.mova.dao.MovieUserDetailDao;
import com.assemble.mova.vo.MovieUserDetailVO;
import com.assemble.mova.vo.MovieVO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class MyPageController implements Initializable {

	static Logger log = Logger.getLogger(MyPageController.class);

	@FXML
	private Button searchBtn;
	@FXML
	private VBox vboxBtn01, vboxBtn02, vboxBtn03, vboxBtn04, vboxBtn05, vboxBtn06;
	@FXML
	private Label sTitle01, sTitle02, sTitle03, sTitle04, sTitle05, sTitle06 ,loginId;
	@FXML
	private ImageView sImg01, sImg02, sImg03, sImg04, sImg05, sImg06;
	@FXML
	private PieChart pieChart;

	@SuppressWarnings({ "unchecked"})
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		loginId.setText(LoginController.memName + " 님");

		VBox[] arrVbox = { vboxBtn01, vboxBtn02, vboxBtn03, vboxBtn04, vboxBtn05, vboxBtn06 };
		Label[] arrLabel = { sTitle01, sTitle02, sTitle03, sTitle04, sTitle05, sTitle06 };
		ImageView[] arrImg = { sImg01, sImg02, sImg03, sImg04, sImg05, sImg06 };

		for (int i = 0; i < arrVbox.length; i++) {
			arrVbox[i].setVisible(false);
		}

		List<MovieUserDetailVO> udList = new ArrayList<MovieUserDetailVO>();
		MovieUserDetailVO mudvo = new MovieUserDetailVO();
		MovieUserDetailDao userDetailDao = new MovieUserDetailDao();

		mudvo = userDetailDao.getInputMoveCode2(LoginController.loginId);
		udList = (List<MovieUserDetailVO>) userDetailDao.do_retrieve2(mudvo);

		List<MovieVO> mvList = new ArrayList<MovieVO>();
		MovieVO mvo = new MovieVO();
		MovieDao movieDao = new MovieDao();
		MovieVO resultMvo = new MovieVO();

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		for (int i = 0; i < 6; i++) {
			mvo = movieDao.getInputMoveCode(udList.get(i).getMovieCode());

			/*
			 * if (udList.get(i).getMovieCode() == null ||
			 * udList.get(i).getMovieCode().equals("")) {
			 * 
			 * break; }
			 */

			resultMvo = (MovieVO) movieDao.do_selectOne(mvo);
			System.out.println("★★★★★★★★★★★★★: " + resultMvo);
			System.out.println("[" + i + "]" + "영화제목:" + resultMvo.getTitle());

			arrVbox[i].setVisible(true);
			arrLabel[i].setText(resultMvo.getTitle());
			arrImg[i].setImage(new Image(resultMvo.getImgPath()));

			String mGenre = resultMvo.getGenre().trim();
			mGenre = mGenre.trim();
			
		

			if (!map.containsKey(mGenre))
				map.put(mGenre, 0);

			map.put(mGenre, map.get(mGenre) + 1);
			
			if(mGenre.equals("") || mGenre.equals(null) || mGenre==null) {
				mGenre="기타";
			}

			System.out.println("액션:"+map.get("액션"));
			System.out.println("멜로:"+map.get("멜로"));
			System.out.println("드라마:"+map.get("드라마"));


		}
		
		
		
		ObservableList<Data> list = FXCollections.observableArrayList(
				
				
				new PieChart.Data("액션", map.get("액션")),
				//new PieChart.Data("기타", map.get("기타")),
				new PieChart.Data("드라마", map.get("드라마")),
				new PieChart.Data("애니메이션", map.get("애니메이션"))
				//new PieChart.Data("멜로", map.get("멜로"))
				
				);
		pieChart.setData(list);

	

	}

	public void searchPage(ActionEvent e) {
		try {
			log.debug("This is Search Page");
			Parent mainHome = FXMLLoader.load(getClass().getResource("../view/SearchFX.fxml"));
			Scene scene = new Scene(mainHome);
			Stage primaryStage = (Stage) searchBtn.getScene().getWindow();
			primaryStage.setScene(scene);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
