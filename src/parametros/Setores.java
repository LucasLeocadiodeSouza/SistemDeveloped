package parametros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connectSQL.DB;

public class Setores {
	private String nomeSetor;
	private Integer codSetor;

	private Connection conn = null;
	private PreparedStatement st = null;
	private ResultSet rs = null;

	
	public Setores(String nomeSetor, Integer codSetor) {
		this.nomeSetor = nomeSetor;
		this.codSetor = codSetor;
	}
	public Setores(String nomeSetor) {
		this.nomeSetor = nomeSetor;
	}
	public Setores(Integer codSetor) {
		this.codSetor = codSetor;	
		setNomeSetor(retornarSetor(codSetor));	
	}

	public String getNomeSetor() {
		return nomeSetor;
	}
	public void setNomeSetor(String nomeSetor) {
		this.nomeSetor = nomeSetor;
	}
	public Integer getCodSetor() {
		return codSetor;
	}
	public void setCodSetor(Integer codSetor) {
		this.codSetor = codSetor;
	}
	public String retornarSetor(Integer id){
		try{

			conn = DB.getConnection();

			String query = "SELECT NOMESETORES FROM SETORES WHERE IDSETORES =  ?";

			st.setInt(1, id);
			st.executeQuery(query);
			
			setNomeSetor(rs.getString("NOMESETORES"));

		}catch (Exception e) {
	    	e.getMessage();
	    }finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		}

		return nomeSetor;
	}
}
