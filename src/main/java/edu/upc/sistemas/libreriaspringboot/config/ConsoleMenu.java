package edu.upc.sistemas.libreriaspringboot.config;

import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import edu.upc.sistemas.libreriaspringboot.entity.Libro;
import edu.upc.sistemas.libreriaspringboot.service.AutorService;
import edu.upc.sistemas.libreriaspringboot.service.LibroService;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

//@Component
public class ConsoleMenu implements CommandLineRunner {

    private final AutorService autorService;
    private final LibroService libroService;
    private final Scanner scanner;

    //@Autowired
    public ConsoleMenu(AutorService autorService, LibroService libroService) {
        this.autorService = autorService;
        this.libroService = libroService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("╔══════════════════════════════════════════════════════╗");
//        System.out.println("║     SISTEMA DE GESTIÓN DE LIBRERÍA - SPRING BOOT     ║");
//        System.out.println("║              PostgreSQL + Spring Data JPA            ║");
//        System.out.println("╚══════════════════════════════════════════════════════╝");
//
//        boolean salir = false;
//        while (!salir) {
//            mostrarMenuPrincipal();
//            int opcion = leerEntero("Seleccione una opción: ");
//
//            switch (opcion) {
//                case 1 -> menuAutores();
//                case 2 -> menuLibros();
//                case 3 -> menuReportes();
//                case 0 -> {
//                    salir = true;
//                    System.out.println("\n¡Hasta luego!");
//                }
//                default -> System.out.println("\nOpción no válida.");
//            }
//        }
//        scanner.close();

        Autor autor = new Autor("Patrick Star", "Australia", LocalDate.parse("1991-04-18", DateTimeFormatter.ISO_LOCAL_DATE));
        Autor creadoAut = autorService.crearAutor(autor);
        autor.setId(creadoAut.getId());

        Libro libro = new Libro("Lib1", "12321154546", 1999, new BigDecimal("53.68"), autor);
        Libro creadoLib = libroService.crearLibro(libro);
        System.out.println("Libro creado con ID: " + creadoLib.getId());

    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n┌────────────── MENÚ PRINCIPAL ──────────────┐");
        System.out.println("│  [1] Gestión de Autores                     │");
        System.out.println("│  [2] Gestión de Libros                      │");
        System.out.println("│  [3] Reportes y Consultas                   │");
        System.out.println("│  [0] Salir                                  │");
        System.out.println("└─────────────────────────────────────────────┘");
    }

    private void menuAutores() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n┌────────────── GESTIÓN DE AUTORES ──────────────┐");
            System.out.println("│  [1] Listar todos los autores                   │");
            System.out.println("│  [2] Buscar autor por ID                        │");
            System.out.println("│  [3] Buscar autor por nombre                    │");
            System.out.println("│  [4] Buscar autor por nacionalidad              │");
            System.out.println("│  [5] Crear nuevo autor                          │");
            System.out.println("│  [6] Actualizar autor                           │");
            System.out.println("│  [7] Eliminar autor                             │");
            System.out.println("│  [8] Ver autor con sus libros                   │");
            System.out.println("│  [0] Volver al menú principal                   │");
            System.out.println("└─────────────────────────────────────────────────┘");

