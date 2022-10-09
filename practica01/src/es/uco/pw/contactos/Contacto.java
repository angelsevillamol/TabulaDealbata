/**
* Informacion personal de un contacto.
*
* @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
* @author Sevilla Molina, Angel (i42semoa@uco.es)
*/

package es.uco.pw.contactos;
import java.time.LocalDate;  
import java.time.Period;
import java.util.ArrayList;

public class Contacto {

	private int id;
	private String nombre; 
	private String apellidos;
	private LocalDate fechaNacimiento; 
	private String email;
	private ArrayList<Boolean> intereses = null;
	
	/**
    * Crea un usuario de contacto a partir de su informacion personal. 
    *
	* @param id               el identificador unico del contacto
    * @param nombre           la cadena con el nombre personal del contacto 
	* @param apellidos        la cadena con los apellidos del contacto 
	* @param fechaNacimiento  la fecha de nacimiento del contacto 
	* @param email            la cadena con el correo electronico del contacto 
    * @param intereses        la lista que contiene los areas de interes del contacto
    */
	public Contacto(int id, String nombre, String apellidos, 
	LocalDate fechaNacimiento, String email, ArrayList<Boolean> intereses) {
		this.id = id;
		setNombre(nombre);
		setApellidos(apellidos);
		setFechaNacimiento(fechaNacimiento);
		setEmail(email);
		this.intereses = new ArrayList<>(intereses);
	}
	
	/**
    * Devuelve la clave de identificacipn del contacto. La clave debe ser
	* un numero entero unico para cada contacto.
    *
    * @return  el identificador unico del contacto
    */
	public int getId() { 
		return id; 
	}

	/**
    * Devuelve el nombre personal del contacto.
    *
    * @return  la cadena con el nombre del contacto 
    */
	public String getNombre() { 
		return nombre; 
	}

	/**
    * Devuelve los apellidos del contacto.
    *
    * @return  la cadena con los apellidos del contacto 
    */
	public String getApellidos() { 
		return apellidos; 
	}

	/**
    * Devuelve la fecha de nacimiento del contacto.
    *
    * @return  la fecha de nacimiento del contacto 
    */
	public LocalDate getFechaNacimiento() { 
		return fechaNacimiento; 
	}

	/**
    * Devuelve la edad actual en años del contacto. La edad se calcula a 
	* partir de la fecha de nacimiento comparandola con el dia actual. 
    *
    * @return  la edad actual en años del contacto
    */
	public int getEdad() { 
		LocalDate actual = LocalDate.now();
		Period p = Period.between(fechaNacimiento, actual);
		return p.getYears(); 
	}

	/**
    * Devuelve el correo electronico del contacto.
    *
    * @return  la cadena con el correo electronico del contacto 
    */
	public String getEmail() { 
		return this.email; 
	}
	
	/**
	* Devuelve si el contacto esta interesado en un campo en concreto.
    *
    * @param   i  el indice correspondiente al interes a comprobar
	* @return     <code>true</code> si el usuario esta interesado en el i-esimo
	*             campo; <code>false</code> en caso contrario.
	*/
	public boolean esInteres(int i) {
		return intereses.get(i);
	}
	
	/**
    * Asigna el nombre personal del contacto.
    *
    * @param  nombre  la cadena con el nombre del contacto 
    */
	public void setNombre(String nombre) { 
		this.nombre = nombre; 
	}

	/**
    * Asigna los apellidos del contacto.
    *
    * @param  apellidos  la cadena con los apellidos del contacto 
    */
	public void setApellidos(String apellidos) { 
		this.apellidos = apellidos; 
	}

	/**
    * Asigna la fecha de nacimiento del contacto.
    *
    * @param  fechaNacimiento  la fecha de nacimiento del contacto 
    */
	public void setFechaNacimiento(LocalDate fechaNacimiento) { 
		this.fechaNacimiento = fechaNacimiento; 
	}

	/**
    * Asigna el correo electronico del contacto.
    *
    * @param  email  la cadena con el correo electronico del contacto 
    */
	public void setEmail(String email) { 
		this.email = email; 
	}
	
	/**
	* Asigna el interes del contacto a un area concreta.
	*
	* @param  i        el indice correspondiente al interes a comprobar
	* @param  interes  el booleano indicativo si esta interesado o no
	*/
	public void setInteres(int i, boolean interes) {
		intereses.set(i, interes);
	}
}
