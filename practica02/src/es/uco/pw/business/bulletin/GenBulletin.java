/**
 * Anuncio que puede ser visto por todos los usuarios.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.business.bulletin;

import java.util.Date;

public class GenBulletin extends Bulletin {

	static private int tipo = 1;
	
	/**
	 * Crea un anuncio de tipo general.
	 * 
	 * @param  id                    el identificador unico del anuncio
	 * @param  titulo                la cadena con el titulo del anuncio
	 * @param  cuerpo                la cadena con el cuerpo del anuncio
	 * @param  fechaPublicacion      la fecha de publicacion del anuncio
	 * @param  propietario           el identificador unico del propietario
	 * @param  fase                  la fase en la que se encuentra el anuncio
	 */
	public GenBulletin(int id, String titulo, String cuerpo, 
			Date fechaPublicacion, int propietario, int fase) {
		setId(id);
		setTitulo(titulo);
		setCuerpo(cuerpo);
		setFechaPublicacion(fechaPublicacion);
		setPropietario(propietario);
		setFase(fase);
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
}
