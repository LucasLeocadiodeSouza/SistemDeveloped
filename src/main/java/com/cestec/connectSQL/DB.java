package com.cestec.connectSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	/* conectar no banco de dados é vc basicamente criar um objeto Connection, conectar com um
	 * DriverManager, informar a url e o objeto properties que esta armazenando o acesso */
	private static Connection conn = null;
	public static Connection getConnection() {
		if(conn == null || isConnectionClosed()) {
			try {
				String url = "jdbc:mysql:///projetofarmacia";
				conn = DriverManager.getConnection(url,"root","696968");

			}catch(SQLException e) {
				throw new dbException(e.getMessage());
			}
			
		}
		return conn;
	}
	 // Verifica se a conexão está fechada
    private static boolean isConnectionClosed() {
        try {
            return conn == null || conn.isClosed();
        } catch (SQLException e) {
            throw new dbException(e.getMessage());
        }
    }
	//e uma operacao pra fechar a conexao
	public static void closeConnection() {
		if (conn != null){
			try {
				conn.close();
			}catch(SQLException e) {
				throw new dbException(e.getMessage());
			}	
		}
	}
	//uma operacao pra fechar a statement
		public static void closeStatement(Statement st) {
			if (st != null){
				try {
					st.close();
				}catch(SQLException e) {
					throw new dbException(e.getMessage());
				}	
			}
		}
		//uma operacao pra fechar o resultSet
		public static void closeResultSet(ResultSet rs) {
			if (rs != null){
				try {
					rs.close();
				}catch(SQLException e) {
					throw new dbException(e.getMessage());
				}	
			}
		}
	
}
