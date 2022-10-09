/**
 * Informacion basica de un anuncio.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.business.bulletin;

import java.util.Date;

public abstract class Bulletin {

	private int id;
	private String titulo;
	private String cuerpo;
	private Date fechaPublicacion;
	private int propietario;
	private int fase;
	
	/**
	 * Devuelve la clave de identificacipn del anuncio. La clave debe ser
	 * un numero entero unico para cada anuncio.
	 * 
	 * @return  el identificador unico del anuncio
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve el titulo del anuncio.
	 * 
	 * @return  la cadena con el titulo del anuncio
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Devuelve el cuerpo del anuncio.
	 * 
	 * @return  la cadena con el cuerpo del anuncio
	 */
	public String getCuerpo() {
		return cuerpo;
	}

	/**
	 * Devuelve la fecha de publicacion del anuncio, que determina a partir
	 * de que fecha el anuncio sera visible para los usuarios.
	 * 
	 * @return  la fecha de publicacion del anuncio
	 */
	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	 * Devuelve la clave de identificacion del usuario propietario del anuncio.
	 * 
	 * @return  el identificador unico del usuario propietario
	 */
	public int getPropietario() {
		return propietario;
	}

	/**
	 * Devuelve la fase en la que se encuentra el anuncio. La fase es un valor 
	 * entre 0 y 3 que representan los siguientes estados respectivamente: 
	 * editado, en-espera, publicado y archivado.
	 * 
	 * @return  la fase en la que se encuentra el anuncio
	 */
	public int getFase() {
		return fase;
	}
	
	/**
	 * Devuelve el tipo del anuncio. El tipo es un valor entre 1 y 4 que 
	 * representan los valores: general, tematico, individualizado
	 * y flash. 
	 * 
	 * @return  el tipo de anuncio definido.
	 */
	public abstract int getTipo();
	
	/**
	 * Asigna la clave de identificacion al anuncio.
	 * 
	 * @param  id  el identificador unico del anuncio
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Asigna el titulo del anuncio.
	 * 
	 * @param  titulo  la cadena con el titulo del anuncio
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Asigna el cuerpo del anuncio.
	 * 
	 * @param  cuerpo  la cadena con el cuerpo del anuncio
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	
	/**
	 * Asigna una fecha de publicacion al anuncio. El anuncio se encontrara
	 * en fase en-espera hasta que llegue la fecha asignada, donde pasara a 
	 * estar publicado.
	 * 
	 * @param  fechaPublicacion  la fecha de publicacion del anuncio
	 */
	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	
	/**
	 * Asigna la clave de identificacion del usuario propietario del anuncio.
	 * 
	 * @return  propietario  el identificador unico del usuario propietario
	 */
	public void setPropietario(int propietario) {
		this.propietario = propietario;
	}
	
	/**
	 * Asigna la fase en la que se encuentra el anuncio. La fase es un valor 
	 * entre 0 y 3 que representan los siguientes estados respectivamente: 
	 * editado, en-espera, publicado y archivado.
	 * 
	 * @return  fase la fase en la que se encuentra el anuncio
	 */
	public void setFase(int fase) {
		this.fase = fase;
	}
}
