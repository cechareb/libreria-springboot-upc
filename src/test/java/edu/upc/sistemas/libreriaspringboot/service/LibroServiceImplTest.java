package edu.upc.sistemas.libreriaspringboot.service;

import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import edu.upc.sistemas.libreriaspringboot.entity.Libro;
import edu.upc.sistemas.libreriaspringboot.repository.AutorRepository;
import edu.upc.sistemas.libreriaspringboot.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para LibroServiceImpl.
 * Se valida la lógica de negocio: creación con autor, búsquedas, reportes.
 */
@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    private Autor autorBase;
    private Libro libroBase;

    @BeforeEach
    void setUp() {
        // Arrange: autor y libro base para reutilizar
        autorBase = new Autor();
        autorBase.setId(1);
        autorBase.setNombre("Gabriel García Márquez");

        libroBase = new Libro();
        libroBase.setId(10);
        libroBase.setTitulo("Cien años de soledad");
        libroBase.setIsbn("978-0307474728");
        libroBase.setAnioPublicacion(1967);
        libroBase.setPrecio(new BigDecimal("45.50"));
        libroBase.setAutor(autorBase);
    }

    // ============================================================
    // TESTS PARA crearLibro
    // ============================================================

    @Test
    @DisplayName("Crear libro: debe guardar cuando el autor existe y es válido")
    void crearLibro_CuandoAutorExiste_DebeGuardarLibro() {
        // Arrange
        Libro nuevo = new Libro();
        nuevo.setTitulo("El amor en los tiempos del cólera");
        nuevo.setIsbn("978-0307389732");
        nuevo.setAnioPublicacion(1985);
        nuevo.setPrecio(new BigDecimal("38.00"));
        nuevo.setAutor(autorBase); // autor con ID 1

        given(autorRepository.findById(1)).willReturn(Optional.of(autorBase));
        given(libroRepository.save(any(Libro.class))).willReturn(libroBase);

        // Act
        Libro resultado = libroService.crearLibro(nuevo);

        // Assert
        assertNotNull(resultado);
        assertEquals(10, resultado.getId());
        verify(autorRepository, times(1)).findById(1);
        verify(libroRepository, times(1)).save(nuevo);
    }

    @Test
    @DisplayName("Crear libro: debe lanzar excepción cuando no tiene autor")
    void crearLibro_CuandoSinAutor_DebeLanzarExcepcion() {
        // Arrange
        Libro nuevo = new Libro();
        nuevo.setTitulo("Sin autor");
        nuevo.setAutor(null);

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            libroService.crearLibro(nuevo);
        });

        assertEquals("El libro debe tener un autor válido", ex.getMessage());
        verify(autorRepository, never()).findById(any());
        verify(libroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Crear libro: debe lanzar excepción cuando el autor no existe en BD")
    void crearLibro_CuandoAutorNoExiste_DebeLanzarExcepcion() {
        // Arrange
        Libro nuevo = new Libro();
        nuevo.setTitulo("Libro huérfano");
        nuevo.setAutor(autorBase); // ID 1

        given(autorRepository.findById(1)).willReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            libroService.crearLibro(nuevo);
        });

        assertEquals("Autor no encontrado", ex.getMessage());
        verify(autorRepository, times(1)).findById(1);
        verify(libroRepository, never()).save(any());
    }

    // ============================================================
    // TESTS PARA obtenerLibroPorId
    // ============================================================

    @Test
    @DisplayName("Obtener libro por ID: debe retornar libro cuando existe")
    void obtenerLibroPorId_CuandoExiste_DebeRetornarLibro() {
        // Arrange
        given(libroRepository.findById(10)).willReturn(Optional.of(libroBase));

        // Act
        Libro resultado = libroService.obtenerLibroPorId(10);

        // Assert
        assertNotNull(resultado);
        assertEquals("Cien años de soledad", resultado.getTitulo());
        verify(libroRepository, times(1)).findById(10);
    }

    @Test
    @DisplayName("Obtener libro por ID: debe lanzar excepción cuando no existe")
    void obtenerLibroPorId_CuandoNoExiste_DebeLanzarExcepcion() {
        // Arrange
        given(libroRepository.findById(99)).willReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            libroService.obtenerLibroPorId(99);
        });

        assertEquals("Libro no encontrado con ID: 99", ex.getMessage());
    }

    // ============================================================
    // TESTS PARA búsquedas
    // ============================================================

    @Test
    @DisplayName("Buscar por título: debe retornar coincidencias parciales")
    void buscarPorTitulo_DebeRetornarCoincidencias() {
        // Arrange
        given(libroRepository.findByTituloContainingIgnoreCase("soledad"))
                .willReturn(Collections.singletonList(libroBase));

        // Act
        List<Libro> resultado = libroService.buscarPorTitulo("soledad");

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Cien años de soledad", resultado.get(0).getTitulo());
    }

    @Test
    @DisplayName("Buscar por autor: debe retornar libros del autor indicado")
    void buscarPorAutor_DebeRetornarLibrosDeAutor() {
        // Arrange
        given(libroRepository.findByAutorId(1)).willReturn(Collections.singletonList(libroBase));

        // Act
        List<Libro> resultado = libroService.buscarPorAutor(1);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Gabriel García Márquez", resultado.get(0).getAutor().getNombre());
    }

    @Test
    @DisplayName("Buscar por precio mayor a: debe retornar libros caros")
    void buscarPorPrecioMayorA_DebeRetornarLibrosFiltrados() {
        // Arrange
        Libro libroCaro = new Libro();
        libroCaro.setId(20);
        libroCaro.setTitulo("Libro caro");
        libroCaro.setPrecio(new BigDecimal("100.00"));

        BigDecimal umbral = new BigDecimal("40.00");
        given(libroRepository.findByPrecioGreaterThan(umbral))
                .willReturn(Arrays.asList(libroBase, libroCaro));

        // Act
        List<Libro> resultado = libroService.buscarPorPrecioMayorA(umbral);

        // Assert
        assertEquals(2, resultado.size());
        verify(libroRepository, times(1)).findByPrecioGreaterThan(umbral);
    }

    // ============================================================
    // TESTS PARA actualizarLibro
    // ============================================================

    @Test
    @DisplayName("Actualizar libro: debe modificar datos básicos")
    void actualizarLibro_CuandoExiste_DebeModificarDatos() {
        // Arrange
        Libro datosNuevos = new Libro();
        datosNuevos.setTitulo("Cien años de soledad - Edición especial");
        datosNuevos.setIsbn("978-NEW");
        datosNuevos.setAnioPublicacion(2020);
        datosNuevos.setPrecio(new BigDecimal("60.00"));
        // No cambiamos autor (null)

        given(libroRepository.findById(10)).willReturn(Optional.of(libroBase));
        given(libroRepository.save(any(Libro.class))).willAnswer(invocation -> invocation.getArgument(0));

        // Act
        Libro resultado = libroService.actualizarLibro(10, datosNuevos);

        // Assert
        assertEquals("Cien años de soledad - Edición especial", resultado.getTitulo());
        assertEquals(new BigDecimal("60.00"), resultado.getPrecio());
        assertEquals("Gabriel García Márquez", resultado.getAutor().getNombre()); // autor original
        verify(libroRepository, times(1)).save(libroBase);
    }

    @Test
    @DisplayName("Actualizar libro: debe cambiar de autor cuando se indica nuevo autor válido")
    void actualizarLibro_CuandoCambiaAutor_DebeActualizarAutor() {
        // Arrange
        Autor nuevoAutor = new Autor();
        nuevoAutor.setId(2);
        nuevoAutor.setNombre("Julio Cortázar");

        Libro datosNuevos = new Libro();
        datosNuevos.setTitulo("Rayuela");
        datosNuevos.setAutor(nuevoAutor); // nuevo autor ID 2

        given(libroRepository.findById(10)).willReturn(Optional.of(libroBase));
        given(autorRepository.findById(2)).willReturn(Optional.of(nuevoAutor));
        given(libroRepository.save(any(Libro.class))).willAnswer(invocation -> invocation.getArgument(0));

        // Act
        Libro resultado = libroService.actualizarLibro(10, datosNuevos);

        // Assert
        assertEquals("Julio Cortázar", resultado.getAutor().getNombre());
        verify(autorRepository, times(1)).findById(2);
    }

    @Test
    @DisplayName("Actualizar libro: debe lanzar excepción cuando libro no existe")
    void actualizarLibro_CuandoNoExiste_DebeLanzarExcepcion() {
        // Arrange
        given(libroRepository.findById(99)).willReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            libroService.actualizarLibro(99, new Libro());
        });
        verify(libroRepository, never()).save(any());
    }

    // ============================================================
    // TESTS PARA eliminarLibro
    // ============================================================

    @Test
    @DisplayName("Eliminar libro: debe eliminar cuando existe")
    void eliminarLibro_CuandoExiste_DebeEliminar() {
        // Arrange
        given(libroRepository.findById(10)).willReturn(Optional.of(libroBase));
        doNothing().when(libroRepository).delete(libroBase);

        // Act & Assert
        assertDoesNotThrow(() -> libroService.eliminarLibro(10));

        verify(libroRepository, times(1)).findById(10);
        verify(libroRepository, times(1)).delete(libroBase);
    }

    // ============================================================
    // TESTS PARA reportes
    // ============================================================

    @Test
    @DisplayName("Promedio precio por autor: debe retornar lista de promedios")
    void promedioPrecioPorAutor_DebeRetornarReporte() {
        // Arrange
        Object[] fila = new Object[]{"Gabriel García Márquez", new BigDecimal("42.00")};
        given(libroRepository.promedioPrecioPorAutor()).willReturn(Collections.singletonList(fila));

        // Act
        List<Object[]> resultado = libroService.promedioPrecioPorAutor();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Gabriel García Márquez", resultado.get(0)[0]);
        assertEquals(new BigDecimal("42.00"), resultado.get(0)[1]);
    }

    @Test
    @DisplayName("Libros con autor desde año: debe retornar resultados nativos")
    void librosConAutorDesdeAnio_DebeRetornarResultados() {
        // Arrange
        Object[] fila = new Object[]{10, "Cien años de soledad", "978-0307474728", 1967, new BigDecimal("45.50"), 1, "Gabriel García Márquez", "Colombiana"};
        given(libroRepository.findLibrosConAutorDesdeAnio(1960)).willReturn(Collections.singletonList(fila));

        // Act
        List<Object[]> resultado = libroService.librosConAutorDesdeAnio(1960);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Cien años de soledad", resultado.get(0)[1]);
        verify(libroRepository, times(1)).findLibrosConAutorDesdeAnio(1960);
    }
}