package farmacia;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private TextField hrAdd;
    @FXML
    private TextField nAjuste;
    @FXML
    private TextField descricaoTF;
    @FXML
    private ComboBox<String> setor;
    private String[] vSetor = {"Almoxarifado","Farmacia"};
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
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Stage stage;
    LocalDateTime dataHoraAtual;
    String descricao;
    
    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    
   ObservableList<medicamentos> listOfMed = FXCollections.observableArrayList();
   private int numeric = 0;
   medicamentos medVazio = new medicamentos((Integer)null, "", (Integer)null, null, "", "");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		acaoFeita.getItems().addAll(vAcaoFeita);
		setor.getItems().addAll(vSetor);
		
		tableAjusteWindowTV.setItems(listOfMed);
		tableAjusteWindowTV.setEditable(true);
		dataHoraAtual = LocalDateTime.now();
		
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
					}); //end comboBox
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
		String query = "select med.idmedicamento, med.nome, med.validade, l.lote, c.classif\r\n"
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
			Date validadeMedQuery = rs.getDate("validade");
			String loteMedQuery = rs.getString("lote");
			String classifMedQuery = rs.getString("classif");
			
			med = new medicamentos(idMedQuery, validadeMedQuery, classifMedQuery, loteMedQuery);//o erro que esta tendo é que tem que adiconar o query para o medicamento e mudar a quantidade pela classe medicamentos e depois puxar q quantidade no medicamento pro banco
			
			listOfMed.set(numeric, med);
			
			codigoTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Integer>("idMed"));
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
	
	public Integer selectQuantity() {
		Integer qtd = null;
		try {
			conn = DB.getConnection();
			
			String query = "SELECT quantidade FROM medicamento;";
			
			st = conn.prepareStatement(query);
			qtd = rs.getInt("quantidade");
			st.executeQuery();
			System.out.println("Deu o select Quantity");
			
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
		return qtd;
	}
	
	public void updateQuantity(medicamentos med, int id, Integer campoQuantity) {
		try {
			Integer newQuantity = campoQuantity + selectQuantity();
			med.setQuantidade(newQuantity);
			conn = DB.getConnection();
			
			String query = "UPDATE medicamento\r\n"
						+ "set QUANTIDADE = ? where IDMEDICAMENTO = ?;";
			
			autorizarUpDel();
			st = conn.prepareStatement(query);
	    	st.setInt(1, newQuantity);
	    	st.setInt(2, id);
	    	int rowsAffected = st.executeUpdate();
			
	    	System.out.println("Deu o UpdateQuantity");
	    	
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
	
	public void updateLote(int id, String newLote) {
		try {
			conn = DB.getConnection();
			
			String query = "UPDATE LOTE\r\n"
					+ "set LOTE = ? where IDLOTE = ?;";
			
			autorizarUpDel();
			st = conn.prepareStatement(query);
	    	st.setString(1, newLote);
	    	st.setInt(2, id);
	    	int rowsAffected = st.executeUpdate();
	    	
	    	System.out.println("Deu o Update Lote");
	    	
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
	
	public void nmrAjuste() {
		try {
			conn = DB.getConnection();
			
			//INSERT INTO AJUSTE
			st = conn.prepareStatement("INSERT INTO AJUSTE (DESCRICAO, ACAO) VAlUES" + 
					" (?,?);", Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, descricao);
			st.setString(2, acaoFeita.getValue());
			
			int rowsAffected = st.executeUpdate();
			int generatedId = 0;
			
			// Recuperar o ID gerado automaticamente
		    if (rowsAffected > 0) {
		        rs = st.getGeneratedKeys();
		        if (rs.next()) {
		            generatedId = rs.getInt(1); // Captura o ID gerado
		        }
		    }
			
			//INSERT INTO AJUSTEWINDOW
		    st = conn.prepareStatement("INSERT INTO AJUSTEWINDOW "
		    		+ "(NOMEPROD, ACAO, HR_ADICIONADA, SETOR, ID_AJUSTE) VALUES" + 
					" (?, ?, ?, ?, ?);"
					);

			st.setString(1, nomeTC.getText());
			st.setString(2, acaoFeita.getValue());
			st.setDate(3,new java.sql.Date(sdf.parse(hrAdd.getText()).getTime()));
			st.setString(4, setor.getValue());
			st.setInt(5, generatedId);
			st.executeUpdate();
			
		}catch (SQLException e2) {
			e2.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		}
	}
	
	public void save(ActionEvent event) throws SQLException { //botao de salvar
		  try {
		        stage = (Stage) tableAjusteWindowTV.getScene().getWindow();
		        quantTC.setOnEditCommit(editEvent -> {
		            medicamentos med = editEvent.getRowValue();
		            updateQuantity(med, med.getIdMed(), med.getQuantidade());
		            stage.close();
		            tableAjusteWindowTV.refresh();
		        });
		        loteTC.setOnEditCommit(this::onEditChargedLote);
		        nmrAjuste();
		        System.out.println("Deu o save");
		        //stage.close();
		    }catch (Exception e) {
		        e.printStackTrace(); // Outros tipos de erro
		        System.out.println("Erro: " + e.getMessage());
		    }
	}
	
    public void onEditChargedLote(TableColumn.CellEditEvent<medicamentos, String> medStringCellEditEvent) {
		medicamentos med = tableAjusteWindowTV.getSelectionModel().getSelectedItem();
    	Integer codIdLine = med.getIdMed();
    	med.setLote(medStringCellEditEvent.getNewValue());
    	updateLote(codIdLine, medStringCellEditEvent.getNewValue());
    	tableAjusteWindowTV.refresh();
    }
}