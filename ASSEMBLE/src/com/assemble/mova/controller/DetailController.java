
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.assemble.mova.dao.MovieDao;
import com.assemble.mova.dao.MovieDetailDao;
import com.assemble.mova.dao.MovieUserDetailDao;
import com.assemble.mova.vo.MovieDetailVO;
import com.assemble.mova.vo.MovieUserDetailVO;
import com.assemble.mova.vo.MovieVO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/** 
 * @category 영화 상세보기
 * @author 김주희
 */

public class DetailController implements Initializable {
	static Logger log = Logger.getLogger(DetailController.class);
	@FXML
	private RadioButton rbtn1, rbtn2, rbtn3, rbtn4, rbtn5;
	@FXML
	private Button upBtn, loginBtn, searchBtn, refreshBtn, moreBtn;
	@FXML
	private TextArea writeReview, story, bestReview;
	@FXML
	private BarChart<?, ?> StartChart;
	@FXML
	private Label likeTotal, choiceStart, title, cast, genre, year, scoreAvg, loginId;
	@FXML
	private TextArea infoStory;
	@FXML
	private ImageView imgView;

	// 영화 기본정보 DAO 선언
	MovieDao movieDao = new MovieDao();
	// 상세보기 DAO 선언
	MovieDetailDao detailDao = new MovieDetailDao();
	// 유저 상세보기 DAO 선언
	MovieUserDetailDao userDetailDao = new MovieUserDetailDao();

	// movie_info.csv에서 필요한 정보 읽어오기
	MovieVO mVO = movieDao.getInputMoveCode(SearchListController.movieCode);
	MovieVO rmVO = (MovieVO) movieDao.do_selectOne(mVO);

	// movie_detail_Info.csv에서 필요한 정보 읽어오기
	MovieDetailVO dVO = detailDao.getInputMoveCode(SearchListController.movieCode);
	MovieDetailVO rdVO = (MovieDetailVO) detailDao.do_selectOne(dVO);

	// movie_user_detail_info.csv에서 필요한 정보 읽어오기
	MovieUserDetailVO udVO = userDetailDao.getInputMoveCode(SearchListController.movieCode, LoginController.loginId);
	MovieUserDetailVO rudVO = (MovieUserDetailVO) userDetailDao.do_selectOne(udVO);

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		log.debug("현재 접속중인 ID" + LoginController.loginId);
		log.debug("현재 선택된 MovieCode" + SearchListController.movieCode);

		// System.out.println(udVO);
		loginId.setText(LoginController.memName + " 님");

		// 영화 기본정보 읽어오기
		title.setText(rmVO.getTitle());
		cast.setText(rmVO.getCast());
		genre.setText(rmVO.getGenre());
		story.setText(rmVO.getStory());
		year.setText(rmVO.getYear());
		imgView.setImage(new Image(rmVO.getImgPath()));

		// 평균 별점 읽어오기
		scoreAvg.setText(rdVO.getAvgScore() + " 점");

		// var chart 읽어오기
		@SuppressWarnings("rawtypes")
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("별점");
		series1.setData(FXCollections.observableArrayList(new XYChart.Data<String, Integer>("1점", rdVO.getOneScore()),
				new XYChart.Data<String, Integer>("2점", rdVO.getTwoScore()),
				new XYChart.Data<String, Integer>("3점", rdVO.getThreeScore()),
				new XYChart.Data<String, Integer>("4점", rdVO.getFourScore()),
				new XYChart.Data<String, Integer>("5점", rdVO.getFiveScore())));
		StartChart.getData().add(series1);

		// 현재 좋아요 총점 보여주기
		likeTotal.setText(Integer.toString(rdVO.getTotalLike()));

		// 임의로 베스트 리뷰.
		try {
			bestReview.setText(rudVO.getMyReview().replaceAll("(\r\n|\r|\n|\n\r)", " "));
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 만약 로그인 후 userDtailInfo.csv에 데이터가 없다면 기본값으로 ID, MovieCode 입력 후 나머지는 디폴트값으로 데이터
		// 저장한다

	}
	
	

	
		
	/** 
	 * 
	 * @category 리뷰저장 기능
	 * @author 김주희
	 * @param  e 
	 * 리뷰작성 후 CSV 파일로 작성자 ID 및 리뷰내용 저장액션
	 * 
	 */

