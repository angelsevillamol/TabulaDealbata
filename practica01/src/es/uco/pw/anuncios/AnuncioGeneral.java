/**
* Anuncio que puede ser visto por todos los usuarios.
*
* @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
* @author Sevilla Molina, Angel (i42semoa@uco.es)
*/

package es.uco.pw.anuncios;

import java.time.LocalDate;

public class AnuncioGeneral implements IAnuncio {

	static private int tipo = 1;
	private int id;
	private String titulo;
	private String cuerpo;
	private LocalDate fechaPublicacion;
	private int idUsuarioPropietario;
	private int fase;
	
	/**
	* Crea un anuncio de tipo general en fase de editado.
	* 
	* @param  id                    el identificador unico del anuncio
	* @param  titulo                la cadena con el titulo del anuncio
	* @param  cuerpo                la cadena con el cuerpo del anuncio
	* @param  idUsuarioPropietario  el identificador unico del propietario
	*/
	public AnuncioGeneral(int id, String titulo, String cuerpo, 
	int idUsuarioPropietario) {
		this.id = id;
		setTitulo(titulo);
		setCuerpo(cuerpo);
		this.fechaPublicacion = null;
		this.idUsuarioPropietario = idUsuarioPropietario;
		fase = 0;
	}
	
	/**
	* Crea un anuncio de tipo general.
	* 
	* @param  id                    el identificador unico del anuncio
	* @param  titulo                la cadena con el titulo del anuncio
	* @param  cuerpo                la cadena con el cuerpo del anuncio
	* @param  idUsuarioPropietario  el identificador unico del propietario
	* @param  fechaPublicacion      la fecha de publicacion del anuncio
	*/
	public AnuncioGeneral(int id, String titulo, String cuerpo, 
	int idUsuarioPropietario, LocalDate fechaPublicacion, int fase) {
		this.id = id;
		setTitulo(titulo);
		setCuerpo(cuerpo);
		this.fechaPublicacion = fechaPublicacion;
		this.idUsuarioPropietario = idUsuarioPropietario;
		this.fase = fase;
	}
	
	/**
	* Devuelve el tipo del anuncio. El tipo es un valor entre 1 y 4 que 
	* representan los siguientes valores: general, tematico, individualizado
	* y flash. 
	* 
	* @return  el tipo de anuncio definido.
	*/
	@Override
	public int getTipo() {
		return tipo;
	}
	
	/**
	* Devuelve la clave de identificacipn del anuncio. La clave debe ser
	* un numero entero unico para cada anuncio.
	* 
	* @return  el identificador unico del anuncio
	*/
	@Override
	public int getId() {
		return id;
	}

	/**
	* Devuelve el titulo del anuncio.
	* 
	* @return  la cadena con el titulo del anuncio
	*/
	@Override
	public String getTitulo() {
		return titulo;
	}

	/**
	* Devuelve el cuerpo del anuncio.
	* 
	* @return  la cadena con el cuerpo del anuncio
	*/
	@Override
	public String getCuerpo() {
		return cuerpo;
	}

	/**
	* Devuelve la fecha de publicacion del anuncio, que determina a partir
	* de que fecha el anuncio sera visible para los usuarios.
	* 
	* @return  la fecha de publicacion del anuncio
	*/
	@Override
	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	* Devuelve la clave de identificacion del usuario propietario del anuncio.
	* 
	* @return  el identificador unico del contacto propietario
	*/
	@Override
	public int getIdUsuarioPropietario() {
		return idUsuarioPropietario;
	}

	/**
	* Devuelve la fase en la que se encuentra el anuncio. La fase es un valor 
	* entre 0 y 3 que representan los siguientes estados respectivamente: 
	* editado, en-espera, publicado y archivado.
	* 
	* @return  la fase en la que se encuentra el anuncio
	*/
	@Override
	public int getFase() {
		return fase;
	}
	
	/**
	* Devuelve si el anuncio se encuentra publicado.
    *
	* @return   <code>true</code> si el anuncio esta en fase de publicado;
	*           <code>false</code> en caso contrario.
	*/
	@Override
	public boolean estaPublicado() {
		return this.fase == 2;
	}
	
	/**
	* Devuelve si el anuncio se encuentra archivado.
    *
	* @return   <code>true</code> si el anuncio esta en fase de archivado;
	*           <code>false</code> en caso contrario.
	*/
	@Override
	public boolean estaArchivado() {
		return this.fase == 3;
	}

	/**
	* Asigna el titulo del anuncio.
	* 
	* @param  titulo  la cadena con el titulo del anuncio
	*/
	@Override
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	* Asigna el cuerpo del anuncio.
	* 
	* @param  cuerpo  la cadena con el cuerpo del anuncio
	*/
	@Override
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
	@Override
	public void publicarAnuncio(LocalDate fechaPublicacion) {
		if (fase == 0) {
			this.fechaPublicacion = fechaPublicacion;
			fase = fase + 1;
			if (this.fechaPublicacion.isBefore(LocalDate.now()) || 
					this.fechaPublicacion.isEqual(LocalDate.now())) {
				fase = fase + 1;
			}
		}
	}
	
	/**
	* Archiva el anuncio.
	*/
	@Override
	public void archivarAnuncio() {
		fase = 3;
	}
}
