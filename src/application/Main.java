package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/frames/desktopSistem.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show(); 
			
			//esse comando que coloca o desktop no canto direito da tela tem que ser executado apos o priamryStage.show() para o programa calcular seu tamanho certo antes
			Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	        // Calcula a posição para o canto inferior direito
	        double x = screenSize.getWidth() - primaryStage.getWidth(); // largura da janela
	        double y = screenSize.getHeight() - primaryStage.getHeight(); // altura da janela
	        // Define a posição da janela no canto inferior direito
	        primaryStage.setX(x);
	        primaryStage.setY(y);
			
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void createConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}