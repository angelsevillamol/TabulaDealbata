/**
 * Objeto de acceso de datos de los objetivos de los anuncios 
 * individualizados de la aplicacion.
 * 
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.data.dao.bulletin;

import es.uco.pw.data.dao.common.DAO;
import es.uco.pw.business.bulletin.IndBulletin;

import java.sql.*;
import java.util.ArrayList;

public class DAOReceiversIndBulletins {
	
	/**
	 * Registra los objetivos de un anuncio individualizado.
	 * 
	 * @param    ib  el anuncio cuyos intereses se van a registrar
	 * @return       el estado resultado de la operacion          
	 */
	public static int save(IndBulletin tb) {
		int status = 0;
		ArrayList<Integer> objetivos = tb.getObjetivos();
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Receivers_ind_bulletins.save"));
			
			// Se asigna el identificador del anuncio
			ps.setInt(1, tb.getId());
			
			// Para cada objetivo
			for (int i : objetivos) {
				ps.setInt(2, i);
				status = ps.executeUpdate();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	/**
	 * Actualiza los objetivos de un anuncio individualizado.
	 * 
	 * @param   ib  el anuncio cuyos objetivos se van a actualizar
	 * @return      el estado resultado de la operacion          
	 */
	public static int update(IndBulletin ib) {
		int status = 0;
		status = delete(ib.getId());
		status = save(ib);
		return status;
	}
	
	/**
	 * Elimina la informacion de los objetivos de un anuncio individualizado.
	 * 
	 * @param   id  el identificador del anuncio cuyos objetivos se van 
	 *              a eliminar
	 * @return      el estado resultado de la operacion          
	 */
	public static int delete(int id) {
		int status = 0;
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps=con.prepareStatement(DAO.getStmt("Receivers_ind_bulletins.delete"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1, id);
			
			// Se ejecuta la declaracion
			status = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	
		return status;
	}
	
	/**
	 * Devuelve la informacion de todos los objetivos de un anuncio individualizado
	 * seleccionado por su clave de identificacion.
	 * 
	 * @param   id  el identificador unico del anuncio
	 * @return      la lista de intereses almacenados en la base de datos
	 */
	public static ArrayList<Integer> queryById(int id) {;
		ArrayList<Integer> resultado = new ArrayList<>();
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Receivers_ind_bulletins.queryById"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1,  id);
			
			// Se ejecuta la declaracion
		    ResultSet rs = ps.executeQuery();
		    
		    // Se recorre lps resultados de la consulta
		    while (rs.next()) {
		    	int user = rs.getInt("user");
		        
		        // Se agrega el tema al resultado
		        resultado.add(user);
		    }
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return resultado;
	}
}
