/**
* Gestor de anuncios.
*
* @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
* @author Sevilla Molina, Angel (i42semoa@uco.es)
*/

package es.uco.pw.anuncios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;

import es.uco.pw.contactos.Contacto;

public class GestorAnuncios {

	private static GestorAnuncios instance = null;
	private ArrayList<IAnuncio> anuncios;
	private String ruta1;
	private String ruta2;
	private String ruta3;
	private String ruta4;
	private ArrayList<String> nombresIntereses; //!<
	private String[] nombreFases = {"editado", "en-espera", "publicado", "archivado"}; 
	private String[] nombreTipos = {"general", "tematico", "individualizado", "flash"}; 
	private int idDisponible;
	
	/**
	* Crea el tablon de anuncios.
	*/
	private GestorAnuncios() {
		this.anuncios = new ArrayList<IAnuncio>();
		this.nombresIntereses = new ArrayList<String>();
		
		try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.ruta1 = prop.getProperty("fichero.generales");     
            this.ruta2 = prop.getProperty("fichero.intereses");  
            this.ruta3 = prop.getProperty("fichero.individuales");  
            this.ruta4 = prop.getProperty("fichero.flash");    
            String intereses = prop.getProperty("nombres.intereses");
    		String[] parts = intereses.split(";");
    		for (int i=0; i < parts.length;i++) {
    			nombresIntereses.add(parts[i]);
    		}
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		idDisponible = 0;
	}
	
	/**
	* Obtiene acceso al gestor de contactos.
	*
    * @return  el gestor de contactos
	*/
	public static GestorAnuncios getInstance() {
		if (instance == null) {
			instance = new GestorAnuncios();
		}
		return instance;
	}
	
	/** 
	* Obtiene el numero de anuncios que maneja el gestor.
	* 
	* @return  el numero de anuncios almacenados
	*/
	public int getNumAnuncios() {
		return anuncios.size();
	}
	
