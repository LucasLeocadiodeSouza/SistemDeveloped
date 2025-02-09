/*
 	Voltar quando a Parte de Usuario estiver completa para Adicionar o Setor e Id do Usuario 
*/

package  com.cestec.farmacia;

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

import com.cestec.connectSQL.DB;
import com.cestec.medicamentos.medicamentos;
import com.cestec.parametros.prm001;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class frameReqSetor implements Initializable{

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
    private TableView<medicamentos> reqSetorTV;
    @FXML
    private Button sairB;
    @FXML
    private Button salvarB;
    @FXML
    private TextField setorTF;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private medicamentos med;
    private Stage stage;

    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    private ArrayList<medicamentos> ajusteMed;
    private ObservableList<medicamentos> listOfMed = FXCollections.observableArrayList();
    private Integer generatedId = 0;
	private String medSelected;
        

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    	ajusteMed = new ArrayList<medicamentos>();
    	listOfMed.add(new medicamentos((Integer)null, "", (Integer)null, null, "", ""));
    	centEstocadoTC.getItems().addAll(vCentEstocadorTC);
    	reqSetorTV.setEditable(true);
    
    	dataTF.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    	
    	selectNmrReqWindow();    	
    	prm001.nextTableItem(reqSetorTV, 
							 listOfMed, 
							 new medicamentos("", null, "", null, ""));


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
			            prm001.carregarNomesChoiceBox(setaCB);
			            setGraphic(setaCB);

			            setaCB.setOnAction(event -> {
			                medSelected = setaCB.getValue();
			                med = getTableView().getItems().get(getIndex());
			                reqSetorTV.getSelectionModel().select(getIndex());
			                updateLine(medSelected, med, getIndex());
			            });
			        }
			    }
		});
    	codTC.setCellFactory    (TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    	nomeTC.setCellFactory   (TextFieldTableCell.forTableColumn());
		quantTC.setCellFactory  (TextFieldTableCell.forTableColumn(new IntegerStringConverter())); 
    	medidaTC.setCellFactory (TextFieldTableCell.forTableColumn());    
    	classifTC.setCellFactory(TextFieldTableCell.forTableColumn());
    	
    	reqSetorTV.setItems(listOfMed);    	
    }


	@FXML
	public void tableView_pressed(){
		prm001.nextTableItem(reqSetorTV,
							 listOfMed,
							 new medicamentos(null, 
							 	              (Integer)null,
										         "", 
											  (Integer)null,
									   ""));

	    reqSetorTV.refresh();
	}

    
    public void selectNmrReqWindow() {
		Integer id = null;
		 
		try {
	        conn = DB.getConnection();
	        
        	String query = "select max(IDREQSETMED) as ultimoID from reqsetormed;";
        	
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
    			+ "where idmedicamento = ?;";
    	
    	st = conn.prepareStatement(query);
    	st.setString(1, prm001.getElemento(1, medSelected));

    	rs = st.executeQuery();
    	
    	if(rs.next()) {
    		Integer idMedQuery     = rs.getInt   ("idmedicamento");
    		String nomeMedQuery    = rs.getString("nome");
    		String medidaQuery     = rs.getString("medida");
			String classifMedQuery = rs.getString("classif");
			
			med = new medicamentos(nomeMedQuery, null, medidaQuery, idMedQuery, classifMedQuery);
			
			listOfMed.set(numeric, med);
			ajusteMed.add(med);			
			
			codTC.setCellValueFactory    (new PropertyValueFactory<medicamentos, Integer>("idMed"));
			nomeTC .setCellValueFactory  (new PropertyValueFactory<medicamentos, String>("nomeMed"));
			classifTC.setCellValueFactory(new PropertyValueFactory<medicamentos, String>("nomeClassificacao"));
			medidaTC.setCellValueFactory (new PropertyValueFactory<medicamentos, String>("medida"));
			
			reqSetorTV.refresh();

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
	
	public void save(ActionEvent event) throws SQLException {
		try {
	        stage = (Stage) reqSetorTV.getScene().getWindow();	        
	        conn = DB.getConnection();	 

	        StringBuilder listProd = new StringBuilder();
	        StringBuilder  listQtd = new StringBuilder();
	        
	        for(int j = 0; j < listOfMed.size(); j++) {
        		medicamentos mds = new medicamentos(reqSetorTV.getItems().get(j).getNomeMed() ,
					        					 	reqSetorTV.getItems().get(j).getQuantidade(),
					        					 	reqSetorTV.getItems().get(j).getMedida(),
					        					 	reqSetorTV.getItems().get(j).getIdMed(),
					        					    reqSetorTV.getItems().get(j).getNomeClassificacao()
					        					    );
        		
        		if(j == listOfMed.size() - 1) {        			
        			listProd.append(mds.getIdMed());        		
            		listQtd.append(mds.getQuantidade());            		
        		}else {
        			listProd.append(mds.getIdMed()).append(",");        		
        			listQtd.append(mds.getQuantidade()).append(",");
        		}  		        		
        	}
	        
        	prm001.nmrReqSetorMed(listProd, listQtd, new java.sql.Date(sdf.parse(dataTF.getText()).getTime()));        	
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
		 TablePosition<medicamentos, ?> pos = reqSetorTV.getFocusModel().getFocusedCell();
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
		 reqSetorTV.refresh();
		 quantTC.setCellValueFactory(new PropertyValueFactory<medicamentos, Integer>("quantidade"));
   }	
}