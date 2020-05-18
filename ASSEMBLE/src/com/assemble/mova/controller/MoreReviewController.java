/**
 *<pre>
 * com.assemble.mova.controller
 * Class Name : MoreReviewController.java
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
import com.assemble.mova.dao.MovieMoreReviewDao;
import com.assemble.mova.vo.MovieUserDetailVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** 
 * @category 영화 리뷰 더 보기
 * @author 김주희
 */

public class MoreReviewController implements Initializable {

	static Logger log = Logger.getLogger(MoreReviewController.class);

	@FXML
	private Button detailBtn;

	@FXML
	private VBox vboxBtn1, vboxBtn2, vboxBtn3, vboxBtn4, vboxBtn5, vboxBtn6;

	@FXML
	private Label idLabel1, idLabel2, idLabel3, idLabel4, idLabel5, idLabel6, loginId ;
	
	@FXML
	private Label choiceBtn1,choiceBtn2,choiceBtn3,choiceBtn4,choiceBtn5,choiceBtn6;

	@FXML
	private TextArea textArea1, textArea2, textArea3, textArea4, textArea5, textArea6;

	// 유저 상세보기 DAO 선언

	List<MovieUserDetailVO> list = new ArrayList<MovieUserDetailVO>();
	MovieMoreReviewDao moreReviewDao = new MovieMoreReviewDao();

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loginId.setText(LoginController.memName+ " 님");

		VBox[] arrayVbox = { vboxBtn1, vboxBtn2, vboxBtn3, vboxBtn4, vboxBtn5, vboxBtn6 };

		for (int i = 0; i < arrayVbox.length; i++) {
			arrayVbox[i].setVisible(true);
		}

		list = (List<MovieUserDetailVO>) moreReviewDao.do_retrieve2();
		VBox[] arrVbox = { vboxBtn1, vboxBtn2, vboxBtn3, vboxBtn4, vboxBtn5, vboxBtn6 };
		Label[] arrLabel = { idLabel1, idLabel2, idLabel3, idLabel4, idLabel5, idLabel6 };
		Label[] arrChoiceLabel = { choiceBtn1, choiceBtn2, choiceBtn3, choiceBtn4, choiceBtn5, choiceBtn6 };
		TextArea[] arrTextArea = { textArea1, textArea2, textArea3, textArea4, textArea5, textArea6 };

		for (int i = 0; i < list.size(); i++) {

			if (!list.isEmpty()) {
				arrVbox[i].setVisible(true);
				arrLabel[i].setText(list.get(i).getUserid() + " 님의 리뷰");
				arrTextArea[i].setText(list.get(i).getMyReview().trim());
				arrChoiceLabel[i].setText("선택한 별점: " + list.get(i).getChoiceScore());
			} else {
				arrVbox[i].getChildren().clear();
			}
		}
	}

	public void detailPage(ActionEvent e) {
		try {
			log.debug("This is Detail Page");
			Parent refreshPage = FXMLLoader.load(getClass().getResource("../view/DetailPageFX.fxml"));
			Scene scene = new Scene(refreshPage);
			Stage primaryStage = (Stage) detailBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
