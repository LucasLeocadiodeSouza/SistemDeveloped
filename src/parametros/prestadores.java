package parametros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connectSQL.DB;

public class prestadores {
	private String nomePrestador;
	private Integer codPrestador;

	private Connection conn = null;
	private PreparedStatement st = null;
	private ResultSet rs = null;

	
	public prestadores(String nomePrestador, Integer codPrestador) {
		this.nomePrestador = nomePrestador;
		this.codPrestador = codPrestador;
	}
	public prestadores(String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}
	public prestadores(Integer codPrestador) {
		this.codPrestador = codPrestador;	
		setnomePrestador(retornarPrestador(codPrestador));	
	}

	public String getnomePrestador() {
		return nomePrestador;
	}
	public void setnomePrestador(String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}
	public Integer getcodPrestador() {
		return codPrestador;
	}
	public void setcodPrestador(Integer codPrestador) {
		this.codPrestador = codPrestador;
	}
	public String retornarPrestador(Integer id){
		try{

			conn = DB.getConnection();

			String query = "SELECT NOMEPREST FROM PRESTADOR WHERE IDPRESTADOR =  ?";

			st.setInt(1, id);
			st = conn.prepareStatement(query);
			rs = st.executeQuery();
			
			setnomePrestador(rs.getString("NOMEPREST"));

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

		return nomePrestador;
	}
}
