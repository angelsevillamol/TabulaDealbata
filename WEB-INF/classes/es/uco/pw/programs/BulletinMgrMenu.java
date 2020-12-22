/**
 * Aplicacion que maneja la gestion de anuncios de una agenda.
 *
 * @author Mu�oz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */
package es.uco.pw.programs;

import es.uco.pw.business.user.InterestMgr;
import es.uco.pw.business.user.User;
import es.uco.pw.business.user.UserMgr;
import es.uco.pw.business.bulletin.Bulletin;
import es.uco.pw.business.bulletin.GenBulletin;
import es.uco.pw.business.bulletin.ThemBulletin;
import es.uco.pw.business.bulletin.IndBulletin;
import es.uco.pw.business.bulletin.FlashBulletin;
import es.uco.pw.business.bulletin.BulletinMgr;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class BulletinMgrMenu {
	
	private static InterestMgr interestMgr = InterestMgr.getInstance();
	private static UserMgr userMgr = UserMgr.getInstance();
	private static BulletinMgr bulletinMgr = BulletinMgr.getInstance();
	private static Scanner sc;
	private static User propietario;
	
	/**
	 * Solicita al usuario que pulse enter para continuar.
	 */
	private static void pressEnter() {
		System.out.print("Pulse ENTER para continuar.");
		sc.nextLine();
	}
	
	/**
	 * Realiza el inicio de sesion en el sistema.
	 * 
	 * @return  el usuario correspondiente si la informacion de 
	 *                    sesion es correcta
	 */
	private static User login() {
		User propietario = null;
		System.out.print("\tCorreo electronico: ");
		String email = sc.nextLine();
		System.out.print("\tContraseña: ");
		String password = sc.nextLine();
		propietario = userMgr.login(email, password);		
		return propietario;
	}
	
	/**
	 * Imprime el menu principal de la gestion de anuncios y 
	 * solicita al usuario una opcion.
	 * 
	 * @return  la opcion seleccionada por el usuario.
	 */
	private static int printMenu() {
		int option = -1;
		
		System.out.println("GESTIÓN DE ANUNCIOS:");
		System.out.println("\t1. Registrar anuncio.");
		System.out.println("\t2. Publicar anuncio.");
		System.out.println("\t3. Editar anuncio.");
		System.out.println("\t4. Archivar anuncio.");
		System.out.println("\t5. Mostrar tablon de anuncios.");
		System.out.println("\t6. Mostrar mis anuncios.");
		System.out.println("\t0. Salir.");
		
		try {
			// Se pide la opcion al propietario
			System.out.print("Seleccione opcion: ");
			option =  Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException nfe) {
			System.out.print("Opcion incorrecta. ");
			System.out.print("Pulse ENTER para continuar.");
			sc.nextLine();
			option = -1;
		}
		
		return option;
	}
	
	/**
	 * Imprime el menu de registros y solicita al usuario una opcion.
	 * 
	 * @return  la opcion seleccionada por el usuario.
	 */
	private static int printMenuRegistrar() {
		int option = -1;
		
		System.out.println("REGISTRAR ANUNCIO:");
		System.out.println("\t1. Anuncio general.");
		System.out.println("\t2. Anuncio tematico.");
		System.out.println("\t3. Anuncio individualizado.");
		System.out.println("\t4. Anuncio flash.");
		System.out.println("\t0. Volver al menu programa");
		
		try {
			// Se pide la opcion al propietario
			System.out.print("Seleccione el tipo de anuncio: ");
			option =  Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException nfe) {
			System.out.print("Opcion incorrecta. ");
			option = -1;
		}
		
		return option;
	}
	
	/**
	 * Maneja el menu de registros.
	 */
	private static void menuRegistrar() {
		int option;
		
		do {
			option = printMenuRegistrar();
			
			// Crear anuncio general
			if (option == 1) {
				registrarAnuncioGeneral();
				System.out.print("Anuncio creado con exito. ");
			    pressEnter();
			}
			// Crear anuncio tematico
			else if (option == 2) {
				registrarAnuncioTematico();
				System.out.print("Anuncio creado con exito. ");
			    pressEnter();
			}
			// Crear anuncio individualizado
			else if (option == 3) {
				registrarAnuncioIndividualizado();
				System.out.print("Anuncio creado con exito. ");
			    pressEnter();
			}
			// Crear anuncio flash
			if (option == 4) {
				registrarAnuncioFlash();
				System.out.print("Anuncio creado con exito. ");
			    pressEnter();
			}
			// Opcion no definida
			else if (option < 0 || option > 4) {
				System.out.print("Opcion incorrecta. ");
				pressEnter();
			}
		} while (option != 0);
	}
	
	/**
	 * Realiza el registro de la informacion de un nuevo anuncio general.
	 */
	private static void registrarAnuncioGeneral() {
		boolean esIncorrecto;
		String titulo;
		String cuerpo;
		
		// Se solicita el titulo
		do {
			esIncorrecto = false;
			System.out.print("\tTitulo: ");
			titulo = sc.nextLine();
			if (titulo == null || titulo.isEmpty()) {
				System.out.println("El titulo es un campo obligatorio.");
				esIncorrecto = true;
			}
		} while (esIncorrecto);
		
		// Se solicita el cuerpo del anuncio
		do {
			esIncorrecto = false;
			System.out.print("\tCuerpo: ");
			cuerpo = sc.nextLine();
			if (cuerpo == null || cuerpo.isEmpty()) {
				System.out.println("El cuerpo es un campo obligatorio.");
				esIncorrecto = true;
			}
		} while (esIncorrecto);
		
		// Se añade el anuncio
		GenBulletin gb = new GenBulletin(0, titulo, cuerpo, null, propietario.getId(), 0);
	    bulletinMgr.agregarAnuncio(gb);
	}
	
	/**
	 * Realiza el registro de la informacion de un nuevo anuncio tematico.
	 */
	private static void registrarAnuncioTematico() {
		ArrayList<Boolean> intereses = 
				new ArrayList<Boolean>(Arrays.asList(new Boolean[interestMgr.getNumInterests()]));
		boolean esIncorrecto;
		int interes;
		String titulo;
		String cuerpo;
		
		// Se solicita el titulo
		do {
			esIncorrecto = false;
			System.out.print("\tTitulo: ");
			titulo = sc.nextLine();
			if (titulo == null || titulo.isEmpty()) {
				System.out.println("El titulo es un campo obligatorio.");
				esIncorrecto = true;
			}
		} while (esIncorrecto);
		
		// Se solicita el cuerpo del anuncio
		do {
			esIncorrecto = false;
			System.out.print("\tCuerpo: ");
			cuerpo = sc.nextLine();
			if (cuerpo == null || cuerpo.isEmpty()) {
				System.out.println("El cuerpo es un campo obligatorio.");
				esIncorrecto = true;
			}
		} while (esIncorrecto);
		
		// Se solicitan los intereses
		Collections.fill(intereses, Boolean.FALSE);
		do {
			esIncorrecto = false;
			System.out.print("Los intereses son: \n");
			for (int i = 0; i < interestMgr.getNumInterests(); i++) {
				System.out.println("\t" + (i+1) + ". " + interestMgr.getInterest(i));
			}
			System.out.println("\t0. Terminar.");
			
			System.out.print("\nElija un interes o terminar: ");
			interes = sc.nextInt();
			if ((interes == 0) || (interes > interestMgr.getNumInterests())) {
				esIncorrecto=true;
			}else {
				intereses.set(interes - 1, true);
			}
		} while (!esIncorrecto);
		
		// Se añade el anuncio
		ThemBulletin tb = new ThemBulletin(0, titulo, cuerpo, null, propietario.getId(), 0, intereses);
	    bulletinMgr.agregarAnuncio(tb);
	}
	
	/**
	 * Realiza el registro de la informacion de un nuevo anuncio individualizado.
	 */
	private static void registrarAnuncioIndividualizado() {
		ArrayList<Integer> objetivos = new ArrayList<Integer>();
		boolean esIncorrecto;
		String titulo;
		String cuerpo;
		int idObjetivo;
		String strOpt;
		
		// Se solicita el titulo
		do {
			esIncorrecto = false;
			System.out.print("\tTitulo: ");
			titulo = sc.nextLine();
			if (titulo == null || titulo.isEmpty()) {
				System.out.println("El titulo es un campo obligatorio.");
				esIncorrecto = true;
			}
		} while (esIncorrecto);
		
		// Se solicita el cuerpo del anuncio
		do {
			esIncorrecto = false;
			System.out.print("\tCuerpo: ");
			cuerpo = sc.nextLine();
			if (cuerpo == null || cuerpo.isEmpty()) {
				System.out.println("El cuerpo es un campo obligatorio.");
				esIncorrecto = true;
			}
		} while (esIncorrecto);
		
		// Se solicitan los objetivos
		do {
			do {
				esIncorrecto = false;		
				System.out.print("\nId del usuario objetivo: ");
				try {
					idObjetivo = Integer.parseInt(sc.nextLine());
					objetivos.add(idObjetivo);
					
				} catch (NumberFormatException nfe) {
					System.out.print("Opcion introducida no valida. ");
					System.out.print("Pulse ENTER para continuar.");
					sc.nextLine();
				}
			} while (esIncorrecto);
			System.out.print("\n¿Añadir otro objetivo? (s/n): ");
			strOpt = sc.nextLine();
		} while (strOpt.equals("S") || strOpt.equals("s"));
		
		// Se añade el anuncio
		IndBulletin ib = new IndBulletin(0, titulo, cuerpo, null, propietario.getId(), 0, objetivos);
	    bulletinMgr.agregarAnuncio(ib);
	}
	
	/**
	 * Realiza el registro de la informacion de un nuevo anuncio flash.
	 */
	private static void registrarAnuncioFlash() {
		boolean esIncorrecto;
		String titulo;
		String cuerpo;
		
		// Se solicita el titulo
		do {
			esIncorrecto = false;
			System.out.print("\tTitulo: ");
			titulo = sc.nextLine();
			if (titulo == null || titulo.isEmpty()) {
				System.out.println("El titulo es un campo obligatorio.");
				esIncorrecto = true;
			}
		} while (esIncorrecto);
		
		// Se solicita el cuerpo del anuncio
		do {
			esIncorrecto = false;
			System.out.print("\tCuerpo: ");
			cuerpo = sc.nextLine();
			if (cuerpo == null || cuerpo.isEmpty()) {
				System.out.println("El cuerpo es un campo obligatorio.");
				esIncorrecto = true;
			}
		} while (esIncorrecto);
		
		// Se añade el anuncio
		FlashBulletin fb = new FlashBulletin(0, titulo, cuerpo, null, propietario.getId(), 0, null);
	    bulletinMgr.agregarAnuncio(fb);
	}
	
	/**
	 * Publica un anuncio en el tablon con fecha indicada por el usuario.
	 */
	private static void publicar() {
		ArrayList<Bulletin> result = bulletinMgr.getAnuncios();
		bulletinMgr.filtrarAnunciosPropietario(result, propietario.getId());
		bulletinMgr.filtrarAnunciosFase(result, 0);
		bulletinMgr.consultarAnuncios(result);
		
		// Se solicita el anuncio a publicar
		if (result.size() > 0) {
			try {
				System.out.print("Seleccione el id del anuncio a publicar: ");
				int id = Integer.parseInt(sc.nextLine());
				Bulletin b = bulletinMgr.buscarAnuncioId(id);
				
				// Si elige un anuncio correcto
				if (b != null && b.getPropietario() == propietario.getId() && b.getFase() == 0) {
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					String strFecha;
					Date fechaPublicacion = null;
					do {
						System.out.print("\tFecha de publicacion (dd/MM/yyyy): ");
						strFecha = sc.nextLine();
						try {
							fechaPublicacion = df.parse(strFecha);
						} catch (ParseException e) {
							System.out.println("La fecha de publicacion es un "
									+ "campo obligatorio y debe introducirse "
									+ "en el formato dado.");
						}
					} while (fechaPublicacion == null);
					
					// Si no es un anuncio flash
					if (b.getTipo() != 4) {
						bulletinMgr.publicarAnuncio(b, fechaPublicacion);
					}
					else {
						Date fechaFinalizacion = null;
						do {
							System.out.print("\tFecha de finalizacion (dd/MM/yyyy): ");
							strFecha = sc.nextLine();
							try {
								fechaFinalizacion = df.parse(strFecha);
							} catch (ParseException e) {
								System.out.println("La fecha de publicacion es un campo obligatorio y debe introducirse en el formato dado.");
							}
						} while (fechaFinalizacion == null);
						FlashBulletin fb = (FlashBulletin) b;
						fb.setFechaFinalizacion(fechaFinalizacion);
						bulletinMgr.publicarAnuncio(fb, fechaFinalizacion);
					}
					
				    System.out.print("Anuncio publicado con exito. ");
				}
				else {
					System.out.print("El anuncio seleccionado no existe o no tienes permisos. ");
				}
			} catch (NumberFormatException nfe) {
				System.out.print("Identificador introducido no valido. ");
			}
		}
		else {
			System.out.print("No hay anuncios en edicion. ");
		}
	}
	
	/**
	 * Realiza la edicion de la informacion de un anuncio.
	 */
	private static void editar() {
		ArrayList<Bulletin> result = null;
		
		result = bulletinMgr.getAnuncios();
		bulletinMgr.filtrarAnunciosPropietario(result, propietario.getId());
		bulletinMgr.filtrarAnunciosFase(result, 0);
		bulletinMgr.consultarAnuncios(result);
		
		if (result.size() > 0) {
			try {
				System.out.print("Seleccione el id del anuncio a editar: ");
				int id = Integer.parseInt(sc.nextLine());
				Bulletin b = bulletinMgr.buscarAnuncioId(id);
				
				if (b != null && b.getPropietario() == propietario.getId() && b.getFase() == 0) {
					boolean esIncorrecto;
					
					// Se solicita el titulo
					do {
						esIncorrecto = false;
						System.out.print("\tTitulo: ");
						String titulo = sc.nextLine();
						if (titulo == null || titulo.isEmpty()) {
							System.out.println("El titulo es un campo obligatorio.");
							esIncorrecto = true;
						}
						else {
							b.setTitulo(titulo);
						}
						
					} while (esIncorrecto);
				
					// Se solicita el cuerpo del anuncio
					do {
						esIncorrecto = false;
						System.out.print("\tCuerpo: ");
						String cuerpo = sc.nextLine();
						if (cuerpo == null || cuerpo.isEmpty()) {
							System.out.println("El cuerpo es un campo obligatorio.");
							esIncorrecto = true;
						}
						else {
							b.setCuerpo(cuerpo);
						}
					} while (esIncorrecto);
				
					bulletinMgr.editarAnuncio(b);
					System.out.print("Anuncio editado correctamente. ");
				}
				else {
					System.out.print("El anuncio seleccionado no existe o no tienes permisos. ");
				}
			} catch (NumberFormatException nfe) {
				System.out.print("Opcion introducida no valida. ");
			}
		}
		else {
			System.out.print("No hay anuncios en edicion. ");
		}
	}

	/**
	 * Archiva un anuncio indicado por el usuario del que el usuario 
	 * es propietario. 
	 */
	private static void archivar() {
		ArrayList<Bulletin> result = bulletinMgr.getAnuncios();
		bulletinMgr.filtrarAnunciosPropietario(result, propietario.getId());
		bulletinMgr.consultarAnuncios(result);
		
		// Se solicita el anuncio a archivar
		if (result.size() > 0) {
			try {
				System.out.print("Seleccione el id del anuncio a archivar: ");
				int id = Integer.parseInt(sc.nextLine());
				Bulletin b = bulletinMgr.buscarAnuncioId(id);
				
				// Si elige un anuncio correcto
				if (b != null && b.getPropietario() == propietario.getId()) {
					bulletinMgr.archivarAnuncio(id);
					System.out.print("Anuncio archivado correctamente. ");
				}
				else {
					System.out.print("El anuncio seleccionado no existe o no tienes permisos. ");
				}
			} catch (NumberFormatException nfe) {
				System.out.print("Identificador introducido no valido. ");
			}
		}
		else {
			System.out.print("No dispone de anuncios que pueda archivar. " );
		}
	}
	
	/**
	 * Imprime el menu de consultas del tablon y solicita al usuario una opcion.
	 * 
	 * @return  la opcion seleccionada por el usuario.
	 */
	private static int printMenuTablon() {
		int option;
		
		System.out.println("TABLON DE ANUNCIOS:");
		System.out.println("\t1. Mostrar todos los anuncios.");
		System.out.println("\t2. Buscar anuncios por propietario.");
		System.out.println("\t3. Buscar anuncios por intereses.");
		System.out.println("\t4. Buscar anuncios dirigidos a ti.");
		System.out.println("\t5. Buscar anuncios por fecha de publicacion.");
		System.out.println("\t0. Volver al menu programa");
		
		// Se pide la opcion de mostrar al propietario
		try {
			System.out.print("Seleccione el tipo de anuncio: ");
			option =  Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException nfe) {
			System.out.print("Opcion incorrecta. ");
			System.out.print("Pulse ENTER para continuar.");
			sc.nextLine();
			option = -1;
		}
		
		return option;
	}
	
	/**
	 * Maneja el menu de consultas del tablon de anuncios.
	 */
	private static void menuTablon() {
		int option;
		ArrayList<Bulletin> result = null;
		
		do {
			option = printMenuTablon();
			
			// Se solicita mostrar anuncios
			if (option > 0 && option < 6) {
				
				// Mostrar todos los anuncios
				if (option == 1) {
					result = consultarTablon();
				}
				// Buscar anuncios por propietario
				else if (option == 2) {
					result = consultarPorPropietario();
				}
				// Buscar anuncios por intereses
				else if (option == 3) {
					result = consultarPorIntereses();
				}
				// Buscar anuncios dirigidos a ti
				else if (option == 4) {
					result = consultarIndividualizados();
				}
				// Buscar anuncios por fecha de publicacion
				else if (option == 5) {
					result = consultarPorFecha();
				}
				
				// Se pide las opciones de ordenacion al usuario
				if (result != null) {
					ordenarAnuncios(result);
					bulletinMgr.consultarAnuncios(result);
					pressEnter();
				}
			} else if (option != 0) {
				System.out.print("Opcion incorrecta. ");
				pressEnter();
			}
		} while (option != 0);
	}
	
	/**
	 * Obtiene todos los anuncios publicados en el tablon accesibles por el 
	 * usuario que maneja la aplicacion.
	 * 
	 * @return  la lista de los anuncios a mostrar
	 */
	private static ArrayList<Bulletin> consultarTablon() {
		ArrayList<Bulletin> result = bulletinMgr.getAnuncios();
		bulletinMgr.filtrarAnunciosFase(result, 2);
		bulletinMgr.filtrarAnunciosDestinatario(result, propietario.getId());
		return result;
	}
	
	/**
	 * Obtiene todos los anuncios publicados en el tablon indicados por 
	 * su usuario propietario.
	 * 
	 * @return  la lista de los anuncios a mostrar
	 */
	private static ArrayList<Bulletin> consultarPorPropietario() {
		ArrayList<Bulletin> result = null;
		
		try {
			System.out.print("Seleccione el id del propietario: ");
			int id = Integer.parseInt(sc.nextLine());
			if (userMgr.existeUsuarioId(id)) {
				result = bulletinMgr.getAnuncios();
				bulletinMgr.filtrarAnunciosPropietario(result, id);
				bulletinMgr.filtrarAnunciosFase(result, 2);
			}
			else {
				System.out.print("El usuario seleccionado no existe. ");
				result = null;
			}
		} catch (NumberFormatException nfe) {
			System.out.print("Identificador introducido no valido. ");
			result = null;
		}
		
		return result;
	}
	
	/**
	 * Obtiene todos los anuncios tematicos publicados en el tablon indicados 
	 * por los intereses del usuario que maneja la aplicacion.
	 * 
	 * @return  la lista de los anuncios a mostrar
	 */
	private static ArrayList<Bulletin> consultarPorIntereses() {
		ArrayList<Bulletin> result = bulletinMgr.getAnuncios();
		bulletinMgr.filtrarAnunciosTipo(result, 2);
		bulletinMgr.filtrarAnunciosIntereses(result, propietario);
		bulletinMgr.filtrarAnunciosFase(result, 2);
		return result;
	}
	
	/**
	 * Obtiene todos los anuncios individualizados publicados en el tablon 
	 * que tengan como objetivo al usuario que maneja la aplicacion.
	 * 
	 * @return  la lista de los anuncios a mostrar
	 */
	private static ArrayList<Bulletin> consultarIndividualizados() {
		ArrayList<Bulletin> result = bulletinMgr.getAnuncios();
		bulletinMgr.filtrarAnunciosTipo(result, 3);
		bulletinMgr.filtrarAnunciosDestinatario(result, propietario.getId());
		bulletinMgr.filtrarAnunciosFase(result, 2);
		return result;
	}
	
	/**
	 * Obtiene todos los anuncios publicados en el tablon indicados por 
	 * su fecha de publicacion.
	 * 
	 * @return  la lista de los anuncios a mostrar
	 */
	private static ArrayList<Bulletin> consultarPorFecha() {
		ArrayList<Bulletin> result;
		
		System.out.print("\tFecha de publicacion (dd/MM/yyyy): ");
		String strFecha = sc.nextLine();
	    try {
	    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	    	Date fecha = df.parse(strFecha);
	    	result = bulletinMgr.getAnuncios();
	    	bulletinMgr.filtrarAnunciosFase(result, 2);
		    bulletinMgr.filtrarAnunciosFechaPublicacion(result, fecha);
	    } catch (ParseException e) {
	    	System.out.println("Formato de fecha incorrecto. ");
	    	result = null;
	    }
	    
	    return result;
	}
	
	/**
	 * Ordena la lista de anuncios a mostrar bajo un criterio seleccionado 
	 * por el usuario.
	 * 
	 * @param  bulletins  la lista de anuncios a ordenar.
	 */
	private static void ordenarAnuncios(ArrayList<Bulletin> bulletins) {
		int option;
		
		do {
			try {
				System.out.println("Criterios de ordenacion:");
				System.out.println("\t1. Por fecha de publicacion.");
				System.out.println("\t2. Por usuario propietario.");
				System.out.print("Seleccione el criterio de ordenacion: ");
				option = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException nfe) {
				option = -1;
			}
			if (option < 1 || option > 2) {
				System.out.print("Opcion incorrecta. ");
				pressEnter();
			}
		} while (option < 1 || option > 2);
		
		// Si se ordena por fecha de publicacion
		if (option == 1) {
			bulletinMgr.ordenarAnunciosFechaPublicacion(bulletins);
		}
		// Si se ordena por usuario propietario
		else if (option == 2) {
			bulletinMgr.ordenarAnunciosPropietario(bulletins);
		}
	}
	
	/**
	 * Muestra los anuncios de los que el usuario es propietario.
	 */
	private static void mostrarMisAnuncios() {
		ArrayList<Bulletin> result = null;
		result = bulletinMgr.getAnuncios();
		bulletinMgr.filtrarAnunciosPropietario(result, propietario.getId());
		bulletinMgr.consultarAnuncios(result);
	}
	
	/**
	 * Maneja el menu principal de la gestion de anuncios.
	 */
	protected static void menu() {	
		sc = new Scanner(System.in);
		int option;
		
		// Se solicita inicio de sesion al usuario
		System.out.println("INICIO DE SESION:");
		propietario = login();		
		
		if (propietario != null) {
			System.out.print("Sesion iniciada. ");
			pressEnter();
			
			do {
				// Imprime menu
				option = printMenu();
				
				// Crear anuncio
				if (option == 1) {
					menuRegistrar();
				}	
				// Publicar anuncio
				else if (option == 2) {
					System.out.println("PUBLICAR ANUNCIO:");
					publicar();
					pressEnter();
				}
				// Editar anuncio
				else if (option == 3) {
					System.out.println("EDITAR ANUNCIO:");
					editar();
					pressEnter();
				}
				// Archivar anuncio
				else if (option == 4) {
					System.out.println("ARCHIVAR ANUNCIO:");
					archivar();
					pressEnter();
				}		
				// Mostrar anuncios
				else if (option == 5) {
					menuTablon();
				}		
				// Mostrar mis anuncios
				else if (option == 6) {
					System.out.println("MIS ANUNCIOS:");
					mostrarMisAnuncios();
					pressEnter();
				}
				// Opcion no definida
				else if (option < 0 || option > 6) {
					System.out.print("Opcion incorrecta. ");
					pressEnter();
				}
			} while (option != 0);
		}
		else {
			System.out.print("Error al iniciar sesion. ");
			pressEnter();
		}
	}	
}