            switch (leerEntero("Opción: ")) {
                case 1 -> listarAutores();
                case 2 -> buscarAutorPorId();
                case 3 -> buscarAutorPorNombre();
                case 4 -> buscarAutorPorNacionalidad();
                case 5 -> crearAutor();
                case 6 -> actualizarAutor();
                case 7 -> eliminarAutor();
                case 8 -> verAutorConLibros();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void menuLibros() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n┌────────────── GESTIÓN DE LIBROS ──────────────┐");
            System.out.println("│  [1] Listar todos los libros                   │");
            System.out.println("│  [2] Buscar libro por ID                       │");
            System.out.println("│  [3] Buscar libro por título                   │");
            System.out.println("│  [4] Buscar libros por autor                   │");
            System.out.println("│  [5] Crear nuevo libro                         │");
            System.out.println("│  [6] Actualizar libro                          │");
            System.out.println("│  [7] Eliminar libro                            │");
            System.out.println("│  [8] Buscar libros por precio mayor a          │");
            System.out.println("│  [0] Volver al menú principal                  │");
            System.out.println("└────────────────────────────────────────────────┘");

            switch (leerEntero("Opción: ")) {
                case 1 -> listarLibros();
                case 2 -> buscarLibroPorId();
                case 3 -> buscarLibroPorTitulo();
                case 4 -> buscarLibrosPorAutor();
                case 5 -> crearLibro();
                case 6 -> actualizarLibro();
                case 7 -> eliminarLibro();
                case 8 -> buscarLibrosPorPrecio();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void menuReportes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n┌────────────── REPORTES Y CONSULTAS ──────────────┐");
            System.out.println("│  [1] Contar libros por autor                      │");
            System.out.println("│  [2] Promedio de precio por autor                 │");
            System.out.println("│  [3] Libros con autor desde un año específico     │");
            System.out.println("│  [0] Volver al menú principal                     │");
            System.out.println("└───────────────────────────────────────────────────┘");

            switch (leerEntero("Opción: ")) {
                case 1 -> reporteLibrosPorAutor();
                case 2 -> reportePromedioPrecio();
                case 3 -> reporteLibrosDesdeAnio();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ==================== OPERACIONES AUTOR ====================

    private void listarAutores() {
        System.out.println("\nLISTA DE AUTORES:");
        List<Autor> autores = autorService.listarTodos();
        if (autores.isEmpty()) {
            System.out.println("   No hay autores registrados.");
        } else {
            System.out.println("┌────┬─────────────────────────┬──────────────┬──────────────┐");
            System.out.println("│ ID │ NOMBRE                  │ NACIONALIDAD │ NACIMIENTO   │");
            System.out.println("├────┼─────────────────────────┼──────────────┼──────────────┤");
            for (Autor a : autores) {
                System.out.printf("│ %2d │ %-23s │ %-12s │ %-12s │%n",
                        a.getId(), truncar(a.getNombre(), 23),
                        truncar(a.getNacionalidad(), 12),
                        a.getFechaNacimiento());
            }
            System.out.println("└────┴─────────────────────────┴──────────────┴──────────────┘");
        }
    }

    private void buscarAutorPorId() {
        int id = leerEntero("Ingrese ID del autor: ");
        try {
            Autor a = autorService.obtenerAutorPorId(id);
            System.out.println("\nAutor encontrado:");
            System.out.println(a);
        } catch (RuntimeException e) {
            System.out.println("" + e.getMessage());
        }
    }

    private void buscarAutorPorNombre() {
        System.out.print("Ingrese nombre (o parte) a buscar: ");
        String nombre = scanner.nextLine();
        List<Autor> autores = autorService.buscarPorNombre(nombre);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores.");
        } else {
            autores.forEach(a -> System.out.println("   • " + a.getNombre() + " (" + a.getNacionalidad() + ")"));
        }
    }

    private void buscarAutorPorNacionalidad() {
        System.out.print("Ingrese nacionalidad: ");
        String nacionalidad = scanner.nextLine();
        List<Autor> autores = autorService.buscarPorNacionalidad(nacionalidad);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores.");
        } else {
            autores.forEach(a -> System.out.println("   • " + a.getNombre()));
        }
    }

    private void crearAutor() {
        System.out.println("\nCREAR NUEVO AUTOR:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nacionalidad: ");
        String nacionalidad = scanner.nextLine();
        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);

        Autor autor = new Autor(nombre, nacionalidad, fecha);
        Autor creado = autorService.crearAutor(autor);
        System.out.println("Autor creado con ID: " + creado.getId());
    }

