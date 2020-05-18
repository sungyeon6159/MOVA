/**
 *<pre>
 * com.assemble.mova.controller
 * Class Name : JoinController.java
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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * <pre>
 * com.assemble.mova.controller
 * 		JoinController.java
 * 
 * </pre>
 * @date : 2019. 12. 11. 오후 4:23:19
 * @version : 
 * @author : 김민지
 */
public class JoinController implements Initializable {
	private final Logger LOG = Logger.getLogger(this.getClass());
	
	@FXML
	private Button checkIdBtn;
	@FXML
	private Button preLoginBtn;
	@FXML
	private Button joinBtn;
	@FXML
	private TextField joinId;
	@FXML
	private TextField joinName;
	@FXML
	private TextField joinEmail;
	@FXML
	private PasswordField joinPw;
	@FXML
	private PasswordField joinPwCheck;
	@FXML
	private Label labelEmail;
	@FXML
	private Label labelPw;
	@FXML
	private Label labelPwCheck;
	@FXML
	private MenuButton menuBtn;
	@FXML
	private MenuItem koreanMenu;
	@FXML
	private MenuItem englishMenu;
	@FXML
	private Text text01;
	@FXML
	private Label title;

	// 중복확인 버튼 클릭했는지 확인하는 값
	private boolean idBtnCheck;
	// 아이디 중복확인 값
	private boolean idCheck;
	// 선택된 언어
	private String checkLang = "한국어";

	Properties props = new Properties();
	// 파일 path
	private String profFile = "src\\file\\lableJoin_kor_KOREA.properties";

