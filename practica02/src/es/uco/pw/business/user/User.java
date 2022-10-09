/**
 * Informacion personal de un usuario.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.business.user;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;

public class User {

	private int id;
	private String nombre; 
	private String apellidos;
	private Date fechaNacimiento; 
	private String email;
	private ArrayList<Boolean> intereses = null;
	
	/**
     * Crea un usuario a partir de su informacion personal. 
     *
	 * @param id               el identificador unico del usuario
     * @param nombre           la cadena con el nombre personal del usuario 
	 * @param apellidos        la cadena con los apellidos del usuario 
	 * @param fechaNacimiento  la fecha de nacimiento del usuario 
	 * @param email            la cadena con el correo electronico del usuario 
     * @param intereses        la lista que contiene los areas de interes
     */
	public User(int id, String nombre, String apellidos, 
			Date fechaNacimiento, String email, ArrayList<Boolean> intereses) {
		this.id = id;
		setNombre(nombre);
		setApellidos(apellidos);
		setFechaNacimiento(fechaNacimiento);
		setEmail(email);
		this.intereses = new ArrayList<>(intereses);
	}
	
	/**
     * Devuelve la clave de identificacipn del usuario. La clave debe ser
	 * un numero entero unico para cada usuario.
     *
     * @return  el identificador unico del usuario
     */
	public int getId() { 
		return id; 
	}

	/**
     * Devuelve el nombre personal del usuario.
     *
     * @return  la cadena con el nombre del usuario 
     */
	public String getNombre() { 
		return nombre; 
	}

	/**
     * Devuelve los apellidos del usuario.
     *
     * @return  la cadena con los apellidos del usuario 
     */
	public String getApellidos() { 
		return apellidos; 
	}

	/**
     * Devuelve la fecha de nacimiento del usuario.
     *
     * @return  la fecha de nacimiento del usuario 
     */
	public Date getFechaNacimiento() { 
		return fechaNacimiento; 
	}

	/**
     * Devuelve la edad actual en años del usuario. La edad se calcula a 
	 * partir de la fecha de nacimiento comparandola con el dia actual. 
     *
     * @return  la edad actual en años del usuario
     */
	public int getEdad() { 
		Calendar birthDay = new GregorianCalendar(); 
		Calendar today = new GregorianCalendar(); 
		
		birthDay.setTime(getFechaNacimiento());
		today.setTime(new Date()); 
		
		int years = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR); 
		return years; 
	}

	/**
     * Devuelve el correo electronico del usuario.
     *
     * @return  la cadena con el correo electronico del usuario 
     */
	public String getEmail() { 
		return this.email; 
	}
	
	/**
	 * Devuelve la lista de intereses del usuario.
     *
	 * @return  la lista de intereses del usuario
	 */
	public ArrayList<Boolean> getIntereses() {
		return intereses;
	}
	
	/**
	 * Devuelve si el usuario esta interesado en un campo en concreto.
     *
     * @param   i  el indice correspondiente al interes a comprobar
	 * @return     <code>true</code> si el usuario esta interesado en el i-esimo
	 *             campo; <code>false</code> en caso contrario.
	 */
	public boolean esInteres(int i) {
		return intereses.get(i);
	}
	
	/**
	 * Asigna la clave de identificacipn del usuario.
	 *
	 * @param  id  el identificador unico del usuario
	 */
	public void setId(int id) { 
		this.id = id; 
	}

	/**
     * Asigna el nombre personal del usuario.
     *
     * @param  nombre  la cadena con el nombre del usuario 
     */
	public void setNombre(String nombre) { 
		this.nombre = nombre; 
	}

	/**
     * Asigna los apellidos del usuario.
     *
     * @param  apellidos  la cadena con los apellidos del usuario 
     */
	public void setApellidos(String apellidos) { 
		this.apellidos = apellidos; 
	}

	/**
     * Asigna la fecha de nacimiento del usuario.
     *
     * @param  fechaNacimiento  la fecha de nacimiento del usuario 
     */
	public void setFechaNacimiento(Date fechaNacimiento) { 
		this.fechaNacimiento = fechaNacimiento; 
	}

	/**
     * Asigna el correo electronico del usuario.
     *
     * @param  email  la cadena con el correo electronico del usuario 
     */
	public void setEmail(String email) { 
		this.email = email; 
	}
	
	/**
	 * Asigna la lista de intereses del usuario.
	 *
	 * @param  intereses  la lista de intereses de usuario
	 */
	public void setIntereses(ArrayList<Boolean> intereses) { 
		this.intereses = intereses; 
	}
	
	/**
	 * Asigna el interes del usuario a un area concreta.
	 *
	 * @param  i        el indice correspondiente al interes a comprobar
	 * @param  interes  <code>true</code> si el usuario esta interesado en el 
	 *                  i-esimo campo; <code>false</code> en caso contrario.
	 */
	public void setInteres(int i, boolean interes) {
		intereses.set(i, interes);
	}
}
