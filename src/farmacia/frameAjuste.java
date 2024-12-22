package farmacia;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import ajuste.ajuste;
import connectSQL.DB;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class frameAjuste implements Initializable{
	
    @FXML
    private TableColumn<ajuste, String> acaoTC;
    @FXML
    private TableColumn<ajuste, Integer> codAjusteTC;
    @FXML
    private TableColumn<ajuste, String> descTC;
    @FXML
    private Button fecharB;
    @FXML
    private Button incluirB;
    @FXML
    private Button pesquisarB;
    @FXML
    private TextField descricaoTF;
    @FXML
    private TextField nAjusteTF;
    @FXML
    private TableView<ajuste> tableAjusteTV;

    private Stage stage;
    private Scene scene;

    private ajuste ajt;
    
    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    
    ObservableList<ajuste> listOfAjt = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tableAjusteTV.setItems(listOfAjt);
		
		acaoTC.setCellFactory	   (TextFieldTableCell.forTableColumn());
		codAjusteTC.setCellFactory (TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		descTC.setCellFactory	   (TextFieldTableCell.forTableColumn());
		
		selectItems();
	}
	
	public void selectItems() {
		try {
	        conn = DB.getConnection();
	        
        	String query = "select * from AJUSTE";
        	
        	st = conn.prepareStatement(query);
        	rs = st.executeQuery();
        	
        	while(rs.next()) {
        		Integer id  = rs.getInt   ("IDAJUSTE");
        		String acao = rs.getString("ACAO");
        		String desc = rs.getString("DESCRICAO");
        		        		
        		ajt = new ajuste(id, acao, desc);
        		listOfAjt.add(ajt);
        		
        		codAjusteTC.setCellValueFactory (new PropertyValueFactory<ajuste, Integer>("id"));
        		acaoTC.setCellValueFactory      (new PropertyValueFactory<ajuste, String>("acao"));
        		descTC.setCellValueFactory      (new PropertyValueFactory<ajuste, String>("descricao"));        		
        		
        		tableAjusteTV.refresh();
        	}
        	
	    }catch (Exception e) {
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
	
	public void selectFilter(String txt, int nmr) {

		listOfAjt.clear();
		String query = null;

		try {
	        conn = DB.getConnection();
	        
	        if(txt != null && nmr != 0) {
	        	query = "SELECT * FROM AJUSTE WHERE DESCRICAO LIKE ? AND IDAJUSTE LIKE ?;";
	        }else if((txt == null || txt.isEmpty()) || nmr == 0) {
	        	query = "SELECT * FROM AJUSTE WHERE DESCRICAO LIKE ? OR IDAJUSTE LIKE ?;";
	        }
	        
        	st = conn.prepareStatement(query);	

        	st.setString(1, "%" + txt + "%");
        	st.setInt   (2, nmr);

        	rs = st.executeQuery();
        	
        	while(rs.next()) {
        		Integer id = rs.getInt    ("IDAJUSTE");
        		String acao = rs.getString("ACAO");
        		String desc = rs.getString("DESCRICAO");
        		
        		if(desc == null) {
        			ajuste ajt = new ajuste(id, acao, " ");        		
        			listOfAjt.add(ajt);
        		}else {
        			ajuste ajt = new ajuste(id, acao, desc);        		
        			listOfAjt.add(ajt);
        		}
        		       		        	
        	}
        	codAjusteTC.setCellValueFactory(new PropertyValueFactory<ajuste, Integer>("id"));
    		descTC.setCellValueFactory     (new PropertyValueFactory<ajuste, String>("descricao"));        		
    		acaoTC.setCellValueFactory     (new PropertyValueFactory<ajuste, String>("acao"));
    		
    		tableAjusteTV.refresh();
        	
	    }catch (Exception e) {
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
    
	public void pesquisar() {
		if(!descricaoTF.getText().isEmpty() && nAjusteTF.getText().isEmpty()) {
			selectFilter(descricaoTF.getText(), 0);
			
		}else if(!descricaoTF.getText().isEmpty() && !nAjusteTF.getText().isEmpty()) {
			selectFilter(descricaoTF.getText(), Integer.parseInt(nAjusteTF.getText()));
			
		}else if(descricaoTF.getText().isEmpty() && !nAjusteTF.getText().isEmpty()) {
			selectFilter(null, Integer.parseInt(nAjusteTF.getText()));
		}
		else {
			listOfAjt.clear();
			selectItems();
		}
	}
	
    public void save(ActionEvent e) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/farmacia/frameAjusteWindow.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void fechar(ActionEvent e) {
    	stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	stage.close();
    }
}
