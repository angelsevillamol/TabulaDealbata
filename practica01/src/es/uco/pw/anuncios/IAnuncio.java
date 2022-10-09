/**
* Interfaz que declara operaciones de los anuncios.
*
* @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
* @author Sevilla Molina, Angel (i42semoa@uco.es)
*/

package es.uco.pw.anuncios;

import java.time.LocalDate; 

public interface IAnuncio {
	
	/**
	* Devuelve el tipo del anuncio. El tipo es un valor entre 1 y 4 que 
	* representan los siguientes valores: general, tematico, individualizado
	* y flash. 
	* 
	* @return  el tipo de anuncio definido.
	*/
	public int getTipo();
	
	/**
	* Devuelve la clave de identificacipn del anuncio. La clave debe ser
	* un numero entero unico para cada anuncio.
	* 
	* @return  el identificador unico del anuncio
	*/
	public int getId();
	
	/**
	* Devuelve el titulo del anuncio.
	* 
	* @return  la cadena con el titulo del anuncio
	*/
	public String getTitulo();
	
	/**
	* Devuelve el cuerpo del anuncio.
	* 
	* @return  la cadena con el cuerpo del anuncio
	*/
	public String getCuerpo();
	
	/**
	* Devuelve la fecha de publicacion del anuncio, que determina a partir
	* de que fecha el anuncio sera visible para los usuarios.
	* 
	* @return  la fecha de publicacion del anuncio
	*/
	public LocalDate getFechaPublicacion();
	
	/**
	* Devuelve la clave de identificacion del usuario propietario del anuncio.
	* 
	* @return  el identificador unico del contacto propietario
	*/
	public int getIdUsuarioPropietario();
	
	/**
	* Devuelve la fase en la que se encuentra el anuncio. La fase es un valor 
	* entre 0 y 3 que representan los siguientes estados respectivamente: 
	* editado, en-espera, publicado y archivado.
	* 
	* @return  la fase en la que se encuentra el anuncio
	*/
	public int getFase();
	
	/**
	* Devuelve si el anuncio se encuentra publicado.
    *
	* @return   <code>true</code> si el anuncio esta en fase de publicado;
	*           <code>false</code> en caso contrario.
	*/
	public boolean estaPublicado();
	
	/**
	* Devuelve si el anuncio se encuentra archivado.
    *
	* @return   <code>true</code> si el anuncio esta en fase de archivado;
	*           <code>false</code> en caso contrario.
	*/
	public boolean estaArchivado();
	
	/**
	* Asigna el titulo del anuncio.
	* 
	* @param  titulo  la cadena con el titulo del anuncio
	*/
	public void setTitulo(String titulo);
	
	/**
	* Asigna el cuerpo del anuncio.
	* 
	* @param  cuerpo  la cadena con el cuerpo del anuncio
	*/
	public void setCuerpo(String cuerpo);
	
	/**
	* Asigna una fecha de publicacion al anuncio. El anuncio se encontrara
	* en fase en-espera hasta que llegue la fecha asignada, donde pasara a 
	* estar publicado.
	* 
	* @param  fechaPublicacion  la fecha de publicacion del anuncio
	*/
	public void publicarAnuncio(LocalDate fechaPublicacion);
	
	/**
	* Archiva el anuncio.
	*/
	public void archivarAnuncio();
}