	Alert alert = new Alert(AlertType.INFORMATION);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkIdBtn.setOnAction(event -> clickCheckIdBtn(event));
		joinBtn.setOnAction(event -> clickJoinBtn01(event));
		preLoginBtn.setOnAction(event -> clickPreLoginBtn(event));
		koreanMenu.setOnAction(event -> clickKorean(event));
		englishMenu.setOnAction(event -> clickEnglish(event));
	}

	/**
	    * clickKorean
	    * 다국어 지원 중 한국어 선택
	    * @author 김민지
	    * @param e
	    * @return void
	    */
	public void clickKorean(ActionEvent e) {// 한국어 선택
		// 파일 path
		profFile = "src\\file\\lableJoin_kor_KOREA.properties";

		BufferedInputStream bis = null;
		try {
			FileInputStream fis = new FileInputStream(profFile);
			bis = new BufferedInputStream(fis);
			props.load(bis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		title.setText(props.getProperty("title"));
		joinId.setPromptText(props.getProperty("joinId"));
		joinName.setPromptText(props.getProperty("joinName"));
		joinEmail.setPromptText(props.getProperty("joinEmail"));
		joinPw.setPromptText(props.getProperty("joinPw"));
		joinPwCheck.setPromptText(props.getProperty("joinPwCheck"));
		checkIdBtn.setText(props.getProperty("checkIdBtn"));
		preLoginBtn.setText(props.getProperty("preLoginBtn"));
		joinBtn.setText(props.getProperty("joinBtn"));
		menuBtn.setText(props.getProperty("menuBtn"));
		text01.setText(props.getProperty("text01"));

		checkLang = "한국어";
	}

	/**
	    * clickEnglish
	    * 다국어 지원 중 영어 선택
	    * @author 김민지
	    * @param e
	    * @return void
	    */
	public void clickEnglish(ActionEvent e) {// 영어 선택
		// 파일 path
		profFile = "src\\file\\lableJoin_en_KOREA.properties";

		BufferedInputStream bis = null;
		try {
			FileInputStream fis = new FileInputStream(profFile);
			bis = new BufferedInputStream(fis);
			props.load(bis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		title.setText(props.getProperty("title"));
		joinId.setPromptText(props.getProperty("joinId"));
		joinName.setPromptText(props.getProperty("joinName"));
		joinEmail.setPromptText(props.getProperty("joinEmail"));
		joinPw.setPromptText(props.getProperty("joinPw"));
		joinPwCheck.setPromptText(props.getProperty("joinPwCheck"));
		checkIdBtn.setText(props.getProperty("checkIdBtn"));
		preLoginBtn.setText(props.getProperty("preLoginBtn"));
		joinBtn.setText(props.getProperty("joinBtn"));
		menuBtn.setText(props.getProperty("menuBtn"));
		text01.setText(props.getProperty("text01"));

		checkLang = "영어";
	}

	/**
	    * clickCheckIdBtn
	    * 아이디 중복확인 버튼
	    * @author 김민지
	    * @param e
	    * @return void
	    */
	public void clickCheckIdBtn(ActionEvent e) {// 아이디 중복 확인하는 버튼
		String id = joinId.getText().trim();

		if (id.equals("") || id == null) {
			blankInput(0);
		}

		BufferedInputStream bis = null;
		try {
			FileInputStream fis = new FileInputStream(profFile);
			bis = new BufferedInputStream(fis);
			props.load(bis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		MemberDao dao = new MemberDao(new HROraConnectionMaker());
		MemberVO inVO = new MemberVO();
		inVO.setGrpDiv("4");// 조구분
		inVO.setMemId(id);// ID

		int flag = dao.checkUserId(inVO); // 있으면 1 없으면 0

		switch (checkLang) {
		case "한국어":
			if (flag == 1) {// 회원이 존재할때 : 중복 아이디
				alert.setTitle(props.getProperty("idCheck"));
				alert.setHeaderText(null);
				alert.setContentText("[ "+id+" ]"+ props.getProperty("alreadyId"));
				alert.show();
				idCheck = false; // 회원가입 불가능하게 만드는 boolean
				idBtnCheck = true;
			} else {// 회원이 존재하지 않을 때
				alert.setTitle(props.getProperty("idCheck"));
				alert.setHeaderText(null);
				alert.setContentText("[ "+id+" ]"+ props.getProperty("okId"));
				alert.show();
				idCheck = true; // 회원가입 가능하게 만드는 boolean
				idBtnCheck = true;
			}
			break;
		case "영어":
			if (flag == 1) {// 회원이 존재할때 : 중복 아이디
				alert.setTitle(props.getProperty("idCheck"));
				alert.setHeaderText(null);
				alert.setContentText("[ "+id+" ]"+ props.getProperty("alreadyId"));
				alert.show();
				idCheck = false; // 회원가입 불가능하게 만드는 boolean
				idBtnCheck = true;
			} else {// 회원이 존재하지 않을 때
				alert.setTitle(props.getProperty("idCheck"));
				alert.setHeaderText(null);
				alert.setContentText("[ "+id+" ]"+ props.getProperty("okId"));
				alert.show();
				idCheck = true; // 회원가입 가능하게 만드는 boolean
				idBtnCheck = true;
			}
			break;
		}

	}// clickCheckIdBtn

	/**
	    * clickJoinBtn01
	    * 회원가입 버튼
	    * @author 김민지
	    * @param e
	    * @return void
	    */
	public void clickJoinBtn01(ActionEvent e) {// 회원가입 버튼(입력된 값 저장하기)
		String id = joinId.getText().trim();
		String name = joinName.getText().trim();
		String email = joinEmail.getText().trim();
		String pw = joinPw.getText().trim();
		String pwCheck = joinPwCheck.getText().trim();
		String level = "1";
		
		String[] inputTxt = { id, name, email, pw, pwCheck };
		for (int i = 0; i < inputTxt.length; i++) {
			if (inputTxt[i].equals("") || inputTxt == null) {
				blankInput(i);
				return;
			}
		}
		if (idBtnCheck) {// 중복확인 버튼 클릭했는지 확인
			if (idCheck) {// 아이디 중복체크해서 사용가능 ID
				infor("메일형식");
				infor("비밀번호형식");
				if (pw.equals(pwCheck)) {
					infor("비밀번호확인가능");
					MemberVO vo = new MemberVO();
					MemberDao dao = new MemberDao(new HROraConnectionMaker());

					vo.setMemId(id);// 아이디 저장
					vo.setName(name);// 이름 저장
					vo.setEmail(email);// 이메일 저장
					vo.setPw(pw);// 패스워드 저장
					vo.setAuth(level);// 권한 저장
					vo.setGrpDiv("4");// 조

					dao.do_save(vo);

					LOG.debug("회원가입 성공. ID: " + id);

					infor("성공");
				} else {
					//infor("비밀번호 불일치");
					infor("비밀번호확인틀림");
				}

			} else {// 아이디 중복체크해서 사용 불가능 ID
				infor("존재하는 아이디");
				idBtnCheck=false;
				return;
			}
		} else {
			infor("중복확인");
			return;
		}

	}// clickJoinBtn

	/**
	    * clickPreLoginBtn
	    * 로그인 화면으로 넘어가는 버튼
	    * @author 김민지
	    * @param e
	    * @return void
	    */
	public void clickPreLoginBtn(ActionEvent e) {// 로그인 창으로 넘어갈 메소드
		try {
			AnchorPane loaderLogin = FXMLLoader.load(getClass().getResource("../view/LoginFX.fxml"));
			Scene sceneJoin = new Scene(loaderLogin);
			Stage loginStage = (Stage) preLoginBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			loginStage.setTitle("영화 정보 검색 할땐? MOVA");
			loginStage.setScene(sceneJoin);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}// clickPreLoginBtn

	/**
	    * blankInput
	    * 정보 입력창이 빈칸일때 알림창
	    * @author 김민지
	    * @param blank
	    * @return void
	    */
	public void blankInput(int blank) { // 정보 입력창이 빈칸일때

		BufferedInputStream bis = null;
		try {
			FileInputStream fis = new FileInputStream(profFile);
			bis = new BufferedInputStream(fis);
			props.load(bis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		switch (blank) {
		case 0:// 아이디칸이 빈칸
			if (checkLang.equals("영어")) {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputId"));
				alert.show();
				joinId.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			} else {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputId"));
				alert.show();
				joinId.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			}
		case 1: // 이름칸이 빈칸
			String name = joinName.getText().trim();
			if (checkLang.equals("영어")) {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputName"));
				alert.show();
				joinName.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			} else {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputName"));
				alert.show();
				joinName.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			}
		case 2: // 이메일칸이 빈칸
			String email = joinEmail.getText().trim();
			if (checkLang.equals("영어")) {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputEmail"));
				alert.show();
				joinEmail.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			} else {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputEmail"));
				alert.show();
				joinEmail.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			}
		case 3: // 패스워드 칸이 빈칸
			String pw = joinPw.getText().trim();
			if (checkLang.equals("영어")) {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputPw"));
				alert.show();
				joinPw.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			} else {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputPw"));
				alert.show();
				joinPw.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			}
		case 4: // 패스워드체크 칸이 빈칸
			if (checkLang.equals("영어")) {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputPwCheck"));
				alert.show();
				joinPwCheck.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			} else {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("inputPwCheck"));
				alert.show();
				joinPwCheck.requestFocus();

				return;// 확인만 하고 지나가면 안되니깐
			}
		}
	}// blankInput

	/**
	    * infor
	    * 알림창/label별 다국어 지원
	    * @author 김민지
	    * @param i
	    * @return void
	    */
	public void infor(String i) {//Alert창이거나 아니면 label 다국어
		String id = joinId.getText().trim();
		String email = joinEmail.getText().trim();
		String pw = joinPw.getText().trim();
		String pwCheck = joinPwCheck.getText().trim();
		// email 정규식(유효검사)
		String patternE = "^[a-zA-Z0-9_-]+@[a-z]+\\.[a-z]+$";
		// 비밀번호 정규식(유효검사)
		String patternP = "^[a-z0-9]{6}$";// 소문자,대문자,숫자 포함. 길이는 6자이상
		
		BufferedInputStream bis = null;
		try {
			FileInputStream fis = new FileInputStream(profFile);
			bis = new BufferedInputStream(fis);
			props.load(bis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		switch (i) {
		case "성공":
			if (checkLang.equals("한국어")) {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("signupSucess"));
				alert.show();
			} else {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("signupSucess"));
				alert.show();
			}
			break;
		case "존재하는 아이디":
			if (checkLang.equals("한국어")) {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText("[ "+id+" ]"+props.getProperty("alreadyId"));
				alert.show();
			} else {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText("[ "+id+" ]"+props.getProperty("alreadyId"));
				alert.show();
			}
			break;
		case "중복확인":
			if (checkLang.equals("한국어")) {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("signupIdCheck"));
				alert.show();
			} else {
				alert.setTitle(props.getProperty("signupTxt"));
				alert.setHeaderText(null);
				alert.setContentText(props.getProperty("signupIdCheck"));
				alert.show();
			}
		case "메일형식":
			if (checkLang.equals("한국어")) {
				// email 정규식(유효검사)
				// 소문자,숫자,._-포함
				// @
				// 소문자와 . 2글자 이상 6글자이하
				if (!(email.matches(patternE))) {
					labelEmail.setText(props.getProperty("emailNot"));
					joinEmail.requestFocus();
					return;
				} else {
					labelEmail.setText(props.getProperty("emailCan"));
				}
			} else {
				if (!(email.matches(patternE))) {
					labelEmail.setText(props.getProperty("emailNot"));
					joinEmail.requestFocus();
					return;
				} else {
					labelEmail.setText(props.getProperty("emailCan"));
				}
			}
			break;
		case "비밀번호형식":
			if (checkLang.equals("한국어")) {
				// 비밀번호 정규식(유효검사)
				if (!(pw.matches(patternP))) {
					labelPw.setText(props.getProperty("pwNot"));
					joinPw.requestFocus();
					return;
				} else {
					labelPw.setText(props.getProperty("pwCan"));
				}
			} else {
				if (!(pw.matches(patternP))) {
					labelPw.setText(props.getProperty("pwNot"));
					joinPw.requestFocus();
					return;
				} else {
					labelPw.setText(props.getProperty("pwCan"));
				}
			}
			break;
		case "비밀번호확인가능":
			if (checkLang.equals("한국어")) {
				labelPwCheck.setText(props.getProperty("pwCheckCan"));
			} else {
				labelPwCheck.setText(props.getProperty("pwCheckCan"));
			}
			break;
		case "비밀번호확인틀림":
			if (checkLang.equals("한국어")) {
				labelPwCheck.setText(props.getProperty("pwCheckNot"));
			} else {
				labelPwCheck.setText(props.getProperty("pwCheckNot"));
			}
			break;
		}
	}// infor

}
