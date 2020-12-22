/**
 * Objeto de acceso de datos que realiza una conexion con la base de datos
 * y obtiene las consultas definidas para la aplicacion.
 * 
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.data.dao.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DAO {
	
	private static Connection con = null;

	/**
	 * Realiza una conexion con la base de datos.
	 * 
	 * @return  la conexion con la base de datos
	 */
	public static Connection getConnection() {
		String server = null;
		String user = null;
		String password = null;
		
		if (con == null) {
			// Se obtiene la informacion del servidor de la base de datos
			try (InputStream input = new FileInputStream("config.properties")) {
	            Properties prop = new Properties();
	            prop.load(input);
	            server = prop.getProperty("database.server");
	            user = prop.getProperty("database.user");
	            password = prop.getProperty("database.password");
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
			
			// Se obtiene una instancia del Driver de MySQL
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con =  DriverManager.getConnection(server, user, password);
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		
		return con;
	}
	
	
	/**
	 * Devuelve la declaracion en lenguaje SQL con la operacion a realizar 
	 * sobre la base de datos, almacenada en un fichero de propiedades.
	 * 
	 * @param   optStr  la cadena con la operacion a realizar
	 * @return          la cadena con la declaracion de sql a realizar
	 */
	public static String getStmt(String optStr) {
		String stmt = null;
		
		try (InputStream input = new FileInputStream("sql.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            stmt = prop.getProperty(optStr);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
		return stmt;
	}
}
