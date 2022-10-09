/**
 * Gestor de usuarios.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.business.user;
import es.uco.pw.data.dao.user.DAOUser;
import java.util.ArrayList;
import java.text.SimpleDateFormat; 

public class UserMgr {

	private static UserMgr instance = null;
	private InterestMgr interestMgr = null;
	
	/**
	 * Crea la agenda con los usuarios.
	 */
	private UserMgr() {
		this.interestMgr = InterestMgr.getInstance();
	}

	/**
     * Obtiene acceso al gestor de usuarios.
     *
	 * @return  el gestor de usuarios
     */
	public static UserMgr getInstance() {
		if (instance == null) {
			instance = new UserMgr();
		}
		return instance;
	}
	
	/**
	 * Obtiene la informacion del usuario que tenga un email determinado. 
	 *
	 * @param   email  la cadena con el correo electronico a buscar
	 * @return         el usuario con el mismo correo electronico
	 */
	public User login(String email, String password) {
		User u = DAOUser.login(email, password);
		return u;
	}
	
	/**
	 * Agrega un usuario en el gestor con información personal especificada.
	 * El usuario debe verificar previamente que el email no esté en uso.
	 *
     * @param  u         el usuario cuya informacion se va a registrar
     * @param  password  la cadena con la contraseña de sesion
	 */
	public void agregarUsuario(User u, String password) {
		DAOUser.save(u, password);
	}
	
	/**
	 * Sobreescribe la informacion de un usuario.
	 *
	 * @param  u  el usuario cuya informacion se va a modificar
	 */
	public void editarUsuario(User u) {
		DAOUser.update(u);
	}
	
	/**
	 * Elimina la informacion de un usuario.
	 *
     * @param  id  el codigo de identificacion del usuario
	 */
	public void eliminarUsuario(int id) {
		DAOUser.delete(id);
	}
	
	/**
     * Imprime la informacion del usuario.
     *
	 * @param  u  el usuario cuya informacion se va a mostrar
     */
	public void consultarUsuario(User u) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
		System.out.println("\tIdentificador: " + u.getId());
		System.out.println("\tNombre: " + u.getNombre());
		System.out.println("\tApellidos: " + u.getApellidos());
		System.out.println("\tFecha de nacimiento: " + df.format(u.getFechaNacimiento()));
		System.out.println("\tEmail: " + u.getEmail());
		System.out.print("\tIntereses: ");
		boolean primero = true;
		
		for (int i=0; i < interestMgr.getNumInterests(); i++) {
			if (u.esInteres(i)) {
				if (primero) {
					System.out.print(interestMgr.getInterest(i));
					primero = false;
				} else {
					System.out.print(", " + interestMgr.getInterest(i));
				}
			}
		}
		System.out.println("");
		System.out.println("");
	}
	
	/**
	 * Imprime la informacion de todos los usuarios de una lista.
	 * 
	 * @param  users  la lista de usuarios a mostrar
	 */
	public void consultarUsuarios(ArrayList<User> users) {
		for (User u : users) {  
			consultarUsuario(u);
		}
	}
	
	/**
	 * Imprime la informacion de todos los usuarios de la agenda.
	 */
	public void consultarUsuarios() {
		ArrayList<User> usuarios = DAOUser.getAll();
		consultarUsuarios(usuarios);
	}
	
	/**
	 * Comprueba si un identificador pertenece a un usuario de la agenda.
	 *
	 * @param   id  el identificador del usuario que se pretende buscar
	 * @return      <code>true</code> si existe un usuario con el mismo 
	 *              identificador; <code>false</code> en caso contrario.
	 */
	public boolean existeUsuarioId(int id) {
		User u = buscarUsuarioId(id);
		return (u != null);
	}
	
	/**
	 * Comprueba si un correo electronico pertenece a un usuario de la agenda.
	 *
	 * @param   email  la cadena con el correo electronico que se pretende buscar
     * @return         <code>true</code> si hay un usuario con el mismo 
	 *                 correo electronico; <code>false</code> en caso contrario.
	 */
	public boolean existeUsuarioEmail(String email) {
		User u = buscarUsuarioEmail(email);
		return (u != null);
	}
	
	/**
	 * Obtiene la informacion del usuario a partir de su identificador.
	 *
	 * @param   id  el identificador del usuario qye se pretende buscar
	 * @return      el usuario con el mismo identificador
	 */
	public User buscarUsuarioId(int id) {
		User u = DAOUser.queryById(id);	
		return u;
	}
	
	/**
	 * Obtiene la informacion del usuario que tenga un email determinado. 
	 *
	 * @param email  la cadena con el correo electronico que se pretende buscar
	 * @return       el usuario con el mismo correo electronico
	 */
	public User buscarUsuarioEmail(String email) {
		User u = DAOUser.queryByEmail(email);
		return u;
	}
	
	/**
	 * Obtiene la informacion de los usuarios que tengan un nombre determinado 
	 *
	 * @param  nombre     la cadena con el nombre que se pretende buscar
	 * @param  apellidos  la cadena con los apellidos que se pretende buscar
 	 * @return            la lista con los usuarios que tengan el mismo nombre
	 */
	public ArrayList<User> buscarUsuariosNombre(String nombre, String apellidos) {
		ArrayList<User> result = DAOUser.queryByName(nombre, apellidos);
		return result;
	}
	
	/**
	 * Obtiene la informacion de los usuarios que tengan una edad determinada.
	 *
	 * @param  edad  la edad de los usuarios que se pretenden buscar
	 * @return       la lista con los usuarios que tengan la misma edad 
	 */
	public ArrayList<User> buscarUsuariosEdad(int edad) {
		ArrayList<User> result = DAOUser.queryByAge(edad);
		return result;
	}
}
