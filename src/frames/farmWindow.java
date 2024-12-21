package frames;

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

	// Método chamado automaticamente após o carregamento do FXML
	@FXML
	public void initialize() {
		// Use o listener para ajustar a posição após o Stage ser exibido
		Platform.runLater(() -> {
		// Obtém o Stage a partir de qualquer nó
		stage = (Stage) barraIcon.getScene().getWindow();
		stage.setMaximized(true);
		
		stage.setTitle("Sistem Enterpreise - Farmacia");
		
		//ajustando a barraIcon e barraDeMenu para tamanho total da tela e a posicao do LogOut
		Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        double x = screenSize.getWidth(); 
        double y = screenSize.getHeight(); 
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
		Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameMedCadas.fxml"));
		Scene scene = new Scene(root);
	    // Configura a cena no novo Stage pq se vc usar os nó s que nem os anteriores ele vai usar o "palco" anterior
	    stageMedCadas.setScene(scene);
	    
		/* stageMedCadas = (Stage) frameAnchorPane.getScene().getWindow(); isso seria o palco anterior, e que por
		   conta da linha 63 nao precisamos pq criamos um novo */
	    
		stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}
	
	public void fWAjuste() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameAjuste.fxml"));
		Scene scene = new Scene(root);
	    // Configura a cena no novo Stage pq se vc usar os nó s que nem os anteriores ele vai usar o "palco" anterior
	    stageMedCadas.setScene(scene);
	    
		/* stageMedCadas = (Stage) frameAnchorPane.getScene().getWindow(); isso seria o palco anterior, e que por
		   conta da linha 63 nao precisamos pq criamos um novo */
	    
		stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}
	
	public void fWConsol() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameConsol.fxml"));
		Scene scene = new Scene(root);
	    // Configura a cena no novo Stage pq se vc usar os nó s que nem os anteriores ele vai usar o "palco" anterior
	    stageMedCadas.setScene(scene);
	    
		/* stageMedCadas = (Stage) frameAnchorPane.getScene().getWindow(); isso seria o palco anterior, e que por
		   conta da linha 63 nao precisamos pq criamos um novo */
	    
		stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}

	public void fWEstoqueMed() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameEstoqueMed.fxml"));
		Scene scene = new Scene(root);
	    // Configura a cena no novo Stage pq se vc usar os nó s que nem os anteriores ele vai usar o "palco" anterior
	    stageMedCadas.setScene(scene);
	    
		/* stageMedCadas = (Stage) frameAnchorPane.getScene().getWindow(); isso seria o palco anterior, e que por
		   conta da linha 63 nao precisamos pq criamos um novo */
	    
		stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}
	
	public void fWReqSetor() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameReqSetor.fxml"));
		Scene scene = new Scene(root);
	    // Configura a cena no novo Stage pq se vc usar os nó s que nem os anteriores ele vai usar o "palco" anterior
	    stageMedCadas.setScene(scene);
	    
		/* stageMedCadas = (Stage) frameAnchorPane.getScene().getWindow(); isso seria o palco anterior, e que por
		   conta da linha 63 nao precisamos pq criamos um novo */
	    
    	stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}
	
	public void fWReqPrest() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameReqPrest.fxml"));
		Scene scene = new Scene(root);
	    // Configura a cena no novo Stage pq se vc usar os nó s que nem os anteriores ele vai usar o "palco" anterior
	    stageMedCadas.setScene(scene);
	    
		/* stageMedCadas = (Stage) frameAnchorPane.getScene().getWindow(); isso seria o palco anterior, e que por
		   conta da linha 63 nao precisamos pq criamos um novo */
	    
    	stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}

	public void fWKardexMed() throws IOException {
		Stage stageMedCadas = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameKardexMed.fxml"));
		Scene scene = new Scene(root);
	    // Configura a cena no novo Stage pq se vc usar os nó s que nem os anteriores ele vai usar o "palco" anterior
	    stageMedCadas.setScene(scene);
	    
		/* stageMedCadas = (Stage) frameAnchorPane.getScene().getWindow(); isso seria o palco anterior, e que por
		   conta da linha 63 nao precisamos pq criamos um novo */
	    
    	stageMedCadas.setScene(scene);
		stageMedCadas.show();
		stageMedCadas.centerOnScreen();
	}

}