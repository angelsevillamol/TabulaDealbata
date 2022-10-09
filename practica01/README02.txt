PRACTICA 01 ejercicio 2

INSTRUCCIONES DE CONFIGURACION:
	-Predeterminadamente las rutas de los ficheros de datos seran el directorio "data" de la carpeta raiz del proyecto.
	-En la carpeta raiz hay un ficher config.properties, el cual contiene las rutas de los ficheros de datos. Si se desea cambiar dichas rutas simplemente habria que cambiar la ruta en dicho fichero
	-Importante: No cambiar las claves de config.properties
	-los datos de contactos se leen con el siguiente formato: id;nombre;apellidos;correo;fecha_de_nacimiento;[intereses],[intereses]...
	-los datos de anuncios generales se leen con el siguiente formato: id;id_propietario;fase;Fecha_de_publicacion;titulo;cuerpo
	-los datos de anuncios tematicosse leen con el siguiente formato: id;id_propietario;fase;Fecha_de_publicacion;[intereses],[intereses],,,[intereses];titulo;cuerpo
	-los datos de anuncios individualizados se leen con el siguiente formato: id;id_propietario;fase;Fecha_de_publicacion;[id_objetivo],[id_objetivo],,,[id_objetivo];titulo;cuerpo
	-los datos de anuncios flashse leen con el siguiente formato: id;id_propietario;fase;Fecha_de_publicacion;Fecha_de_finalizacion;titulo;cuerpo

INSTRUCCIONES DE INSTALACION:
	-El programa necesita que el sistema tenga una version de java superior a la 7.0.
	-No hace falta instalar nada del proyecto, solo ejecutar los respectivos programas.

INSTRUCCIONES DE OPERACION:
	-El programa consta de 2 ejecutables: ContactosMainProgram.jar y AnunciosMainProgram.jar.
	-Con el segundo podra ver, gestionar e interactuar con anuncios creados por usuarios de la agenda, relativo al ejercicio 2.
	-Para su ejecucion debera utilizar una consola. (java -jar AnunciosMainProgram.jar)
	-Ejemplo de ejecucion en consola de windows seria:  C:\Users\Juan Pedro\Desktop\practica01>java -jar AnunciosMainProgram.jar

DESARROLLADORES:
	-Ángel Sevilla Molina (i42semoa@uco.es)
	-Juan Pedro Muñoz Jiménez (p52mujij@uco.es)
	
PROBLEMAS CONOCIDOS:
	-Si se realizan cambios en los ficheros de anuncios, los cuales no sean apropiados dara en errores de cargado y guardado.
	-Si no se respeta el formato de los ficheros de datos se ocasionaran errores de cargado y guardado.
	-Los anuncios se publican al dia siguiente de la fecha de publicacion.
	-Si un Anuncio individualizado tiene 1 o menos objetivos, dara error al cargar/guardar.
