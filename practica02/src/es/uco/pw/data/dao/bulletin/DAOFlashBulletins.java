/**
 * Objeto de acceso de datos de la fecha de finalizacion de los anuncios 
 * flash de la aplicacion.
 * 
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.data.dao.bulletin;

import es.uco.pw.data.dao.common.DAO;
import es.uco.pw.business.bulletin.FlashBulletin;

import java.sql.*;

public class DAOFlashBulletins {
	
	/**
	 * Registra los objetivos de un anuncio flash.
	 * 
	 * @param    fb  el anuncio cuyos intereses se van a registrar
	 * @return       el estado resultado de la operacion          
	 */
	public static int save(FlashBulletin fb) {
		int status = 0;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Flash_bulletins.save"));
			
			// Se asigna el identificador del anuncio
			ps.setInt(1, fb.getId());
			
			// Se asigna la fecha de finalizacion
			ps.setDate(2, null);
			
			// Se ejecuta la declaracion
			status = ps.executeUpdate();
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	/**
	 * Actualiza la fecha de finalizacion de un anuncio flash.
	 * 
	 * @param   fb  el anuncio cuya fecha de finalizacion se van a actualizar
	 * @return      el estado resultado de la operacion          
	 */
	public static int update(FlashBulletin fb) {
		int status = 0;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Flash_bulletins.update"));
			
			// Se asigna el identificador del usuario 
			
			if (fb.getFechaFinalizacion() != null) {
				ps.setDate(1, new java.sql.Date(fb.getFechaFinalizacion().getTime()));
			} 
			else {
				ps.setDate(1, null);
			}
			
			ps.setInt(2, fb.getId());
			
			// Para cada interes
			status = ps.executeUpdate();
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	/**
	 * Elimina la informacion de la fecha de finalizacion de un anuncio flash.
	 * 
	 * @param   id  el identificador del anuncio cuya fecha de finalizacion 
	 *              se va a eliminar
	 * @return      el estado resultado de la operacion          
	 */
	public static int delete(int id) {
		int status = 0;
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps=con.prepareStatement(DAO.getStmt("Flash_bulletins.delete"));
			
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
	 * Devuelve la fecha de finalizacion de un anuncio flash seleccionado por 
	 * su clave de identificacion.
	 * 
	 * @param   id  el identificador unico del anuncio
	 * @return      la fecha de finalizacion del anuncio
	 */
	public static Date queryById(int id) {
		Date fechaFinalizacion = null;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Flash_bulletins.queryById"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1,  id);
			
			// Se ejecuta la declaracion
		    ResultSet rs = ps.executeQuery();
		    
		    // Se recorre lps resultados de la consulta
		    while (rs.next()) {
		    	fechaFinalizacion = rs.getDate("finalization_date");
		    }
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return fechaFinalizacion;
	}
}
