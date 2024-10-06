package farmacia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import connectSQL.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class frameMedCadas implements Initializable{
	
	@FXML
	private AnchorPane frameAnchorPane;
	@FXML
	private TextField dataSistem;
	@FXML
	private TextField loteTF;
	@FXML
	private TextField validadeTF;
	@FXML
	private TextField marcaTF;
	@FXML
	private TextField nomeTF;
	@FXML
	private TextField quantidadeTF;
	@FXML
	private TextField obsTF;
	@FXML
	private Button cancelButton;
	@FXML
	private ChoiceBox<String> classCB;
	private String[] vClassCB = {"Analgésico", "Anti-Inflamatorio", "Anti-Biotipo"};
	
	@FXML
	private ChoiceBox<String> tipoCB;
	private String[] vTipoCB = {"Controlado", "Manipulado","Biológico", "Antibiótico", "Hormonal"};
	
	@FXML
	private ChoiceBox<String> fornCB;
	private String[] vFornCB = {"Droga raia", "Drogaria Sao Paulo", "Hosp. Bom Samaritano", "Hosp. Rede Cross", "Hosp. Santa Rita"};
	
	private Stage stage;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		classCB.getItems().addAll(vClassCB);
		tipoCB.getItems().addAll(vTipoCB);
		fornCB.getItems().addAll(vFornCB);
	}
	
	@FXML
	public void save(ActionEvent event) {
		//conection DB
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cadasMedQuery cmQuery = new cadasMedQuery();
			cmQuery.insertCadasQuery(conn, st, rs, nomeTF, quantidadeTF, validadeTF, loteTF, classCB, fornCB, marcaTF);
		}finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		}
	}
	
	
	@FXML
	public void logOut(ActionEvent event) {
		stage = (Stage) frameAnchorPane.getScene().getWindow();
		stage.close();
	}
}