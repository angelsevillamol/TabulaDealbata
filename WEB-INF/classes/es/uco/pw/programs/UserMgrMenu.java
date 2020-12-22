/**
 * Aplicacion que maneja la gestión de usuarios de una agenda.
 *
 * @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.programs;
import es.uco.pw.business.user.*;
import java.util.Date; 
import java.text.SimpleDateFormat; 
import java.text.ParseException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserMgrMenu {
	
	private static Scanner sc;
	private static InterestMgr interestMgr = InterestMgr.getInstance();
	private static UserMgr gestorContactos = UserMgr.getInstance();
	
	/**
	 * Solicita al usuario que pulse enter para continuar.
	 */
	private static void pressEnter() {
		System.out.print("Pulse ENTER para continuar.");
		sc.nextLine();
	}
	
	/**
	 * Imprime el menu principal de la gestion de usuarios y 
	 * solicita al usuario una opcion.
	 * 
	 * @return  la opcion seleccionada por el usuario.
	 */
	private static int printMenu() {
		int option = -1;
		
		System.out.println("GESTIÓN DE USUARIOS:");
		System.out.println("\t1. Registrar usuario.");
		System.out.println("\t2. Editar usuario.");
		System.out.println("\t3. Eliminar usuario.");
		System.out.println("\t4. Consultar usuarios.");
		System.out.println("\t0. Salir.");
		
		try {
			// Se pide la opcion al usuario
			System.out.print("Seleccione opción: ");
			option =  Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException nfe) {
			System.out.print("Opcion incorrecta. ");
			pressEnter();
			option = -1;
		}
		
		return option;
	}
	
	/**
	 * Imprime el menu de consultas y solicita al usuario una opcion.
	 * 
	 * @return  la opcion seleccionada por el usuario.
	 */
	private static int printMenuConsultar() {
		int option;
		
		System.out.println("CONSULTAR USUARIOS:");
		System.out.println("\t1. Buscar por identificador.");
		System.out.println("\t2. Buscar por correo electronico.");
		System.out.println("\t3. Buscar por nombre y apellidos.");
		System.out.println("\t4. Buscar por edad.");
		System.out.println("\t5. Mostrar todos los usuarios.");
		System.out.println("\t0. Volver.");
		
		do {
			try {
				// Se pide la opcion al usuario
				System.out.print("Seleccione opción: ");
				option =  Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException nfe) {
				option = -1;
			}
		} while (option < 0 || option > 5);
		
		return option;
	}
	
	/**
	 * Maneja el menu de consultas.
	 */
	private static void menuConsultar() {
		int option = -1;
		
		do {
			option = printMenuConsultar();
			
			// Buscar por identificador
			if (option == 1) {
				consultarPorId();
				pressEnter();
			}
			// Buscar por correo electronico
			else if (option == 2) {
				consultarPorEmail();
				pressEnter();
			}
			// Buscar por nombre y apellidos
			else if (option == 3) {
				consultarPorNombre();
				pressEnter();
			}
			// Buscar por edad
			else if (option == 4) {
				consultarPorEdad();
				pressEnter();
			}
			// Mostrar todos los contactos
			else if (option == 5) {
				gestorContactos.consultarUsuarios();
				pressEnter();
			}
			
			
		} while (option != 0);
	}
	
	/**
	 * Realiza el registro de la informacion de un nuevo usuario.
	 */
	private static void registrar() {
		boolean esIncorrecto;
		
		// Se solicita el correo electronico
		String email;
		do {
			esIncorrecto = false;
			System.out.print("\tCorreo electronico: ");
			email = sc.nextLine();
			if (email == null || email.isEmpty()) {
				System.out.println("El correo electronico es un campo obligatorio.");
				esIncorrecto = true;
			}
		} while (esIncorrecto);
	    
	    // Comprueba que el correo electronico esté disponible
		if (!gestorContactos.existeUsuarioEmail(email)) {
			String password;
			// Se solicita la contraseña
			do {
				esIncorrecto = false;
				System.out.print("\tContraseña: ");
				password = sc.nextLine();
				if (password == null || password.isEmpty()) {
					System.out.println("La contraseña es un campo obligatorio.");
					esIncorrecto = true;
				}
			} while (esIncorrecto);
			
			String nombre;
			// Se solicita el nombre
			do {
				esIncorrecto = false;
				System.out.print("\tNombre: ");
				nombre = sc.nextLine();
				if (nombre == null || nombre.isEmpty()) {
					System.out.println("El nombre es un campo obligatorio.");
					esIncorrecto = true;
				}
			} while (esIncorrecto);
		    
			String apellidos;
			// Se solicita el apellido
			do {
				esIncorrecto = false;
				System.out.print("\tApellidos: ");
				apellidos = sc.nextLine();
				if (apellidos == null || apellidos.isEmpty()) {
					System.out.println("Los apellidos es un campo obligatorio.");
					esIncorrecto = true;
				}
			} while (esIncorrecto);
		    
		    // Se solicita la fecha de nacimiento
			Date fechaNacimiento = new Date();
			Date fechaActual = new Date();
			do {
				esIncorrecto = false;
				System.out.print("\tFecha de nacimiento (dd/MM/yyyy): ");
				String strDate = sc.nextLine();
			    try {
			    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
			    	fechaNacimiento = df.parse(strDate);
			    	if (fechaNacimiento.compareTo(fechaActual) > 0) {
				    	System.out.println("Fecha de nacimiento incorrecta.");
				    	esIncorrecto = true;
				    }
			    } catch (ParseException e) {
			    	System.out.println("La fecha de nacimiento es un campo "
			    			+ "obligatorio y debe introducirse en el formato "
			    			+ "dado.");
			    	esIncorrecto = true;
			    }
			} while (esIncorrecto);
		    
			// Se solicitan los intereses
			ArrayList<Boolean> intereses = new ArrayList<Boolean>(Arrays.asList(new Boolean[interestMgr.getNumInterests()]));
			Collections.fill(intereses, Boolean.FALSE);
			do {
				esIncorrecto = false;
				System.out.print("Los intereses son: \n");
				for (int i = 0; i < interestMgr.getNumInterests(); i++) {
					System.out.println("\t" + (i+1) + ". " + interestMgr.getInterest(i));
				}
				System.out.println("\t0. Terminar.");
				
				System.out.print("\nElija un interes o terminar: ");
				int interes = sc.nextInt();
				if ((interes == 0) || (interes > interestMgr.getNumInterests())) {
					esIncorrecto=true;
				}else {
					intereses.set(interes-1, true);
				}
			} while (!esIncorrecto);
		    
		    // Se añade el usuario
			User u = new User(0, nombre, apellidos, fechaNacimiento, email, intereses);
		    gestorContactos.agregarUsuario(u, password);
		} else {
			System.out.println("Error. Correo electrónico en uso.");
		}
	}

	/**
	 * Realiza la edicion de la informacion de un usuario.
	 */
	private static void editar() {
		boolean esIncorrecto;
		System.out.print("\tCorreo electronico del usuario a modificar: ");
		String email = sc.nextLine();
			
		User u = gestorContactos.buscarUsuarioEmail(email);
		if (u != null) {
			System.out.println("User encontrado: ");
			gestorContactos.consultarUsuario(u);
			System.out.print("¿Desea modificar la informacion de usuario? (S/N): ");
			String strAux = sc.nextLine();
			if (strAux.equals("S") || strAux.equals("s")) {
				// Se solicita el correo electronico
				do {
					esIncorrecto = false;
					System.out.print("\tCorreo electronico: ");
					email = sc.nextLine();
					if (email == null || email.isEmpty()) {
						System.out.println("El correo electronico es un campo obligatorio.");
						esIncorrecto = true;
					}
					else {
						u.setEmail(email);
					}
				} while (esIncorrecto);
				    
				// Comprueba que el correo electronico esté disponible
				if (!gestorContactos.existeUsuarioEmail(email)) {
					// Se solicita el nombre
					String nombre;
					do {
						esIncorrecto = false;
						System.out.print("\tNombre: ");
						nombre = sc.nextLine();
						if (nombre == null || nombre.isEmpty()) {
							System.out.println("El nombre es un campo obligatorio.");
							esIncorrecto = true;
						}
						else {
							u.setNombre(nombre);
						}
					} while (esIncorrecto);
					    
					// Se solicita el apellido
					do {
						esIncorrecto = false;
						System.out.print("\tApellidos: ");
						String apellidos = sc.nextLine();
						if (apellidos == null || apellidos.isEmpty()) {
							System.out.println("Los apellidos es un campo obligatorio.");
							esIncorrecto = true;
						}
						else {
							u.setApellidos(apellidos);
						}
					} while (esIncorrecto);
					    
					// Se solicita la fecha de nacimiento
					Date fechaNacimiento = new Date();
					Date fechaActual = new Date();
					do {
						esIncorrecto = false;
						System.out.print("\tFecha de nacimiento (dd/MM/yyyy): ");
						String strDate = sc.nextLine();
					    try {
					    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
					    	fechaNacimiento = df.parse(strDate);
					    	if (fechaNacimiento.compareTo(fechaActual) > 0) {
						    	System.out.println("Fecha de nacimiento incorrecta.");
						    	esIncorrecto = true;
						    }
					    	else {
					    		u.setFechaNacimiento(fechaNacimiento);
					    	}
					    } catch (ParseException e) {
					    	System.out.println("La fecha de nacimiento es un campo obligatorio y debe introducirse en el formato dado.");
					    	esIncorrecto = true;
					    }
					} while (esIncorrecto);
					    
					ArrayList<Boolean> intereses = new ArrayList<Boolean>(Arrays.asList(new Boolean[interestMgr.getNumInterests()]));
					Collections.fill(intereses, Boolean.FALSE);
					do {
						esIncorrecto = false;
						System.out.print("Los intereses son: \n");
						for (int i = 0; i < interestMgr.getNumInterests(); i++) {
							System.out.println("\t" + (i+1) + ". " + interestMgr.getInterest(i));
						}
						System.out.println("\t0. Terminar.");
						
						System.out.print("\nElija un interes o terminar: ");
						int interes = sc.nextInt();
						if ((interes == 0) || (interes > interestMgr.getNumInterests())) {
							esIncorrecto=true;
						} else {
							intereses.set(interes-1, true);
						}
						
						u.setIntereses(intereses);
					} while (!esIncorrecto);
					    
					// Actualiza el usuario
					gestorContactos.editarUsuario(u);
					System.out.print("Usuario editado correctamente. ");
				} else {
					System.out.println("Correo electrónico en uso.");
				}
			}
		}	
		else {
			System.out.print("No se ha encontrado el usuario. ");
		}
	}
	
	/**
	 * Elimina la informacion de un usuario.
	 */
	private static void eliminar() {
		System.out.print("Correo electronico del usuario a eliminar: ");
		String email = sc.nextLine();
			
		User u = gestorContactos.buscarUsuarioEmail(email);
		if (u != null) {
			System.out.println("Usuario encontrado: ");
			gestorContactos.consultarUsuario(u);
			System.out.print("¿Desea eliminarlo de la agenda? (S/N): ");
			String strAux = sc.nextLine();
			if (strAux.equals("S") || strAux.equals("s")) {
				gestorContactos.eliminarUsuario(u.getId());
				System.out.print("Usuario eliminado correctamente. ");
			}
		} 
		else {
			System.out.print("No se ha encontrado el usuario. ");
		}
	}

	/**
	 * Realiza una consulta de la informacion de un usuario indicado 
	 * por su identificador.
	 */
	private static void consultarPorId() {
		try {
			System.out.print("\tIdentificador: ");
			int id = Integer.parseInt(sc.nextLine());
				
			User u = gestorContactos.buscarUsuarioId(id);
			if (u != null) {
				System.out.println("Usuario encontrado:");
				gestorContactos.consultarUsuario(u);
			} 
			else {
				System.out.print("No se ha encontrado al usuario. ");
			}
		} catch (NumberFormatException nfe) {
			System.out.print("Identificador introducido no valido. ");
		}
	}
	
	/**
	 * Realiza una consulta de la informacion de un usuario indicado 
	 * por su correo electronico.
	 */
	private static void consultarPorEmail() {
		System.out.print("\tCorreo electronico: ");
		String email = sc.nextLine();
		User u = gestorContactos.buscarUsuarioEmail(email);
			
		if (u != null) {
			System.out.println("Usuario encontrado:");
			gestorContactos.consultarUsuario(u);
		} 
		else {
			System.out.print("No se ha encontrado el usuario. ");
		}
	}
	
	/**
	 * Realiza una consulta de la informacion de un usuario indicado 
	 * por su nombre completo.
	 */
	private static void consultarPorNombre() {
		ArrayList<User> resultado = null;
		
		System.out.print("\tNombre: ");
		String nombre = sc.nextLine();
		System.out.print("\tApellidos: ");
		String apellidos = sc.nextLine();
			
		resultado = gestorContactos.buscarUsuariosNombre(nombre, apellidos);
		if (resultado.isEmpty()) {
			System.out.print("No se han encontrado resultados. ");
		} else {
			gestorContactos.consultarUsuarios(resultado);
		}
	}
	
	/**
	 * Realiza una consulta de la informacion de un usuario indicado 
	 * por su edad.
	 */
	private static void consultarPorEdad() {
		ArrayList<User> resultado = null;
		
		try {
			System.out.print("\tEdad: ");
			int edad = Integer.parseInt(sc.nextLine());
				
			resultado = gestorContactos.buscarUsuariosEdad(edad);
			if (resultado.isEmpty()) {
				System.out.print("No se han encontrado resultados. ");
			} else {
				gestorContactos.consultarUsuarios(resultado);
			}
		} catch (NumberFormatException nfe) {
			System.out.print("Edad introducida no valida. ");
		}
	}
	
	/**
	 * Maneja el menu principal de la gestion de usuarios.
	 */
	protected static void menu() {	
		sc = new Scanner(System.in);
		int option;
		
		do {
			// Imprime menu
			option = printMenu();
			
			// Registrar usuario
			if (option == 1) {
				System.out.println("REGISTRAR USUARIO:");
				registrar();
				pressEnter();
			} 
			// Editar usuario
			else if (option == 2) {
				System.out.println("EDITAR USUARIO:");
				editar();
				pressEnter();
			}
			// Eliminar usuario
			else if (option == 3) {
				System.out.println("ELIMINAR USUARIO:");
				eliminar();
				pressEnter();
			}
			// Consultar usuarios
			else if (option == 4) {
				menuConsultar();
			}
			// Opcion no definida
			else if (option < 0 || option > 4) {
				System.out.print("Opcion incorrecta. ");
				pressEnter();
			}
		} while (option != 0);
	}
}
