/**
* Aplicacion que maneja la gesti�n de Anuncios.
*
* @author Mu�oz Jimenez, Juan Pedro (p52mujij@uco.es)
* @author Sevilla Molina, Angel (i42semoa@uco.es)
*/
package es.uco.pw.anuncios;
import es.uco.pw.contactos.Contacto;
import es.uco.pw.contactos.GestorContactos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class AnunciosMainProgram {
	public static void main(String[] args) {
		GestorContactos gestorContactos = GestorContactos.getInstance();
		GestorAnuncios gestorAnuncios = GestorAnuncios.getInstance();
		gestorContactos.cargarContactos();
		gestorAnuncios.cargarAnuncios();
		Contacto usuario = null;
		IAnuncio a = null;
		ArrayList<IAnuncio> resultado = null;
		Scanner sc = new Scanner(System.in);
		int id;
		String titulo = "";
		String cuerpo = "";
		String strAux = "";
		String email = "";
		int intAux=0;
		LocalDate fecha = LocalDate.now();
		int option, optionPublicar, optionMostrar, optionOrdenar;
		ArrayList<Boolean> intereses = new ArrayList<Boolean>(Arrays.asList(new Boolean[gestorContactos.getIntereses().size()]));
		ArrayList<Integer> objetivos = new ArrayList<Integer>();
		boolean esIncorrecto = false;
		boolean otroMas = true;
		
		boolean logged = false;
		while(!logged) {
			System.out.println("INICIO DE SESION:");
			
			System.out.print("\tCorreo de usuario: ");
			email = sc.nextLine();
			logged = gestorContactos.existeContactoEmail(email);
			if(!logged) {
				System.out.print("Error al logear. ");
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
				email = "";
			}
			else {
				usuario = gestorContactos.buscarContactoEmail(email);
				System.out.print("Sesion iniciada. ");
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
		}
		
		do {
			// Imprime menu
			System.out.println("MENU DE OPCIONES:");
			System.out.println("\t1. Crear anuncio.");
			System.out.println("\t2. Publicar anuncio.");
			System.out.println("\t3. Editar anuncio.");
			System.out.println("\t4. Archivar anuncio.");
			System.out.println("\t5. Mostrar tablon de anuncios.");
			System.out.println("\t6. Mostrar mis anuncios.");
			System.out.println("\t0. Salir del programa");
			
			try {
				// Se pide la opcion al usuario
				System.out.print("Seleccione opcion: ");
				option =  Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException nfe) {
				System.out.print("Opcion incorrecta. ");
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
				option = -1;
			}
			
			// Crear anuncio
			if (option == 1) {
				do {
					
					System.out.println("CREAR ANUNCIO:");
					System.out.println("\t1. Anuncio general.");
					System.out.println("\t2. Anuncio tematico.");
					System.out.println("\t3. Anuncio individualizado.");
					System.out.println("\t4. Anuncio flash.");
					System.out.println("\t0. Volver al menu programa");
					
					try {
						// Se pide la opcion al usuario
						System.out.print("Seleccione el tipo de Anuncio: ");
						optionPublicar =  Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException nfe) {
						System.out.print("Opcion incorrecta. ");
						optionPublicar = -1;
					}
					// Crear anuncio general
					if (optionPublicar == 1) {
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
					    gestorAnuncios.agregarAnuncioGeneral(titulo, cuerpo, usuario.getId());
					    System.out.print("Anuncio creado con exito. ");
					    System.out.print("Pulse ENTER para continuar.");
						sc.nextLine();
					}
					// Crear anuncio tematico
					else if (optionPublicar == 2) {
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
							System.out.print("\t1. Deporte\n\t2. Cultura\n\t3. Oficina\n\t4. Videojuegos\n\t5. Cine\n\t6. Arte\n\t7. Turismo\n\t8. Música\n\t9. Fotografía\n\t10. Gastronomía\n\n\t0. TERMINAR");
							System.out.print("\nElija un interes o terminar: ");
							try {
								intAux = Integer.parseInt(sc.nextLine());
								if ((intAux==0)||(intAux>10)) {
									esIncorrecto=true;
								} else {
									intereses.set(intAux-1, true);
								}
							} catch (NumberFormatException nfe) {
								System.out.print("Opcion introducida no valida. ");
								System.out.print("Pulse ENTER para continuar.");
								sc.nextLine();
							}
						} while (!esIncorrecto);
						
						// Se añade el anuncio
					    gestorAnuncios.agregarAnuncioTematico(titulo, cuerpo, usuario.getId(), intereses);
					    System.out.print("Anuncio creado con exito. ");
					    System.out.print("Pulse ENTER para continuar.");
						sc.nextLine();
					}
					// Crear anuncio individualizado
					else if (optionPublicar == 3) {
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
									intAux = Integer.parseInt(sc.nextLine());
									objetivos.add(intAux);
									
								} catch (NumberFormatException nfe) {
									System.out.print("Opcion introducida no valida. ");
									System.out.print("Pulse ENTER para continuar.");
									sc.nextLine();
								}
							} while (esIncorrecto);
							System.out.print("\n¿Añadir otro objetivo? (s/n): ");
							strAux = sc.nextLine();
							if (strAux.equals("N") || strAux.equals("n")) {
								otroMas = false;
							}
						}while (otroMas);
						
						// Se aÃ±ade el anuncio
					    gestorAnuncios.agregarAnuncioIndividualizado(titulo, cuerpo, usuario.getId(), objetivos);
					    System.out.print("Anuncio creado con exito. ");
					    System.out.print("Pulse ENTER para continuar.");
						sc.nextLine();
					}
					// Crear anuncio flash
					else if (optionPublicar == 4) {
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
						
						// Se solicita la fecha limite del anuncio flash
						do {
							esIncorrecto = false;
							System.out.print("\tFecha de finalizacion (dd/MM/yyyy): ");
							strAux = sc.nextLine();
							if (strAux == null || strAux.isEmpty()) {
								System.out.println("Esta fecha es un campo obligatorio.");
								esIncorrecto = true;
							}
							fecha = LocalDate.parse(strAux, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						} while (esIncorrecto);
						
						// Se aÃ±ade el anuncio
					    gestorAnuncios.agregarAnuncioFlash(titulo, cuerpo, usuario.getId(), fecha);
					    System.out.print("Anuncio creado con exito. ");
					    System.out.print("Pulse ENTER para continuar.");
						sc.nextLine();
					}
				} while (optionPublicar != 0);
			}
			// Publicar anuncio
			else if (option == 2) {
				System.out.println("PUBLICAR ANUNCIO");
				resultado = gestorAnuncios.buscarAnunciosPropietario(usuario.getId());
				gestorAnuncios.filtrarAnunciosFase(resultado, 0);
				gestorAnuncios.consultarAnuncios(resultado);
				
				// Se solicita el anuncio a publicar
				if (resultado.size() > 0) {
					try {
						System.out.print("Seleccione el id del anuncio a publicar: ");
						id = Integer.parseInt(sc.nextLine());
						a = gestorAnuncios.buscarAnuncio(id);
						
						// Si elige un anuncio correcto
						if (a != null && a.getIdUsuarioPropietario() == usuario.getId()) {
							System.out.print("\tFecha de publicacion (dd/MM/yyyy): ");
							strAux = sc.nextLine();
							try {
							    fecha = LocalDate.parse(strAux, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
							    gestorAnuncios.publicarAnuncio(id, fecha);
							    System.out.print("Anuncio publicado con exito. ");
							} catch (DateTimeParseException e) {
							    System.out.println("La fecha de publicacion es un campo obligatorio y debe introducirse en el formato dado.");
							}
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
				
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
			// Editar anuncio
			else if (option == 3) {
				System.out.println("EDITAR ANUNCIO:");
				resultado = gestorAnuncios.buscarAnunciosPropietario(usuario.getId());
				gestorAnuncios.filtrarAnunciosFase(resultado, 0);
				gestorAnuncios.consultarAnuncios(resultado);
				
				if (resultado.size() > 0) {
					try {
						System.out.print("Seleccione el id del anuncio a editar: ");
						id = Integer.parseInt(sc.nextLine());
						a = gestorAnuncios.buscarAnuncio(id);
						
						if (a != null && a.getIdUsuarioPropietario() == usuario.getId()) {
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
						
							gestorAnuncios.editarAnuncio(id, titulo, cuerpo);
							System.out.print("Anuncio editado correctamente. ");
						}
						else {
							System.out.print("El anuncio seleccionado no existe o no tienes permisos. ");
						}
					} catch (NumberFormatException nfe) {
						System.out.print("Opcion introducida no valida. ");
					}
					System.out.print("Pulse ENTER para continuar.");
					sc.nextLine();
				}
				else {
					System.out.print("No hay anuncios en edicion. ");
					System.out.print("Pulse ENTER para continuar.");
					sc.nextLine();
				}
			}
			// Archivar anuncio
			else if (option == 4) {
				System.out.println("ARCHIVAR ANUNCIO");
				resultado = gestorAnuncios.buscarAnunciosPropietario(usuario.getId());
				gestorAnuncios.consultarAnuncios(resultado);
				
				// Se solicita el anuncio a archivar
				if (resultado.size() > 0) {
					try {
						System.out.print("Seleccione el id del anuncio a archivar: ");
						id = Integer.parseInt(sc.nextLine());
						a = gestorAnuncios.buscarAnuncio(id);
						
						// Si elige un anuncio correcto
						if (a != null && a.getIdUsuarioPropietario() == usuario.getId()) {
							gestorAnuncios.archivarAnuncio(id);
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
				
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
			// Mostrar anuncios
			else if (option == 5) {
				do {
					System.out.println("TABLON DE ANUNCIOS:");
					System.out.println("\t1. Mostrar todos los anuncios.");
					System.out.println("\t2. Buscar anuncios por propietario.");
					System.out.println("\t3. Buscar anuncios por intereses.");
					System.out.println("\t4. Buscar anuncios dirigidos solo a ti.");
					System.out.println("\t5. Buscar anuncios por fecha de publicacion.");
					System.out.println("\t0. Volver al menu programa");
					
					// Se pide la opcion de mostrar al usuario
					try {
						
						System.out.print("Seleccione el tipo de Anuncio: ");
						optionMostrar =  Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException nfe) {
						System.out.print("Opcion incorrecta. ");
						System.out.print("Pulse ENTER para continuar.");
						sc.nextLine();
						optionMostrar = -1;
					}
					
					// Se solicita mostrar anuncios
					if (optionMostrar > 0 && optionMostrar < 6) {
						// Mostrar todos los anuncios
						if (optionMostrar == 1) {
							resultado = gestorAnuncios.buscarAnunciosPublicados(gestorContactos.buscarContactoEmail(email).getId());
							esIncorrecto = false;
						}
						// Buscar anuncios por propietario
						else if (optionMostrar == 2) {
							try {
								System.out.print("Seleccione el id del propietario: ");
								id = Integer.parseInt(sc.nextLine());
								if (gestorContactos.existeContacto(id)) {
									resultado = gestorAnuncios.buscarAnunciosPropietario(id);
									gestorAnuncios.filtrarAnunciosFase(resultado, 2);
									esIncorrecto = false;
								}
								else {
									System.out.print("El contacto seleccionado no existe. ");
									System.out.print("Pulse ENTER para continuar.");
									sc.nextLine();
									esIncorrecto = true;
								}
							} catch (NumberFormatException nfe) {
								System.out.print("Identificador introducido no valido. ");
								System.out.print("Pulse ENTER para continuar.");
								sc.nextLine();
								esIncorrecto = true;
							}
						}
						// Buscar anuncios por intereses
						else if (optionMostrar == 3) {
							resultado = gestorAnuncios.buscarAnunciosIntereses(usuario);
							gestorAnuncios.filtrarAnunciosFase(resultado, 2);
							esIncorrecto = false;
						}
						// Buscar anuncios dirigidos a ti
						else if (optionMostrar == 4) {
							resultado = gestorAnuncios.buscarAnunciosIndividualizados(gestorContactos.buscarContactoEmail(email).getId());
							gestorAnuncios.filtrarAnunciosFase(resultado, 2);
							esIncorrecto = false;
						}
						// Buscar anuncios por fecha de publicacion
						else if (optionMostrar == 5) {
							System.out.print("\tFecha de publicacion (dd/MM/yyyy): ");
							strAux = sc.nextLine();
						    try {
						    	fecha = LocalDate.parse(strAux, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						    } catch (DateTimeParseException e) {
						    	System.out.println("Formato de fecha incorrecto. ");
						    	esIncorrecto = true;
						    }
						    
						    resultado = gestorAnuncios.buscarAnunciosFechaPublicacion(fecha);
						    gestorAnuncios.filtrarAnunciosFase(resultado, 2);
						}
						
						// Se pide las opciones de ordenacion al usuario
						if (!esIncorrecto) {
							try {
								System.out.println("Criterios de ordenacion:");
								System.out.println("\t1. Por fecha de publicacion.");
								System.out.println("\t2. Por usuario propietario.");
								System.out.print("Seleccione el criterio de ordenacion: ");
								optionOrdenar = Integer.parseInt(sc.nextLine());
							} catch (NumberFormatException nfe) {
								System.out.print("Opcion incorrecta. ");
								System.out.print("Pulse ENTER para continuar.");
								sc.nextLine();
								optionOrdenar = -1;
							}
							
							// Si se ordena por fecha de publicacion
							if (optionOrdenar == 1) {
								gestorAnuncios.ordenarAnunciosFechaPublicacion(resultado);
								gestorAnuncios.consultarAnuncios(resultado);
								System.out.print("Pulse ENTER para continuar.");
								sc.nextLine();
							}
							// Si se ordena por usuario propietario
							else if (optionOrdenar == 2) {
								gestorAnuncios.ordenarAnunciosPropietario(resultado);
								gestorAnuncios.consultarAnuncios(resultado);
								System.out.print("Pulse ENTER para continuar.");
								sc.nextLine();
							}
						}
					} else if (optionMostrar != 0) {
						System.out.print("Opcion incorrecta. ");
						System.out.print("Pulse ENTER para continuar.");
						sc.nextLine();
					}
				} while (optionMostrar != 0);
			}
			// Mostrar mis anuncios
			else if (option == 6) {
				System.out.println("MIS ANUNCIOS:");
				resultado = gestorAnuncios.buscarAnunciosPropietario(usuario.getId());
				gestorAnuncios.consultarAnuncios(resultado);
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
			
		} while (option != 0);
		
		sc.close();
		// Al terminar se guardan los cambios realizados
		gestorAnuncios.guardarAnuncios();
	}
	
}
