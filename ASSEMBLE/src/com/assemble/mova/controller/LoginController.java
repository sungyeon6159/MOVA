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
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.sist.hr.cmn.HROraConnectionMaker;
import com.sist.hr.member.dao.MemberDao;
import com.sist.hr.member.domain.MemberVO;

//import com.assemble.mova.dao.MemberDao;
//import com.assemble.mova.vo.MemberVO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * <pre>
 * com.assemble.mova.controller
 * 		LoginController.java
 * 
 * </pre>
 * @date : 2019. 12. 11. 오후 4:23:19
 * @version : 
 * @author : 김민지
 */
public class LoginController implements Initializable {
	private final Logger LOG = Logger.getLogger(this.getClass());	
	
	public static String loginId;//넘겨줄 ID값
	public static String memName;//넘겨줄 멤버 이름값

	@FXML // 그 다음에 오는 멤버변수, 매서드의 소스코드에 추가적인 데이터를 주입해주는 역할
	// 간단하게 FXML 문서의 각각의 컨트롤과 매칭될 수 있도록 데이터를 부여해준다는 것을 이해
	private TextField txtId;
	@FXML
	private PasswordField txtPw;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnJoin;

	Alert alert = new Alert(AlertType.INFORMATION);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.setOnAction(event -> clickLoinBtn(event));
		btnJoin.setOnAction(event -> clickJoinBtn(event));

	}

	 /**
	    * clickLoinBtn
	    * 아이디와 비밀번호를 받아서 회원정보와 비교하여 로그인 or 실패
	    * @author 김민지
	    * @param e
	    * @return void
	    */
	public void clickLoinBtn(ActionEvent e) {// 아이디/비번 입력받아 로그인 하는 창
		String InputId = txtId.getText().trim();
		String InputPw = txtPw.getText().trim();

		MemberDao dao = new MemberDao(new HROraConnectionMaker());

		// ID 입력 Validation
		if (null == InputId || "".equals(InputId)) {
			alert.setTitle("로그인 알림창");
			alert.setHeaderText(null);
			alert.setContentText("아이디를 입력 하세요.");
			alert.showAndWait();

			txtId.requestFocus();// 커서를 이곳으로
			return;
		}
		// 비번 Validation
		if (null == InputPw || "".equals(InputPw)) {
			alert.setTitle("로그인 알림창");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호를 입력 하세요.");
			alert.showAndWait();
			txtPw.requestFocus();
			return;
		}

		MemberVO inVO = new MemberVO();
		inVO.setGrpDiv("4");// 조구분
		inVO.setMemId(InputId);// ID
		inVO.setPw(InputPw);// 비번

		LOG.debug("----------------------------");
		LOG.debug("기존 Mova Member: "+dao);
		LOG.debug("입력한 Id, Pw: "+inVO);
		LOG.debug("----------------------------");
		
		
		// Login
		MemberVO outVO = dao.do_login(inVO);
		
		if(outVO.getMessageDiv().equals("11")) {
			memName = outVO.getName();
			LOG.debug("로그인 성공.");
			alert.setTitle("로그인 알림창");
			alert.setHeaderText(null);
			alert.setContentText("[ "+memName+" ]"+"님 환영합니다!");
			alert.show();
			
			loginId = InputId;
		
			LOG.debug("로그인된 ID: "+loginId);
			
			String level = outVO.getAuth(); //권한
			//LOG.debug(level);
			switch(level) {
				case "1": //일반 사용자 
					try {//페이지 불러오기 (검색페이지)
						AnchorPane searchPage = FXMLLoader.load(getClass().getResource("../view/SearchFX.fxml"));
						Scene sceneSearch = new Scene(searchPage);
						Stage searchStage = (Stage) btnJoin.getScene().getWindow(); // 현재 윈도우 가져오기
						searchStage.setTitle("MOVA 영화를 검색하세요!");
						searchStage.setScene(sceneSearch);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
				case "9": //관리자
					try {//관리자 페이지 불러오기
						AnchorPane adminPage = FXMLLoader.load(getClass().getResource("../view/AdminPageFX.fxml"));
						Scene sceneAdmin = new Scene(adminPage);
						Stage adminStage = (Stage) btnJoin.getScene().getWindow(); // 현재 윈도우 가져오기
						adminStage.setTitle("MOVA 회원가입");
						adminStage.setScene(sceneAdmin);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
			}
			
		}else {
			LOG.debug("로그인 실패.");
			alert.setTitle("로그인 알림창");
			alert.setHeaderText(null);
			alert.setContentText("로그인 실패");
			alert.show();
		}
	}

	/**
	    * clickJoinBtn
	    * 회원가입 창 불러오는 버튼
	    * @author 김민지
	    * @param e
	    * @return void
	    */
	public void clickJoinBtn(ActionEvent e) {// 회원가입 창으로 넘어갈 메소드
		try {
			AnchorPane joinPage = FXMLLoader.load(getClass().getResource("../view/JoinFX.fxml"));
			Scene sceneJoin = new Scene(joinPage);
			Stage loginStage = (Stage) btnJoin.getScene().getWindow(); // 현재 윈도우 가져오기
			loginStage.setTitle("MOVA 회원가입");
			loginStage.setScene(sceneJoin);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
}
