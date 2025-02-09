package  com.cestec.farmacia;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.cestec.connectSQL.DB;
import com.cestec.parametros.prm001;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class frameConsolInterna implements Initializable {

    @FXML
    private TableColumn<String, Integer> codTC;
    @FXML
    private TableColumn<String, String> consolTC;
    @FXML
    private TableView<String> consolTV;
    @FXML
    private TableColumn<String, String> medTC;
    @FXML
    private TableColumn<String, String> medidaTC;
    @FXML
    private TableColumn<String, Integer> qtdConsolTC;
    @FXML
    private TableColumn<String, Integer> qtdSolicTC;
    @FXML
    private Button sairB;
    @FXML
    private Button salvarB;
    @FXML
    private TextField userAddTF;
    @FXML
    private TextField descReqTF;
    @FXML
    private TextField setorUserTF;
    @FXML
    private TextField userDataAddTF;

    private boolean setorOuPrest;
    private String listOfQtd;
    private String listOfId;

    private static Connection      conn = null;
    private static ResultSet         rs = null;
    private static PreparedStatement st = null; 

    private ObservableList<String> listOfConsol = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

     

        consolTV.setItems(listOfConsol);

        listOfConsol.add("");
    }

    public frameConsolInterna(boolean setorOuPrest, String listOfId, String listOfQtd){
        this.setorOuPrest = setorOuPrest;
        this.listOfId = listOfId;
        this.listOfQtd = listOfQtd;

        carregarReq();

    }

    public frameConsolInterna(){}

    public void carregarReq(){
        for(int j = 1; j <= prm001.getLengthLista(listOfId); j++){
            listOfConsol.set(0, prm001.getElemento (j, listOfId));
            listOfConsol.set(1, prm001.getNomeMed  (Integer.parseInt(prm001.getElemento(j, listOfId))));
            listOfConsol.set(2, prm001.getMedidaMed(Integer.parseInt(prm001.getElemento(j, listOfId))));
            listOfConsol.set(3, prm001.getElemento (j, listOfQtd));
        }
    }

    @FXML
    public void exit(ActionEvent event) {
        
    }

    @FXML
    public void salvar(ActionEvent event) {
        try {            
            conn = DB.getConnection();

            

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (st != null) {
                DB.closeStatement(st);
            }
            if (conn != null) {
                DB.closeConnection();
            }
        }
    }

}

 