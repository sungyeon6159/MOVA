/**
 *<pre>
 * com.assemble.mova.controller
 * Class Name : SearchListController.java
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

import java.io.IOException;
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
 *    |_ SearchListController.java
 * 
 * </pre>
 * 
 * @date : 2019. 12. 11. 오후 4:23:19
 * @version :
 * @author : 박성연
 */
public class SearchListController implements Initializable {

	@FXML
	private Button listBtn, dBtn1, dBtn2, dBtn3, dBtn4, dBtn5, search1;

	@FXML
	private ImageView sImg01, sImg02, sImg03, sImg04, sImg05;

	@FXML
	private VBox vboxBtn01, vboxBtn02, vboxBtn03, vboxBtn04, vboxBtn05;

	@FXML
	private TextField searchTitle;

	@FXML
	private Label sTitle01, sTitle02, sTitle03, sTitle04, sTitle05, loginId;

	static Logger log = Logger.getLogger(SearchListController.class);
	public static String movieCode;

	MovieDetailDao detailDao = new MovieDetailDao();
	MovieDetailVO detailVO = new MovieDetailVO();
	// 유저 상세보기 DAO 선언
	MovieUserDetailDao userDetailDao = new MovieUserDetailDao();
	MovieUserDetailVO userDetailVO = new MovieUserDetailVO();

	// 데이터 검색하여 List 담아 가져오기위함.
	List<MovieVO> list = new ArrayList<MovieVO>();
	MovieSearchListDao searchDao = new MovieSearchListDao();

