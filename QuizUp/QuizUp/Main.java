package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;



public class Main extends Application {
	public static int portno;
	@Override
	public void start(Stage primaryStage) {
			try
			{
				Parent root = FXMLLoader.load(getClass().getResource("/application/quizfx.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} 
			catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		System.out.println ("arguments " + args[0]);
		portno = Integer.parseInt(args[0]);
		launch(args);
	}
}
