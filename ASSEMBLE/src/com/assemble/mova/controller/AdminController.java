/**
 *<pre>
 * com.assemble.mova.controller
 * Class Name : AdminController.java
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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.assemble.mova.dao.AdminDao;
import com.assemble.mova.dao.MovieDao;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
/**
 * <pre>
 * com.assemble.mova.controller
 *    AdminController.java
 * 
 * </pre>
 * @date : 2019. 12. 11. 오후 4:23:19
 * @version : 
 * @author : 이재원
 */
public class AdminController implements Initializable {

	@FXML
	private Button loginBtn, chooseBtn, saveBtn;

	@FXML
	TextField tMoveCode, tTitle, tCast, tGenre, tYear;

	@FXML
	Label lImgPath, loginId;

	@FXML
	TextArea tStory;

	@FXML
	ImageView posterImg;

	static Logger log = Logger.getLogger(AdminController.class);

	static File selectedFile;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loginId.setText(LoginController.memName + " 님");
		MovieDao dao = new MovieDao();
		List<MovieVO> vo = dao.readMovieData("mv");
		tMoveCode.setText("mv" + (vo.size() + 1));

	}
	 /**
	    * fileChoose
	    * 이미지 파일선택
	    * @author 이재원
	    * @param e
	    */
	public void fileChoose(ActionEvent e) {

		// 사진 선택 창
		FileChooser fc = new FileChooser();
		fc.setTitle("이미지 선택");
		fc.setInitialDirectory(new File("C:\\")); // default 디렉토리 설정
		// 선택한 파일 정보 추출
		// 확장자 제한

		ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg", "*.gif", "*.png","*.jpeg");
		fc.getExtensionFilters().addAll(imgType);
		selectedFile = fc.showOpenDialog(null); // showOpenDialog는 창을 띄우는데 어느 위치에 띄울건지 인자를 받고
		log.debug("selectedFile:" + selectedFile); // 그리고 선택한 파일의 경로값을 반환한다.
		// 선택한 경로가 출력된다.

		// 파일을 InputStream으로 읽어옴
		try {
			// 파일 읽어오기
			FileInputStream fis = new FileInputStream(selectedFile);
			System.out.println("");
			BufferedInputStream bis = new BufferedInputStream(fis);
			// 이미지 생성하기
			Image img = new Image(bis);
			// 이미지 띄우기
			posterImg.setImage(img);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		lImgPath.setText("img/" + selectedFile.getName());

	}

	// 저장할 데이터의 이름
	 /**
	    * saveAction
	    * 이미지 업로드
	    * @author 이재원
	    * @param e
	    * @return void
	    */
	public void saveAction(ActionEvent e) throws IOException {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("이미지 업로드 ");
		alert.setHeaderText(null);
		alert.setContentText("이미지 업로드를 진행하세요");
		alert.show();

		AdminDao movieDao = new AdminDao();
		MovieVO movieVO = new MovieVO();
		movieVO.setMovieCode(tMoveCode.getText());
		movieVO.setTitle(tTitle.getText());
		movieVO.setCast(tCast.getText().replaceAll("(\r\n|\r|\n|\n\r|,)", " "));
		movieVO.setGenre(tGenre.getText().replaceAll("(\r\n|\r|\n|\n\r|,)", " "));
		movieVO.setYear(tYear.getText());
		movieVO.setStory(tStory.getText().replaceAll("(\r\n|\r|\n|\n\r|,)", " "));
		movieVO.setImgPath("img/" + selectedFile.getName());

		MovieVO dVO = movieDao.getMovieInputData(movieVO);
		if (movieDao.isMemberExists(dVO) == true) {
			System.out.println("존재하는 MovieCode입니다.");
		} else {
			try {
				movieDao.do_save(dVO);
				alert.setTitle("영화 등록하기");
				alert.setHeaderText(null);
				alert.setContentText("영화 등록을 성공하였습니다.");
				alert.show();

				try {
					log.debug("This is Admin Page");
					Parent login = FXMLLoader.load(getClass().getResource("../view/AdminPageFX.fxml"));
					Scene scene = new Scene(login);
					Stage primaryStage = (Stage) loginBtn.getScene().getWindow(); // 현재 윈도우 가져오기
					primaryStage.setScene(scene);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		// saveBtn 클릭 시 fileChoose에서 받아온 이미지를 img/src 밑에 저장한다.

		// 파일을 InputStream으로 읽어옴
		FileInputStream fin = null;
		FileOutputStream fout = null;

		try {
			byte[] tmpByte = new byte[2048];
			// 입력 스트림을 구한다
			fin = new FileInputStream(new File(selectedFile.getPath()));
			fout = new FileOutputStream(new File("src/img/" + selectedFile.getName()));

			int read;
			// 입력 스트림을 파일로 저장
			for (;;) {
				read = fin.read(tmpByte);
				if (read <= 0) {
					break;
				}
				fout.write(tmpByte, 0, read); // file 생성
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();

		} finally {

			fin.close();
			fout.close();
		}

	}
	 /**
	    * loginPage
	    * 로그인 페이지 화면전환
	    * @author 이재원
	    * @param e
	    * @return void
	    */
	public void loginPage(ActionEvent e) {

		try {
			log.debug("This is Login Page");
			Parent login = FXMLLoader.load(getClass().getResource("../view/LoginFX.fxml"));
			Scene scene = new Scene(login);
			Stage primaryStage = (Stage) loginBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
