package  com.cestec.frames;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class farmWindow {
		
	@FXML
	private AnchorPane frameAnchorPane;
	@FXML
	private Rectangle barraIcon; 
	@FXML
	private MenuBar barraDeMenu;
	@FXML
	private Button botaoLogout;
	
	private Stage stage;

	@FXML
	public void initialize() {
		// Use o listener para ajustar a posição após o Stage ser exibido
		Platform.runLater(() -> {
			stage = (Stage) barraIcon.getScene().getWindow();
			stage.setMaximized(true);
			
			stage.setTitle("Sistem Enterpreise - Farmacia");
			
			//ajustando a barraIcon e barraDeMenu para tamanho total da tela e a posicao do LogOut
			Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
			double x = screenSize.getWidth(); 
			barraIcon.setWidth(x);
			barraDeMenu.setPrefWidth(x);
		});
	}
	
	
	public void logOut(ActionEvent event) {
		stage = (Stage) frameAnchorPane.getScene().getWindow();
		stage.close();
	}
	

	public void fWMedCadas() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/frameMedCadas.fxml"));
		Scene scene = new Scene(root);
	    // Configura a cena no novo Stage pq se vc usar os nó s que nem os anteriores ele vai usar o "palco" anterior
	    stageMedCadas.setScene(scene);
	    
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}
	
	public void fWAjuste() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/frameAjuste.fxml"));
		Scene scene = new Scene(root);

	    stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}
	
	public void fWConsol() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/frameConsol.fxml"));
		Scene scene = new Scene(root);

	    stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}

	public void fWEstoqueMed() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/frameEstoqueMed.fxml"));
		Scene scene = new Scene(root);

	    stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}
	
	public void fWReqSetor() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/frameReqSetor.fxml"));
		Scene scene = new Scene(root);

	    stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}
	
	public void fWReqPrest() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/frameReqPrest.fxml"));
		Scene scene = new Scene(root);

	    stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}

	public void fWKardexMed() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/frameKardexMed.fxml"));
		Scene scene = new Scene(root);
		
	    stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}

}