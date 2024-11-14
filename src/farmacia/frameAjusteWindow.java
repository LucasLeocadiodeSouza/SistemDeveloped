package farmacia;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import connectSQL.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private Button delButton;
    @FXML
    private Button exitButton;
    @FXML
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Stage stage;
    LocalDateTime dataHoraAtual;
    String descricao;
    ArrayList<medicamentos> ajusteMed;
    medicamentos med;
    
    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    
   ObservableList<medicamentos> listOfMed = FXCollections.observableArrayList();
   private int numeric = 0;
   medicamentos medVazio = new medicamentos((Integer)null, "", (Integer)null, null, "", "");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ajusteMed = new ArrayList<medicamentos>();
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
			        if (empty) {
			            setGraphic(null);
			        } else {
			        	comboBox.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			            comboBox.setEditable(true);
			            comboBox.setMaxWidth(Double.MAX_VALUE);
			            comboBox.setPrefWidth(Double.MAX_VALUE);
			            
			            comboBox.getItems().clear();
			            carregarNomesComboBox(comboBox);
			            comboBox.setValue(item);
			            setGraphic(comboBox);

			            comboBox.setOnAction(event -> {
			                String nomeTCValueCB = comboBox.getValue();
			                med = getTableView().getItems().get(getIndex());
			                med.setNomeMed(nomeTCValueCB);
			                updateLine(nomeTCValueCB, med, numeric);			                
			                numeric++;
			            });
			        }
			    }			
		});
		quantTC.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		loteTC.setCellFactory(TextFieldTableCell.forTableColumn());
		
		listOfMed.add(medVazio);
		tableAjusteWindowTV.setItems(listOfMed);//um incluir de primeiro pra nao nvolver o arraylist

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
		String query = "select med.idmedicamento, med.quantidade, med.nome, med.validade, l.lote, c.classif\r\n"
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
    		Integer quantityQuery = rs.getInt("quantidade");
			Date validadeMedQuery = rs.getDate("validade");
			String loteMedQuery = rs.getString("lote");
			String classifMedQuery = rs.getString("classif");
			
			med = new medicamentos(idMedQuery, validadeMedQuery, classifMedQuery, loteMedQuery);
			
			listOfMed.set(numeric, med);
			ajusteMed.add(med);
			
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
	
	@FXML
	public void nextTableItem() {
		tableAjusteWindowTV.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode().equals(KeyCode.E)){
					listOfMed.add(medVazio);
				}
				
			}//endHandler
		});
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
	
	public Integer selectNmrAjusteWindow() {
		Integer id = null;
		 
		try {
	        conn = DB.getConnection();
	        
        	String query = "select max(IDAJUSTEWINDOW) from ajustewindow;";
        	
        	st = conn.prepareStatement(query);
        	rs = st.executeQuery();
        	
        	if(rs.next()) {
        		id = rs.getInt("ultimoID");
        	}
     	      
	    }catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Erro: " + e.getMessage());
	    }finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		}
		if(id == null) {
			return 0;
		}else {
			return id;
		}
	}
	
	public Integer selectQtd(int idMed) {
		Integer qtd = null;
		 
		try {
	        conn = DB.getConnection();
	        
        	String query = "SELECT QUANTIDADE FROM MEDICAMENTO WHERE IDMEDICAMENTO = ?";
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, idMed);
        	rs = st.executeQuery();
        	
        	if(rs.next()) {
        		qtd = rs.getInt("QUANTIDADE");
        	}
     	      
	    }catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Erro: " + e.getMessage());
	    }
		if(qtd == null) {
			return 0;
		}else {
			return qtd;
		}
	}
	
	public void save(ActionEvent event) throws SQLException { //botao de salvar
		try {
	        stage = (Stage) tableAjusteWindowTV.getScene().getWindow();
	        
	        conn = DB.getConnection();
	        
	        if(acaoFeita.getValue() == "Entrada") {
	        	for(int j = 0; j < ajusteMed.size(); j++) {
		        	String query = "UPDATE medicamento\r\n"
		        			+ "set quantidade = "+ (ajusteMed.get(j).getQuantidade() + selectQtd(ajusteMed.get(j).getIdMed())) +" where idmedicamento = "+ ajusteMed.get(j).getIdMed() +";";
		        	
		        	st = conn.prepareStatement(query);
		        	st.executeUpdate();
		        	
		        }
	        }
	        else if(acaoFeita.getValue() == "Saida"){
	        	for(int j = 0; j < ajusteMed.size(); j++) {
		        	String query = "UPDATE medicamento\r\n"
		        			+ "set quantidade = ? where idmedicamento = ?;";
		        	
		        	st = conn.prepareStatement(query);
		        	st.setInt(1, (selectQtd(ajusteMed.get(j).getIdMed())) - ajusteMed.get(j).getQuantidade());
		        	st.setInt(2, ajusteMed.get(j).getIdMed());
		        	int rowsAffected = st.executeUpdate();
		        	
		        }
	        }	        	      
	        
	        nmrAjuste();
	        stage.close();
	    }catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Erro: " + e.getMessage());
	    }finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		}
	}
	
	public void onEditChargedQtd(TableColumn.CellEditEvent<medicamentos, Integer> medIntegerCellEditEvent) {
		 TablePosition<medicamentos, ?> pos = tableAjusteWindowTV.getFocusModel().getFocusedCell();
         int currentRow = pos.getRow();
		 ajusteMed.set(
				 currentRow,
				 new medicamentos(
						 ajusteMed.get(currentRow).getIdMed(),
						 medIntegerCellEditEvent.getNewValue(), 
						 ajusteMed.get(currentRow).getValidade(), 
						 ajusteMed.get(currentRow).getNomeClassificacao(), 
						 ajusteMed.get(currentRow).getCodLote()
						 ));
		 medicamentos med = listOfMed.get(currentRow);
		 med.setQuantidade(medIntegerCellEditEvent.getNewValue());
		 tableAjusteWindowTV.refresh();
		 quantTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Integer>("quantidade"));
    }
    public void onEditChargedLote(TableColumn.CellEditEvent<medicamentos, String> medStringCellEditEvent) {
    }
}