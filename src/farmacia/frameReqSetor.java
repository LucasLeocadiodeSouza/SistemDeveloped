package farmacia;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import medicamentos.medicamentos;

public class frameReqSetor implements Initializable{

    @FXML
    private TextField centEstocadoTF;
    @FXML
    private TableColumn<medicamentos, String> classifTC;
    @FXML
    private TextField codTF;
    @FXML
    private TableColumn<medicamentos, Integer> codigoTC;
    @FXML
    private TextField dataTF;
    @FXML
    private TextField descTF;
    @FXML
    private TableColumn<medicamentos, String> medidaTC;
    @FXML
    private TableColumn<medicamentos, String> nomeTC;
    @FXML
    private TableColumn<medicamentos, Integer> quantTC;
    @FXML
    private TableView<medicamentos> reqSetorTV;
    @FXML
    private Button sairB;
    @FXML
    private Button salvarB;
    @FXML
    private TextField setorTF;

    @FXML
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	    	
    }
    
    
}