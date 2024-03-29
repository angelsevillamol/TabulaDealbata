/**
* Anuncio que puede ser visto por todos los usuarios.
*
* @author Mu�oz Jimenez, Juan Pedro (p52mujij@uco.es)
* @author Sevilla Molina, Angel (i42semoa@uco.es)
*/
package es.uco.pw.anuncios;

import java.time.LocalDate;
import java.util.ArrayList;

public class AnuncioIndividualizado implements IAnuncio {

	static private int tipo = 3;
	private int id;
	private String titulo;
	private String cuerpo;
	private int idPropietario;
	private ArrayList<Integer> idObjetivos;
	private LocalDate fechaPublicacion;
	private int fase;		
		
	/**
	* Crea un anuncio de tipo Individualizado en fase de editado.
	* 
	* @param  id                    el identificador unico del anuncio
	* @param  titulo                la cadena con el titulo del anuncio
	* @param  cuerpo                la cadena con el cuerpo del anuncio
	* @param  idUsuarioPropietario  el identificador unico del propietario
	* @param  objetivos				array de ids objetivos del anuncio
	*/
	public AnuncioIndividualizado(int id, String titulo, String cuerpo, 
	int propietario, ArrayList<Integer> objetivos) {
		this.idObjetivos = objetivos;
		this.id = id;
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.idPropietario = propietario;
		this.fechaPublicacion = null;
		this.fase = 0;
	}
	
	/**
	* Crea un anuncio de tipo Individualizado.
	* 
	* @param  id                    el identificador unico del anuncio
	* @param  titulo                la cadena con el titulo del anuncio
	* @param  cuerpo                la cadena con el cuerpo del anuncio
	* @param  idUsuarioPropietario  el identificador unico del propietario
	* @param  objetivos				array de ids objetivos del anuncio
	* @param  fecha      la fecha de publicacion del anuncio
	*/
	public AnuncioIndividualizado(int id, String titulo, String cuerpo, 
	int propietario, LocalDate fecha, int fase, ArrayList<Integer> objetivos) {
		this.idObjetivos = objetivos;
		this.id = id;
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.idPropietario = propietario;
		this.fechaPublicacion = fecha;
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
		return this.id;
	}

	/**
	* Devuelve el titulo del anuncio.
	* 
	* @return  la cadena con el titulo del anuncio
	*/
	@Override
	public String getTitulo() {
		return this.titulo;
	}

	/**
	* Devuelve el cuerpo del anuncio.
	* 
	* @return  la cadena con el cuerpo del anuncio
	*/
	@Override
	public String getCuerpo() {
		return this.cuerpo;
	}

	/**
	* Devuelve la fecha de publicacion del anuncio, que determina a partir
	* de que fecha el anuncio sera visible para los usuarios.
	* 
	* @return  la fecha de publicacion del anuncio
	*/
	@Override
	public LocalDate getFechaPublicacion() {
		return this.fechaPublicacion;
	}

	/**
	* Devuelve la clave de identificacion del usuario propietario del anuncio.
	* 
	* @return  el identificador unico del contacto propietario
	*/
	@Override
	public int getIdUsuarioPropietario() {
		return this.idPropietario;
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
		return this.fase;
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
	* Devuelve la lista de objetivos.
    *
	*/
	public ArrayList<Integer> getObjetivos(){
		return this.idObjetivos;
	}
	
	/**
	* Devuelve si el id es objetivo o no.
    *
    * @param  id     id a comparar
	*/
	public boolean esObjetivo(int id) {
		for (int i:this.idObjetivos) {
			if(this.idObjetivos.get(i)==id) {
				return true;
			}
		}
		return false;
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
	public void archivarAnuncio() {
		fase = 3;
	}
	
	/**
	* A�ade un objetivo a la lista
	* 
	* @param  id		id del usuario objetivo
	*/
	public void addObjetivo(int id) {
		this.idObjetivos.add(id);
	}

}
