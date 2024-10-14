package farmacia;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connectSQL.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import medicamentos.classificacao;
import medicamentos.lote;
import medicamentos.marca;
import medicamentos.medicamentos;

public class frameEstoqueMed implements Initializable{

    @FXML
    private TableColumn<medicamentos, String> classificacaoTC;
    @FXML
    private TableColumn<medicamentos, Integer> codigoTC;
    @FXML
    private TableColumn<medicamentos, String> nomeTC;
    @FXML
    private TableView<medicamentos> tableMedEstTV;
    @FXML
    private TableColumn<medicamentos, String> loteTC;
    @FXML
    private TableColumn<medicamentos, Date> validadeTC;
    @FXML
    private TableColumn<medicamentos, String> marcaTC;
    
    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	    ObservableList<medicamentos> listOfMed = FXCollections.observableArrayList();

	    try{
	    	conn = DB.getConnection();
	    	
	    	//id, nome, valdade, lote, classificacao e marca
	    	String query = "select med.idmedicamento, med.nome, med.validade, l.lote, c.classif, mar.nomemarca\r\n"
	    			+ "from medicamento med \r\n"
	    			+ "inner join lote l \r\n"
	    			+ "on med.IDMEDICAMENTO = l.ID_MEDICAMENTO\r\n"
	    			+ "inner join classificacao c \r\n"
	    			+ "on med.IDMEDICAMENTO = c.ID_MEDICAMENTO\r\n"
	    			+ "inner join marca mar\r\n"
	    			+ "on med.IDMEDICAMENTO = mar.ID_MEDICAMENTO;";

	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();

	    	while(rs.next()) {
	    		Integer idMedQuery = rs.getInt("idmedicamento");
	    		String nomeMedQuery = rs.getString("nome");
	    		Date validadeMedQuery = rs.getDate("validade");
	    		String loteMedQuery = rs.getString("lote");
	    		String classifMedQuery = rs.getString("classif");
	    		String nomeMarcaMedQuery = rs.getString("nomemarca");
	    		
	    		medicamentos med = new medicamentos(idMedQuery, nomeMedQuery, validadeMedQuery, classifMedQuery, loteMedQuery, nomeMarcaMedQuery);
	    		
	    		listOfMed.add(med);
	    		
	    		codigoTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Integer>("idMed"));
	    		nomeTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("nomeMed"));
	    		validadeTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Date>("validade"));
	    		classificacaoTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("nomeClassificacao"));
	    		loteTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("codLote"));
	    		marcaTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("nomeMarca"));
	    	}
	    	
	    	tableMedEstTV.setItems(listOfMed);
	    	
	    	
	    } catch (SQLException e2) {
			e2.printStackTrace();
		}finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		}
	    
	}
    
}