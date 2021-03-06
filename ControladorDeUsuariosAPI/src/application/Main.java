package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import ui.controlUserController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.*;
import java.util.regex.Pattern;

import javax.sql.*;

import database.Querys;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/ui/controlUser.fxml"));
			Scene scene = new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setMaximized(false);
			primaryStage.resizableProperty().setValue(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			/*Stage stage= new Stage();
			AnchorPane anchor=new AnchorPane();
			Scene novaScene= new Scene(anchor,400,400);
			stage.setTitle("Titulo 2");
			stage.setScene(novaScene);
			stage.show();
			Parent parent = FXMLLoader.load(getClass().getResource("/gui/View.fxml"));
			Scene scene2 = new Scene(parent);
			stage.setScene(scene);
			stage.show();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
