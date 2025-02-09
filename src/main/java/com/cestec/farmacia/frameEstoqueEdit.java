package  com.cestec.farmacia;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import com.cestec.connectSQL.DB;
import com.cestec.parametros.prm002;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class frameEstoqueEdit implements Initializable{

    @FXML
    private CheckBox ativCB;
    @FXML
    private CheckBox permDevCB;
    @FXML
    private CheckBox permEstCB;
    @FXML
    private CheckBox permInvCB;
    @FXML
    private CheckBox permReqPresCB;
    @FXML
    private CheckBox permReqSetCB;
    @FXML 
    private Button sairB;
    @FXML 
    private Button salvarB;
    @FXML 
    private Button excluirB;

    @FXML
    private ChoiceBox<String> centEstCB;
    private String[] vCentEstCB = {"Farmacia"};

    @FXML
    private TextField codeTF;
    @FXML
    private TextField dataAddTF;
    @FXML
    private TextField descTF;
    @FXML
    private TextField validadeTF;    
    @FXML
    private TextField estabelTF;
    @FXML
    private TextField fornTF;
    @FXML
    private TextField marcaTF;
    @FXML
    private TextField classifTF;
    @FXML
    private TextField loteTF;

    @FXML
    private ChoiceBox<String> medidaCB;
    private String[] vMedidaCB = {"Und.", "Cx.","Pct.", "Par"};

    @FXML
    private TextField nameTF;
    @FXML
    private TextField userAddTF;
  
	private Stage stage;    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	centEstCB.getItems().addAll(vCentEstCB);

    	medidaCB.getItems().addAll(vMedidaCB);    	    	    
    	updateMedByIdCode();    	
    }
    
    public void updateMedByIdCode() {
		try {
		conn = DB.getConnection();
		String query = "select med.idmedicamento, med.ativo, med.nome, med.quantidade, med.validade, med.medida, med.addHours, l.lote, c.classif, f.FORN, mar.NOMEMARCA, perm.PERM_ESTOQUE, perm.PERM_REQ_PRESTAD, perm.PERM_REQ_SETOR, perm.PERM_INVENTARIO, perm.PERM_DEVOLUCAO\r\n"
				+ "from medicamento med \r\n"
				+ "inner join lote l\r\n"
				+ "on med.IDMEDICAMENTO = l.ID_MEDICAMENTO\r\n"
				+ "inner join classificacao c\r\n"
				+ "on med.IDMEDICAMENTO = c.ID_MEDICAMENTO\r\n"
				+ "inner join fornecedor f\r\n"
				+ "on med.IDMEDICAMENTO = f.ID_MEDICAMENTO\r\n"
				+ "inner join marca mar\r\n"
				+ "on med.IDMEDICAMENTO = mar.ID_MEDICAMENTO\r\n"
				+ "inner join permdemovimentacao perm\r\n"
				+ "on med.IDMEDICAMENTO = perm.ID_MEDICAMENTO\r\n"
				+ "where IDMEDICAMENTO = ?;";
    	
    	st = conn.prepareStatement(query);
    	st.setInt(1, frameEstoqueMed.idCode);
    	rs = st.executeQuery();

    	if(rs.next()) {
    		Integer idMedQuery 		 	 = rs.getInt("idmedicamento");
    		boolean ativoMedQuery 		 = rs.getBoolean("ativo");
    		String  nomeMedQuery 		 = rs.getString("nome");    		
			Date 	validadeMedQuery     = rs.getDate("validade");
			String  medidaMedQuery 		 = rs.getString("medida");
			Date    addUserQuery    	 = rs.getDate("addHours");
			String  loteMedQuery 		 = rs.getString("lote");
			String  classifMedQuery 	 = rs.getString("classif");
			String  fornMedQuery 		 = rs.getString("forn");
			String  marcaMedQuery 		 = rs.getString("nomemarca");
			boolean permEstMedQuery 	 = rs.getBoolean("PERM_ESTOQUE");
			boolean permReqPrestMedQuery = rs.getBoolean("PERM_REQ_PRESTAD");
			boolean permReqSetMedQuery	 = rs.getBoolean("PERM_REQ_SETOR");
			boolean permInvMedQuery		 = rs.getBoolean("PERM_INVENTARIO");
			boolean permDevMedQuery 	 = rs.getBoolean("PERM_DEVOLUCAO");
			
			codeTF.setText			 (idMedQuery.toString());
			fornTF.setText			 (fornMedQuery);
			marcaTF.setText			 (marcaMedQuery);
			loteTF.setText			 (loteMedQuery);
			nameTF.setText			 (nomeMedQuery);
			ativCB.setSelected		 (ativoMedQuery);
			medidaCB.setValue		 (medidaMedQuery);
			userAddTF.setText		 (sdf.format(addUserQuery));
			classifTF.setText		 (classifMedQuery);
			validadeTF.setText		 (sdf.format(validadeMedQuery));
			permDevCB.setSelected	 (permDevMedQuery);
			permEstCB.setSelected	 (permEstMedQuery);
			permInvCB.setSelected	 (permInvMedQuery);
			permReqPresCB.setSelected(permReqPrestMedQuery);
			permReqSetCB.setSelected (permReqSetMedQuery);
			

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
        
    public void sair() {
    	stage = (Stage) nameTF.getScene().getWindow();
    	stage.close();
    }
    
    public void salvar() throws ParseException{    
    	try {
    		conn = DB.getConnection();

    		prm002.editarMedBasic(nameTF.getText() ,
								 new java.sql.Date(sdf.parse(validadeTF.getText()).getTime()),
								  medidaCB.getValue(),
								  ativCB.isSelected() ,
								  frameEstoqueMed.idCode
								  );
    		prm002.editarMedForn(fornTF.getText(),   frameEstoqueMed.idCode);
    		prm002.editarMedMarca(marcaTF.getText(), frameEstoqueMed.idCode);
			prm002.editarMedLote(loteTF.getText(), 	 frameEstoqueMed.idCode);
    		prm002.editarMedPerm(permEstCB.isSelected(), 
                                 permReqPresCB.isSelected(), 
                                 permReqSetCB.isSelected(), 
                                 permInvCB.isSelected(), 
                                 permDevCB.isSelected(),
                                 frameEstoqueMed.idCode);

        	}finally {
    		    if (st != null) {
    		        DB.closeStatement(st);
    		    }
    		    if (conn != null) {
    		        DB.closeConnection();
    		    }
    		}		
    }
    
    public void excluir() throws ParseException{
    	try {  
    		int x = JOptionPane.showConfirmDialog(null, "Deseja Excluir o medicamento mesmo", "Error", JOptionPane.YES_NO_OPTION); //YES é 0 NO é 1
    		
			if (x == 0) {    			
    			prm002.delMedComp(frameEstoqueMed.idCode);
    		}
    		    		
        	}finally {
    		    if (st != null) {
    		        DB.closeStatement(st);
    		    }
    		    if (conn != null) {
    		        DB.closeConnection();
    		    }
    		}
    }
}