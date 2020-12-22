/**
 * Objeto de acceso de datos de anuncios de la aplicacion.
 * 
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.data.dao.bulletin;

import es.uco.pw.data.dao.common.DAO;
import es.uco.pw.business.bulletin.Bulletin;
import es.uco.pw.business.bulletin.GenBulletin;
import es.uco.pw.business.bulletin.ThemBulletin;
import es.uco.pw.business.bulletin.IndBulletin;
import es.uco.pw.business.bulletin.FlashBulletin;

import java.sql.*;
import java.util.ArrayList;

public class DAOBulletin {
	
	/**
	 * Registra un anuncio en la aplicacion.
	 * 
	 * @param   b  el anuncio a registrar
	 * @return     el estado resultado de la operacion          
	 */
	public static int save(Bulletin b) {
		int status = 0;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps=con.prepareStatement(DAO.getStmt("Bulletins.save"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1, b.getTipo());
			ps.setString(2, b.getTitulo());
			ps.setString(3, b.getCuerpo());
			ps.setDate(4, null);
			ps.setInt(5, b.getPropietario());
			ps.setInt(6, 0);
			
			// Se ejecuta la declaracion
			status = ps.executeUpdate();
			
			// Se le asigna el identificador generado
			PreparedStatement psid = con.prepareStatement(DAO.getStmt("Bulletins.getLastId"));
			ResultSet rs = psid.executeQuery();
			while (rs.next()) {
				b.setId(rs.getInt("id"));
			}
			
			// Si es un anuncio tematico
			if (b.getTipo() == 2) {
				// Se insertan tambien los intereses correspondientes
				ThemBulletin tb = (ThemBulletin) b;
				status = DAOInterestsThemBulletins.save(tb);
			}
			// Si es un anuncio individualizado
			else if (b.getTipo() == 3) {
				// Se insertan tambien los objetivos correspondientes
				IndBulletin ib = (IndBulletin) b;
				status = DAOReceiversIndBulletins.save(ib);
			}
			// Si es un anuncio flash
			else if (b.getTipo() == 4) {
				// Se insertan tambien la fecha de finalizacion
				FlashBulletin fb = (FlashBulletin) b;
				status = DAOFlashBulletins.save(fb);
			}
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	/**
	 * Actualiza la informacion de un anuncio.
	 * 
	 * @param   b  el anuncio a actualizar
	 * @return     el estado resultado de la operacion          
	 */
	public static int update(Bulletin b) {
		int status = 0;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Bulletins.update"));
			
			// Se insertan los parametros en la consulta
			ps.setString(1, b.getTitulo());
			ps.setString(2, b.getCuerpo());
			if (b.getFechaPublicacion() != null) {
				ps.setDate(3, new java.sql.Date(b.getFechaPublicacion().getTime()));
			}
			else {
				ps.setDate(3, null);
			}	
			ps.setInt(4, b.getFase());
			ps.setInt(5, b.getId());
			
			// Se ejecuta la declaracion
			status = ps.executeUpdate();
			
			// Si es un anuncio tematico
			if (b.getTipo() == 2) {
				// Se insertan tambien los intereses correspondientes
				ThemBulletin tb = (ThemBulletin) b;
				status = DAOInterestsThemBulletins.update(tb);
			}
			// Si es un anuncio individualizado
			else if (b.getTipo() == 3) {
				// Se insertan tambien los objetivos correspondientes
				IndBulletin ib = (IndBulletin) b;
				status = DAOReceiversIndBulletins.update(ib);
			}
			// Si es un anuncio flash
			else if (b.getTipo() == 4) {
				// Se insertan tambien los objetivos correspondientes
				FlashBulletin fb = (FlashBulletin) b;
				status = DAOFlashBulletins.update(fb);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	/**
	 * Elimina la informacion de un anuncio.
	 * 
	 * @param   b  el anuncio a eliminar
	 * @return     el estado resultado de la operacion          
	 */
	public static int delete(Bulletin b) {
		int status = 0;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps=con.prepareStatement(DAO.getStmt("Bulletins.delete"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1, b.getId());
			
			// Se ejecuta la declaracion
			status = ps.executeUpdate();	
			
			// Si es un anuncio tematico
			if (b.getTipo() == 2) {
				status = DAOInterestsThemBulletins.delete(b.getId());
			}
			// Si es un anuncio individualizado
			else if (b.getTipo() == 3) {
				status = DAOReceiversIndBulletins.delete(b.getId());
			}
			// Si es un anuncio flash
			else if (b.getTipo() == 4) {
				status = DAOFlashBulletins.delete(b.getId());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	
		return status;
	}
	
	/**
	 * Archiva un anuncio.
	 * 
	 * @param   id  el identificador del anuncio a archivar
	 * @return      el estado resultado de la operacion          
	 */
	public static int close(int id) {
		int status = 0;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps=con.prepareStatement(DAO.getStmt("Bulletins.close"));
			
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
	 * Devuelve la informacion de todos los anuncios.
	 * 
	 * @return  la lista de anuncios almacenados en la base de datos
	 */
	public static ArrayList<Bulletin> getAll() { 
		ArrayList<Bulletin> resultado = new ArrayList<Bulletin>();
		ArrayList<Boolean> intereses = new ArrayList<>();
		ArrayList<Integer> objetivos = new ArrayList<>();
		Date fechaFinalizacion;
			
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();

			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Bulletins.getAll"));
			
			// Se ejecuta la declaracion
			ResultSet rs = ps.executeQuery();
			
			// Se recorre lps resultados de la consulta
			while (rs.next()) {
			    int id = rs.getInt("id");
			    int tipo = rs.getInt("type");
			    String titulo = rs.getString("title");
			    String cuerpo = rs.getString("body");
			    Date fechaPublicacion = rs.getDate("publication_date");
			    int propietario = rs.getInt("proprietary");
			    int fase = rs.getInt("phase");
			    
			    // Si el anuncio es de tipo general
			    if (tipo == 1) {
			    	GenBulletin gb = new GenBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase);
			    	resultado.add(gb);
			    }
			    // Si el anuncio es de tipo tematico
			    else if (tipo == 2) {
			    	intereses = DAOInterestsThemBulletins.queryById(id);
			    	ThemBulletin tb = new ThemBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase, intereses);
			    	resultado.add(tb);
			    }
			    // Si el anuncio es de tipo tematico
			    else if (tipo == 3) {
			    	objetivos = DAOReceiversIndBulletins.queryById(id);
			    	IndBulletin ib = new IndBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase, objetivos);
			    	resultado.add(ib);
			    }
			    // Si el anuncio es de tipo flash
			    else if (tipo == 4) {
			    	fechaFinalizacion = DAOFlashBulletins.queryById(id);
			    	FlashBulletin fb = new FlashBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase, fechaFinalizacion);
			    	resultado.add(fb);
			    }
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return resultado;
	}
	
	/**
	 * Devuelve la informacion del anuncio seleccionado por su clave 
	 * de identificacion.
	 * 
	 * @param   id  el identificador unico del anuncio
	 * @return      el anuncio con la clave de identificacion indicada
	 */
	public static Bulletin queryById(int id) {
		Bulletin b = null;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Bulletins.queryById"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1,  id);
			
			// Se ejecuta la declaracion
		    ResultSet rs = ps.executeQuery();
		    
		    // Se recorre lps resultados de la consulta
		    while (rs.next()) {
			    int tipo = rs.getInt("type");
			    String titulo = rs.getString("title");
			    String cuerpo = rs.getString("body");
			    Date fechaPublicacion = rs.getDate("publication_date");
			    int propietario = rs.getInt("proprietary");
			    int fase = rs.getInt("phase");
		        
			    // Se agrega el anuncio al resultado
			    if (tipo == 1) {
			    	b = new GenBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase);
			    }
			    else if (tipo == 2) {
			    	ArrayList<Boolean> intereses = DAOInterestsThemBulletins.queryById(id);
			    	b = new ThemBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase, intereses);
			    }
			    else if (tipo == 3) {
			    	ArrayList<Integer> objetivos = DAOReceiversIndBulletins.queryById(id);
			    	b = new IndBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase, objetivos);
			    }
			    else if (tipo == 4) {
			    	Date fechaFinalizacion = DAOFlashBulletins.queryById(id);
			    	b = new FlashBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase, fechaFinalizacion);
			    }
		    }
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return b;
	}
	
	/**
	 * Devuelve la informacion de todos los anuncios seleccionados por su 
	 * fecha de publicacion.
	 * 
	 * @param   fechaPublicacion  la fecha de publicacion del anuncio
	 * @return                    la lista de anuncios encontrados
	 */
	public static ArrayList<Bulletin> queryByPublicationDate(Date fechaPublicacion) {
		ArrayList<Bulletin> resultado = new ArrayList<Bulletin>();
		GenBulletin gb = null;
		ThemBulletin tb = null;
		IndBulletin ib = null;
		FlashBulletin fb = null;
		ArrayList<Boolean> intereses = new ArrayList<>();
		ArrayList<Integer> objetivos = new ArrayList<>();
		Date fechaFinalizacion;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Bulletins.queryByPublicationDate"));
			
			// Se insertan los parametros en la consulta
			ps.setDate(1,  fechaPublicacion);
			
			// Se ejecuta la declaracion
		    ResultSet rs = ps.executeQuery();
		    
		    // Se recorre lps resultados de la consulta
		    while (rs.next()) {
		    	int id = rs.getInt("id");
			    int tipo = rs.getInt("type");
			    String titulo = rs.getString("title");
			    String cuerpo = rs.getString("body");
			    int propietario = rs.getInt("proprietary");
			    int fase = rs.getInt("phase");
		        
			    // Se agrega el usuario al resultado
			    if (tipo == 1) {
			    	gb = new GenBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase);
			    	resultado.add(gb);
			    }
			    else if (tipo == 2) {
			    	intereses = DAOInterestsThemBulletins.queryById(id);
			    	tb = new ThemBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase, intereses);
			    	resultado.add(tb);
			    }
			    else if (tipo == 3) {
			    	objetivos = DAOReceiversIndBulletins.queryById(id);
			    	ib = new IndBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase, objetivos);
			    	resultado.add(ib);
			    }
			    else if (tipo == 4) {
			    	fechaFinalizacion = DAOFlashBulletins.queryById(id);
			    	fb = new FlashBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, fase, fechaFinalizacion);
			    	resultado.add(fb);
			    }
		    }
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return resultado;
	}
	
	/**
	 * Devuelve la informacion de todos los anuncios seleccionados por su 
	 * usuario propietario.
	 * 
	 * @param   proprietary  el identificador unico del usuario propietario
	 * @return               la lista de anuncios encontrados
	 */
	public static ArrayList<Bulletin> queryByProprietary(int proprietary) {
		ArrayList<Bulletin> resultado = new ArrayList<Bulletin>();
		GenBulletin gb = null;
		ThemBulletin tb = null;
		IndBulletin ib = null;
		FlashBulletin fb = null;
		ArrayList<Boolean> intereses = new ArrayList<>();
		ArrayList<Integer> objetivos = new ArrayList<>();
		Date fechaFinalizacion;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Bulletins.queryByProprietary"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1,  proprietary);
			
			// Se ejecuta la declaracion
		    ResultSet rs = ps.executeQuery();
		    
		    // Se recorre lps resultados de la consulta
		    while (rs.next()) {
		    	int id = rs.getInt("id");
			    int tipo = rs.getInt("type");
			    String titulo = rs.getString("title");
			    String cuerpo = rs.getString("body");
			    Date fechaPublicacion = rs.getDate("publication_date");
			    int fase = rs.getInt("phase");
		        
			    // Se agrega el usuario al resultado
			    if (tipo == 1) {
			    	gb = new GenBulletin(id, titulo, cuerpo, fechaPublicacion, proprietary, fase);
			    	resultado.add(gb);
			    }
			    else if (tipo == 2) {
			    	intereses = DAOInterestsThemBulletins.queryById(id);
			    	tb = new ThemBulletin(id, titulo, cuerpo, fechaPublicacion, proprietary, fase, intereses);
			    	resultado.add(tb);
			    }
			    else if (tipo == 3) {
			    	objetivos = DAOReceiversIndBulletins.queryById(id);
			    	ib = new IndBulletin(id, titulo, cuerpo, fechaPublicacion, proprietary, fase, objetivos);
			    	resultado.add(ib);
			    }
			    else if (tipo == 4) {
			    	fechaFinalizacion = DAOFlashBulletins.queryById(id);
			    	fb = new FlashBulletin(id, titulo, cuerpo, fechaPublicacion, proprietary, fase, fechaFinalizacion);
			    	resultado.add(fb);
			    }
		    }
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return resultado;
	}
	
	/**
	 * Devuelve la informacion de todos los anuncios seleccionados por su 
	 * fase.
	 * 
	 * @param   phase  la fase en la que se encuentra el anuncio
	 * @return         la lista de anuncios encontrados
	 */
	public static ArrayList<Bulletin> queryByPhase(int phase) {
		ArrayList<Bulletin> resultado = new ArrayList<Bulletin>();
		GenBulletin gb = null;
		ThemBulletin tb = null;
		IndBulletin ib = null;
		FlashBulletin fb = null;
		ArrayList<Boolean> intereses = new ArrayList<>();
		ArrayList<Integer> objetivos = new ArrayList<>();
		Date fechaFinalizacion;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Bulletins.queryByFase"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1,  phase);
			
			// Se ejecuta la declaracion
		    ResultSet rs = ps.executeQuery();
		    
		    // Se recorre lps resultados de la consulta
		    while (rs.next()) {
		    	int id = rs.getInt("id");;
			    int tipo = rs.getInt("type");
			    String titulo = rs.getString("title");
			    String cuerpo = rs.getString("body");
			    Date fechaPublicacion = rs.getDate("publication_date");
			    int propietario = rs.getInt("proprietary");
		        
			    // Se agrega el usuario al resultado
			    if (tipo == 1) {
			    	gb = new GenBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, phase);
			    	resultado.add(gb);
			    }
			    else if (tipo == 2) {
			    	intereses = DAOInterestsThemBulletins.queryById(id);
			    	tb = new ThemBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, phase, intereses);
			    	resultado.add(tb);
			    }
			    else if (tipo == 3) {
			    	objetivos = DAOReceiversIndBulletins.queryById(id);
			    	ib = new IndBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, phase, objetivos);
			    	resultado.add(ib);
			    }
			    else if (tipo == 4) {
			    	fechaFinalizacion = DAOFlashBulletins.queryById(id);
			    	fb = new FlashBulletin(id, titulo, cuerpo, fechaPublicacion, propietario, phase, fechaFinalizacion);
			    	resultado.add(fb);
			    }
		    }
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return resultado;
	}
}
