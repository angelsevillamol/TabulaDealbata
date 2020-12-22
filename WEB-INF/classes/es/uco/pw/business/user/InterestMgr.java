/**
 * Gestor de los temas de interes.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
*/

package es.uco.pw.business.user;

import es.uco.pw.data.dao.common.DAOInterests;
import java.util.ArrayList;

public class InterestMgr {
	
	private static InterestMgr instance = null;
	private ArrayList<String> interests;
	
	/**
	 * Crea el gestor de intereses, obteniendolos del fichero de propiedades.
	 */
	private InterestMgr() {
		this.interests = DAOInterests.getAll();
	}
	
	/**
	 * Obtiene acceso al gestor de intereses.
	 *
	 * @return  el gestor de intereses
	 */
	public static InterestMgr getInstance() {
		if (instance == null) {
			instance = new InterestMgr();
		}
		return instance;
	}
	
	/**
	 * Devuelve el numero de temas de interes.
	 * 
	 * @return  el numero de temas de interes
	 */
	public int getNumInterests() {
		return interests.size();
	}
	
	/**
	 * Devuelve el nombre del i-esimo tema de interes.
	 * 
	 * @param i  el indice del tema de interes
	 * @return   la cadena con el nombre del i-esimo interes
	 */
	public String getInterest(int i) {
		String interest = "";
		
		try {
			interest = interests.get(i);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		return interest;
	}
}
