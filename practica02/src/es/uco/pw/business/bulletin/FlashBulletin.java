/**
 * Anuncio que puede ser visto por todos los usuarios que se archivara 
 * automaticamente dada una fecha.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.business.bulletin;

import java.util.Date;

public class FlashBulletin extends Bulletin {

	static private int tipo = 4;
	Date fechaFinalizacion;
	
	/**
	 * Crea un anuncio de tipo general.
	 * 
	 * @param  id                 el identificador unico del anuncio
	 * @param  titulo             la cadena con el titulo del anuncio
	 * @param  cuerpo             la cadena con el cuerpo del anuncio
	 * @param  fechaPublicacion   la fecha de publicacion del anuncio
	 * @param  propietario        el identificador unico del propietario
	 * @param  fase               la fase en la que se encuentra el anuncio
	 * @param  fechaFinalizacion  la fecha de finalizacion del anuncio
	 */
	public FlashBulletin(int id, String titulo, String cuerpo, 
			Date fechaPublicacion, int propietario, int fase, 
			Date fechaFinalizacion) {
		setId(id);
		setTitulo(titulo);
		setCuerpo(cuerpo);
		setFechaPublicacion(fechaPublicacion);
		setPropietario(propietario);
		setFase(fase);
		setFechaFinalizacion(fechaFinalizacion);
	}
	
	/**
	 * Devuelve el tipo del anuncio. El tipo es un valor entre 1 y 4 que 
	 * representan los valores: general, tematico, individualizado
	 * y flash. 
	 * 
	 * @return  el tipo de anuncio definido.
	 */
	@Override
	public int getTipo() {
		return tipo;
	}
	
	/**
	 * Devuelve la fecha de finalizacion del anuncio.
	 * 
	 * @return  la fecha de finalizacion del anuncio
	 */
	public Date getFechaFinalizacion() {
		return this.fechaFinalizacion;
	}
	
	/**
	 * Asigna una fecha de finalizacion al anuncio. El anuncio se encontrara
	 * en fase publicado hasta que llegue la fecha asignada, donde pasara a 
	 * estar archivado.
	 * 
	 * @param  fechaFinalizacion  la fecha de finalizacion del anuncio
	 */
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}
}
