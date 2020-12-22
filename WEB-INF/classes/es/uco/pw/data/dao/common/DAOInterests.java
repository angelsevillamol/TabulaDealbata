/**
 * Objeto de acceso de datos de los objetivos de los anuncios 
 * individualizados de la aplicacion.
 * 
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.data.dao.common;

import java.sql.*;
import java.util.ArrayList;

public class DAOInterests {
	
	
	/**
	 * Devuelve la informacion de todos los intereses.
	 * 
	 * @return  la lista de intereses almacenados en la base de datos
	 */
	public static ArrayList<String> getAll() { 
		ArrayList<String> interests = new ArrayList<String>();
			
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();

			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Interests.getAll"));
			
			// Se ejecuta la declaracion
			ResultSet rs = ps.executeQuery();
			
			// Se recorre lps resultados de la consulta
			while (rs.next()) {
			    String interest = rs.getString("interest");
			    interests.add(interest);
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return interests;
	}

	/**
	 * Devuelve la informacion del interes seleccionado por su clave 
	 * de identificacion.
	 * 
	 * @param   id  el identificador unico del interes
	 * @return      la cadena con el interes con la clave de identificacion indicada
	 */
	public static String queryById(int id) {;
		String interest = "";
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Interests.queryById"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1,  id);
			
			// Se ejecuta la declaracion
		    ResultSet rs = ps.executeQuery();
		    
		    // Se recorre lps resultados de la consulta
		    while (rs.next()) {
		    	interest = rs.getString("interest");
		    }
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return interest;
	}
}