	/**
	* Carga los anuncios almacenados en un fichero de texto.
	*/
		public void cargarAnuncios() {
		File fichero1 = new File(this.ruta1);
		File fichero2 = new File(this.ruta2);
		File fichero3 = new File(this.ruta3);
		File fichero4 = new File(this.ruta4);
		Scanner s = null;
		AnuncioGeneral ag;
		AnuncioTematico at;
		AnuncioIndividualizado ai;
		AnuncioFlash af;
		int idMaximo = 0;
		int id;
		int idPropietario;
		int fase;
		String titulo="";
		String cuerpo="";
		String linea;
		LocalDate fechaPublicacion;
		LocalDate fechaFin;
		String[] interesesStr;
		ArrayList<Boolean> intereses = new ArrayList<Boolean>(Arrays.asList(new Boolean[nombresIntereses.size()]));
		ArrayList<Integer> objetivos = new ArrayList<Integer>();
		
		vaciarAgenda();
		try {
			
			s = new Scanner(fichero1);			
			while (s.hasNextLine()) {
				linea = s.nextLine();
				String[] parts = linea.split(";");
				
				id = Integer.parseInt(parts[0]);
				if(id>idMaximo) {idMaximo =id;}
				idPropietario = Integer.parseInt(parts[1]);
				fase = Integer.parseInt(parts[2]);
				if(parts[3].equals("null")) {
					fechaPublicacion = null;
				}else {
					fechaPublicacion = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				}
				titulo = parts[4];
				cuerpo = parts[5];
				ag = new AnuncioGeneral(id, titulo, cuerpo, idPropietario, fechaPublicacion, fase);
				anuncios.add(ag);
			}
			s.close();
			
			s = new Scanner(fichero2);
			while (s.hasNextLine()) {
				linea = s.nextLine();
				String[] parts = linea.split(";");
				
				id = Integer.parseInt(parts[0]);
				if(id>idMaximo) {idMaximo =id;}
				idPropietario = Integer.parseInt(parts[1]);
				fase = Integer.parseInt(parts[2]);
				if(parts[3].equals("null")) {
					fechaPublicacion = null;
				}else {
					fechaPublicacion = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				}
				interesesStr = parts[4].split(",");
				for (int i=0; i < interesesStr.length; i++) {
					intereses.set(i, interesesStr[i].equals("true"));
				}
				titulo = parts[5];
				cuerpo = parts[6];
				at = new AnuncioTematico(id, titulo, cuerpo, idPropietario, fechaPublicacion, fase, intereses);
				anuncios.add(at);	
			}
			s.close();
			
			s = new Scanner(fichero3);
			while (s.hasNextLine()) {
				linea = s.nextLine();
				objetivos.clear();
				String[] parts = linea.split(";");
				
				id = Integer.parseInt(parts[0]);
				if(id>idMaximo) {idMaximo =id;}
				idPropietario = Integer.parseInt(parts[1]);
				fase = Integer.parseInt(parts[2]);
				if(parts[3].equals("null")) {
					fechaPublicacion = null;
				}else {
					fechaPublicacion = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				}
				interesesStr = parts[4].split(",");
				for (int i=0; i < interesesStr.length; i++) {
					objetivos.add(Integer.parseInt(interesesStr[i]));
				}
				titulo = parts[5];
				cuerpo = parts[6];
				ai = new AnuncioIndividualizado(id, titulo, cuerpo, idPropietario, fechaPublicacion, fase, objetivos);
				anuncios.add(ai);	
			}
			s.close();
			
			s = new Scanner(fichero4);
			while (s.hasNextLine()) {
				linea = s.nextLine();
				String[] parts = linea.split(";");
				
				id = Integer.parseInt(parts[0]);
				if(id>idMaximo) {idMaximo =id;}
				idPropietario = Integer.parseInt(parts[1]);
				fase = Integer.parseInt(parts[2]);
				if(parts[3].equals("null")) {
					fechaPublicacion = null;
					fechaFin = null;
				}else {
					fechaPublicacion = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					fechaFin = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				}
				
				if(fechaFin.isBefore(LocalDate.now())) {
					fase=3;
				}
				titulo = parts[5];
				cuerpo = parts[6];
				af = new AnuncioFlash(id, titulo, cuerpo, idPropietario, fechaPublicacion, fase, fechaFin);
				anuncios.add(af);
			}
			s.close();
			this.idDisponible = idMaximo+1;
			
		} catch (Exception ex) {
				
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
	
	/**
	* Registra en un fichero de texto los anuncios del gestor.
	*/
	public void guardarAnuncios() {
		Iterator<IAnuncio> it = anuncios.iterator(); 
		FileWriter fichero1 = null;
		FileWriter fichero2 = null;
		FileWriter fichero3 = null;
		FileWriter fichero4 = null;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		try {
			fichero1 = new FileWriter(this.ruta1);	
			//fichero1.write("Generales\n");
			fichero2 = new FileWriter(this.ruta2);
			//fichero2.write("Tematicos\\n");
			fichero3 = new FileWriter(this.ruta3);
			//fichero3.write("Individuales\\n");
			fichero4 = new FileWriter(this.ruta4);
			//fichero4.write("Flash\\n");

			String AuxStr = "";
			
			while (it.hasNext()) { 
				IAnuncio a = it.next();
				if(a.getTipo() == 1) {
					if(a.getFechaPublicacion()==null) {
						fichero1.write(a.getId() + ";" + a.getIdUsuarioPropietario() + ";" + a.getFase() + ";" + "null" + ";" + a.getTitulo() + ";" + a.getCuerpo() + "\n");
					}else {
						fichero1.write(a.getId() + ";" + a.getIdUsuarioPropietario() + ";" + a.getFase() + ";" + a.getFechaPublicacion().format(df).toString() + ";" + a.getTitulo() + ";" + a.getCuerpo() + "\n");
					}
				}
				else if(a.getTipo() == 2) {
					AnuncioTematico at = (AnuncioTematico) a;
					AuxStr = String.valueOf(at.esInteres(0));
					for (int i=1; i < nombresIntereses.size(); i++) {
						AuxStr = AuxStr + "," + String.valueOf(at.esInteres(i));
					}
					if(a.getFechaPublicacion()==null) {
						fichero2.write(a.getId() + ";" + a.getIdUsuarioPropietario() + ";" + a.getFase() + ";" + "null" + ";" + AuxStr + ";" + a.getTitulo() + ";" + a.getCuerpo() + "\n");
					}else {
						fichero2.write(a.getId() + ";" + a.getIdUsuarioPropietario() + ";" + a.getFase() + ";" + a.getFechaPublicacion().format(df).toString() + ";" + AuxStr + ";" + a.getTitulo() + ";" + a.getCuerpo() + "\n");
					}
				}
				else if(a.getTipo() == 3) {
					AnuncioIndividualizado ai = (AnuncioIndividualizado) a;
					Iterator<Integer> itai = ai.getObjetivos().iterator(); 
					AuxStr = itai.next().toString();
					while (it.hasNext()) { 
						AuxStr = AuxStr + "," + itai.next();		
					}
					if(a.getFechaPublicacion()==null) {
						fichero3.write(a.getId() + ";" + a.getIdUsuarioPropietario() + ";" + a.getFase() + ";" + "null" + ";" + AuxStr + ";" + a.getTitulo() + ";" + a.getCuerpo() + "\n");
					}else {
						fichero3.write(a.getId() + ";" + a.getIdUsuarioPropietario() + ";" + a.getFase() + ";" + a.getFechaPublicacion().format(df).toString() + ";" + AuxStr + ";" + a.getTitulo() + ";" + a.getCuerpo() + "\n");
					}
				}
				else if(a.getTipo( ) == 4) {
					AnuncioFlash af = (AnuncioFlash) a;
					if(a.getFechaPublicacion()==null) {
						fichero4.write(a.getId() + ";" + a.getIdUsuarioPropietario() + ";" + a.getFase() + ";" + "null"  + ";" + "null" + ";" + a.getTitulo() + ";" + a.getCuerpo() + "\n");
					}else {
						fichero4.write(a.getId() + ";" + a.getIdUsuarioPropietario() + ";" + a.getFase() + ";" + a.getFechaPublicacion().format(df).toString() + ";" + af.getFechaFin().format(df).toString() + ";" + a.getTitulo() + ";" + a.getCuerpo() + "\n");
					}
				}
					
							}
			fichero1.close();
			fichero2.close();
			fichero3.close();
			fichero4.close();	

		} catch (Exception ex) {
			System.out.println("Mensaje de la excepcion: " + ex.getMessage());
		}
	}
	
	/**
	 * Agrega un anuncio al gestor.
	 * 
	 * @param  anuncio  la informacion del anuncio a añadir
	 */
	public void agregaAnuncio(IAnuncio anuncio) {	  
		anuncios.add(anuncio);
		idDisponible = idDisponible + 1;
	}
	
	/**
	* Agrega un anuncio general en el gestor con informacion especificada.
	* El anuncio agregado se encuentra en fase de edicion.
	*
    * @param  titulo                la cadena con el titulo del anuncio
	* @param  cuerpo                la cadena con el cuerpo del anuncio
	* @param  idUsuarioPropietario  el identificador unico del propietario
	*/
	public void agregarAnuncioGeneral(String titulo, String cuerpo, 
	int idUsuarioPropietario) {
		AnuncioGeneral a = new AnuncioGeneral(idDisponible, titulo, cuerpo, idUsuarioPropietario);
		agregaAnuncio(a);
	}
	
	/**
	* Agrega un anuncio tematico en el gestor con informacion especificada.
	* El anuncio agregado se encuentra en fase de edicion.
	*
    * @param  titulo                la cadena con el titulo del anuncio
	* @param  cuerpo                la cadena con el cuerpo del anuncio
	* @param  idUsuarioPropietario  el identificador unico del propietario
	* @param  intereses             la lista que contiene los areas de interes
	*/
	public void agregarAnuncioTematico(String titulo, String cuerpo, 
	int idUsuarioPropietario, ArrayList<Boolean> intereses) {
		AnuncioTematico a = new AnuncioTematico(idDisponible, titulo, cuerpo, idUsuarioPropietario, intereses);
		agregaAnuncio(a);
	}
	
	/**
	* Agrega un anuncio individualizado en el gestor con informacion especificada.
	* El anuncio agregado se encuentra en fase de edicion.
	*
    * @param  titulo                la cadena con el titulo del anuncio
	* @param  cuerpo                la cadena con el cuerpo del anuncio
	* @param  idUsuarioPropietario  el identificador unico del propietario
	* @param  objetivos             la lista con los id de los receptores
	*/
	public void agregarAnuncioIndividualizado(String titulo, String cuerpo, 
	int idUsuarioPropietario, ArrayList<Integer> objetivos) {
		AnuncioIndividualizado a = new AnuncioIndividualizado(idDisponible, titulo, cuerpo, idUsuarioPropietario, objetivos);
		agregaAnuncio(a);
	}
	
	/**
	* Agrega un anuncio flash en el gestor con informacion especificada.
	* El anuncio agregado se encuentra en fase de edicion.
	*
    * @param  titulo                la cadena con el titulo del anuncio
	* @param  cuerpo                la cadena con el cuerpo del anuncio
	* @param  idUsuarioPropietario  el identificador unico del propietario
	* @param  fechaFin              la fecha de finalizacion del anuncio
	*/
	public void agregarAnuncioFlash(String titulo, String cuerpo, 
	int idUsuarioPropietario, LocalDate fechaFin) {
		AnuncioFlash a = new AnuncioFlash(idDisponible, titulo, cuerpo, idUsuarioPropietario, fechaFin);
		agregaAnuncio(a);
	}
	
	/**
	* Edita la informacion de un anuncio indicado por medio de su identificador.
	*
	* @param  id      el identificador del anuncio a modificar
    * @param  titulo  la cadena con el titulo del anuncio
	* @param  cuerpo  la cadena con el cuerpo del anuncio
	*/
	public void editarAnuncio(int id, String titulo, String cuerpo) {
		for (IAnuncio a : anuncios) {
			if (a.getId() == id) {
				a.setTitulo(titulo);
				a.setCuerpo(cuerpo);
			}
		}
	}
	
	/**
	* Publica un anuncio. El anuncio se encontrara en fase en-espera hasta 
	* que llegue la fecha asignada, donde pasara a estar publicado.
	* @param  id                el identificador del anuncio a publicar
	* @param  fechaPublicacion  la fecha de publicacion del anuncio
	*/
	public void publicarAnuncio(int id, LocalDate fechaPublicacion) {
		for (IAnuncio a:anuncios) {   
			if (a.getId() == id)  {
				if(a.getTipo()==4) {
					return;
				}
				if (a.getFase() == 0) {
					a.publicarAnuncio(fechaPublicacion);
					return;
				}
			}   
		}
	}
	
	/**
	* Publica un anuncio flash. El anuncio se encontrara en fase en-espera hasta 
	* que llegue la fecha asignada, donde pasara a estar publicado.
	* @param  id                el identificador del anuncio a publicar
	* @param  fechaPublicacion  la fecha de publicacion del anuncio
	* @param  fechaFin          la fecha de finalizacion del anuncio
	*/
	public void publicarAnuncioFlash(int id, LocalDate fechaPublicacion, 
	LocalDate fechaFin) {
		for (IAnuncio a:anuncios) {   
			if (a.getId() == id)  {
				if(a.getTipo()!=4) {
					return;
				}
				if (a.getFase() == 0) {
					AnuncioFlash af = (AnuncioFlash) a;
					af.publicarAnuncioFlash(fechaPublicacion, fechaFin);
					return;
				}
			}   
		}
	}
	
	/**
	* Archiva un anuncio.
	* 
	* @param  id  el identificador del anuncio a archivar
	*/
	public void archivarAnuncio(int id) {
		for (IAnuncio a:anuncios) {
			if (a.getId() == id) {
				a.archivarAnuncio();
				return;
			}
		}
	}
	
	/**
	* Elimina todos los anuncios del gestor.
	*/
	public void vaciarAgenda() {
		anuncios.clear();
		idDisponible = 0;
	}
	
	/**
	* Imprime la informacion del anuncio por consola.
	*
    * @param  a  el anuncio cuya informacion se va a mostrar
	*/
	public void consultarAnuncio(IAnuncio a) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("\tIdentificador: " + a.getId());
		System.out.println("\tTitulo: " + a.getTitulo());
		System.out.println("\tTipo: " + nombreTipos[a.getTipo() - 1]);
		System.out.println("\tUsuario propietario: " + a.getIdUsuarioPropietario());
		if (a.getFechaPublicacion() != null) {
			System.out.println("\tFecha de publicacion: " + a.getFechaPublicacion().format(df));
		}
		
		// Si es un anuncio tematico
		if (a.getTipo() == 2) {
			AnuncioTematico at = (AnuncioTematico) a;
			System.out.print("\tIntereses: ");
			boolean primero = true;
			for (int i=0; i < nombresIntereses.size(); i++) {
				if (at.esInteres(i)) {
					if (primero) {
						System.out.print(this.nombresIntereses.get(i));
						primero = false;
					} else {
						System.out.print(", " + this.nombresIntereses.get(i));
					}
				}
			}
			System.out.println("");
		}
		if (a.getTipo() == 3) {
			AnuncioIndividualizado ai = (AnuncioIndividualizado) a;
			System.out.println("\tObjetivos: ");
			for(int i=0; i < ai.getObjetivos().size(); i++) {
				System.out.println("\t\t- id usuario:" + ai.getObjetivos().get(i));
				
			}
		}
		if (a.getTipo() == 4) {
			AnuncioFlash af = (AnuncioFlash) a;
			System.out.println("\tFecha de finalizacion: " + af.getFechaFin().format(df));
			
		}
		
		System.out.println("\tCuerpo: " + a.getCuerpo());
		System.out.println("\tFase: " + nombreFases[a.getFase()]);
		System.out.println("");
	}
	
	/**
	* Imprime la informacion de todos los anuncios de una lista.
	* 
	* @param  anuncios  la lista de anuncios a mostrar
	*/
	public void consultarAnuncios(ArrayList<IAnuncio> anuncios) {
		System.out.println("Numero de ocurrencias: " + anuncios.size());
		for (IAnuncio a:anuncios) {  
			consultarAnuncio(a);
		}
	}
	
	/**
	* Obtiene la informacion del anuncio a partir de su identificador.
	*
	* @param  id  el identificador del anuncio qye se pretende buscar
	* @return     el anuncio con el mismo identificador
	*/
	public IAnuncio buscarAnuncio(int id) {
		IAnuncio resultado = null;
		
		for (IAnuncio a:anuncios) {   
			if (a.getId() == id)  {
				resultado = a;
				
				return resultado;
			}   
		}
		return resultado;
	}
	
	/**
	* Obtiene la informacion de los anuncios que se encuentren publicados.
	*
	* @return  la lista con los anuncios que se encuentren en fase de publicado
	*/
	public ArrayList<IAnuncio> buscarAnunciosPublicados(int id) {
		ArrayList<IAnuncio> resultado = new ArrayList<IAnuncio>();
		
		for (IAnuncio a:anuncios) {   
			if (a.estaPublicado())  {
				if(a.getTipo()==3) {
					AnuncioIndividualizado ai = (AnuncioIndividualizado) a;
					if(ai.esObjetivo(id)) {
						resultado.add(a);
					}
				}else {
					resultado.add(a);
				}
				
			}   
		}
		return resultado;
	}
	
	/**
	* Obtiene la informacion de los anuncios creados por un determinado usuario.
	*
	* @param   idUsuarioPropietario  el identificador unico del propietario
	* @return                        los anuncios creados por el usuario dado
	*/
	public ArrayList<IAnuncio> buscarAnunciosPropietario(int idUsuarioPropietario) {
		ArrayList<IAnuncio> resultado = new ArrayList<IAnuncio>();
		
		for (IAnuncio a:anuncios) {   
			if (a.getIdUsuarioPropietario() == idUsuarioPropietario)  {
				resultado.add(a);
			}   
		}
		return resultado;
	}
	
	/**
	* Obtiene la informacion de los anuncios publicados en un determinado dia
	*
	* @param   fechaPublicacion  la fecha de publicacion a buscar
	* @return                    los anuncios publicados esa misma fecha
	*/
	public ArrayList<IAnuncio> buscarAnunciosFechaPublicacion(LocalDate fechaPublicacion) {
		ArrayList<IAnuncio> resultado = new ArrayList<IAnuncio>();
		
		for (IAnuncio a:anuncios) {
			if (a.getFechaPublicacion() != null) {
				if (a.getFechaPublicacion().isEqual(fechaPublicacion))  {
					resultado.add(a);
				} 
			}
		}
		return resultado;
	}
	
	/**
	* Obtiene la informacion de los anuncios que pertenezcan a alguna tematica
	* por la que un determinado usuario tenga interes.
	*
	* @param   c  el contacto con los temas de interes a buscar
	* @return     los anuncios tematicos de interes por el usuario
	*/
	public ArrayList<IAnuncio> buscarAnunciosIntereses(Contacto c) {
		ArrayList<IAnuncio> resultado = new ArrayList<IAnuncio>();
		boolean found;
		AnuncioTematico at = null;
		
		for (IAnuncio a:anuncios) { 
			if (a.getTipo() == 2) {
				at = (AnuncioTematico) a;
				found = false;
				int i = 0;
				
				while (i < nombresIntereses.size() && !found) {
					if (c.esInteres(i) == true && at.esInteres(i) == true) {
						found = true;
						resultado.add(at);
					}
					i = i+1;
				}
			}
		}
		return resultado;
	}
	
	/**
	* Obtiene la informacion de los anuncios individualizados que han sido
	* enviados a un usuario dado.
	*
	* @param   idUsuarioObjetivo  el identificador del usuario receptor
	* @return                     los anuncios enviados a dicho usuario
	*/
	public ArrayList<IAnuncio> buscarAnunciosIndividualizados(int idUsuarioObjetivo) {
		ArrayList<IAnuncio> resultado = new ArrayList<IAnuncio>();
		AnuncioIndividualizado ai = null;
		
		for (IAnuncio a : anuncios) { 
			if (a.getTipo() == 3) {
				ai = (AnuncioIndividualizado) a;
				
				for(int x:ai.getObjetivos()) {
					if(x==idUsuarioObjetivo) {
						resultado.add(ai);
					}
				}
			}
		}
		return resultado;
	}
	
	
	/**
	 * Selecciona los anuncios que se encuentren en una determinada fase.
	 * 
	 * @param  anuncios  la lista de anuncios que se pretende filtrar
	 * @param  fase      la fase que se pretende seleccionar
	 */
	public void filtrarAnunciosFase(ArrayList<IAnuncio> anuncios, int fase) {
		Iterator<IAnuncio> it = anuncios.iterator(); 
		
		while (it.hasNext()) { 
			IAnuncio a = it.next(); 
			if (a.getFase() != fase) { 
				it.remove(); 
			}
		}
	}
	
	/**
	* Ordena los anuncios de una lista utilizando como criterio la clave
	* de indentificacion de los usuarios propietarios, de forma ascendente.
	* 
	* @param  anuncios  es la lista con los anuncios a ordenar
	*/
	public void ordenarAnunciosPropietario(ArrayList<IAnuncio> anuncios) {
		ArrayList<IAnuncio> list = new ArrayList<IAnuncio>(anuncios);
		Iterator<IAnuncio> itList = list.iterator(); 
		boolean found;
		anuncios.clear();
		
		while (itList.hasNext()) { 
			IAnuncio alist = itList.next(); 
			found = false;
			
			for (int i = 0; i < anuncios.size() && !found; i++) {
		        if (anuncios.get(i).getIdUsuarioPropietario() > alist.getIdUsuarioPropietario()) {
		        	anuncios.add(i, alist);
		        	found = true;
		        }
		    }
			
			if (!found) {
				anuncios.add(alist);
			}
		}
	}
	
	/**
	* Ordena los anuncios de una lista utilizando como criterio la clave
	* de indentificacion de los usuarios propietarios, de forma ascendente.
	* 
	* @param  anuncios  es la lista con los anuncios a ordenar
	*/
	public void ordenarAnunciosFechaPublicacion(ArrayList<IAnuncio> anuncios) {
		ArrayList<IAnuncio> list = new ArrayList<IAnuncio>(anuncios);
		Iterator<IAnuncio> itList = list.iterator(); 
		boolean found;
		anuncios.clear();
		
		while (itList.hasNext()) { 
			IAnuncio alist = itList.next(); 
			found = false;
			
			for (int i = 0; i < anuncios.size() && !found; i++) {
		        if (anuncios.get(i).getFechaPublicacion().isAfter(alist.getFechaPublicacion())) {
		        	anuncios.add(i, alist);
		        	found = true;
		        }
		    }
			
			if (!found) {
				anuncios.add(alist);
			}
		}
	}
}
