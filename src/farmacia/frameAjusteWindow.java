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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import medicamentos.medicamentos;

public class frameAjusteWindow implements Initializable{

    @FXML
    private ComboBox<String> acaoFeita;
    private String[] vAcaoFeita = {"Entrada", "Saida"};
    @FXML
    private DatePicker hrAdd;
    @FXML
    private TextField nAjuste;
    @FXML
    private ComboBox<String> setor;
    private String[] vSetor;
    @FXML
    private TableView<medicamentos> tableAjusteWindowTV;
    @FXML
    private TableColumn<medicamentos, Integer> codigoTC;    
    @FXML
    private TableColumn<medicamentos, String> nomeTC;
    @FXML
    private TableColumn<medicamentos, Integer> quantTC;      
    @FXML
    private TableColumn<medicamentos, Date> validadeTC;
    @FXML
    private TableColumn<medicamentos, String> loteTC;
     @FXML
    private TableColumn<medicamentos, String> classificacaoTC;   
    @FXML
    private Button saveButton;
    @FXML
    private Button incluirButton;
    
    private Stage stage;
    
    
    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    
   ObservableList<medicamentos> listOfMed = FXCollections.observableArrayList();
   private int numeric = 0;
   medicamentos medVazio = new medicamentos((Integer)null, "", (Integer)null, null, "", "");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		tableAjusteWindowTV.setItems(listOfMed);
		tableAjusteWindowTV.setEditable(true);
		
		nomeTC.setCellFactory(column -> new TableCell<medicamentos, String>(){
			private final ComboBox<String> comboBox = new ComboBox<>();
			
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(empty) {
					setGraphic(null);
				}else {
					comboBox.getItems().clear();
					carregarNomesComboBox(comboBox);
					comboBox.setValue(item);
					setGraphic(comboBox);
					
					
					comboBox.setOnAction(event -> {
					String nomeTCValueCB = comboBox.getValue();
					medicamentos med = getTableView().getItems().get(getIndex());
					med.setNomeMed(nomeTCValueCB);
					
					updateLine(nomeTCValueCB, med, numeric);
					numeric ++;
					});
					
				}
			}
		});
		quantTC.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		loteTC.setCellFactory(TextFieldTableCell.forTableColumn());

	}//end initialize

	public void carregarNomesComboBox(ComboBox<String> comboBox) {
		
		try{
	    	conn = DB.getConnection();
	    	//id, nome, qtd, validade, lote, tipo
	    	String query = "select nome from medicamento;";
	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();
	    	while(rs.next()) {
	    		comboBox.getItems().add(rs.getString("nome"));	
	    	}//end while
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
	
	public void updateLine(String nomeProd, medicamentos med, int numeric) {
		try {
		conn = DB.getConnection();
		String query = "select med.idmedicamento, med.nome, med.quantidade, med.validade, l.lote, c.classif\r\n"
    			+ "from medicamento med \r\n"
    			+ "inner join lote l\r\n"
    			+ "on med.IDMEDICAMENTO = l.ID_MEDICAMENTO\r\n"
    			+ "inner join classificacao c\r\n"
    			+ "on med.IDMEDICAMENTO = c.ID_MEDICAMENTO\r\n"
    			+ "where nome = ?;";
    	
    	st = conn.prepareStatement(query);
    	st.setString(1, nomeProd);
    	rs = st.executeQuery();
    	
    	if(rs.next()) {
    		Integer idMedQuery = rs.getInt("idmedicamento");
			Integer quantidadeMedQuery = rs.getInt("quantidade");
			Date validadeMedQuery = rs.getDate("validade");
			String loteMedQuery = rs.getString("lote");
			String classifMedQuery = rs.getString("classif");
			
			med = new medicamentos(idMedQuery, quantidadeMedQuery, validadeMedQuery, classifMedQuery, loteMedQuery);
			
			listOfMed.set(numeric, med);
			
			codigoTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Integer>("idMed"));
    		quantTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Integer>("quantidade"));
    		validadeTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Date>("validade"));
    		loteTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("codLote"));
    		classificacaoTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("nomeClassificacao"));
			
			tableAjusteWindowTV.refresh();
    	}}catch (SQLException e2) {
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
	
	
	public void incluirLine(ActionEvent event) {
		listOfMed.add(medVazio);
		tableAjusteWindowTV.setItems(listOfMed);
	}

	public void autorizarUpDel() throws SQLException {

			String query = "SET SQL_SAFE_UPDATES = 0;";
			
			st = conn.prepareStatement(query);
			int rowsAffected = st.executeUpdate();
	    	
	}
	
	public void updateQuantity(medicamentos med, int id, Integer newQuantity) {
		try {
			conn = DB.getConnection();
			
			String query = "UPDATE medicamento\r\n"
						+ "set QUANTIDADE = ? where IDMEDICAMENTO = ?;";
			
			autorizarUpDel();
			st = conn.prepareStatement(query);
	    	st.setInt(1, newQuantity);
	    	st.setInt(2, id);
	    	int rowsAffected = st.executeUpdate();
	    	
			
		}catch (SQLException e2) {
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
	
	public void updateLote(medicamentos med, int id, String newLote) {
		try {
			conn = DB.getConnection();
			
			String query = "UPDATE LOTE\r\n"
					+ "set LOTE = ? where IDLOTE = ?;";
			
			autorizarUpDel();
			st = conn.prepareStatement(query);
	    	st.setString(1, newLote);
	    	st.setInt(2, id);
	    	int rowsAffected = st.executeUpdate();
	    	
			
		}catch (SQLException e2) {
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
	
	public void save(ActionEvent event) {
		stage = (Stage) tableAjusteWindowTV.getScene().getWindow();
		quantTC.setOnEditCommit(this::onEditChargedQtd);
		loteTC.setOnEditCommit(this::onEditChargedLote);
		stage.close();
	}
	
	@FXML
	protected void onEditChargedQtd(TableColumn.CellEditEvent<medicamentos, Integer> medIntegerCellEditEvent) {
    	medicamentos med = tableAjusteWindowTV.getSelectionModel().getSelectedItem();
    	Integer codIdLine = med.getIdMed();
    	med.setQuantidade(medIntegerCellEditEvent.getNewValue());
    	updateQuantity(med, codIdLine, medIntegerCellEditEvent.getNewValue());
    	tableAjusteWindowTV.refresh();
    }
	@FXML
    public void onEditChargedLote(TableColumn.CellEditEvent<medicamentos, String> medStringCellEditEvent) {
		medicamentos med = tableAjusteWindowTV.getSelectionModel().getSelectedItem();
    	Integer codIdLine = med.getIdMed();
    	med.setLote(medStringCellEditEvent.getNewValue());
    	updateLote(med, codIdLine, medStringCellEditEvent.getNewValue());
    	tableAjusteWindowTV.refresh();
    }
}