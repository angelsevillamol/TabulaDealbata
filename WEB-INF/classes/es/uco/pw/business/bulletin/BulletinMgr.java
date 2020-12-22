/**
 * Gestor de anuncios.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.business.bulletin;

import es.uco.pw.business.user.User;
import es.uco.pw.business.user.InterestMgr;
import es.uco.pw.data.dao.bulletin.DAOBulletin;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class BulletinMgr {
	
	private static BulletinMgr instance = null;
	private InterestMgr interestMgr = null;
	private String[] nombreFases = {"editado", "en-espera", "publicado", 
			"archivado"}; 
	private String[] nombreTipos = {"general", "tematico", "individualizado", 
			"flash"}; 
	
	/**
	 * Crea el tablon de anuncios.
	 */
	private BulletinMgr() {
		this.interestMgr = InterestMgr.getInstance();
	}
	
	/**
	 * Obtiene acceso al gestor de usuarios.
	 *
     * @return  el gestor de usuarios
	 */
	public static BulletinMgr getInstance() {
		if (instance == null) {
			instance = new BulletinMgr();
		}
		return instance;
	}
	
	/**
	 * Agrega un anuncio en el gestor con información personal especificada.
	 * 
	 * @param  b  el anuncio cuya informacion se va a registrar
	 */
	public void agregarAnuncio(Bulletin b) {	  
		DAOBulletin.save(b);
	}
	
	/**
	 * Sobreescribe la informacion de un anuncio.
	 *
	 * @param  b  el anuncio cuya informacion se va a modificar
	 */
	public void editarAnuncio(Bulletin b) {
		DAOBulletin.update(b);
	}
	
	/**
	 * Publica un anuncio. El anuncio se encontrara en fase en-espera hasta 
	 * que llegue la fecha asignada, donde pasara a estar publicado.
	 * @param 
	 * @param  fechaPublicacion  la fecha de publicacion del anuncio
	 */
	public void publicarAnuncio(Bulletin b, Date fechaPublicacion) {
		Date today = new Date();
		
		// Si el anuncio esta en fase de edicion
		if (b.getFase() == 0) {
			// Se asigna la fecha de publicacion
			b.setFechaPublicacion(fechaPublicacion);
				
			// Si se ha alcanzado la fecha de publicacion
			if ((b.getFechaPublicacion().compareTo(today) < 0) || 
					(b.getFechaPublicacion().compareTo(today) == 0)) {
				// Se establece estado publicado
				b.setFase(2);
			}
			// Si no se ha alcanzado
			else {
				// Se establece estado en-espera
				b.setFase(1);
			}
		}
		
		// Se actualiza la informacion
		DAOBulletin.update(b); 
	}
	
	/**
	 * Archiva un anuncio.
	 * 
	 * @param  id  el identificador del anuncio a archivar
	 */
	public void archivarAnuncio(int id) {
		DAOBulletin.close(id);
	}
	
	/**
	 * Imprime la informacion del anuncio.
	 *
     * @param  b  el anuncio cuya informacion se va a mostrar
	 */
	public void consultarAnuncio(Bulletin b) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
		System.out.println("\tIdentificador: " + b.getId());
		System.out.println("\tTitulo: " + b.getTitulo());
		System.out.println("\tCuerpo: " + b.getCuerpo());
		System.out.println("\tTipo: " + nombreTipos[b.getTipo() - 1]);
		System.out.println("\tUsuario propietario: " + b.getPropietario());
		
		// Si es un anuncio tematico
		if (b.getTipo() == 2) {
			System.out.print("\tIntereses: ");
			ThemBulletin tb = (ThemBulletin) b;
			boolean primero = true;
			
			for (int i = 0; i < interestMgr.getNumInterests(); i++) {
				if (tb.esInteres(i)) {
					if (primero) {
						System.out.print(interestMgr.getInterest(i));
						primero = false;
					} else {
						System.out.print(", " + interestMgr.getInterest(i));
					}
				}
			}
			System.out.println("");
		}
		
		// Si es un anuncio individualizado
		if (b.getTipo() == 3) {
			IndBulletin ib = (IndBulletin) b;
			System.out.println("\tObjetivos: ");
			for (int i=0; i < ib.getObjetivos().size(); i++) {
				System.out.println("\t\t- Identificador de usuario: " + ib.getObjetivos().get(i));	
			}
		}
		
		if (b.getFechaPublicacion() != null) {
			System.out.println("\tFecha de publicacion: " + df.format(b.getFechaPublicacion()));
		}
		
		// Si es un anuncio flash
		if (b.getTipo() == 4) {
			FlashBulletin fb = (FlashBulletin) b;
			if (fb.getFechaFinalizacion() != null) {
				System.out.println("\tFecha de finalizacion: " + df.format(fb.getFechaFinalizacion()));
			}
		}
		
		System.out.println("\tFase: " + nombreFases[b.getFase()]);
		System.out.println("");
	}
	
	/**
	 * Imprime la informacion de todos los usuarios de una lista.
	 * 
	 * @param  bulletins  la lista de anuncios a mostrar
	 */
	public void consultarAnuncios(ArrayList<Bulletin> bulletins) {
		System.out.println("Numero de ocurrencias: " + bulletins.size());
		for (Bulletin b:bulletins) {  
			consultarAnuncio(b);
		}
	}
	
	/**
	 * Obtiene la informacion del anuncio a partir de su identificador.
	 *
	 * @param   id  el identificador del anuncio qye se pretende buscar
	 * @return      el anuncio con el mismo identificador
	 */
	public Bulletin buscarAnuncioId(int id) {
		Bulletin result = DAOBulletin.queryById(id);
		return result;
	}
	
	/**
	 * Obtiene la informacion de los anuncios que maneja el gestor.
	 *
	 * @return  la lista con los anuncios del gestor
	 */
	public ArrayList<Bulletin> getAnuncios() {
		ArrayList<Bulletin> bulletins = DAOBulletin.getAll();
		return bulletins;
	}
	
	/**
	 * Selecciona los anuncios que sean de un determinado tipo.
	 * 
	 * @param  bulletins  la lista de anuncios que se pretende filtrar
	 * @param  tipo       el tipo del anuncio que se pretende buscar
	 */
	public void filtrarAnunciosTipo(ArrayList<Bulletin> bulletins, int tipo) {
		Iterator<Bulletin> it = bulletins.iterator(); 
		
		while (it.hasNext()) { 
			Bulletin b = it.next(); 
			if (b.getTipo() != tipo) { 
				it.remove(); 
			}
		}
	}
	
	/**
	 * Selecciona los anuncios publicados en una determinada fecha.
	 *
	 * @param  bulletins         la lista de anuncios que se pretende filtrar
	 * @param  fechaPublicacion  la fecha de publicacion a buscar
	 */
	public void filtrarAnunciosFechaPublicacion(ArrayList<Bulletin> bulletins, 
			Date fechaPublicacion) {
		Iterator<Bulletin> it = bulletins.iterator(); 
		
		while (it.hasNext()) { 
			Bulletin b = it.next(); 
			if (b.getFechaPublicacion() == null) {
				it.remove(); 
			}
			else if (b.getFechaPublicacion().compareTo(fechaPublicacion) != 0) {
				it.remove(); 
			}
		}
	}
	
	/**
	 * Selecciona los anuncios que sean de un determinado usuario propietario.
	 * 
	 * @param  bulletins    la lista de anuncios que se pretende filtrar
	 * @param  propietario  el identificador unico del propietario
	 */
	public void filtrarAnunciosPropietario(ArrayList<Bulletin> bulletins, int propietario) {
		Iterator<Bulletin> it = bulletins.iterator(); 
		
		while (it.hasNext()) { 
			Bulletin b = it.next(); 
			if (b.getPropietario() != propietario) { 
				it.remove(); 
			}
		}
	}
	
	/**
	 * Selecciona los anuncios que se encuentren en una determinada fase.
	 * 
	 * @param  bulletins  la lista de anuncios que se pretende filtrar
	 * @param  fase       la fase que se pretende seleccionar
	 */
	public void filtrarAnunciosFase(ArrayList<Bulletin> bulletins, int fase) {
		Iterator<Bulletin> it = bulletins.iterator(); 
		
		while (it.hasNext()) { 
			Bulletin b = it.next(); 
			if (b.getFase() != fase) { 
				it.remove(); 
			}
		}
	}
	
	/**
	 * Selecciona los anuncios que pertenezcan a alguna tematica por la que un
	 * determinado usuario tenga interes. Los anuncios que no sean tematicos se 
	 * mantienen en la lista. 
	 *
	 * @param  bulletins  la lista de anuncios que se pretende filtrar
	 * @param  u          el usuario con los temas de interes a buscar
	 */
	public void filtrarAnunciosIntereses(ArrayList<Bulletin> bulletins, User u) {
		Iterator<Bulletin> it = bulletins.iterator();
		boolean interested;
		ThemBulletin tb = null;
		
		while (it.hasNext()) {
			Bulletin b = it.next();
			// Si no es un anuncio tematico, se ignora
			if (b.getTipo() == 2) {
				tb = (ThemBulletin) b;
				interested = false;
				int i = 0;
				
				while (i < interestMgr.getNumInterests() && !interested) {
					// Si la tematica del anuncio es de interes para el usuario
					if (u.esInteres(i) == true && tb.esInteres(i) == true) {
						interested = true;
					}
					i = i+1;
				}
				
				// Si el usuario no esta interesado en la tematica del anuncio
				if (!interested) {
					it.remove();
				}
			}
		}
	}
	
	/**
	 * Selecciona los anuncios individualizados que han sido enviados a un 
	 * usuario dado. Los anuncios que no sean indivualizados se mantienen en
	 * la lista. 
	 *
	 * @param   bulletins  la lista de anuncios que se pretende filtrar
	 * @param   id         el identificador del usuario receptor
	 * @return             los anuncios enviados a dicho usuario
	 */
	public void filtrarAnunciosDestinatario(ArrayList<Bulletin> bulletins, 
			int id) {
		Iterator<Bulletin> it = bulletins.iterator(); 
		IndBulletin ib = null;
		
		while (it.hasNext()) { 
			Bulletin b = it.next(); 
			// Si no es un anuncio individualizado, se ignora
			if (b.getTipo() == 3) { 
				ib = (IndBulletin) b;
				// Si no es usuario objetivo, se elimina
				if (!ib.esObjetivo(id)) {
					it.remove();
				}
			}
		}
	}
	
	/**
	 * Ordena los anuncios de una lista utilizando como criterio la clave
	 * de indentificacion de los usuarios propietarios, de forma ascendente.
	 * 
	 * @param  bulletins  la lista de los anuncios a ordenar
	 */
	public void ordenarAnunciosPropietario(ArrayList<Bulletin> bulletins) {
		ArrayList<Bulletin> list = new ArrayList<Bulletin>(bulletins);
		Iterator<Bulletin> itList = list.iterator(); 
		boolean found;
		bulletins.clear();
		
		while (itList.hasNext()) { 
			Bulletin blist = itList.next(); 
			found = false;
			
			for (int i = 0; i < bulletins.size() && !found; i++) {
		        if (bulletins.get(i).getPropietario() > blist.getPropietario()) {
		        	bulletins.add(i, blist);
		        	found = true;
		        }
		    }
			
			if (!found) {
				bulletins.add(blist);
			}
		}
	}
	
	/**
	 * Ordena los anuncios de una lista utilizando como criterio la clave
	 * de indentificacion de los usuarios propietarios, de forma ascendente.
	 * 
	 * @param  bulletins  la lista de los anuncios a ordenar
	 */
	public void ordenarAnunciosFechaPublicacion(ArrayList<Bulletin> bulletins) {
		ArrayList<Bulletin> list = new ArrayList<Bulletin>(bulletins);
		Iterator<Bulletin> itList = list.iterator(); 
		boolean found;
		bulletins.clear();
		
		while (itList.hasNext()) { 
			Bulletin blist = itList.next(); 
			found = false;
			
			for (int i = 0; i < bulletins.size() && !found; i++) {
				if (bulletins.get(i).getFechaPublicacion().compareTo(blist.getFechaPublicacion()) > 0) {
		        	bulletins.add(i, blist);
		        	found = true;
		        }
		    }
			
			if (!found) {
				bulletins.add(blist);
			}
		}
	}
}