    private void actualizarAutor() {
        int id = leerEntero("ID del autor a actualizar: ");
        try {
            Autor existente = autorService.obtenerAutorPorId(id);
            System.out.println("Autor actual: " + existente.getNombre());

            System.out.print("Nuevo nombre (Enter para mantener '" + existente.getNombre() + "'): ");
            String nombre = scanner.nextLine();
            System.out.print("Nueva nacionalidad (Enter para mantener '" + existente.getNacionalidad() + "'): ");
            String nacionalidad = scanner.nextLine();
            System.out.print("Nueva fecha (YYYY-MM-DD, Enter para mantener '" + existente.getFechaNacimiento() + "'): ");
            String fechaStr = scanner.nextLine();

            Autor actualizado = new Autor();
            actualizado.setNombre(nombre.isEmpty() ? existente.getNombre() : nombre);
            actualizado.setNacionalidad(nacionalidad.isEmpty() ? existente.getNacionalidad() : nacionalidad);
            actualizado.setFechaNacimiento(fechaStr.isEmpty() ? existente.getFechaNacimiento() :
                    LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE));

            Autor resultado = autorService.actualizarAutor(id, actualizado);
            System.out.println("Autor actualizado: " + resultado.getNombre());
        } catch (RuntimeException e) {
            System.out.println("" + e.getMessage());
        }
    }

    private void eliminarAutor() {
        int id = leerEntero("ID del autor a eliminar: ");
        System.out.print("Se eliminarán también todos sus libros. ¿Confirmar? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            try {
                autorService.eliminarAutor(id);
                System.out.println("Autor eliminado.");
            } catch (RuntimeException e) {
                System.out.println("" + e.getMessage());
            }
        }
    }

    private void verAutorConLibros() {
        int id = leerEntero("ID del autor: ");
        try {
            Autor a = autorService.obtenerAutorConLibros(id);
            System.out.println("\nAutor: " + a.getNombre());
            System.out.println("   Libros (" + a.getLibros().size() + "):");
            for (Libro l : a.getLibros()) {
                System.out.println("      • " + l.getTitulo() + " (" + l.getAnioPublicacion() + ") - S/ " + l.getPrecio());
            }
        } catch (RuntimeException e) {
            System.out.println("" + e.getMessage());
        }
    }

    // ==================== OPERACIONES LIBRO ====================

    private void listarLibros() {
        System.out.println("\nLISTA DE LIBROS:");
        List<Libro> libros = libroService.listarTodos();
        if (libros.isEmpty()) {
            System.out.println("   No hay libros registrados.");
        } else {
            System.out.println("┌────┬──────────────────────────┬─────────────────┬──────┬──────────┬─────────────────┐");
            System.out.println("│ ID │ TÍTULO                   │ ISBN            │ AÑO  │ PRECIO   │ AUTOR           │");
            System.out.println("├────┼──────────────────────────┼─────────────────┼──────┼──────────┼─────────────────┤");
            for (Libro l : libros) {
                System.out.printf("│ %2d │ %-24s │ %-15s │ %4d │ %8.2f │ %-15s │%n",
                        l.getId(), truncar(l.getTitulo(), 24),
                        truncar(l.getIsbn(), 15),
                        l.getAnioPublicacion(), l.getPrecio(),
                        truncar(l.getAutor().getNombre(), 15));
            }
            System.out.println("└────┴──────────────────────────┴─────────────────┴──────┴──────────┴─────────────────┘");
        }
    }

    private void buscarLibroPorId() {
        int id = leerEntero("ID del libro: ");
        try {
            Libro l = libroService.obtenerLibroPorId(id);
            System.out.println("\nLibro encontrado:\n" + l);
        } catch (RuntimeException e) {
            System.out.println("" + e.getMessage());
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Título (o parte) a buscar: ");
        String titulo = scanner.nextLine();
        List<Libro> libros = libroService.buscarPorTitulo(titulo);
        libros.forEach(l -> System.out.println("   • " + l.getTitulo() + " - " + l.getAutor().getNombre()));
    }

    private void buscarLibrosPorAutor() {
        listarAutores();
        int autorId = leerEntero("ID del autor: ");
        List<Libro> libros = libroService.buscarPorAutor(autorId);
        if (libros.isEmpty()) {
            System.out.println("Este autor no tiene libros registrados.");
        } else {
            libros.forEach(l -> System.out.println("   • " + l.getTitulo() + " (" + l.getAnioPublicacion() + ")"));
        }
    }

    private void crearLibro() {
        System.out.println("\nCREAR NUEVO LIBRO:");
        listarAutores();
        int autorId = leerEntero("ID del autor: ");

        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        int anio = leerEntero("Año de publicación: ");
        System.out.print("Precio: ");
        BigDecimal precio = new BigDecimal(scanner.nextLine());

        Autor autor = new Autor();
        autor.setId(autorId);

        Libro libro = new Libro(titulo, isbn, anio, precio, autor);
        Libro creado = libroService.crearLibro(libro);
        System.out.println("Libro creado con ID: " + creado.getId());
    }

    private void actualizarLibro() {
        int id = leerEntero("ID del libro a actualizar: ");
        try {
            Libro existente = libroService.obtenerLibroPorId(id);
            System.out.println("Libro actual: " + existente.getTitulo());

            System.out.print("Nuevo título (Enter para mantener): ");
            String titulo = scanner.nextLine();
            System.out.print("Nuevo ISBN (Enter para mantener): ");
            String isbn = scanner.nextLine();
            System.out.print("Nuevo año (Enter para mantener " + existente.getAnioPublicacion() + "): ");
            String anioStr = scanner.nextLine();
            System.out.print("Nuevo precio (Enter para mantener " + existente.getPrecio() + "): ");
            String precioStr = scanner.nextLine();

            listarAutores();
            System.out.print("Nuevo autor ID (Enter para mantener " + existente.getAutor().getId() + "): ");
            String autorIdStr = scanner.nextLine();

            Libro actualizado = new Libro();
            actualizado.setTitulo(titulo.isEmpty() ? existente.getTitulo() : titulo);
            actualizado.setIsbn(isbn.isEmpty() ? existente.getIsbn() : isbn);
            actualizado.setAnioPublicacion(anioStr.isEmpty() ? existente.getAnioPublicacion() : Integer.parseInt(anioStr));
            actualizado.setPrecio(precioStr.isEmpty() ? existente.getPrecio() : new BigDecimal(precioStr));

            if (!autorIdStr.isEmpty()) {
                Autor nuevoAutor = new Autor();
                nuevoAutor.setId(Integer.parseInt(autorIdStr));
                actualizado.setAutor(nuevoAutor);
            }

            Libro resultado = libroService.actualizarLibro(id, actualizado);
            System.out.println("Libro actualizado: " + resultado.getTitulo());
        } catch (RuntimeException e) {
            System.out.println("" + e.getMessage());
        }
    }

    private void eliminarLibro() {
        int id = leerEntero("ID del libro a eliminar: ");
        System.out.print("¿Confirmar eliminación? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            try {
                libroService.eliminarLibro(id);
                System.out.println("Libro eliminado.");
            } catch (RuntimeException e) {
                System.out.println("" + e.getMessage());
            }
        }
    }

    private void buscarLibrosPorPrecio() {
        System.out.print("Precio mínimo: ");
        BigDecimal precio = new BigDecimal(scanner.nextLine());
        List<Libro> libros = libroService.buscarPorPrecioMayorA(precio);
        libros.forEach(l -> System.out.println("   • " + l.getTitulo() + " - S/ " + l.getPrecio()));
    }

    // ==================== REPORTES ====================

    private void reporteLibrosPorAutor() {
        System.out.println("\nLIBROS POR AUTOR:");
        List<Object[]> resultado = autorService.contarLibrosPorAutor();
        System.out.println("┌─────────────────────────┬─────────────┐");
        System.out.println("│ AUTOR                   │ N° LIBROS   │");
        System.out.println("├─────────────────────────┼─────────────┤");
        for (Object[] fila : resultado) {
            System.out.printf("│ %-23s │ %11d │%n", fila[0], fila[1]);
        }
        System.out.println("└─────────────────────────┴─────────────┘");
    }

    private void reportePromedioPrecio() {
        System.out.println("\nPROMEDIO DE PRECIO POR AUTOR:");
        List<Object[]> resultado = libroService.promedioPrecioPorAutor();
        System.out.println("┌─────────────────────────┬─────────────┐");
        System.out.println("│ AUTOR                   │ PROMEDIO    │");
        System.out.println("├─────────────────────────┼─────────────┤");
        for (Object[] fila : resultado) {
            System.out.printf("│ %-23s │ %11.2f │%n", fila[0], fila[1]);
        }
        System.out.println("└─────────────────────────┴─────────────┘");
    }

    private void reporteLibrosDesdeAnio() {
        int anio = leerEntero("Año mínimo de publicación: ");
        List<Object[]> resultado = libroService.librosConAutorDesdeAnio(anio);
        System.out.println("\nLIBROS DESDE " + anio + ":");
        for (Object[] fila : resultado) {
            System.out.printf("   • %s | %s | %s | S/ %s | %s (%s)%n",
                    fila[0], fila[1], fila[2], fila[4], fila[6], fila[7]);
        }
    }

    // ==================== UTILIDADES ====================

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private String truncar(String texto, int maximo) {
        if (texto == null) return "";
        return texto.length() > maximo ? texto.substring(0, maximo - 3) + "..." : texto;
    }

}
