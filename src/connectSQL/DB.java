package connectSQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	/* conectar no banco de dados é vc basicamente criar um objeto Connection, conectar com um
	 * DriverManager, informar a url e o objeto properties que esta armazenando o acesso */
	private static Connection conn = null;
	public static Connection getConnection() {
		if(conn == null || isConnectionClosed()) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);

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
	
	/* cria um objeto fileInputStream e armazena o db.properties, apos isso ele 
	cria um objeto properties pra ler e armazenar o objeto fileInputStream */
	private static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("src/connectSQL/db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		}catch(IOException e) {
			throw new dbException(e.getMessage());
		}
	}
	
}