	public void SaveReview(ActionEvent e) throws IOException {

		// 리뷰 작성 후 작성완료 알림 창 및 n 값을 y값으로 업데이트, 리뷰내용 업데이트
		if ((rudVO.getReviewYN()).equals("n")) {
			rudVO = userDetailDao.getUdateReview(rudVO, writeReview.getText().replaceAll("(\r\n|\r|\n|\n\r)", " "));

			if (writeReview.getText().equals("") || writeReview.getText().equals(null)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("리뷰 작성 실패 ");
				alert.setHeaderText(null);
				alert.setContentText("리뷰작성를 다시 작성하세요");
				alert.show();
			} else {
				userDetailDao.do_update(rudVO);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("리뷰 작성 완료 ");
				alert.setHeaderText(null);
				alert.setContentText("리뷰작성을 완료하였습니다.");
				alert.show();
			}

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("리뷰 작성 실패 ");
			alert.setHeaderText(null);
			alert.setContentText("이미 리뷰를 작성하였습니다");
			alert.show();
		}

	}
	
	
	/** 
	 * @category 업버튼 기능
	 * @author 김주희
	 * @param e
	 * 업버튼 클릭 시 CSV 파일에 누적 갯수 저장
	 *
	 */

	public void ClickUpButton(ActionEvent e) {
		// 좋아요 버튼 클릭 시, 총갯수가 1증가 또한 likeYN 값을 y로 변경

		if ((rudVO.getLikeYN()).equals("n")) {
			// 업버튼 숫자 업데이트, 업버튼의 현재 갯수 로드

			rudVO = userDetailDao.getUdateLikeYN(rudVO);
			userDetailDao.do_update(rudVO);

			rdVO = detailDao.getUpdateLikeTotal(rdVO, rdVO.getTotalLike() + 1);
			detailDao.do_update(rdVO);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("업버튼 클릭");
			alert.setHeaderText(null);
			alert.setContentText("업버튼 완료!");
			alert.show();

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("업버튼 클릭");
			alert.setHeaderText(null);
			alert.setContentText("이미 업버튼을 눌렀습니다");
			alert.show();
		}

	}

	int ChoiceStarScore = 1;
	boolean starBtnFlag = true;

	
	
