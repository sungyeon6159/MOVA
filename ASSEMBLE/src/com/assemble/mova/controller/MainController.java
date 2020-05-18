package com.assemble.mova.controller;

import java.io.IOException;
import org.apache.log4j.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController extends Application {
	@FXML
	private Button loginBtn;

	static Logger log = Logger.getLogger(MainController.class);

	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			log.debug("This is login page");
			Parent login = FXMLLoader.load(getClass().getResource("../view/SearchFX.fxml"));
			Scene scene = new Scene(login);
			primaryStage.setScene(scene);
			primaryStage.setTitle("영화 정보 검색 할땐? MOVA");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
