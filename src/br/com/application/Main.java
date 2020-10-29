package br.com.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {

	static Stage stage;
	private static Scene mainScene;
	private static Scene loginScene;

	@Override
	public void start(Stage primaryStage) {
		Main.stage = primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/view/Login.fxml"));
			FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/br/com/view/Menu.fxml"));
			ScrollPane scrollPane = loader.load();
			ScrollPane scrollPane2 = loaderMain.load();
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);	
			scrollPane2.setFitToHeight(true);
			scrollPane2.setFitToWidth(true);			
			loginScene = new Scene(scrollPane);
			mainScene = new Scene(scrollPane2);
			primaryStage.setScene(loginScene);
			primaryStage.setTitle("Biblioteca CEPEDC");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 public static void changeStage(String nomeTela) {
	    	
	    	if(nomeTela.equals("Menu")) {
	    		stage.setScene(mainScene);
	    	}
	    	
	    	
	    }
	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
