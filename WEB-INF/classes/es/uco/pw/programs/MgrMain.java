/**
 * Aplicacion que maneja la gestion de usuarios y anuncios.
 *
 * @author Mu�oz Jimenez, Juan Pedro (p52mujij@uco.es)
 * @author Sevilla Molina, Angel (i42semoa@uco.es)
 */

package es.uco.pw.programs;

import java.util.Scanner;

public class MgrMain {
	
	private static Scanner sc;
	
	/**
	 * Solicita al usuario que pulse enter para continuar.
	 */
	private static void pressEnter() {
		System.out.print("Pulse ENTER para continuar.");
		sc.nextLine();
	}
	
	/**
	 * Imprime el menu principal y solicita al usuario una opcion.
	 * 
	 * @return  la opcion seleccionada por el usuario.
	 */
	private static int printMenu() {
		int option = -1;
		
		System.out.println("MENU DE OPCIONES:");
		System.out.println("\t1. Gestión de usuarios.");
		System.out.println("\t2. Gestión de anuncios.");
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
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		int option;
		
		do {
			// Imprime menu
			option = printMenu();
			
			// Gestor de usuarios
			if (option == 1) {
				UserMgrMenu.menu();
			} 
			// Gestor de anuncios
			else if (option == 2) {
				BulletinMgrMenu.menu();
			} 
			// Opcion no definida
			else if (option < 0 || option > 2) {
				System.out.print("Opcion incorrecta. ");
				pressEnter();
			}
		} while (option != 0);
	}
}
