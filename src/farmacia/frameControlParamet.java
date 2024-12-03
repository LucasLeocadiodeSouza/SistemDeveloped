package farmacia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectSQL.DB;
import parametros.Setores;

public class frameControlParamet {
	
	private ArrayList<Setores> setores;
	
	Connection conn = null;
    PreparedStatement st = null;
    ResultSet rs = null;
	
	public void setorArray() {
    	try {
    		conn = DB.getConnection();
    		String query = "select  se.NOMESETORES, se.IDSETORES\r\n"
    				+ "from ESTABELECIMENTO est\r\n"
    				+ "inner join setores se \r\n"
    				+ "on est.IDESTABELECIMENTO = se.ID_ESTABELECIMENTO\r\n"
    				+ "where IDESTABELECIMENTO = 1;";
        	
        	st = conn.prepareStatement(query);
        	rs = st.executeQuery();
        	
        	if(rs.next()) {
        		Integer idSetorQuery = rs.getInt("IDSETORES");
        		String nomeSetorQuery = rs.getString("NOMESETORES");
        		
        		setores.add(new  Setores(nomeSetorQuery, idSetorQuery));

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
}
