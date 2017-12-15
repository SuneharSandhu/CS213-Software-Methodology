/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import controllers.ListController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View.fxml"));
			//loader.setLocation();
			AnchorPane root = (AnchorPane)loader.load();

			ListController listController = loader.getController();
			listController.start(primaryStage);

			Scene scene = new Scene(root, 310, 568);
			primaryStage.setTitle("SongLib - by amt223 & aj470");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
