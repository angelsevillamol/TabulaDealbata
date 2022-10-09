/**
* Gestor de contactos de usuarios.
*
* @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
* @author Sevilla Molina, Angel (i42semoa@uco.es)
*/

package es.uco.pw.contactos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class GestorContactos {

	private static GestorContactos instance = null;
	private ArrayList<Contacto> contactos;
	private int idDisponible;
	private ArrayList<String> nombresIntereses;
	private String ruta;
	
	/**
	* Crea la agenda con los contactos.
	*/
	private GestorContactos() {
		this.contactos = new ArrayList<Contacto>();
		this.nombresIntereses = new ArrayList<String>();

		try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.ruta = prop.getProperty("ruta.datos");
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
	public static GestorContactos getInstance() {
		if (instance == null) {
			instance = new GestorContactos();
		}
		return instance;
	}
	
	/** 
	* Obtiene el numero de contactos que maneja el gestor.
	* 
	* @return  el numero de contactos almacenados
	*/
	public int getNumContactos() {
		return contactos.size();
	}
	
	/** 
	* Obtiene los temas que se consideran de interes.
	* 
	* @return  la lista con los nombres de los temas de interes
	*/
	public ArrayList<String> getIntereses() {
		return this.nombresIntereses;
	}
	
	/**
	* Carga los contactos almacenados en un fichero de texto.
	*/
	public void cargarContactos() {
		File fichero = new File(this.ruta);
		Scanner s = null;
		int id = 0;
		String nombre = null;
		String apellidos = null;
		String email = null;
		LocalDate fechaNacimiento = null;
		ArrayList<Boolean> intereses = new ArrayList<Boolean>(Arrays.asList(new Boolean[nombresIntereses.size()]));
		String[] interesesStr = null;
		Contacto c = null;
		
		// Se eliminan los contactos almacenados 
		this.vaciarAgenda();
		
		try {
			s = new Scanner(fichero);
			
			// Se lee el id del siguiente contacto a almacenar
			String linea = s.nextLine();
			idDisponible = Integer.parseInt(linea);
			
			while (s.hasNextLine()) {
				// Se lee la linea y se divide en los distintos campos6
				linea = s.nextLine();
				String[] parts = linea.split(";");
				
				id = Integer.parseInt(parts[0]);
				nombre = parts[1];
				apellidos = parts[2];
				email = parts[3];
				fechaNacimiento = LocalDate.parse(parts[4], 
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				
				// Se leen las cadenas que contienen si el usuario se interesa 
				// en el area i-esima				
				interesesStr = parts[5].split(",");
				for (int i=0; i < interesesStr.length; i++) {
					intereses.set(i, interesesStr[i].equals("true"));
				}
				
				// Se añade el contacto al gestor
				c = new Contacto(id, nombre, apellidos, fechaNacimiento, email, 
						intereses);
				contactos.add(c);
			}
		} catch (Exception ex) {
				
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
	
	/**
	* Registra en un fichero de texto los contactos del gestor.
	*/
	public void guardarContactos() {
		Iterator<Contacto> it = contactos.iterator(); 
		FileWriter fichero = null;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String interesesStr;
		
		try {
			fichero = new FileWriter(this.ruta);	
			fichero.write(this.idDisponible + "\n");
			while (it.hasNext()) { 
				Contacto c = it.next();
				interesesStr = String.valueOf(c.esInteres(0));
				for (int i=1; i < nombresIntereses.size(); i++) {
					interesesStr = interesesStr + "," + String.valueOf(c.esInteres(i));
				}
				fichero.write(c.getId() + ";" + c.getNombre() + ";" + c.getApellidos() + ";" + c.getEmail() + ";" + c.getFechaNacimiento().format(df) + ";" + interesesStr + "\n");
			}
			fichero.close();

		} catch (Exception ex) {
			System.out.println("Mensaje de la excepcion: " + ex.getMessage());
		}
	}
	
	/**
	* Agrega un contacto en el gestor con información personal especificada.
	* El usuario debe verificar previamente que el email no esté en uso.
	*
    * @param  nombre           la cadena con el nombre personal del contacto 
	* @param  apellidos        la cadena con los apellidos del contacto 
	* @param  fechaNacimiento  la fecha de nacimiento del contacto 
	* @param  email            la cadena con el correo electronico del contacto 
	* @param  intereses        la lista que contiene los areas de interes
	*/
	public void agregarContacto(String nombre, String apellidos, 
	LocalDate fechaNacimiento, String email, ArrayList<Boolean> intereses) {
		Contacto c = new Contacto(idDisponible, nombre, apellidos, 
				fechaNacimiento, email, intereses);
		contactos.add(c);
		idDisponible = idDisponible + 1;
	}
	
	/**
	* Edita la informacion de un contacto indicado por medio de su identificador.
	*
	* @param  id               el identificador del contacto a modificar
    * @param  nombre           la cadena con el nuevo nombre personal
	* @param  apellidos        la cadena con los nuevos apellidos
	* @param  fechaNacimiento  la nueva fecha de nacimiento
	* @param  email            la cadena con el nuevo correo electronico
	* @param  intereses        la lista que contiene los areas de interes
	*/
	public void editarContacto(int id, String nombre, String apellidos, 
	LocalDate fechaNacimiento, String email, ArrayList<Boolean> intereses) {
		for (Contacto c : contactos) {
			if (c.getId() == id) {
				c.setNombre(nombre);
				c.setApellidos(apellidos);
				c.setFechaNacimiento(fechaNacimiento);
				c.setEmail(email);
				for (int i=0; i < nombresIntereses.size(); i++) {
					c.setInteres(i, intereses.get(i));
				}
			}
		}
	}
	
	/**
	* Elimina un contacto del gestor.
	*
    * @param  id  el codigo de identificacion del contacto
	*/
	public void eliminarContacto(int id) {
		Iterator<Contacto> it = contactos.iterator(); 
		boolean found = false;
		
		while (it.hasNext() && !found) { 
			Contacto c = it.next(); 
			if (c.getId() == id) { 
				it.remove(); 
				found = true;
			}
		}
	}
	
	/**
	* Elimina todos los contactos del gestor.
	*/
	public void vaciarAgenda() {
		contactos.clear();
		idDisponible = 0;
	}
	
	/**
    * Imprime la informacion del contacto por consola.
    *
	* @param  c  el contacto cuya informacion se va a mostrar
    */
	public void consultarContacto(Contacto c) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("\tIdentificador: " + c.getId());
		System.out.println("\tNombre: " + c.getNombre());
		System.out.println("\tApellidos: " + c.getApellidos());
		System.out.println("\tFecha de nacimiento: " + c.getFechaNacimiento().format(df));
		System.out.println("\tEmail: " + c.getEmail());
		System.out.print("\tIntereses: ");
		boolean primero = true;
		for (int i=0; i < nombresIntereses.size(); i++) {
			if (c.esInteres(i)) {
				if (primero) {
					System.out.print(this.nombresIntereses.get(i));
					primero = false;
				} else {
					System.out.print(", " + this.nombresIntereses.get(i));
				}
			}
		}
		System.out.println("");
		System.out.println("");
	}
	
	/**
	* Imprime la informacion de todos los contactos de una lista.
	* 
	* @param  contactos  la lista de contactos a mostrar
	*/
	public void consultarContactos(ArrayList<Contacto> contactos) {
		System.out.println("Numero de ocurrencias: " + contactos.size());
		for (Contacto c : contactos) {  
			consultarContacto(c);
		}
	}
	
	/**
	* Imprime la informacion de todos los contactos de la agenda.
	*/
	public void consultarContactos() {
		consultarContactos(contactos);
	}
	
	/**
	* Comprueba si un identificador pertenece a un contacto de la agenda.
	*
	* @param  id  el identificador del contacto que se pretende buscar
	* @return     <code>true</code> si hay un contacto con el mismo 
	*             identificador; <code>false</code> en caso contrario.
	*/
	public boolean existeContacto(int id) {
		Contacto c = buscarContacto(id);
		return (c != null);
	}
	
	/**
	* Comprueba si un correo electronico pertenece a un contacto de la agenda.
	*
	* @param  email  la cadena con el correo electronico que se pretende buscar
    * @return     <code>true</code> si hay un contacto con el mismo 
	*             correo electronico; <code>false</code> en caso contrario.
	*/
	public boolean existeContactoEmail(String email) {
		Contacto c = buscarContactoEmail(email);
		return (c != null);
	}
	
	/**
	* Obtiene la informacion del contacto a partir de su identificador.
	*
	* @param  id  el identificador del contacto qye se pretende buscar
	* @return     el contacto con el mismo identificador
	*/
	public Contacto buscarContacto(int id) {
		Contacto resultado = null;
		
		for (Contacto c:contactos) {   
			if (c.getId() == id)  {
				resultado = c;
				return resultado;
			}   
		}
		return resultado;
	}
	
	/**
	* Obtiene la informacion del contacto que tenga un email determinado. 
	*
	* @param email  la cadena con el correo electronico que se pretende buscar
	* @return       el contacto con el mismo correo electronico
	*/
	public Contacto buscarContactoEmail(String email) {
		Contacto resultado = null;
		
		for (Contacto c:contactos) {  
			if (c.getEmail().equals(email))  {
				resultado = c;
				return resultado;
			}
		}
		return resultado;
	}
	
	/**
	* Obtiene la informacion de los contactos que tengan un nombre determinado 
	*
	* @param  nombre     la cadena con el nombre que se pretende buscar
	* @param  apellidos  la cadena con los apellidos que se pretende buscar
	* @return            la lista con los contactos que tengan el mismo nombre
	*/
	public ArrayList<Contacto> buscarContacto(String nombre, String apellidos) {
		ArrayList<Contacto> resultado = new ArrayList<Contacto>();
		
		for (Contacto c:contactos) {   
			if (c.getNombre().equals(nombre) & c.getApellidos().equals(apellidos))  {
				resultado.add(c);
			}   
		}
		return resultado;
	}
	
	/**
	* Obtiene la informacion de los contactos que tengan una edad determinada.
	*
	* @param  edad  la edad de los contactos que se pretenden buscar
	* @return       la lista con los contactos que tengan la misma edad 
	*/
	public ArrayList<Contacto> buscarContactoEdad(int edad) {
		ArrayList<Contacto> resultado = new ArrayList<Contacto>();
		
		for (Contacto c:contactos) {   
			if (c.getEdad() == edad)  {
				resultado.add(c);
			}   
		}
		return resultado;
	}
}
