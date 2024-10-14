package farmacia;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class frameAjuste implements Initializable{
	
    @FXML
    private TableColumn<?, ?> acaoTC;
    @FXML
    private TableColumn<?, ?> codAjusteTC;
    @FXML
    private TableColumn<?, ?> descTC;
    @FXML
    private Button fecharB;
    @FXML
    private Button incluirB;
    @FXML
    private TableView<?> tableAjusteTV;
    private Stage stage;
    private Scene scene;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
	}
    
    public void save(ActionEvent e) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameAjusteWindow.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void fechar(ActionEvent e) {
    	stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	stage.close();
    }
}
