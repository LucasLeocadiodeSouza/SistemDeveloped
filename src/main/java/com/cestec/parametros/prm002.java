package com.cestec.parametros;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;

import com.cestec.connectSQL.DB;

public class prm002 {

    private static Connection      conn = null;
    private static ResultSet         rs = null;
    private static PreparedStatement st = null; 


    public static void editarMedBasic( String nome, Date validade, String medida, boolean ativo, Integer id){
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE medicamento\r\n"
    				+ "set nome = ?, validade = ?, medida = ?, ativo = ? where IDMEDICAMENTO = ?;";    

        	st = conn.prepareStatement(query);

        	st.setString (1, nome);
        	st.setDate	 (2, validade);
        	st.setString (3, medida);
        	st.setBoolean(4, ativo);
        	st.setInt	 (5, id);

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

    public static void editarMedForn(String fornecedor, Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE fornecedor\r\n"
    				       + "set FORN = ? where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);

        	st.setString(1, fornecedor);
        	st.setInt	(2, id);
			
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

    public static void editarMedMarca(String marca, Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE marca\r\n"
    				+ "set nomemarca = ? where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);

        	st.setString(1, marca);
        	st.setInt	(2, id);

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

    public static void editarMedLote(String lote, Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE LOTE\r\n"
    				+ "set LOTE = ? where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);

        	st.setString(1, lote);
        	st.setInt	(2, id);

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


    public static void editarMedPerm(boolean permEstCB, 
                                     boolean permReqPresCB, 
                                     boolean permReqSetCB, 
                                     boolean permInvCB, 
                                     boolean permDevCB,
                                     Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();
    		String query = "UPDATE permdemovimentacao\r\n"
    				+ "set PERM_ESTOQUE = ?, PERM_REQ_PRESTAD = ?, PERM_REQ_SETOR = ?, PERM_INVENTARIO = ?, PERM_DEVOLUCAO = ? where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setBoolean (1, permEstCB);
        	st.setBoolean (2, permReqPresCB);
        	st.setBoolean (3, permReqSetCB);
        	st.setBoolean (4, permInvCB);
        	st.setBoolean (5, permDevCB);       	
        	st.setInt	  (6, id);

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


    public static void delMedBasic(Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM MEDICAMENTO where IDMEDICAMENTO = ?;"; 

        	st = conn.prepareStatement(query);
        	st.setInt(1, id);

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
    
    public static void delMedFor(Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM FORNECEDOR where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, id);

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
    
    public static void delMedMar(Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM MARCA where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, id);

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
    
    public static void delMedLote(Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM LOTE where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, id);

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
    
    public static void delMedClassif(Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM CLASSIFICACAO where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, id);

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
    
    public static void delMedPerm(Integer id) throws ParseException{
    	try {
    		conn = DB.getConnection();

    		String query = "DELETE FROM permdemovimentacao where ID_MEDICAMENTO = ?;"; 
        	
        	st = conn.prepareStatement(query);       	
        	st.setInt(1, id);

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

    public static void delMedComp(Integer id) throws ParseException{
        try {
    		conn = DB.getConnection();

    		delMedFor(id);
    		delMedLote(id);
    		delMedMar(id);
    		delMedPerm(id);
    		delMedClassif(id);
    		delMedBasic(id);

        }catch (Exception e2) {
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
