package  com.cestec.farmacia;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import com.cestec.connectSQL.DB;
import com.cestec.consolidacao.Consolidacao;
import com.cestec.parametros.prm001;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

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
    private TableColumn<Consolidacao, String> listOfQtdTC;
    @FXML
    private TableColumn<Consolidacao, Date> dtAberturaTC;
    @FXML
    private DatePicker dtFinalDP;
    @FXML
    private DatePicker dtInicioDP;
    @FXML
    private Label setorL;
    @FXML
    private RadioButton prestadorRB;
    @FXML
    private RadioButton setorRB;
    @FXML
    private Button pesqB;
    @FXML
    private TableView<Consolidacao> requisicoesTV;
    @FXML
    private Button sairB;
    @FXML
    private ChoiceBox<String> solicitanteCB;
    @FXML
    private TableColumn<Consolidacao, String> solicitanteTC;
    @FXML
    private ChoiceBox<String> situacaoCB;
    private String[] vSituacao = {"Sim","Não","Parcial"};
    private String query;

    private Stage stage;
    private Scene scene;

    private Connection  conn     = null;
    private PreparedStatement st = null;
    private ResultSet rs         = null;

    private ObservableList<Consolidacao> listOfConsol = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        situacaoCB.getItems().addAll(vSituacao);
        
        prestadorRB.isSelected();
        dtInicioDP.setValue(LocalDate.parse(prm001.getDataNow(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dtFinalDP.setValue(LocalDate.parse(prm001.getDataNow(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        situacaoCB.setValue("Não");

        prm001.carregarSetorCB(solicitanteCB);
        codTC.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		requisicoesTV.setItems(listOfConsol);

        listOfConsol.add(new Consolidacao(null, "", null,  null, null, ""));
	}

    public void carregarReqPrest(ResultSet rs){
        try {
            conn = DB.getConnection();

            while(rs.next()){
                Integer  idprestmedQuery    = rs.getInt   ("IDPRESTMED");
                String   listmedQuery       = rs.getString("LISTMED");
                String   listQtdQuery       = rs.getString("LISTQTD");
                String   nomeprestadorQuery = rs.getString("NOMEPREST");
                Integer  consolidadoQuery   = rs.getInt   ("consolidado");
                Date     datareqQuery       = rs.getDate  ("DATAREQ");

                Consolidacao consol = new Consolidacao(consolidadoQuery, listmedQuery, idprestmedQuery, nomeprestadorQuery, datareqQuery, listQtdQuery);
                listOfConsol.add(consol);

                codTC.setCellValueFactory        (new PropertyValueFactory<Consolidacao, Integer>("id"));
                consolTC.setCellValueFactory     (new PropertyValueFactory<Consolidacao, String> ("situacao"));
                descTC.setCellValueFactory       (new PropertyValueFactory<Consolidacao, String> ("descricao"));
                solicitanteTC.setCellValueFactory(new PropertyValueFactory<Consolidacao, String> ("nomePrest"));
                dtAberturaTC.setCellValueFactory (new PropertyValueFactory<Consolidacao, Date>   ("dataReq"));
                listOfQtdTC.setCellValueFactory  (new PropertyValueFactory<Consolidacao, String> ("listOfQtd"));
            }
            requisicoesTV.refresh();

        } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    }
    }

    public void carregarReqSet(ResultSet rs){
        try {
            conn = DB.getConnection();

            while(rs.next()){
                Integer idreqsetorQuery  = rs.getInt     ("IDREQSETMED");
                String listmedQuery      = rs.getString  ("LISTMED");
                String listQtdQuery      = rs.getString  ("LISTQTD");
                String nomesetorQuery    = rs.getString  ("NOMESETORES");
                Integer consolidadoQuery = rs.getInt     ("consolidado");
                Date datareqQuery        = rs.getDate    ("DATAREQ");

                Consolidacao consol = new Consolidacao(idreqsetorQuery, consolidadoQuery, listmedQuery, nomesetorQuery, datareqQuery, listQtdQuery);
                listOfConsol.add(consol);

                codTC.setCellValueFactory        (new PropertyValueFactory<Consolidacao, Integer>("id"));
                consolTC.setCellValueFactory     (new PropertyValueFactory<Consolidacao, String> ("situacao"));
                descTC.setCellValueFactory       (new PropertyValueFactory<Consolidacao, String> ("descricao"));
                solicitanteTC.setCellValueFactory(new PropertyValueFactory<Consolidacao, String> ("nomeSetor")); //esse parametro tem que estar com o get sobre camel-case e com o mesmo nome da propriedade do construtor
                dtAberturaTC.setCellValueFactory (new PropertyValueFactory<Consolidacao, Date>   ("dataReq"));
                listOfQtdTC.setCellValueFactory  (new PropertyValueFactory<Consolidacao, String> ("listOfQtd"));
                
            }
            requisicoesTV.refresh();

        } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    }
    }

    @FXML
    public void atualizarPrestOuSetor(){
        solicitanteCB.getItems().clear();
        if(setorRB.isSelected()){
            setorL.setText("Filtro Por Setor");
            prm001.carregarSetorCB(solicitanteCB);
            solicitanteCB.setValue("Todos");
        }else if(prestadorRB.isSelected()){
            setorL.setText("Filtro Por Prest");
            prm001.carregarPrestadorCB(solicitanteCB);
            solicitanteCB.setValue("Todos");
        }
    }

    public String filtroDinamico(Integer cod,
                                 String situacao,
                                 String solicitante,
                                 Integer prestOuSetor,
                                 java.util.Date periodoIni,
                                 java.util.Date periodoFim){
        boolean mtemand = false;


        if(prestOuSetor == 0){
            query = "SELECT rpm.idprestmed, rpm.listmed, rpm.listqtd, rpm.id_usuarios, pres.nomeprest, rpm.consolidado, rpm.datareq \n" + //
                    "FROM reqprestmed rpm\n" + //
                    "INNER JOIN prestador pres\n" + //
                    "on rpm.ID_PRESTADOR = pres.IDPRESTADOR \n" + //
                    "WHERE ";
            if(cod != 0){
                query += "rpm.idprestmed = " + cod;
                mtemand = true;                
            }
            if(solicitante != "Todos"){
                query += (mtemand?" AND ":"") + "rpm.id_prestador = " + solicitante;
                mtemand = true;
            }
        }else if(prestOuSetor == 1){
            query = "SELECT rsm.idreqsetmed, rsm.listmed, rsm.listqtd, rsm.id_usuarios, se.nomesetores, rsm.consolidado, rsm.datareq \n" + //
                    "FROM reqsetormed rsm\n" + //
                    "INNER JOIN setores se\n" + //
                    "on rsm.ID_SETOR = se.IDSETORES \n" + //
                    "WHERE ";
            if(cod != 0){
                query += (mtemand?" AND ":"") + "rsm.idreqsetmed = " + cod.toString();
                mtemand = true;
            }   
            if(solicitante != "Todos"){         
                query += "rsm.id_setor = " + solicitante;
                mtemand = true;
            }
        }
        

        if(situacao.equals("não")){ 
            query += (mtemand?" AND ":"") + "consolidado = 1";
            mtemand = true;
        }else if(situacao.equals("sim")){
            query += (mtemand?" AND ":"") + "consolidado = 2";
            mtemand = true;
        }else if(situacao.equals("parcial")){
            query += (mtemand?" AND ":"") + "consolidado = 3";
            mtemand = true;
        }

        query += (mtemand?" AND ":"") + "datareq >= \"" + prm001.fmtDataSQL(periodoIni) + "\" AND datareq <= \"" + prm001.fmtDataSQL(periodoFim) + "\" ;";

        return query;
    }
    
    public void refreshTableOK(ResultSet rs, int prestOuSetor){
        try{
	    	conn = DB.getConnection();

	    	if(!rs.isBeforeFirst()) {
                listOfConsol.clear();
                if(prestOuSetor == 0){
                    listOfConsol.add(new Consolidacao(null, null,"", "", null, ""));
                }else{
                    listOfConsol.add(new Consolidacao(null,  "",  null, "", null,""));
                }                                
	    	}

		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		} 
    }

    @FXML
    public void pesquisar(ActionEvent event){
        try {
            conn = DB.getConnection();     
            
            listOfConsol.clear();
            if (prestadorRB.isSelected()){
                String query = filtroDinamico(codTF.getText() != null? Integer.parseInt(codTF.getText()):0,
                                              situacaoCB.getValue().toLowerCase(),
                                              solicitanteCB.getValue() != "Todos"? prm001.getElemento(1,solicitanteCB.getValue()):"Todos",
                                              0,
                                              Date.from(dtInicioDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                              Date.from(dtFinalDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                System.out.println(query);                            
                
                st = conn.prepareStatement(query);              
                rs = st.executeQuery();
                
                refreshTableOK(rs,0);

                carregarReqPrest(rs); 

            }else if(setorRB.isSelected()){
                String query = filtroDinamico(codTF.getText() != ""?Integer.parseInt(codTF.getText()):0,
                                              situacaoCB.getValue().toLowerCase(),
                                              solicitanteCB.getValue() != "Todos"? prm001.getElemento(1,solicitanteCB.getValue()):"Todos",
                                              1,
                                              Date.from(dtInicioDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                              Date.from(dtFinalDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                st = conn.prepareStatement(query);
                rs = st.executeQuery();

                refreshTableOK(rs,1);

                carregarReqSet(rs);                
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
	public void consolInterna()throws IOException {
        requisicoesTV.setOnMouseClicked((MouseEvent event) -> {
            @SuppressWarnings("unchecked")
            TablePosition<Consolidacao, ?> pos = requisicoesTV.getFocusModel().getFocusedCell();
            int currentRow = pos.getRow();

            if (event.getClickCount() == 2 && listOfConsol.get(currentRow).getId() != null) {
               try {
                System.out.println(listOfConsol.get(currentRow).getDescricao());
                System.out.println(listOfConsol.get(currentRow).getListOfQtd());
                    new frameConsolInterna(prestadorRB.isSelected(),
                                            listOfConsol.get(currentRow).getDescricao(),
                                            listOfConsol.get(currentRow).getListOfQtd()                                            
                                            );

                    Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameConsolInterna.fxml"));
                    
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
               } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });		        
	}


    @FXML
    public void exit(ActionEvent event){
        stage = (Stage) solicitanteCB.getScene().getWindow();
    	stage.close();
    }

}