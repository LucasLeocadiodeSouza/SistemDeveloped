package com.cestec.parametros;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

import com.cestec.connectSQL.DB;
import com.cestec.medicamentos.medicamentos;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class prm001 {        

    private static Connection      conn = null;
    private static ResultSet         rs = null;
    private static PreparedStatement st = null; 	

		public static String fmtDataBR(java.util.Date data){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 		
			return sdf.format(data);
		}

		public static String fmtDataSQL(java.util.Date data){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 		
			return sdf.format(data);	
		}

		public static String getDataNow(){		
			return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}

		public static String getDataHoraNow(){
			return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
		}

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

	public static String getNomeMed(Integer idMed){
		try {
			conn = DB.getConnection();

		    st = conn.prepareStatement("SELECT nome FROM medicamento WHERE idmedicamento = ?");

			st.setInt(1, idMed);
			st.executeQuery();

			return rs.getString("nome");
			
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
				return "";
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

	public static int getLengthLista(String lista) {
		int count = 0;
		int index = 0;
	
		while ((index = lista.indexOf(",", index)) != -1) {
			count++;
			index += ",".length();
		}
	
		return count + 1;
	}

	public static String getNomeSetor(Integer id){
		try{
	    	conn = DB.getConnection();

	    	String query = "select NOMESETORES from SETORES WHERE IDSETORES = " + id + ";";

	    	st = conn.prepareStatement(query);
	    	rs = st.executeQuery();

			while(rs.next()){
				return rs.getString("NOMESETORES");
			}

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

			while(rs.next()){
				return rs.getString("NOMEPREST");
			}			

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

	public static void nmrReqPrestMed(StringBuilder listProdutos, StringBuilder listQuant, Date dataTF, int prestSelectionIndex) {
		try {
			conn = DB.getConnection();

			//INSERT INTO REQSETORMED
		    st = conn.prepareStatement("INSERT INTO reqPrestMed "
		    		+ "(DATAREQ, LISTMED, LISTQTD, ID_PRESTADOR, ID_USUARIOS, Consolidado) VALUES" + 
					  " (?, ?, ?, ?, ?, ?);"
					);

			st.setDate   (1, dataTF);
			st.setString (2, listProdutos.toString());
			st.setString (3, listQuant.toString());
			st.setInt    (4, prestSelectionIndex);
			//st.setString(5, "FAZER UM ESTATICO NO FRAME DE LOGIN");
			st.setInt    (5, 2);
			st.setInt    (6, 1);

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

	public static void nmrReqSetorMed(StringBuilder listProdutos, StringBuilder listQuant, Date dataTF) {
		try {
			conn = DB.getConnection();

		    st = conn.prepareStatement("INSERT INTO reqSetorMed "
		    		+ "(DATAREQ, ID_USUARIOS, listMed, listQtd, ID_SETOR, consolidado) VALUES" + 
					  " (?, ?, ?, ?, ?, ?);"
					);

			st.setDate  (1, dataTF);
			//st.setString(2, "FAZER UM ESTATICO NO FRAME DE LOGIN");
			st.setInt   (2, 2);
			st.setString(3, listProdutos.toString());
			st.setString(4, listQuant.toString());
			st.setInt   (5, 21 /* setorTF.getText() */);
			st.setInt   (6, 1);

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

	public static String getMedidaMed(Integer idMed){
		try {
			conn = DB.getConnection();

		    st = conn.prepareStatement("SELECT medida FROM medicamento WHERE idmedicamento = ?");

			st.setInt(1, idMed);
			st.executeQuery();

			return rs.getString("medida");
			
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
				return "";
	}

	public static String getAcaoAjuste(Integer idAjuste){
		switch (idAjuste) {
			case 1:
				return "Entrada";
			case 2:
				return "Saída";		
			default:
				return "Parametro de Acão invalido";
		}		
	}

	public static int getAcaoAjuste(String acaoAjuda){
		switch (acaoAjuda) {
			case "entrada":
				return 1;
			case "saída":
				return 2;		
			default:
				return 0;
		}		
	}

	public static String getConsolidadoReq(Integer idReq){
		switch (idReq) {
			case 1:
				return "Não";
			case 2:
				return "Sim";
			case 3:
				return "Parcial";
			default:
				return "Parametro de Situacao Consolidado invalido";
		}		
	}

	public static int getConsolidadoReq(String consolReq){
		switch (consolReq) {
			case "não":
				return 1;
			case "sim":
				return 2;
			case "parcial":
				return 3;	
			default:
				return 0;
		}		
	}

}