	/**
	 * initialize
	 * 초기화, 검색 후 읽어온 데이터를 list.size 사이즈 만큼 보여줌.
	 * @author 박성연
	 * @param location, resources
	 * @return void
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginId.setText(LoginController.memName + " 님");

		MovieVO retrieveVO = searchDao.getSeachData(SearchController.searchWord);
		list = (List<MovieVO>) searchDao.do_retrieve(retrieveVO);
		VBox[] arrVbox = { vboxBtn01, vboxBtn02, vboxBtn03, vboxBtn04, vboxBtn05 };
		ImageView[] arrImage = { sImg01, sImg02, sImg03, sImg04, sImg05 };
		Label[] arrLabel = { sTitle01, sTitle02, sTitle03, sTitle04, sTitle05 };
		Button[] arrBtn = { dBtn1, dBtn2, dBtn3, dBtn4, dBtn5 };

		for (int i = 0; i < arrVbox.length; i++) {
			arrVbox[i].setVisible(false);
		}

		for (int i = 0; i < list.size(); i++) {
			if (!(list.get(i).getTitle().equals("") || (list.get(i)).getTitle().equals(null))) {
				arrVbox[i].setVisible(true);
				arrLabel[i].setText(list.get(i).getTitle());
				arrBtn[i].setText(list.get(i).getTitle() + "상세정보");
				arrImage[i].setImage(new Image(list.get(i).getImgPath()));
			}

		}

	}

	/**
	 * searchPage
	 * 검색 화면으로 되돌아가기 버튼.
	 * @author 박성연
	 * @param event
	 * @return void
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

	/**
	 * onePage
	 * 상세페이지 버튼1
	 * 데이터 비교 후 존재 하지 않는 데이터 새로 생성하기 (4 page). 
	 * @author 박성연
	 * @param event
	 * @return void
	 */
	public void onePage(ActionEvent e) {

		try {
			// 이 키값이 DetailPage로 넘어가야된다
			movieCode = list.get(0).getMovieCode();
			log.debug("검색된 무비코드" + movieCode);

			// movie_user_detail_info.csv에 데이터 없을 경우 초기데이터 생성
			MovieDetailVO dVO = detailDao.getMovieInputData(detailVO);
			if (detailDao.isMemberExists(dVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				detailDao.do_save(dVO);
				log.debug("데이터가 추가되었습니다");
			}

			// movie_user_detail_info.csv에 데이터가 없을 경우 초기데이터 생성
			MovieUserDetailVO udVO = userDetailDao.getMovieIDInputData(userDetailVO);
			if (userDetailDao.isMemberExists(udVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				userDetailDao.do_save(udVO);
				log.debug("데이터가 추가되었습니다");
			}

			Parent search = FXMLLoader.load(getClass().getResource("../view/DetailPageFX.fxml"));
			Scene scene = new Scene(search);
			Stage primaryStage = (Stage) dBtn1.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	/**
	 * twoPage
	 * 상세페이지 버튼2
	 * 데이터 비교 후 존재 하지 않는 데이터 새로 생성하기 (4 page). 
	 * @author 박성연
	 * @param event
	 * @return void
	 */
	public void twoPage(ActionEvent e) {

		try {
			

			movieCode = list.get(1).getMovieCode();
			log.debug("검색된 무비코드" + movieCode);

		
			MovieDetailVO dVO = detailDao.getMovieInputData(detailVO);
			if (detailDao.isMemberExists(dVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				detailDao.do_save(dVO);
				log.debug("데이터가 추가되었습니다");
			}

		
			MovieUserDetailVO udVO = userDetailDao.getMovieIDInputData(userDetailVO);
			if (userDetailDao.isMemberExists(udVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				userDetailDao.do_save(udVO);
				log.debug("데이터가 추가되었습니다");
			}

			Parent search = FXMLLoader.load(getClass().getResource("../view/DetailPageFX.fxml"));
			Scene scene = new Scene(search);
			Stage primaryStage = (Stage) dBtn2.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	/**
	 * threePage
	 * 상세페이지 버튼3
	 * 데이터 비교 후 존재 하지 않는 데이터 새로 생성하기 (4 page). 
	 * @author 박성연
	 * @param event
	 * @return void
	 */
	public void threePage(ActionEvent e) {

		try {
			
			movieCode = list.get(2).getMovieCode();
			log.debug("검색된 무비코드" + movieCode);

		
			MovieDetailVO dVO = detailDao.getMovieInputData(detailVO);
			if (detailDao.isMemberExists(dVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				detailDao.do_save(dVO);
				log.debug("데이터가 추가되었습니다");
			}

		
			MovieUserDetailVO udVO = userDetailDao.getMovieIDInputData(userDetailVO);
			if (userDetailDao.isMemberExists(udVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				userDetailDao.do_save(udVO);
				log.debug("데이터가 추가되었습니다");
			}

			Parent search = FXMLLoader.load(getClass().getResource("../view/DetailPageFX.fxml"));
			Scene scene = new Scene(search);
			Stage primaryStage = (Stage) dBtn3.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	/**
	 * fourPage
	 * 상세페이지 버튼4
	 * 데이터 비교 후 존재 하지 않는 데이터 새로 생성하기 (4 page). 
	 * @author 박성연
	 * @param event
	 * @return void
	 */
	public void fourPage(ActionEvent e) {

		try {
			
			movieCode = list.get(3).getMovieCode();
			log.debug("검색된 무비코드" + movieCode);

		
			MovieDetailVO dVO = detailDao.getMovieInputData(detailVO);
			if (detailDao.isMemberExists(dVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				detailDao.do_save(dVO);
				log.debug("데이터가 추가되었습니다");
			}

		
			MovieUserDetailVO udVO = userDetailDao.getMovieIDInputData(userDetailVO);
			if (userDetailDao.isMemberExists(udVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				userDetailDao.do_save(udVO);
				log.debug("데이터가 추가되었습니다");
			}

			Parent search = FXMLLoader.load(getClass().getResource("../view/DetailPageFX.fxml"));
			Scene scene = new Scene(search);
			Stage primaryStage = (Stage) dBtn4.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	/**
	 * fivePage
	 * 상세페이지 버튼5
	 * 데이터 비교 후 존재 하지 않는 데이터 새로 생성하기 (5 page). 
	 * @author 박성연
	 * @param e
	 * @return void
	 */
	public void fivePage(ActionEvent e) {

		try {

			
			movieCode = list.get(4).getMovieCode();
			log.debug("검색된 무비코드" + movieCode);

			
			MovieDetailVO dVO = detailDao.getMovieInputData(detailVO);
			if (detailDao.isMemberExists(dVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				detailDao.do_save(dVO);
				log.debug("데이터가 추가되었습니다");
			}

			
			MovieUserDetailVO udVO = userDetailDao.getMovieIDInputData(userDetailVO);
			if (userDetailDao.isMemberExists(udVO) == true) {
				log.debug("존재하는 MovieCode입니다.");
			} else {
				userDetailDao.do_save(udVO);
				log.debug("데이터가 추가되었습니다");
			}

			Parent search = FXMLLoader.load(getClass().getResource("../view/DetailPageFX.fxml"));
			Scene scene = new Scene(search);
			Stage primaryStage = (Stage) dBtn5.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
