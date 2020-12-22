/**
 * Anuncio que tiene asociado uno o mas temas de interes.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.business.bulletin;

import java.util.Date;
import java.util.ArrayList;

public class ThemBulletin extends Bulletin {

	static private int tipo = 2;
	private ArrayList<Boolean> intereses = null;
	
	/**
	 * Crea un anuncio de tipo tenatico.
	 * 
	 * @param  id                    el identificador unico del anuncio
	 * @param  titulo                la cadena con el titulo del anuncio
	 * @param  cuerpo                la cadena con el cuerpo del anuncio
	 * @param  fechaPublicacion      la fecha de publicacion del anuncio
	 * @param  propietario           el identificador unico del propietario
	 * @param  fase                  la fase en la que se encuentra el anuncio
	 * @param  intereses             la lista que contiene los areas de interes
	 */
	public ThemBulletin(int id, String titulo, String cuerpo, 
			Date fechaPublicacion, int propietario, int fase, 
			ArrayList<Boolean> intereses) {
		setId(id);
		setTitulo(titulo);
		setCuerpo(cuerpo);
		setFechaPublicacion(fechaPublicacion);
		setPropietario(propietario);
		setFase(fase);
		this.intereses = new ArrayList<>(intereses);
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
	 * Devuelve si el anuncio pertenece a un tema de interes.
     *
     * @param   i  el indice correspondiente al interes a comprobar
	 * @return     <code>true</code> si el anuncio pertenece al i-esimo
	 *             tema de interes; <code>false</code> en caso contrario.
	 */
	public boolean esInteres(int i) {
		return intereses.get(i);
	}
	
	/**
	 * Asigna el interes del anuncio a un area concreta.
	 *
	 * @param  i        el indice correspondiente al interes a asignar
	 * @param  interes  <code>true</code> si el anuncio pertenece al i-esimo
	 *                  tema de interes; <code>false</code> en caso contrario.
	 */
	public void setInteres(int i, boolean interes) {
		intereses.set(i, interes);
	}
}