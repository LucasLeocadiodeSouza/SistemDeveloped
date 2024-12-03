package farmacia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import connectSQL.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class frameMedCadas implements Initializable{
	
	@FXML
	private AnchorPane frameAnchorPane;
	@FXML
	private TextField dataSistem;
	/* tem que adicionar isso, qual usuario adicionou, a logica para ativar ele e o horario de add
	@FXML
	private ChoiceBox<String> medidaCB;
    private String[] vMedidaCB = {"Und.", "Cx.","Pct.", "Par"};
    */
	@FXML
	private TextField loteTF;
	@FXML
	private TextField validadeTF;
	@FXML
	private TextField marcaTF;
    @FXML
    private ChoiceBox<String> medidaCB;
    private String[] vMedidaCB = {"Und.", "Cx.","Pct.", "Par"};
	@FXML
    private CheckBox permDevCB;
    @FXML
    private CheckBox permEstCB;
    @FXML
    private CheckBox permInvCB;
    @FXML
    private CheckBox permReqPrestCB;
    @FXML
    private CheckBox permReqSetCB;
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
	
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		medidaCB.getItems().addAll(vMedidaCB); 
		classCB.getItems().addAll(vClassCB);
		tipoCB.getItems().addAll(vTipoCB);
		fornCB.getItems().addAll(vFornCB);
		
		DateTimeFormatter horasNow = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		dataSistem.setText(LocalDateTime.now().format(horasNow)); 
	}
	
	@FXML
	public void save(ActionEvent event) {
		stage = (Stage) frameAnchorPane.getScene().getWindow();		
		try {
			cadasMedQuery cmQuery = new cadasMedQuery();
			cmQuery.insertCadasQuery(conn, st, rs, nomeTF, quantidadeTF, validadeTF, dataSistem, medidaCB,  loteTF, permDevCB, permEstCB, permInvCB, permReqPrestCB, permReqSetCB, classCB, fornCB, marcaTF);
		}finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		}
		stage.close();
	}
	
	@FXML
	public void logOut(ActionEvent event) {
		stage = (Stage) frameAnchorPane.getScene().getWindow();
		stage.close();
	}
}