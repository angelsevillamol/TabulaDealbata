/**
 * Objeto de acceso de datos de usuarios de la aplicacion.
 * 
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.data.dao.user;

import es.uco.pw.data.dao.common.DAO;
import es.uco.pw.business.user.User;
import java.sql.*;
import java.util.ArrayList;

public class DAOUser {
	
	/**
	 * Registra un usuario en la aplicacion.
	 * 
	 * @param   u         el usuario a registrar
	 * @param   password  la cadena con la contraseña de sesion del usuario
	 * @return            el estado resultado de la operacion          
	 */
	public static int save(User u, String password) {
		int status = 0;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps=con.prepareStatement(DAO.getStmt("Users.save"));
			
			// Se insertan los parametros en la consulta
			ps.setString(1, u.getEmail());
			ps.setString(2, password);
			ps.setString(3, u.getNombre());
			ps.setString(4, u.getApellidos());
			ps.setDate(5, new java.sql.Date(u.getFechaNacimiento().getTime()));
			
			// Se ejecuta la declaracion
			status = ps.executeUpdate();
			
			// Se busca el identificador asignado
			PreparedStatement psid = con.prepareStatement(DAO.getStmt("Users.getLastId"));
			ResultSet rs = psid.executeQuery();
			
			// Se recorre el resultado de la consulta
			while (rs.next()) {
				// Se le asigna el identificador generado
				u.setId(rs.getInt("id"));
			}

			// Se insertan tambien los intereses correspondientes
			status = DAOInterestsUsers.save(u);
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	/**
	 * Actualiza la informacion de un usuario.
	 * 
	 * @param   u  el usuario a actualizar
	 * @return     el estado resultado de la operacion          
	 */
	public static int update(User u) {
		int status = 0;
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Users.update"));
			
			// Se insertan los parametros en la consulta
			ps.setString(1, u.getEmail());
			ps.setString(2, u.getNombre());
			ps.setString(3, u.getApellidos());
			ps.setDate(4, new java.sql.Date(u.getFechaNacimiento().getTime()));
			ps.setInt(5, u.getId());
			
			// Se ejecuta la declaracion
			status = ps.executeUpdate();
			
			// Se actualizan sus intereses
			status = DAOInterestsUsers.update(u);
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	
	/**
	 * Elimina la informacion de un usuario.
	 * 
	 * @param   id  el identificador del usuario a eliminar
	 * @return      el estado resultado de la operacion          
	 */
	public static int delete(int id) {
		int status = 0;
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps=con.prepareStatement(DAO.getStmt("Users.delete"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1, id);
			
			// Se ejecuta la declaracion
			status = ps.executeUpdate();
			
			// Se eliminan sus intereses asociados
			status = DAOInterestsUsers.delete(id);	
		} catch (Exception e) {
			System.out.println(e);
		}
	
		return status;
	}
	
	/**
	 * Realiza el inicio de sesion 
	 * 
	 * @param   email     la cadena con el correo electronico del usuario 
	 * @param   password  la cadena con la contraseña de acceso del usuario
	 * @return            el usuario correspondiente si la informacion de 
	 *                    sesion es correcta
	 */
	public static User login(String email, String password) { 
		User u = null;
		ArrayList<Boolean> intereses = new ArrayList<>();
			
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Users.login"));
			
			// Se insertan los parametros en la consulta
			ps.setString(1,  email);
			ps.setString(2,  password);
			
			// Se ejecuta la declaracion
			ResultSet rs = ps.executeQuery();
			
			// Se recorre el resultado de la consulta
			while (rs.next()) {
			    int id = rs.getInt("id");
			    String nombre = rs.getString("name");
			    String apellidos = rs.getString("surnames");
			    Date fechaNacimiento = rs.getDate("birthdate");
			    intereses = DAOInterestsUsers.queryById(id);
			    u = new User(id, nombre, apellidos, fechaNacimiento, email, intereses);
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
			
		return u;
	}
	
	/**
	 * Devuelve la informacion de todos los usuarios.
	 * 
	 * @return  la lista de usuarios almacenados en la base de datos
	 */
	public static ArrayList<User> getAll() { 
		ArrayList<User> resultado = new ArrayList<User>();
		User u = null;
		ArrayList<Boolean> intereses = new ArrayList<>();
			
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();

			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Users.getAll"));
			
			// Se ejecuta la declaracion
			ResultSet rs = ps.executeQuery();
			
			// Se recorre el resultado de la consulta
			while (rs.next()) {
			    int id = rs.getInt("id");
			    String email = rs.getString("email");
			    String nombre = rs.getString("name");
			    String apellidos = rs.getString("surnames");
			    Date fechaNacimiento = rs.getDate("birthdate");
			    intereses = DAOInterestsUsers.queryById(id);
			    u = new User(id, nombre, apellidos, fechaNacimiento, email, intereses);
			    
			    // Se agrega el usuario al resultado
			    resultado.add(u);
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return resultado;
	}
	
	/**
	 * Devuelve la informacion del usuario seleccionado por su clave 
	 * de identificacion.
	 * 
	 * @param   id  el identificador unico del usuario
	 * @return      el usuario con la clave de identificacion indicada
	 */
	public static User queryById(int id) {
		User u = null;
		ArrayList<Boolean> intereses = new ArrayList<>();
		
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Users.queryById"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1,  id);
			
			// Se ejecuta la declaracion
		    ResultSet rs = ps.executeQuery();
		    
		    // Se recorre el resultado de la consulta
		    while (rs.next()) {
		    	String email = rs.getString("email");
		        String nombre = rs.getString("name");
		        String apellidos = rs.getString("surnames");
		        Date fechaNacimiento = rs.getDate("birthdate");
		        intereses = DAOInterestsUsers.queryById(id);
		        u = new User(id, nombre, apellidos, fechaNacimiento, email, intereses);
		    }
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return u;
	}
	
	/**
	 * Devuelve la informacion del usuario seleccionado por su  
	 * correo electronico.
	 * 
	 * @param   email  la cadena con el correo elecronico del usuario
	 * @return         el usuario con el correo electronico indicado
	 */
	public static User queryByEmail(String email) { 
		User u = null;
		ArrayList<Boolean> intereses = new ArrayList<>();
			
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Users.queryByEmail"));
			
			// Se insertan los parametros en la consulta
			ps.setString(1,  email);
			
			// Se ejecuta la declaracion
			ResultSet rs = ps.executeQuery();
			
			// Se recorre el resultado de la consulta
			while (rs.next()) {
			    int id = rs.getInt("id");
			    String nombre = rs.getString("name");
			    String apellidos = rs.getString("surnames");
			    Date fechaNacimiento = rs.getDate("birthdate");
			    intereses = DAOInterestsUsers.queryById(id);
			    u = new User(id, nombre, apellidos, fechaNacimiento, email, intereses);
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
			
		return u;
	}
	
	/**
	 * Devuelve la informacion de todos los usuarios seleccionados por su 
	 * nombre completo.
	 * 
	 * @param   nombre     la cadena con el nombre del usuario
	 * @param   apellidos  la cadena con los apellidos del usuario
	 * @return             la lista de usuarios encontrados
	 */
	public static ArrayList<User> queryByName(String nombre, String apellidos) { 
		ArrayList<User> resultado = new ArrayList<User>();
		User u = null;
		ArrayList<Boolean> intereses = new ArrayList<>();
			
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Users.queryByName"));
			
			// Se insertan los parametros en la consulta
			ps.setString(1,  nombre);
			ps.setString(2,  apellidos);
			
			// Se ejecuta la declaracion
			ResultSet rs = ps.executeQuery();
			
			// Se recorre el resultado de la consulta
			while (rs.next()) {
			    int id = rs.getInt("id");
			    String email = rs.getString("email");
			    Date fechaNacimiento = rs.getDate("birthdate");
			    intereses = DAOInterestsUsers.queryById(id);
			    u = new User(id, nombre, apellidos, fechaNacimiento, email, intereses);
			    
			    // Se agrega el usuario al resultado
			    resultado.add(u);
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
			
		return resultado;
	} 
	
	/**
	 * Devuelve la informacion de todos los usuarios seleccionados por su 
	 * edad.
	 * 
	 * @param   edad  la edad del usuario
	 * @return        la lista de usuarios encontrados
	 */
	public static ArrayList<User> queryByAge(int edad) { 
		ArrayList<User> resultado = new ArrayList<User>();
		User u = null;
		ArrayList<Boolean> intereses = new ArrayList<>();
			
		try {
			// Se realiza la conexion con la base de datos
			Connection con = DAO.getConnection();
			
			// Se prepara la declaracion
			PreparedStatement ps = con.prepareStatement(DAO.getStmt("Users.queryByAge"));
			
			// Se insertan los parametros en la consulta
			ps.setInt(1,  edad);
			
			// Se ejecuta la declaracion
			ResultSet rs = ps.executeQuery();
			
			// Se recorre el resultado de la consulta
			while (rs.next()) {
			    int id = rs.getInt("id");
			    String email = rs.getString("email");
			    String nombre = rs.getString("name");
			    String apellidos = rs.getString("surnames");
			    Date fechaNacimiento = rs.getDate("birthdate");
			    intereses = DAOInterestsUsers.queryById(id);
			    u = new User(id, nombre, apellidos, fechaNacimiento, email, intereses);
			    
			    // Se agrega el usuario al resultado
			    resultado.add(u);
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
			
		return resultado;
	}
}
