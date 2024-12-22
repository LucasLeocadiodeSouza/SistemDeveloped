package farmacia;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import connectSQL.DB;
import consolidacao.Consolidacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class frameConsol implements Initializable{

    @FXML
    private TableColumn<Consolidacao, Integer> codTC;
    @FXML
    private TextField codTF;
    @FXML
    private TableColumn<Consolidacao, String> descTC;
    @FXML
    private TableColumn<Consolidacao, String> consolTC;
    @FXML
    private TableColumn<Consolidacao, Date> dtAberturaTC;
    @FXML
    private DatePicker dtFinalDB;
    @FXML
    private DatePicker dtInicioDP;
    @FXML
    private Label setorL;
    @FXML
    private CheckBox prestadorCB;
    @FXML
    private CheckBox setorCB;
    @FXML
    private Button pesqB;
    @FXML
    private TableView<Consolidacao> requisicoesTV;
    @FXML
    private Button sairB;
    @FXML
    private Button salvarB;
    @FXML
    private ChoiceBox<String> solicitanteCB;
    @FXML
    private TableColumn<Consolidacao, String> solicitanteTC;
    @FXML
    private ChoiceBox<String> situacaoCB;
    private String[] vSituacao = {"Sim","Nao","Parcial"};

    Stage stage;

    Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    ObservableList<Consolidacao> listOfConsol = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        situacaoCB.getItems().addAll(vSituacao);
	
        
		requisicoesTV.setItems(listOfConsol);
	}

    public void filtroTipo(){
        
    }

    public void filtroSituacao(Integer situacao){

    }

    public void filtroSetor(Integer codSetor){

    }

    public void filtroCod (Integer codTF){
        
    }

    public void filtroData(DatePicker dtInicio, DatePicker dtFinal){        
        try {
            conn = DB.getConnection();
            
            if (prestadorCB.isSelected()){                
                String query = "SELECT * FROM reqprestmed WHERE\r\n" + //
                            "DATAREQ <= ? AND DATAREQ >= ?; ";
                
                st.setString(1, dtInicio.toString());
                st.setString(2, dtFinal.toString());
                
                st = conn.prepareStatement(query);
                rs = st.executeQuery();

                while(rs.next()){
                    Integer idprestmedQuery  = rs.getInt("IDPRESTMED");
                    String listmedQuery      = rs.getString("LISTMED");
                    Integer idprestadorQuery = rs.getInt("ID_PRESTADOR");
                    String consolidadoQuery  = rs.getString("consolidado");
                    Date datareqQuery        = rs.getDate("DATAREQ");

                    Consolidacao consol = new Consolidacao(consolidadoQuery, idprestmedQuery, listmedQuery, idprestadorQuery, datareqQuery);
                    listOfConsol.add(consol);

                    codTC.setCellValueFactory        (new PropertyValueFactory<Consolidacao, Integer>("id"));
                    consolTC.setCellValueFactory     (new PropertyValueFactory<Consolidacao, String> ("situacao"));
                    descTC.setCellValueFactory       (new PropertyValueFactory<Consolidacao, String> ("descricao"));
                    solicitanteTC.setCellValueFactory(new PropertyValueFactory<Consolidacao, String> ("idPrest"));
                    dtAberturaTC.setCellValueFactory (new PropertyValueFactory<Consolidacao, Date>   ("dataReq"));

                    requisicoesTV.refresh();
                }
            }else if(setorCB.isSelected()){
                String query = "SELECT * FROM reqsetormed WHERE\r\n" + //
                            "DATAREQ <= ? AND DATAREQ >= ?; ";
                
                st.setString(1, dtInicio.toString());
                st.setString(2, dtFinal.toString());
                st = conn.prepareStatement(query);
                rs = st.executeQuery();

                while(rs.next()){
                    Integer idreqsetorQuery = rs.getInt("IDREQSETMED");
                    String listmedQuery     = rs.getString("LISTMED");
                    Integer idsetorQuery    = rs.getInt("ID_SETOR");
                    String consolidadoQuery = rs.getString("consolidado");
                    Date datareqQuery       = rs.getDate("DATAREQ");

                    Consolidacao consol = new Consolidacao(idreqsetorQuery, consolidadoQuery, listmedQuery, idsetorQuery, datareqQuery);
                    listOfConsol.add(consol);

                    codTC.setCellValueFactory        (new PropertyValueFactory<Consolidacao, Integer>("id"));
                    consolTC.setCellValueFactory     (new PropertyValueFactory<Consolidacao, String> ("situacao"));
                    descTC.setCellValueFactory       (new PropertyValueFactory<Consolidacao, String> ("descricao"));
                    solicitanteTC.setCellValueFactory(new PropertyValueFactory<Consolidacao, String> ("idSetor"));
                    dtAberturaTC.setCellValueFactory (new PropertyValueFactory<Consolidacao, Date>   ("dataReq"));

                    requisicoesTV.refresh();
                }
            }

        } catch (Exception e) {
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
    
    public void carregarReq(){
        try {
            conn = DB.getConnection();

            


        } catch (Exception e) {
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
    public void pesquisar(ActionEvent event){
        try {
            conn = DB.getConnection();
            
            if (prestadorCB.isSelected()){                
                String query = "SELECT * FROM reqprestmed WHERE IDPRESTMED = 2";  
                st = conn.prepareStatement(query);              
                rs = st.executeQuery();

                while(rs.next()){
                    Integer idprestmedQuery  = rs.getInt("IDPRESTMED");
                    String listmedQuery      = rs.getString("LISTMED");
                    Integer idprestadorQuery = rs.getInt("ID_PRESTADOR");
                    String consolidadoQuery  = rs.getString("consolidado");
                    Date datareqQuery        = rs.getDate("DATAREQ");

                    Consolidacao consol = new Consolidacao(consolidadoQuery, idprestmedQuery, listmedQuery, idprestadorQuery, datareqQuery);
                    listOfConsol.add(consol);

                    codTC.setCellValueFactory        (new PropertyValueFactory<Consolidacao, Integer>("id"));
                    consolTC.setCellValueFactory     (new PropertyValueFactory<Consolidacao, String> ("situacao"));
                    descTC.setCellValueFactory       (new PropertyValueFactory<Consolidacao, String> ("descricao"));
                    solicitanteTC.setCellValueFactory(new PropertyValueFactory<Consolidacao, String> ("idPrest"));
                    dtAberturaTC.setCellValueFactory (new PropertyValueFactory<Consolidacao, Date>   ("dataReq"));

                    requisicoesTV.refresh();
                }
            }if(setorCB.isSelected()){
                String query = "SELECT * FROM reqsetormed WHERE IDREQSETMED = 4";
                st = conn.prepareStatement(query);     
                rs = st.executeQuery();

                while(rs.next()){
                    Integer idreqsetorQuery = rs.getInt("IDREQSETMED");
                    String listmedQuery     = rs.getString("LISTMED");
                    Integer idsetorQuery    = rs.getInt("ID_SETOR");
                    String consolidadoQuery = rs.getString("consolidado");
                    Date datareqQuery       = rs.getDate("DATAREQ");

                    Consolidacao consol = new Consolidacao(idreqsetorQuery, consolidadoQuery, listmedQuery, idsetorQuery, datareqQuery);
                    listOfConsol.add(consol);

                    codTC.setCellValueFactory        (new PropertyValueFactory<Consolidacao, Integer>("id"));
                    consolTC.setCellValueFactory     (new PropertyValueFactory<Consolidacao, String> ("situacao"));
                    descTC.setCellValueFactory       (new PropertyValueFactory<Consolidacao, String> ("descricao"));
                    solicitanteTC.setCellValueFactory(new PropertyValueFactory<Consolidacao, String> ("idSetor"));
                    dtAberturaTC.setCellValueFactory (new PropertyValueFactory<Consolidacao, Date>   ("dataReq"));

                    requisicoesTV.refresh();
                }
            }

        } catch (Exception e) {
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
	public void consolInterna() {
		requisicoesTV.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode().equals(KeyCode.E)){		
					//chama consol interna
				}				
			}//endHandler
		});
	}

    @FXML
    public void save(ActionEvent event){

    }

    @FXML
    public void exit(ActionEvent event){
        
    }

}