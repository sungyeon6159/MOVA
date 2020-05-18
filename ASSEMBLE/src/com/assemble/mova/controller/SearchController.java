/**
 *<pre>
 * com.assemble.mova.controller
 * Class Name : LoginController.java
 * Description : 
 * Modification Information
 * 
 *   수정일      수정자              수정내용
 *  ---------   ---------   -------------------------------
 *  2019-12-11           최초생성
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import com.assemble.mova.dao.MovieDetailDao;
import com.assemble.mova.dao.MovieSearchListDao;
import com.assemble.mova.dao.MovieUserDetailDao;
import com.assemble.mova.vo.MovieDetailVO;
import com.assemble.mova.vo.MovieUserDetailVO;
import com.assemble.mova.vo.MovieVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <pre>
 * com.assemble.mova.controller
 *    |_ SearchController.java
 * 
 * </pre>
 * 
 * @date : 2019. 12. 11. 오후 4:23:19
 * @version :
 * @author : 박성연
 */

public class SearchController implements Initializable {
	static Logger log = Logger.getLogger(SearchController.class);
	@FXML
	private Button searchBtn, loginBtn, naSearchBtn, myPageBtn;

	@FXML
	private TextField searchTitle;

	@FXML
	private Label loginId;

	static String searchWord;

	/**
	 * initialize
	 * 로그인 맴버이름 read.
	 * @author 박성연
	 * @param location, resources
	 * @return void
	 */
	public void initialize(URL location, ResourceBundle resources) {
		loginId.setText(LoginController.memName + " 님");
	}

	/**
	 * searchListPage
	 * 입력받은 검색어와 데이터 비교.
	 * @author 박성연
	 * @param event
	 * @return void
	 */
	public void searchListPage(ActionEvent e) {
		MovieSearchListDao searchDao = new MovieSearchListDao();
		MovieVO retrieveVO = searchDao.getSeachData(searchTitle.getText());
		List<MovieVO> list = new ArrayList<MovieVO>();
		list = (List<MovieVO>) searchDao.do_retrieve(retrieveVO);

		for (int i = 0; i < list.size(); i++) {

			if (!(list.get(i).getTitle().matches(".*" + searchTitle.getText() + ".*"))
					|| searchTitle.getText().equals("")) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("검색어");
				alert.setHeaderText(null);
				alert.setContentText("검색어를 다시 입력하세요");
				alert.show();
				log.debug("검색어를 다시 입력하세요");
				break;

			} else {
				try {
					searchWord = searchTitle.getText();
					log.debug("This is Search List Page");
					Parent mainHome = FXMLLoader.load(getClass().getResource("../view/SearchListFX.fxml"));
					Scene scene = new Scene(mainHome);
					Stage primaryStage = (Stage) searchBtn.getScene().getWindow();
					primaryStage.setScene(scene);
					break;

				} catch (Exception e1) {
					e1.printStackTrace();
					log.debug(e1.getMessage());
				}
			}

		}

	}

	/**
	 * naSearchPage
	 * 네이버 영화 검색API 페이지로 이동.
	 * @author 박성연
	 * @param event
	 * @return void
	 */
	public void naSearchPage(ActionEvent e) {
		try {
			log.debug("This is naver Movie Search Page");
			Parent myHomePage = FXMLLoader.load(getClass().getResource("../view/NaSearchListFX.fxml"));
			Scene scene = new Scene(myHomePage);
			Stage primaryStage = (Stage) naSearchBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * loginPage
	 * 로그아웃 페이지로 이동.
	 * @author 박성연
	 * @param event
	 * @return void
	 */
	public void loginPage(ActionEvent e) {
		try {
			log.debug("This is my page");
			Parent myHomePage = FXMLLoader.load(getClass().getResource("../view/LoginFX.fxml"));
			Scene scene = new Scene(myHomePage);
			Stage primaryStage = (Stage) loginBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * myPage
	 * 마이페이지로 이동.
	 * @author 박성연
	 * @param event
	 * @return void
	 */
	public void myPage(ActionEvent e) {
		try {
			log.debug("This is my page");
			Parent myHomePage = FXMLLoader.load(getClass().getResource("../view/MyPageFX.fxml"));
			Scene scene = new Scene(myHomePage);
			Stage primaryStage = (Stage) myPageBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
