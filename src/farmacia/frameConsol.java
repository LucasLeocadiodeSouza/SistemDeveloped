package farmacia;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import consolidacao.Consolidacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private Button pesqB;
    @FXML
    private TableView<Consolidacao> requisicoesTV;
    @FXML
    private Button sairB;
    @FXML
    private Button salvarB;
    @FXML
    private ChoiceBox<String> setorCB;
    @FXML
    private TableColumn<Consolidacao, String> setorTC;
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


	}

    public void carregarReq(){

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

    }
    
    public void pesquisar(){

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

    public void save(){

    }

    public void exit(){
        
    }

}