	/** 
	 * @category 별점 저장 기능
	 * @author 김주희
	 * @param e
	 * 별점 선택 시 해당 별점을 숫자로 환산하여, CSV에 저장
	 *
	 */
	public void SaveStarScore(ActionEvent e) {

		if ((rudVO.getScoreYN()).equals("n")) {

			if (rbtn1.isSelected()) {

				int userCount = rdVO.getScoreUserCount() + 1;
				double count = userCount;
				int scoreTotal = rdVO.getTotalScore() + ChoiceStarScore;
				double total = scoreTotal;

				// 소수 2자리까지 표현
				double avg = (total / count);
				double per = Double.parseDouble(String.format("%.2f", avg));
				String lAvg = Double.toString(per);

				int score = rdVO.getOneScore() + 1;

				// 1. 별점을 선택한 사용자의 수, 2. 총별점, 3. 평점, 4 1점 선택
				rdVO = detailDao.getUpdateScore1(rdVO, userCount, scoreTotal, lAvg, score);
				detailDao.do_update(rdVO);

				// ScoreYN 값 변경
				rudVO = userDetailDao.getUpdateScoreYN(rudVO);
				userDetailDao.do_update(rudVO);

				// 내가 선택한 별점 업데이트
				rudVO = userDetailDao.getUpdateChoiceStar(rudVO, ChoiceStarScore);
				userDetailDao.do_update(rudVO);
				choiceStart.setText((Integer.toString(ChoiceStarScore)) + "점을 선택");

			} else if (rbtn2.isSelected()) {
				ChoiceStarScore = 2;

				int userCount = rdVO.getScoreUserCount() + 1;
				double count = userCount;
				int scoreTotal = rdVO.getTotalScore() + ChoiceStarScore;
				double total = scoreTotal;

				// 소수 2자리까지 표현
				double avg = (total / count);
				double per = Double.parseDouble(String.format("%.2f", avg));
				String lAvg = Double.toString(per);

				int score = rdVO.getTwoScore() + 1;

				// 1. 별점을 선택한 사용자의 수, 2. 총별점, 3. 평점, 4 1점 선택
				rdVO = detailDao.getUpdateScore2(rdVO, userCount, scoreTotal, lAvg, score);
				detailDao.do_update(rdVO);

				// ScoreYN 값 변경
				rudVO = userDetailDao.getUpdateScoreYN(rudVO);
				userDetailDao.do_update(rudVO);

				// 내가 선택한 별점 업데이트
				rudVO = userDetailDao.getUpdateChoiceStar(rudVO, ChoiceStarScore);
				userDetailDao.do_update(rudVO);
				choiceStart.setText((Integer.toString(ChoiceStarScore)) + "점을 선택");

			} else if (rbtn3.isSelected()) {
				ChoiceStarScore = 3;

				int userCount = rdVO.getScoreUserCount() + 1;
				double count = userCount;
				int scoreTotal = rdVO.getTotalScore() + ChoiceStarScore;
				double total = scoreTotal;

				// 소수 2자리까지 표현
				double avg = (total / count);
				double per = Double.parseDouble(String.format("%.2f", avg));
				String lAvg = Double.toString(per);

				int score = rdVO.getThreeScore() + 1;

				// 1. 별점을 선택한 사용자의 수, 2. 총별점, 3. 평점, 4 1점 선택
				rdVO = detailDao.getUpdateScore3(rdVO, userCount, scoreTotal, lAvg, score);
				detailDao.do_update(rdVO);

				// ScoreYN 값 변경
				rudVO = userDetailDao.getUpdateScoreYN(rudVO);
				userDetailDao.do_update(rudVO);

				// 내가 선택한 별점 업데이트
				rudVO = userDetailDao.getUpdateChoiceStar(rudVO, ChoiceStarScore);
				userDetailDao.do_update(rudVO);
				choiceStart.setText((Integer.toString(ChoiceStarScore)) + "점을 선택");

			} else if (rbtn4.isSelected()) {
				ChoiceStarScore = 4;

				int userCount = rdVO.getScoreUserCount() + 1;
				double count = userCount;
				int scoreTotal = rdVO.getTotalScore() + ChoiceStarScore;
				double total = scoreTotal;

				// 소수 2자리까지 표현
				double avg = (total / count);
				double per = Double.parseDouble(String.format("%.2f", avg));
				String lAvg = Double.toString(per);

				int score = rdVO.getFourScore() + 1;

				// 1. 별점을 선택한 사용자의 수, 2. 총별점, 3. 평점, 4 1점 선택
				rdVO = detailDao.getUpdateScore4(rdVO, userCount, scoreTotal, lAvg, score);
				detailDao.do_update(rdVO);

				// ScoreYN 값 변경
				rudVO = userDetailDao.getUpdateScoreYN(rudVO);
				userDetailDao.do_update(rudVO);

				// 내가 선택한 별점 업데이트
				rudVO = userDetailDao.getUpdateChoiceStar(rudVO, ChoiceStarScore);
				userDetailDao.do_update(rudVO);
				choiceStart.setText((Integer.toString(ChoiceStarScore)) + "점을 선택");

			} else if (rbtn5.isSelected()) {
				ChoiceStarScore = 5;

				int userCount = rdVO.getScoreUserCount() + 1;
				double count = userCount;
				int scoreTotal = rdVO.getTotalScore() + ChoiceStarScore;
				double total = scoreTotal;

				// 소수 2자리까지 표현
				double avg = (total / count);
				double per = Double.parseDouble(String.format("%.2f", avg));
				String lAvg = Double.toString(per);

				int score = rdVO.getFiveScore() + 1;

				// 1. 별점을 선택한 사용자의 수, 2. 총별점, 3. 평점, 4 1점 선택
				rdVO = detailDao.getUpdateScore5(rdVO, userCount, scoreTotal, lAvg, score);
				detailDao.do_update(rdVO);

				// ScoreYN 값 변경
				rudVO = userDetailDao.getUpdateScoreYN(rudVO);
				userDetailDao.do_update(rudVO);

				// 내가 선택한 별점 업데이트
				rudVO = userDetailDao.getUpdateChoiceStar(rudVO, ChoiceStarScore);
				userDetailDao.do_update(rudVO);

				choiceStart.setText((Integer.toString(ChoiceStarScore)) + "점을 선택");
			}

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("별점 선택");
			alert.setHeaderText(null);
			alert.setContentText("이미 별점을 선택하셨습니다.");
			alert.show();

			choiceStart.setText(rudVO.getChoiceScore() + "점을 선택");
		}

	}


	
	
	/** 
	 * @category 메인페이지 이동
	 * @author 김주희
	 * @param e
	 *
	 */
	public void searchPage(ActionEvent e) {

		try {
			log.debug("This is Search Page");
			Parent refresh = FXMLLoader.load(getClass().getResource("../view/SearchFX.fxml"));
			Scene scene = new Scene(refresh);
			Stage primaryStage = (Stage) searchBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/** 
	 * @category 로그인페이지 이동
	 * @author 김주희
	 * @param e
	 *
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
	 * @category 로그인페이지 이동
	 * @author 김주희
	 * @param e
	 *
	 */
	public void refreshPage(ActionEvent e) {
		try {
			log.debug("This is Refresh Page");
			Parent refreshPage = FXMLLoader.load(getClass().getResource("../view/DetailPageFX.fxml"));
			Scene scene = new Scene(refreshPage);
			Stage primaryStage = (Stage) refreshBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	/** 
	 * @category 새로기침
	 * @author 김주희
	 * @param e
	 *
	 */
	public void moreReview(ActionEvent e) {
		try {
			log.debug("This is More Review Page");
			Parent refreshPage = FXMLLoader.load(getClass().getResource("../view/MoreReviewFX.fxml"));
			Scene scene = new Scene(refreshPage);
			Stage primaryStage = (Stage) moreBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
