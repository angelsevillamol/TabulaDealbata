/**
 * Vista que implementa los métodos para el inicio de sesion.
 * 
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.display.javabean;

public class CustomerBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String emailUser = "";

	/**
     * Devuelve el correo electronico del usuario.
     *
     * @return  la cadena con el correo electronico del usuario 
     */
	public String getEmailUser() {
		return emailUser;
	}

	/**
     * Asigna el correo electronico del usuario.
     *
     * @param  emailUser  la cadena con el correo electronico del usuario 
     */
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	
}
