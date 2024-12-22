/*
 	Voltar quando a Parte de Usuario estiver completa para Adicionar o Setor e Id do Usuario 
*/

package farmacia;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ChoiceBox;
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

public class frameReqPrest implements Initializable{

    @FXML
    private ChoiceBox<String> centEstocadoTC;
    private String[] vCentEstocadorTC = {"Farmacia"};

    @FXML
    private TableColumn<medicamentos, String> classifTC;
    @FXML
    private TextField codTF;
    @FXML
    private TableColumn<medicamentos, Integer> codTC;
    @FXML
    private TableColumn<medicamentos, String> choiceTC;
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
    private TableView<medicamentos> reqPresTV;
    @FXML
    private Button sairB;
    @FXML
    private Button salvarB;
    @FXML
    private ComboBox<String> prestCB;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private medicamentos med;
    private Stage stage;

    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    
    private ArrayList<medicamentos> ajusteMed;
    private ObservableList<medicamentos> listOfMed = FXCollections.observableArrayList();
    private Integer generatedId = 0;
        

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	ajusteMed = new ArrayList<medicamentos>();
    	listOfMed.add(new medicamentos((Integer)null, "", (Integer)null, null, "", ""));
    	centEstocadoTC.getItems().addAll(vCentEstocadorTC);
    	reqPresTV.setEditable(true);
    
    	dataTF.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    	
    	selectNmrReqWindow(); 
    	carregarPrestadores(prestCB);
    	
