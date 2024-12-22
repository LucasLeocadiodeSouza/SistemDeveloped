package farmacia;

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
import connectSQL.DB;
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
    
    public void upMed() throws ParseException{
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE medicamento\r\n"
    				+ "set nome = ?, validade = ?, medida = ?, ativo = ? where IDMEDICAMENTO = ?;";    

        	st = conn.prepareStatement(query);

        	st.setString (1, nameTF.getText());
        	st.setDate	 (2, new java.sql.Date(sdf.parse(validadeTF.getText()).getTime()));
        	st.setString (3, medidaCB.getValue());
        	st.setBoolean(4, ativCB.isSelected());
        	st.setInt	 (5, frameEstoqueMed.idCode);

        	st.executeUpdate();
        	}catch (SQLException e2) {
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
    
    public void upFor() throws ParseException{
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE fornecedor\r\n"
    				+ "set FORN = ? where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);

        	st.setString(1, fornTF.getText());
        	st.setInt	(2, frameEstoqueMed.idCode);
			
        	st.executeUpdate();
        	}catch (SQLException e2) {
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
    
    public void upMar() throws ParseException{
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE marca\r\n"
    				+ "set nomemarca = ? where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);

        	st.setString(1, marcaTF.getText());
        	st.setInt	(2, frameEstoqueMed.idCode);

        	st.executeUpdate();
        	}catch (SQLException e2) {
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
    
    public void upLote() throws ParseException{
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE LOTE\r\n"
    				+ "set LOTE = ? where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);

        	st.setString(1, loteTF.getText());
        	st.setInt	(2, frameEstoqueMed.idCode);

        	st.executeUpdate();
        	}catch (SQLException e2) {
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
    
    public void upPerm() throws ParseException{
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE permdemovimentacao\r\n"
    				+ "set PERM_ESTOQUE = ?, PERM_REQ_PRESTAD = ?, PERM_REQ_SETOR = ?, PERM_INVENTARIO = ?, PERM_DEVOLUCAO = ? where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setBoolean (1, permEstCB.isSelected());
        	st.setBoolean (2, permReqPresCB.isSelected());
        	st.setBoolean (3, permReqSetCB.isSelected());
        	st.setBoolean (4, permInvCB.isSelected());
        	st.setBoolean (5, permDevCB.isSelected());       	
        	st.setInt	  (6, frameEstoqueMed.idCode);

        	st.executeUpdate();
        	}catch (SQLException e2) {
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
    
    public void delMed() throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM MEDICAMENTO where IDMEDICAMENTO = ?;"; 

        	st = conn.prepareStatement(query);
        	st.setInt(1, frameEstoqueMed.idCode);

        	st.executeUpdate();

        	}catch (SQLException e2) {
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
    
    public void delFor() throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM FORNECEDOR where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, frameEstoqueMed.idCode);

        	st.executeUpdate();

        	}catch (SQLException e2) {
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
    
    public void delMar() throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM MARCA where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, frameEstoqueMed.idCode);

        	st.executeUpdate();

        	}catch (SQLException e2) {
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
    
    public void delLote() throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM LOTE where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, frameEstoqueMed.idCode);

        	st.executeUpdate();
        	}catch (SQLException e2) {
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
    
    public void delClass() throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM CLASSIFICACAO where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, frameEstoqueMed.idCode);

        	st.executeUpdate();

        	}catch (SQLException e2) {
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
    
    public void delPerm() throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM permdemovimentacao where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);       	
        	st.setInt(1, frameEstoqueMed.idCode);

        	st.executeUpdate();

        	}catch (SQLException e2) {
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

    		upMed();
    		upFor();
    		upMar();
    		upLote();
    		upPerm();

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
    			delFor();
    			delLote();
    			delMar();
    			delPerm();
    			delClass();
    			delMed();
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