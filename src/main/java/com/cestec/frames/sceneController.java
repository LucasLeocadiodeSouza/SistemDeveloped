package  com.cestec.frames;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class sceneController {
	
	private Stage stage;
	private Scene scene;
	
	public void switchToSceneFarm(ActionEvent e) throws IOException 
	{
		Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/farmWindow.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToSceneAlmo(ActionEvent e) throws IOException 
	{
	Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/almoWindow.fxml"));
	stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	scene = new Scene(root);
	stage.setScene(scene);
	stage.show();
	}
	
}