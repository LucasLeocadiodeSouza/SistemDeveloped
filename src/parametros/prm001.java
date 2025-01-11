package parametros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import connectSQL.DB;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import medicamentos.medicamentos;

public class prm001 {        

    private static Connection      conn = null;
    private static ResultSet         rs = null;
    private static PreparedStatement st = null; 	

    
        public static Integer selectQtd(Integer idMed) {
            Integer qtd = null;            
            try {
                conn = DB.getConnection();
	        
        	String query = "SELECT QUANTIDADE FROM MEDICAMENTO WHERE IDMEDICAMENTO = ?";
        	
        	st = conn.prepareStatement(query);
        	st.setInt(1, idMed);
        	rs = st.executeQuery();
        	
        	if(rs.next()) {
        		qtd = rs.getInt("QUANTIDADE");
        	}
     	      
	    }catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    }finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }
		}
		if(qtd == null) {
			return 0;
		}else {
			return qtd;
		}
	}


	public static void atualizarQtd(Integer idMed, Integer quant){
		try {
			conn = DB.getConnection();
		
			String query = "UPDATE medicamento\r\n" + //
							"\tset QUANTIDADE = ? where IDMEDICAMENTO = ?;";
			
			st = conn.prepareStatement(query);
			st.setInt(1, quant);
			st.setInt(2, idMed);
			st.executeUpdate();
		   
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally {
			if (st != null) {
				DB.closeStatement(st);
			}
			if (conn != null) {
				DB.closeConnection();
			}
		}
	}


	public static void carregarNomesChoiceBox(ChoiceBox<String> choiceBox) {
		try{
	    	conn = DB.getConnection();

			String wcodnome;
	    	String query = "select IDMEDICAMENTO, NOME from medicamento;";

	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();

	    	while(rs.next()) {
				wcodnome = rs.getString("IDMEDICAMENTO") + ", " + rs.getString("NOME");
	    		choiceBox.getItems().add(wcodnome);
	    	}//end while

		} catch (SQLException e2) {
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

	public static void carregarPrestadorCB(ChoiceBox<String> choiceBox) {
		try{
	    	conn = DB.getConnection();

			String wcodnome;
	    	String query = "select IDPRESTADOR, NOMEPREST from PRESTADOR;";

	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();

	    	while(rs.next()) {
				wcodnome = rs.getString("IDPRESTADOR") + ", " + rs.getString("NOMEPREST");
	    		choiceBox.getItems().add(wcodnome);
	    	}//end while

		} catch (SQLException e2) {
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

	public static void carregarSetorCB(ChoiceBox<String> choiceBox) {
		try{
	    	conn = DB.getConnection();

			String wcodnome;
	    	String query = "select IDSETORES, NOMESETORES from SETORES;";

	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();

	    	while(rs.next()) {
				wcodnome = rs.getString("IDSETORES") + ", " + rs.getString("NOMESETORES");
	    		choiceBox.getItems().add(wcodnome);
	    	}//end while

		} catch (SQLException e2) {
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

	public static void nextTableItem(TableView<medicamentos> TV, ObservableList<medicamentos> ObV, medicamentos med) {
		TV.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode().equals(KeyCode.E)){
					ObV.add(med);
					
				}else if(arg0.isControlDown() && arg0.getCode().equals(KeyCode.DELETE)){

					@SuppressWarnings("unchecked")
					TablePosition<medicamentos, ?> pos = TV.getFocusModel().getFocusedCell();
			        int currentRow = pos.getRow();
			        
			        if(ObV.size() > 1) {
			        	ObV.remove(currentRow);
			        }			       
				}							
			}//endHandler
		});
	}	

	public static String getElemento(int fposicao, String fcorpo){
		if(fposicao == 0){
			return "Invalido";
		}
		if(fposicao == 1){
			int pp = fcorpo.indexOf(",");
			return fcorpo.substring(0, pp);
		}
		
		int j = 1;
		int index = 0;
		while(j < fposicao){

			index = fcorpo.indexOf(",", index);			
			if(index == -1){
				return "sv";
			}
			j++;
			index++;
		}
		
		if(fcorpo.indexOf(",", index) == -1){
			return fcorpo.substring(index);
		}

		int pp = fcorpo.indexOf(",", index);				
 		return fcorpo.substring(index, pp);		
	}	

	public static String getNomeSetor(Integer id){
		try{
	    	conn = DB.getConnection();

	    	String query = "select NOMESETORES from SETORES WHERE IDSETORES = " + id + ";";

	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();

			return  rs.getString("NOMESETORES");

		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }			
		}   
		return "";
	}

	public static String getNomePrestador(Integer id){
		try{
	    	conn = DB.getConnection();

	    	String query = "select NOMEPREST from PRESTADOR WHERE IDPRESTADOR = " + id + ";";

	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();

			return  rs.getString("NOMEPREST");

		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}finally {
		    if (st != null) {
		        DB.closeStatement(st);
		    }
		    if (conn != null) {
		        DB.closeConnection();
		    }			
		}   
		return "";
	}

	public static String fmtDataSQL(java.util.Date periodoIni, java.util.Date periodoFim, String periodoIniFormat, String periodoFimFormat){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		periodoIniFormat = sdf.format(periodoIni);
        periodoFimFormat = sdf.format(periodoFim);	
		return (periodoIniFormat + "," + periodoFimFormat);	
	}

	public static String fmtDataBr(java.util.Date periodoIni, java.util.Date periodoFim, String periodoIniFormat, String periodoFimFormat){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		periodoIniFormat = sdf.format(periodoIni);
        periodoFimFormat = sdf.format(periodoFim);	
		
		return (periodoIniFormat + "," + periodoFimFormat);
	}

	public static String getDataNow(){		
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public static String getDataHoraNow(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}