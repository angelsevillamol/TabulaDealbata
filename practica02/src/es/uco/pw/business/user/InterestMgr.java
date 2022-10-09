/**
 * Gestor de los temas de interes.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
*/

package es.uco.pw.business.user;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class InterestMgr {
	
	private static InterestMgr instance = null;
	private ArrayList<String> interests;
	
	/**
	 * Crea el gestor de intereses, obteniendolos del fichero de propiedades.
	 */
	private InterestMgr() {
		this.interests = new ArrayList<String>();

		try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            String intereses = prop.getProperty("nombres.intereses");
    		String[] parts = intereses.split(";");
    		for (int i=0; i < parts.length;i++) {
    			interests.add(parts[i]);
    		}
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
