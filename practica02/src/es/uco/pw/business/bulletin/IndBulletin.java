/**
 * Anuncio que se remite específicamente a uno o varios usuarios 
 * destinatarios.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.business.bulletin;

import java.util.Date;
import java.util.ArrayList;

public class IndBulletin extends Bulletin {

	static private int tipo = 3;
	private ArrayList<Integer> objetivos;
	
	/**
	 * Crea un anuncio de tipo individualizado.
	 * 
	 * @param  id                el identificador unico del anuncio
	 * @param  titulo            la cadena con el titulo del anuncio
	 * @param  cuerpo            la cadena con el cuerpo del anuncio
	 * @param  fechaPublicacion  la fecha de publicacion del anuncio
	 * @param  propietario       el identificador unico del propietario
	 * @param  fase              la fase en la que se encuentra el anuncio
	 * @param  objetivos	     la lista de ids de receptores del anuncio
	 */
	public IndBulletin(int id, String titulo, String cuerpo, 
			Date fechaPublicacion, int propietario, int fase, 
			ArrayList<Integer> objetivos) {
		setId(id);
		setTitulo(titulo);
		setCuerpo(cuerpo);
		setFechaPublicacion(fechaPublicacion);
		setPropietario(propietario);
		setFase(fase);
		this.objetivos = objetivos;
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
	 * Devuelve la lista de identificadores de usuarios receptores del anuncio
	 * 
	 * @return  la lista de ids de receptores del anuncio
	 */
	public ArrayList<Integer> getObjetivos() {
		return this.objetivos;
	}
	
	/**
	 * Devuelve si el usuario de un determinado identificador es objetivo 
	 * receptor del anuncio.
     *
     * @param   id  el identificador correspondiente del usuario a comprobar
	 * @return      <code>true</code> si el usuario es receptor del anuncio; 
	 *              <code>false</code> en caso contrario.
	 */
	public boolean esObjetivo(int id) {
		for (int i:this.objetivos) {
			if (i == id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Añade un usuario objetivo a la lista
	 * 
	 * @param   id  el identificador correspondiente del usuario a comprobar
	 */
	public void addObjetivo(int id) {
		this.objetivos.add(id);
	}
}
