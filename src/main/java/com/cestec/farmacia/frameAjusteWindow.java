package  com.cestec.farmacia;

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

import com.cestec.connectSQL.DB;
import com.cestec.medicamentos.medicamentos;
import com.cestec.parametros.prm001;
import com.cestec.parametros.prmv003;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class frameAjusteWindow implements Initializable{

    @FXML
    private ComboBox<String> acaoFeita;
    private String[] vAcaoFeita = {"Entrada", "Saída"};

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
    private TableColumn<medicamentos, String> selectTC;    
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

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Stage stage;
	
    @SuppressWarnings("unused")
	private LocalDateTime dataHoraAtual;
    private ArrayList<medicamentos> ajusteMed;
    private medicamentos med;
	private String medSelected;
    
	private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    
	private ObservableList<medicamentos> listOfMed = FXCollections.observableArrayList();
	private medicamentos medVazio = new medicamentos((Integer)null, "", (Integer)null, null, "", "");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ajusteMed = new ArrayList<medicamentos>();
		acaoFeita.getItems().addAll(vAcaoFeita);
		setor.getItems().addAll(vSetor);

		hrAdd.setText(prm001.getDataNow());
		selectNmrAjusteWindow();
		
		tableAjusteWindowTV.setEditable(true);
		dataHoraAtual = LocalDateTime.now();
		
		listOfMed.add(medVazio);

		selectTC.setCellFactory(column -> new TableCell<medicamentos, String>(){
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
						tableAjusteWindowTV.getSelectionModel().select(getIndex());
						updateLine(medSelected, med, getIndex());
					});
				}
			}
		});
		nomeTC.setCellFactory (TextFieldTableCell.forTableColumn());
		quantTC.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		loteTC.setCellFactory (TextFieldTableCell.forTableColumn());
				
		tableAjusteWindowTV.setItems(listOfMed);//um incluir de primeiro pra nao envolver o arraylist

	}//end initialize
	

	@FXML
	public void tableView_pressed(){
		prm001.nextTableItem(tableAjusteWindowTV,
							 listOfMed,
							 new medicamentos((Integer)null,
							 				   "", 
							 				  (Integer)null, 
							 				  null, 
							 				  "", 
							 				  ""));

	    tableAjusteWindowTV.refresh();
	}


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
	
	
	
	public void updateLine(String nomeProd, medicamentos med, int numeric) {
		try {
		conn = DB.getConnection();

		String query = "select med.idmedicamento, med.nome, med.validade, l.lote, c.classif\r\n"
    			+ "from medicamento med \r\n"
    			+ "inner join lote l\r\n"
    			+ "on med.IDMEDICAMENTO = l.ID_MEDICAMENTO\r\n"
    			+ "inner join classificacao c\r\n"
    			+ "on med.IDMEDICAMENTO = c.ID_MEDICAMENTO\r\n"
    			+ "where idmedicamento = ?;";
    	
    	st = conn.prepareStatement(query);
    	st.setString(1, prm001.getElemento(1, medSelected));
    	rs = st.executeQuery();
    	
    	if(rs.next()) {
    		Integer idMedQuery       = rs.getInt   ("idmedicamento");
			String  nomeMedQuery     = rs.getString("nome");
			Date    validadeMedQuery = rs.getDate  ("validade");
			String  loteMedQuery     = rs.getString("lote");
			String  classifMedQuery  = rs.getString("classif");
			
			med = new medicamentos(idMedQuery, 
									nomeMedQuery, 
									null,
									validadeMedQuery, 
									classifMedQuery, 
									loteMedQuery);
			
			listOfMed.set(numeric, med);
			ajusteMed.add(med);
			
			codigoTC.setCellValueFactory		(new PropertyValueFactory<medicamentos, Integer>("idMed"));
			nomeTC.setCellValueFactory			(new PropertyValueFactory<medicamentos, String>("nomeMed"));
    		validadeTC.setCellValueFactory		(new PropertyValueFactory<medicamentos, Date>("validade"));
    		loteTC.setCellValueFactory			(new PropertyValueFactory<medicamentos, String>("codLote"));
    		classificacaoTC.setCellValueFactory (new PropertyValueFactory<medicamentos, String>("nomeClassificacao"));
			
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
	
	public void nmrAjuste() {
		try {
			conn = DB.getConnection();
			
			//INSERT INTO AJUSTE
			st = conn.prepareStatement("INSERT INTO AJUSTE (DESCRICAO, ACAO) VALUES" + 
					" (?,?);", Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, descricaoTF.getText());
			st.setInt   (2, prm001.getAcaoAjuste(acaoFeita.getValue().toLowerCase()));
			
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
					  "(?, ?, ?, ?);"
					);

			st.setInt   (1, prm001.getAcaoAjuste(acaoFeita.getValue().toLowerCase()));
			st.setDate	(2, new java.sql.Date(sdf.parse(hrAdd.getText()).getTime()));
			st.setString(3, setor.getValue());
			st.setInt	(4, generatedId);
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
	
	@FXML
	public void validacao(){
		String validacao = prmv003.validaData(hrAdd.getText());
		if(validacao.equals("erro")){
			prmv003.validaData(hrAdd.getText());
			saveButton.setDisable(true);
		}
	}
	
	@FXML
	public void exit(ActionEvent event){
		stage = (Stage) tableAjusteWindowTV.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void save(ActionEvent event) throws SQLException { 
		try {
	        stage = (Stage) tableAjusteWindowTV.getScene().getWindow();	        			

	        conn = DB.getConnection();

	        if(acaoFeita.getValue() == "Entrada") {
	        	for(int j = 0; j < ajusteMed.size(); j++) {

					Integer qtdAtual = (ajusteMed.get(j).getQuantidade() + prm001.selectQtd(ajusteMed.get(j).getIdMed()));
		        	prm001.atualizarQtd(ajusteMed.get(j).getIdMed(), qtdAtual); 					
		        	
		        }
	        	nmrAjuste();		        

	        }else if(acaoFeita.getValue() == "Saída"){
	        	for(int j = 0; j < ajusteMed.size(); j++) {
	        		Integer qtdAtual = (prm001.selectQtd(ajusteMed.get(j).getIdMed()) - ajusteMed.get(j).getQuantidade()); 
					prm001.atualizarQtd(ajusteMed.get(j).getIdMed(), qtdAtual);
		        }
	        	nmrAjuste();			    
	        }else {
	        	JOptionPane.showMessageDialog(null, "Campo ACAO não inserido", "Erro!!", JOptionPane.ERROR_MESSAGE);
	        }			

			exit(event);

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