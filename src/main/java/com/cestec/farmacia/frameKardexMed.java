package  com.cestec.farmacia;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

public class frameKardexMed implements Initializable{

    @FXML
    private CheckBox ativoCB;
    @FXML
    private Button cancelB;
    @FXML
    private DatePicker dtFinalDB;
    @FXML
    private DatePicker dtInicioDP;
    @FXML
    private ChoiceBox<String> filtroclassCB;
    @FXML
    private ChoiceBox<String> filtrofornCB;
    @FXML
    private ChoiceBox<String> filtromedCB;
    @FXML
    private ChoiceBox<String> filtrosetorCB;
    @FXML
    private Button gerarB;
    @FXML
    private ChoiceBox<?> lardexCB;
    @FXML
    private CheckBox naoativoCB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    void gerar(ActionEvent event) {

    }

}