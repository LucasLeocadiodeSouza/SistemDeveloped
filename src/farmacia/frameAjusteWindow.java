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
import javax.swing.JOptionPane;
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
    private Button exitButton;
    @FXML
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Stage stage;
    LocalDateTime dataHoraAtual;
    ArrayList<medicamentos> ajusteMed;
    medicamentos med;
    
    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    
   ObservableList<medicamentos> listOfMed = FXCollections.observableArrayList();
   medicamentos medVazio = new medicamentos((Integer)null, "", (Integer)null, null, "", "");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ajusteMed = new ArrayList<medicamentos>();
		acaoFeita.getItems().addAll(vAcaoFeita);
		setor.getItems().addAll(vSetor);
		selectNmrAjusteWindow();
		
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
			                tableAjusteWindowTV.getSelectionModel().select(getIndex());
			                updateLine(nomeTCValueCB, med, getIndex());
			            });
			        }
			    }
		});
		quantTC.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		loteTC.setCellFactory(TextFieldTableCell.forTableColumn());
		
		listOfMed.add(medVazio);
		tableAjusteWindowTV.setItems(listOfMed);//um incluir de primeiro pra nao envolver o arraylist

	}//end initialize
	
	public void selectNmrAjusteWindow() {
		Integer id = null;
		 
		try {
	        conn = DB.getConnection();
	        
        	String query = "select max(IDAJUSTE) as ultimoID from ajuste;";
        	
        	st = conn.prepareStatement(query);
        	rs = st.executeQuery();
        	
        	if(rs.next()) {
        		id = rs.getInt("ultimoID");
        	}
        	Integer nmrAjt = id + 1;
        	nAjuste.setText(nmrAjt.toString());
        	
	    }catch (Exception e) {
	    	 JOptionPane.showMessageDialog(null, e.getMessage());
	    }finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		}
	}//end selectNmrAjusteWindow
	
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
			JOptionPane.showMessageDialog(null, e2.getMessage());
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
			
			med = new medicamentos(idMedQuery, null, validadeMedQuery, classifMedQuery, loteMedQuery);
			
			listOfMed.set(numeric, med);
			ajusteMed.add(med);
			
			codigoTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Integer>("idMed"));
    		validadeTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Date>("validade"));
    		loteTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("codLote"));
    		classificacaoTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("nomeClassificacao"));
			
			tableAjusteWindowTV.refresh();
    	}}catch (SQLException e2) {
    		JOptionPane.showMessageDialog(null, e2.getMessage());
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
					
				}else if(arg0.isControlDown() && arg0.getCode().equals(KeyCode.DELETE)){
					@SuppressWarnings("unchecked")
					TablePosition<medicamentos, ?> pos = tableAjusteWindowTV.getFocusModel().getFocusedCell();
			        int currentRow = pos.getRow();
			        
			        if(listOfMed.size() > 1) {
			        	listOfMed.remove(currentRow);
			        }			       
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
			
			st.setString(1, descricaoTF.getText());
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
		    		+ "(ACAO, HR_ADICIONADA, SETOR, ID_AJUSTE) VALUES" + 
					" (?, ?, ?, ?);"
					);

			st.setString(1, acaoFeita.getValue());
			st.setDate(2,new java.sql.Date(sdf.parse(hrAdd.getText()).getTime()));
			st.setString(3, setor.getValue());
			st.setInt(4, generatedId);
			st.executeUpdate();
			
		}catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
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
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    }
		if(qtd == null) {
			return 0;
		}else {
			return qtd;
		}
	}
	
	public void save(ActionEvent event) throws SQLException { 
		try {
	        stage = (Stage) tableAjusteWindowTV.getScene().getWindow();
	        
	        conn = DB.getConnection();
	        
	        if(acaoFeita.getValue() == "Entrada") {
	        	for(int j = 0; j < ajusteMed.size(); j++) {
		        	String query = "UPDATE medicamento\r\n"
		        			+ "set quantidade = " + (ajusteMed.get(j).getQuantidade() + selectQtd(ajusteMed.get(j).getIdMed())) + " where idmedicamento = " + ajusteMed.get(j).getIdMed()+ " ;";
		        	
		        	st = conn.prepareStatement(query);
		        	st.executeUpdate();
		        }
	        	nmrAjuste();
		        stage.close();
	        }else if(acaoFeita.getValue() == "Saida"){
	        	for(int j = 0; j < ajusteMed.size(); j++) {
	        		String query = "UPDATE medicamento\r\n"
		        			+ "set quantidade = " + (selectQtd(ajusteMed.get(j).getIdMed()) - ajusteMed.get(j).getQuantidade()) + " where idmedicamento = " + ajusteMed.get(j).getIdMed()+ " ;";
		        	st = conn.prepareStatement(query);
		        	@SuppressWarnings("unused")
					int rowsAffected = st.executeUpdate();		        	
		        }
	        	nmrAjuste();
			    stage.close();
	        }else {
	        	JOptionPane.showMessageDialog(null, "Campo ACAO não inserido", "Erro!!", JOptionPane.ERROR_MESSAGE);
	        }
	    }catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage());
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
		 @SuppressWarnings("unchecked")
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
}