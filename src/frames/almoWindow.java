package frames;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class almoWindow {
		
	@FXML
	private AnchorPane frameAnchorPane;
	@FXML
	private Rectangle barraIcon; 
	@FXML
	private MenuBar barraDeMenu;
	@FXML
	private Button botaoLogout;
	
	private Stage stage;

	// Método chamado automaticamente após o carregamento do FXML
	@FXML
	public void initialize() {
		// Use o listener para ajustar a posição após o Stage ser exibido
		Platform.runLater(() -> {
			// Obtém o Stage a partir de qualquer nó
			stage = (Stage) frameAnchorPane.getScene().getWindow();
			
			stage.setMaximized(true);
			stage.setTitle("Sistem Enterpreise - Almoxarifado");
			
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
	
}