/**
 * Objeto de acceso de datos de los intereses de anuncios tematicos 
 * de la aplicacion.
 * 
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.data.dao.bulletin;

import es.uco.pw.data.dao.common.DAO;
import es.uco.pw.business.user.InterestMgr;
import es.uco.pw.business.bulletin.ThemBulletin;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DAOInterestsThemBulletins {
	
	/**
	 * Registra los intereses de un anuncio tematico.
	 * 
	 * @param    tb  el anuncio cuyos intereses se van a registrar
	 * @return       el estado resultado de la operacion          
	 */
	public static int save(ThemBulletin tb) {
		int status = 0;
		InterestMgr interestMgr = InterestMgr.getInstance();
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Interests_them_bulletins.save"));
			
			// Se asigna el identificador del usuario 
			ps.setInt(1, tb.getId());
			
			// Para cada interes
			for (int i = 0; i < interestMgr.getNumInterests(); i++) {
				// Se indica que interes es, y si esta o no interesado
				ps.setInt(2, i);
				ps.setString(3, tb.esInteres(i)? "Si" : "No");
				
				// Se ejecuta la declaracion
				status = ps.executeUpdate();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	/**
	 * Actualiza los intereses de un anuncio tematico.
	 * 
	 * @param   tb  el anuncio cuyos intereses se van a actualizar
	 * @return      el estado resultado de la operacion          
	 */
	public static int update(ThemBulletin tb) {
		int status = 0;
		InterestMgr interestMgr = InterestMgr.getInstance();
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Interests_them_bulletins.update"));
			
			// Se asigna el identificador del anuncio 
			ps.setInt(2, tb.getId());
			
			// Para cada interes
			for (int i = 0; i < interestMgr.getNumInterests(); i++) {
				// Se indica que interes es, y si esta o no interesado
				ps.setInt(3, i);
				ps.setString(1, tb.esInteres(i)? "Si" : "No");
							
				// Se ejecuta la declaracion
				status = ps.executeUpdate();
			}
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	/**
	 * Elimina la informacion de los intereses de un anuncio tematico.
	 * 
	 * @param   id  el identificador del anuncio cuyos intereses se van 
	 *              a eliminar
	 * @return      el estado resultado de la operacion          
	 */
	public static int delete(int id) {
		int status = 0;
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps=con.prepareStatement(DAO.getStmt("Interests_them_bulletins.delete"));
			
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
	 * Devuelve la informacion de todos los intereses de un anuncio tematico
	 * seleccionado por su clave de identificacion.
	 * 
	 * @param   id  el identificador unico del anuncio
	 * @return      la lista de intereses almacenados en la base de datos
	 */
	public static ArrayList<Boolean> queryById(int id) {
		InterestMgr interestMgr = InterestMgr.getInstance();
		ArrayList<Boolean> resultado = new ArrayList<Boolean>(Arrays.asList(new Boolean[interestMgr.getNumInterests()]));
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Interests_them_bulletins.queryById"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1,  id);
			
			// Se ejecuta la declaracion
		    ResultSet rs = ps.executeQuery();
		    
		    // Se recorre lps resultados de la consulta
		    while (rs.next()) {
		    	int interest = rs.getInt("interest");
		        Boolean interested = rs.getString("interested").equals("Si");
		        
		        // Se agrega el tema al resultado
		        resultado.set(interest, interested);
		    }
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return resultado;
	}
}
