/**
* Aplicacion que maneja la gestión de contactos de una agenda.
*
* @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
* @author Sevilla Molina, Angel (i42semoa@uco.es)
*/

package es.uco.pw.contactos;

import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GestorMainProgram {

	public static void main(String[] args) {
		GestorContactos gestorContactos = GestorContactos.getInstance();
		gestorContactos.cargarContactos();
		Contacto c = null;
		ArrayList<Contacto> resultado = null;
		Scanner sc = new Scanner(System.in);
		int id;
		String nombre = "";
		String apellidos = "";
		String email = "";
		ArrayList<Boolean> intereses = new ArrayList<Boolean>(Arrays.asList(new Boolean[gestorContactos.getIntereses().size()]));
		String strAux = "";
		int intAux=0;
		int edad;
		LocalDate fechaNacimiento = LocalDate.now();
		int option, optionBusqueda;
		boolean esIncorrecto;
		
		do {
			// Imprime menu
			System.out.println("MENU DE OPCIONES:");
			System.out.println("\t1. Añadir contacto.");
			System.out.println("\t2. Editar contacto.");
			System.out.println("\t3. Eliminar contacto.");
			System.out.println("\t4. Mostrar contactos.");
			System.out.println("\t0. Salir del programa");
			
			try {
				// Se pide la opcion al usuario
				System.out.print("Seleccione opción: ");
				option =  Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException nfe) {
				System.out.print("Opcion incorrecta. ");
				System.out.print("Pulse ENTER para continuar.");
				sc.nextInt();
				option = -1;
			}
			
			// Añadir contacto
			if (option == 1) {
				System.out.println("AÑADIR CONTACTO:");
				
				// Se solicita el correo electronico
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
				if (!gestorContactos.existeContactoEmail(email)) {
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
					do {
						esIncorrecto = false;
						System.out.print("\tFecha de nacimiento (dd/MM/yyyy): ");
						strAux = sc.nextLine();
					    try {
					    	fechaNacimiento = LocalDate.parse(strAux, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					    	if (fechaNacimiento.compareTo(LocalDate.now()) > 0) {
						    	System.out.println("Fecha de nacimiento incorrecta.");
						    	esIncorrecto = true;
						    }
					    } catch (DateTimeParseException e) {
					    	System.out.println("La fecha de nacimiento es un campo obligatorio y debe introducirse en el formato dado.");
					    	esIncorrecto = true;
					    }
					} while (esIncorrecto);
				    
					// Se solicitan los intereses
					Collections.fill(intereses, Boolean.FALSE);
					do {
						esIncorrecto = false;
						
						System.out.print("Los intereses son: \n");
						System.out.print("\t1. Deporte\n\t2. Cultura\n\t3. Oficina\n\t4. Videojuegos\n\t5. Cine\n\t6. Arte\n\t7. Turismo\n\t8. Música\n\t9. Fotografía\n\t10. Gastronomía\n\n\t0. TERMINAR");
						System.out.print("\nElija un interes o terminar: ");
						intAux = sc.nextInt();
						if((intAux==0)||(intAux>10)) {
							esIncorrecto=true;
						}else {
							intereses.set(intAux-1, true);
						}
					} while (!esIncorrecto);
				    
				    // Se añade el contacto
				    gestorContactos.agregarContacto(nombre, apellidos, fechaNacimiento, email, intereses);
				} else {
					System.out.println("Error. Correo electrónico en uso.");
				}
				
				System.out.print("Pulse ENTER para continuar.\n");
				sc.nextLine();
			} 
			// Modificar contacto
			else if (option == 2) {
				if (gestorContactos.getNumContactos() > 0) {
					System.out.println("EDITAR CONTACTO:");
					System.out.print("Correo electronico del contacto a modificar: ");
					email = sc.nextLine();
					
					c = gestorContactos.buscarContactoEmail(email);
					if (c != null) {
						System.out.println("Contacto encontrado: ");
						gestorContactos.consultarContacto(c);
						System.out.print("¿Desea modificar la informacion de contacto? (S/N): ");
						strAux = sc.nextLine();
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
							} while (esIncorrecto);
						    
						    // Comprueba que el correo electronico esté disponible
							if (!gestorContactos.existeContactoEmail(email)) {
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
								do {
									esIncorrecto = false;
									System.out.print("\tFecha de nacimiento (dd/MM/yyyy): ");
									strAux = sc.nextLine();
								    try {
								    	fechaNacimiento = LocalDate.parse(strAux, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
								    	if (fechaNacimiento.compareTo(LocalDate.now()) > 0) {
									    	System.out.println("Fecha de nacimiento incorrecta.");
									    	esIncorrecto = true;
									    }
								    } catch (DateTimeParseException e) {
								    	System.out.println("La fecha de nacimiento es un campo obligatorio y debe introducirse en el formato dado.");
								    	esIncorrecto = true;
								    }
								} while (esIncorrecto);
							    
								// Se solicitan los intereses
								Collections.fill(intereses, Boolean.FALSE);
								do {
									esIncorrecto = false;
									
									System.out.print("Los intereses son: \n");
									System.out.print("\t1. Deporte\n\t2. Cultura\n\t3. Oficina\n\t4. Videojuegos\n\t5. Cine\n\t6. Arte\n\t7. Turismo\n\t8. Música\n\t9. Fotografía\n\t10. Gastronomía\n\n\t0. TERMINAR");
									System.out.print("\nElija un interes o terminar: ");
									intAux = sc.nextInt();
									if((intAux==0)||(intAux>10)) {
										esIncorrecto=true;
									}else {
										intereses.set(intAux-1, true);
									}
								} while (!esIncorrecto);
							    
							    // Actualiza el contacto
							    gestorContactos.editarContacto(c.getId(), nombre, apellidos, fechaNacimiento, email, intereses);
							    System.out.print("Contacto editado correctamente. ");
							} else {
								System.out.println("Correo electrónico en uso.");
							}
						}
					}	
					else {
						System.out.print("No se ha encontrado el contacto. ");
					}
				}
				else {
					System.out.print("La agenda de contactos esta vacia. ");	
				}
				
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
			// Eliminar contacto
			else if (option == 3) {
				if (gestorContactos.getNumContactos() > 0) {
					System.out.println("ELIMINAR CONTACTO:");
					System.out.print("Correo electronico del contacto a eliminar: ");
					email = sc.nextLine();
					
					c = gestorContactos.buscarContactoEmail(email);
					if (c != null) {
						System.out.println("Contacto encontrado: ");
						gestorContactos.consultarContacto(c);
						System.out.print("¿Desea eliminarlo de la agenda? (S/N): ");
						strAux = sc.nextLine();
						if (strAux.equals("S") || strAux.equals("s")) {
							gestorContactos.eliminarContacto(c.getId());
							System.out.print("Contacto eliminado correctamente. ");
						}
					} 
					else {
						System.out.print("No se ha encontrado el contacto. ");
					}
				}
				else {
					System.out.print("La agenda de contactos esta vacia. ");
				}
				
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
			// Mostrar contactos
			else if (option == 4) {
				if (gestorContactos.getNumContactos() > 0) {
					System.out.println("MOSTRAR CONTACTOS:");
					System.out.println("\t1. Buscar por identificador.");
					System.out.println("\t2. Buscar por correo electronico.");
					System.out.println("\t3. Buscar por nombre y apellidos.");
					System.out.println("\t4. Buscar por edad.");
					System.out.println("\t5. Mostrar todos los contactos.");
					do {
						try {
							// Se pide la opcion al usuario
							System.out.print("Seleccione opción: ");
							optionBusqueda =  Integer.parseInt(sc.nextLine());
						} catch (NumberFormatException nfe) {
							optionBusqueda = -1;
						}
					} while (optionBusqueda < 1 || optionBusqueda > 5);
					
					// Buscar por identificador
					if (optionBusqueda == 1) {
						try {
							System.out.print("\tIdentificador: ");
							id = Integer.parseInt(sc.nextLine());
							
							c = gestorContactos.buscarContacto(id);
							if (c != null) {
								System.out.println("Contacto encontrado:");
								gestorContactos.consultarContacto(c);
							} 
							else {
								System.out.print("No se ha encontrado el contacto. ");
							}
						} catch (NumberFormatException nfe) {
							System.out.print("Identificador introducido no valido. ");
						}
					}
					// Buscar por correo electronico
					else if (optionBusqueda == 2) {
						System.out.print("\tCorreo electronico: ");
						email = sc.nextLine();
						c = gestorContactos.buscarContactoEmail(email);
						
						if (c != null) {
							System.out.println("Contacto encontrado:");
							gestorContactos.consultarContacto(c);
						} 
						else {
							System.out.print("No se ha encontrado el contacto. ");
						}
					}
					// Buscar por nombre y apellidos
					else if (optionBusqueda == 3) {
						System.out.print("\tNombre: ");
						nombre = sc.nextLine();
						System.out.print("\tApellidos: ");
						apellidos = sc.nextLine();
						
						resultado = gestorContactos.buscarContacto(nombre, apellidos);
						gestorContactos.consultarContactos(resultado);
					}
					// Buscar por edad
					else if (optionBusqueda == 4) {
						try {
							System.out.print("\tEdad: ");
							edad = Integer.parseInt(sc.nextLine());
							
							resultado = gestorContactos.buscarContactoEdad(edad);
							gestorContactos.consultarContactos(resultado);
						} catch (NumberFormatException nfe) {
							System.out.print("Edad introducida no valida. ");
						}
					}
					// Mostrar todos los contactos
					else if (optionBusqueda == 5) {
						gestorContactos.consultarContactos();
					}
				}
				else {
					System.out.print("La agenda de contactos esta vacia. ");	
				}
				
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
			// Opcion no definida
			else if (option < 0 || option > 4) {
				System.out.print("Opcion incorrecta. ");
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
		} while (option != 0);
		
		sc.close();
		// Al terminar se guardan los cambios realizados
		gestorContactos.guardarContactos();
	}
}
