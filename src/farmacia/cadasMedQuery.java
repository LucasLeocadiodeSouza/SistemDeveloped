package farmacia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import connectSQL.DB;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class cadasMedQuery {
	
	public void insertCadasQuery(Connection conn, PreparedStatement st, ResultSet rs, TextField nomeTF,
			TextField quantidadeTF, 
			TextField validadeTF, 
			TextField loteTF, 
			ChoiceBox<String> classCB, 
			ChoiceBox<String> fornCB, 
			TextField marcaTF) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		//INSERT INTO MEDICAMENTOS 
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement("INSERT INTO medicamento (NOME, QUANTIDADE, VALIDADE) VAlUES" + 
					" (?,?,?);", Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, nomeTF.getText());
			st.setInt(2, Integer.valueOf(quantidadeTF.getText()));
			st.setDate(3, new java.sql.Date(sdf.parse(validadeTF.getText()).getTime()));
			
			int rowsAffected = st.executeUpdate();
			int generatedId = 0;
			
			// Recuperar o ID gerado automaticamente
		    if (rowsAffected > 0) {
		        rs = st.getGeneratedKeys();
		        if (rs.next()) {
		            generatedId = rs.getInt(1); // Captura o ID gerado
		        }
		    }
		    
			//INSERT INTO LOTE 
		    st = conn.prepareStatement("INSERT INTO lote (LOTE, ID_MEDICAMENTO) VAlUES" + 
					" (?,?);"
					);
			st.setString(1, loteTF.getText());
			st.setInt(2, generatedId);
			st.executeUpdate();
			
			//INSERT INTO CLASSIFICACAO 
			st = conn.prepareStatement("INSERT INTO classificacao (CLASSIF, ID_MEDICAMENTO) VAlUES" + 
					" (?,?); "
					);
			st.setString(1, classCB.getValue());
			st.setInt(2, generatedId);
			st.executeUpdate();
			
			//INSERT INTO FORNECEDOR 
			st = conn.prepareStatement("INSERT INTO fornecedor (FORN, ID_MEDICAMENTO) VAlUES" + 
					" (?,?); "
					);
			st.setString(1, fornCB.getValue());
			st.setInt(2, generatedId);
			st.executeUpdate();
			
			//INSERT INTO MARCA
			st = conn.prepareStatement("INSERT INTO marca (NOMEMARCA, ID_MEDICAMENTO) VAlUES" + 
					" (?,?); "
					);
			st.setString(1, marcaTF.getText());
			st.setInt(2, generatedId);
			st.executeUpdate();
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}
}