    	choiceTC.setCellFactory(column -> new TableCell<medicamentos, String>(){
			private final ChoiceBox<String> setaCB = new ChoiceBox<>();
			
			 @Override
			    protected void updateItem(String item, boolean empty) {
			        super.updateItem(item, empty);
			        if (empty) {
			            setGraphic(null);
			        } else {
			        	setaCB.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			            
			        	setaCB.getItems().clear();
			            carregarNomesChoiceBox(setaCB);
			            setGraphic(setaCB);

			            setaCB.setOnAction(event -> {
			                String nomeTCValueCB = setaCB.getValue();
			                med = getTableView().getItems().get(getIndex());
			                med.setNomeMed(nomeTCValueCB);
			                reqPresTV.getSelectionModel().select(getIndex());
			                updateLine(nomeTCValueCB, med, getIndex());
			            });
			        }
			    }
		});
    	codTC.setCellFactory    (TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    	nomeTC.setCellFactory   (TextFieldTableCell.forTableColumn());
		quantTC.setCellFactory  (TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
    	medidaTC.setCellFactory (TextFieldTableCell.forTableColumn());    
    	classifTC.setCellFactory(TextFieldTableCell.forTableColumn());
    	
    	reqPresTV.setItems(listOfMed);
    }
    
    public void carregarNomesChoiceBox(ChoiceBox<String> choiceBox) {
		try{
	    	conn = DB.getConnection();

	    	String query = "select nome from medicamento;";

	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();

	    	while(rs.next()) {
	    		choiceBox.getItems().add(rs.getString("nome"));	
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
    
    public void carregarPrestadores(ComboBox<String> prestadores) {
    	try{
	    	conn = DB.getConnection();

	    	String query = "select nomeprest from PRESTADOR;";

	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();
	    	
	    	prestadores.getItems().clear();
	    
	    	while(rs.next()) {
	    		prestadores.getItems().add(rs.getString("nomeprest"));	
	    	}//end while
			
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar Prestadores" + e2.getMessage());
		}finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		} 
    }
    
    public void selectNmrReqWindow() {
		Integer id = null;
		 
		try {
	        conn = DB.getConnection();
	        
        	String query = "select max(IDPRESTMED) as ultimoID from reqprestmed;";
        	
        	st = conn.prepareStatement(query);
        	rs = st.executeQuery();
        	
        	if(rs.next()) {
        		id = rs.getInt("ultimoID");
        	}
        	generatedId = id + 1;
        	codTF.setText(generatedId.toString());
        	
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
	}//end selectNmrReqWindow
    
    public void updateLine(String nomeProd, medicamentos med, int numeric) {
		try {
		conn = DB.getConnection();

		String query = "select med.idmedicamento, med.quantidade, med.nome, med.medida, med.validade, c.classif\r\n"
    			+ "from medicamento med \r\n"
    			+ "inner join classificacao c\r\n"
    			+ "on med.IDMEDICAMENTO = c.ID_MEDICAMENTO\r\n"
    			+ "where nome = ?;";
    	
    	st = conn.prepareStatement(query);
    	st.setString(1, nomeProd);
    	rs = st.executeQuery();
    	
    	if(rs.next()) {
    		Integer idMedQuery     = rs.getInt   ("idmedicamento");
    		String nomeMedQuery    = rs.getString("nome");
    		String medidaQuery 	   = rs.getString("medida");
			String classifMedQuery = rs.getString("classif");
			
			med = new medicamentos(nomeMedQuery, 
								   null, 
								   medidaQuery, 
								   idMedQuery, 
								   classifMedQuery);
			
			listOfMed.set(numeric, med);
			ajusteMed.add(med);			
			
			codTC.setCellValueFactory	 (new PropertyValueFactory<medicamentos, Integer>("idMed"));
			nomeTC .setCellValueFactory	 (new PropertyValueFactory<medicamentos, String>("nomeMed"));
			classifTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("nomeClassificacao"));
			medidaTC.setCellValueFactory (new PropertyValueFactory<medicamentos, String>("medida"));
			
			reqPresTV.refresh();

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
		reqPresTV.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode().equals(KeyCode.E)){
					listOfMed.add(new medicamentos((Integer)null, "", (Integer)null, null, "", ""));
					
				}else if(arg0.isControlDown() && arg0.getCode().equals(KeyCode.DELETE)){
					@SuppressWarnings("unchecked")
					TablePosition<medicamentos, ?> pos = reqPresTV.getFocusModel().getFocusedCell();
			        int currentRow = pos.getRow();
			        
			        if(listOfMed.size() > 1) {
			        	listOfMed.remove(currentRow);
			        }			       
				}				
			}//endHandler
		});
	}
	
	public void nmrReqPrestMed(StringBuilder listProdutos, StringBuilder listQuant) {
		try {
			conn = DB.getConnection();

			//INSERT INTO REQSETORMED
		    st = conn.prepareStatement("INSERT INTO reqPrestMed "
		    		+ "(DATAREQ, LISTMED, LISTQTD, ID_PRESTADOR, ID_USUARIOS, Consolidado) VALUES" + 
					  " (?, ?, ?, ?, ?, ?);"
					);

			st.setDate   (1, new java.sql.Date(sdf.parse(dataTF.getText()).getTime()));
			st.setString (2, listProdutos.toString());
			st.setString (3, listQuant.toString());
			st.setInt    (4, prestCB.getSelectionModel().getSelectedIndex() + 1);
			//st.setString(5, "FAZER UM ESTATICO NO FRAME DE LOGIN");
			st.setInt    (5, 2);
			st.setString (6, "nao");

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
	
	public void save(ActionEvent event) throws SQLException {
		try {
	        stage = (Stage) reqPresTV.getScene().getWindow();	        
	        conn = DB.getConnection();	        

	        StringBuilder listProd = new StringBuilder();
	        StringBuilder listQtd  = new StringBuilder();
	        
	        for(int j = 0; j < listOfMed.size(); j++) {
        		medicamentos mds = new medicamentos(reqPresTV.getItems().get(j).getNomeMed() ,
        											reqPresTV.getItems().get(j).getQuantidade(),
							        				reqPresTV.getItems().get(j).getMedida(),
							        				reqPresTV.getItems().get(j).getIdMed(),
							        				reqPresTV.getItems().get(j).getNomeClassificacao()
					        					    );
        		
        		if(j == listOfMed.size() - 1) {        			
        			listProd.append(mds.getIdMed());        		
            		listQtd.append(mds.getQuantidade());            		
        		}else {
        			listProd.append(mds.getIdMed()).append(",");        		
        			listQtd.append(mds.getQuantidade()).append(",");
        		}  		        		
        	}
	        
        	nmrReqPrestMed(listProd, listQtd);        	
	        stage.close();	        
	        
	    }catch (Exception e) {
	    	e.getMessage();
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
		 TablePosition<medicamentos, ?> pos = reqPresTV.getFocusModel().getFocusedCell();
         int currentRow = pos.getRow();

		 ajusteMed.set(
				 currentRow,
				 new medicamentos(
						 ajusteMed.get(currentRow).getNomeMed(),						 
						 medIntegerCellEditEvent.getNewValue(),
						 ajusteMed.get(currentRow).getMedida(),
						 ajusteMed.get(currentRow).getIdMed(),
						 ajusteMed.get(currentRow).getNomeClassificacao() 
						 ));

		 medicamentos med = listOfMed.get(currentRow);
		 med.setQuantidade(medIntegerCellEditEvent.getNewValue());
		 
		 reqPresTV.refresh();
		 quantTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Integer>("quantidade"));
   }	
}