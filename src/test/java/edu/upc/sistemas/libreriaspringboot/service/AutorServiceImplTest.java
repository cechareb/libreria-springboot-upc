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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para AutorServiceImpl.
 * Se mockean los repositorios para aislar la lógica de negocio.
 */
@ExtendWith(MockitoExtension.class)
class AutorServiceImplTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private AutorServiceImpl autorService;

    private Autor autorBase;

    @BeforeEach
    void setUp() {
        // Arrange: preparar un autor base reutilizable
        autorBase = new Autor();
        autorBase.setId(1);
        autorBase.setNombre("Gabriel García Márquez");
        autorBase.setNacionalidad("Colombiana");
        autorBase.setFechaNacimiento(LocalDate.of(1927, 3, 6));
    }

    // ============================================================
    // TESTS PARA crearAutor
    // ============================================================

    @Test
    @DisplayName("Crear autor: debe retornar el autor guardado con ID asignado")
    void crearAutor_DebeRetornarAutorGuardado() {
        // Arrange
        Autor autorNuevo = new Autor();
        autorNuevo.setNombre("Mario Vargas Llosa");
        autorNuevo.setNacionalidad("Peruana");
        autorNuevo.setFechaNacimiento(LocalDate.of(1936, 3, 28));

        given(autorRepository.save(any(Autor.class))).willReturn(autorBase);

        // Act
        Autor resultado = autorService.crearAutor(autorNuevo);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Gabriel García Márquez", resultado.getNombre());
        verify(autorRepository, times(1)).save(autorNuevo);
    }

    // ============================================================
    // TESTS PARA obtenerAutorPorId
    // ============================================================

    @Test
    @DisplayName("Obtener autor por ID: debe retornar autor cuando existe")
    void obtenerAutorPorId_CuandoExiste_DebeRetornarAutor() {
        // Arrange
        given(autorRepository.findById(1)).willReturn(Optional.of(autorBase));

        // Act
        Autor resultado = autorService.obtenerAutorPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Gabriel García Márquez", resultado.getNombre());
        verify(autorRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Obtener autor por ID: debe lanzar RuntimeException cuando no existe")
    void obtenerAutorPorId_CuandoNoExiste_DebeLanzarExcepcion() {
        // Arrange
        given(autorRepository.findById(99)).willReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            autorService.obtenerAutorPorId(99);
        });

        assertEquals("Autor no encontrado con ID: 99", ex.getMessage());
        verify(autorRepository, times(1)).findById(99);
    }

    // ============================================================
    // TESTS PARA obtenerAutorConLibros
    // ============================================================

    @Test
    @DisplayName("Obtener autor con libros: debe retornar autor con lista de libros cargada")
    void obtenerAutorConLibros_CuandoExiste_DebeRetornarAutorConLibros() {
        // Arrange
        Libro libro1 = new Libro();
        libro1.setId(10);
        libro1.setTitulo("Cien años de soledad");

        Libro libro2 = new Libro();
        libro2.setId(11);
        libro2.setTitulo("El amor en los tiempos del cólera");

        autorBase.setLibros(Arrays.asList(libro1, libro2));

        given(autorRepository.findByIdWithLibros(1)).willReturn(Optional.of(autorBase));

        // Act
        Autor resultado = autorService.obtenerAutorConLibros(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getLibros().size());
        verify(autorRepository, times(1)).findByIdWithLibros(1);
    }

    // ============================================================
    // TESTS PARA listarTodos
    // ============================================================

    @Test
    @DisplayName("Listar todos: debe retornar lista completa de autores")
    void listarTodos_DebeRetornarListaDeAutores() {
        // Arrange
        Autor autor2 = new Autor();
        autor2.setId(2);
        autor2.setNombre("Julio Cortázar");

        given(autorRepository.findAll()).willReturn(Arrays.asList(autorBase, autor2));

        // Act
        List<Autor> resultado = autorService.listarTodos();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Gabriel García Márquez", resultado.get(0).getNombre());
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Listar todos: debe retornar lista vacía cuando no hay autores")
    void listarTodos_CuandoVacio_DebeRetornarListaVacia() {
        // Arrange
        given(autorRepository.findAll()).willReturn(Collections.emptyList());

        // Act
        List<Autor> resultado = autorService.listarTodos();

        // Assert
        assertTrue(resultado.isEmpty());
        verify(autorRepository, times(1)).findAll();
    }

    // ============================================================
    // TESTS PARA buscarPorNombre y buscarPorNacionalidad
    // ============================================================

    @Test
    @DisplayName("Buscar por nombre: debe retornar autores que contengan el texto")
    void buscarPorNombre_DebeRetornarCoincidencias() {
        // Arrange
        given(autorRepository.findByNombreContainingIgnoreCase("garcia"))
                .willReturn(Collections.singletonList(autorBase));

        // Act
        List<Autor> resultado = autorService.buscarPorNombre("garcia");

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Gabriel García Márquez", resultado.get(0).getNombre());
        verify(autorRepository, times(1)).findByNombreContainingIgnoreCase("garcia");
    }

    @Test
    @DisplayName("Buscar por nacionalidad: debe retornar autores de dicha nacionalidad")
    void buscarPorNacionalidad_DebeRetornarCoincidencias() {
        // Arrange
        given(autorRepository.findByNacionalidadIgnoreCase("Colombiana"))
                .willReturn(Collections.singletonList(autorBase));

        // Act
        List<Autor> resultado = autorService.buscarPorNacionalidad("Colombiana");

        // Assert
        assertEquals(1, resultado.size());
        verify(autorRepository, times(1)).findByNacionalidadIgnoreCase("Colombiana");
    }

    // ============================================================
    // TESTS PARA actualizarAutor
    // ============================================================

    @Test
    @DisplayName("Actualizar autor: debe modificar datos y retornar autor actualizado")
    void actualizarAutor_CuandoExiste_DebeRetornarAutorActualizado() {
        // Arrange
        Autor datosNuevos = new Autor();
        datosNuevos.setNombre("Gabo");
        datosNuevos.setNacionalidad("Colombo-venezolana");
        datosNuevos.setFechaNacimiento(LocalDate.of(1927, 3, 6));

        given(autorRepository.findById(1)).willReturn(Optional.of(autorBase));
        given(autorRepository.save(any(Autor.class))).willAnswer(invocation -> invocation.getArgument(0));

        // Act
        Autor resultado = autorService.actualizarAutor(1, datosNuevos);

        // Assert
        assertEquals("Gabo", resultado.getNombre());
        assertEquals("Colombo-venezolana", resultado.getNacionalidad());
        verify(autorRepository, times(1)).findById(1);
        verify(autorRepository, times(1)).save(autorBase);
    }

    @Test
    @DisplayName("Actualizar autor: debe lanzar excepción cuando el autor no existe")
    void actualizarAutor_CuandoNoExiste_DebeLanzarExcepcion() {
        // Arrange
        given(autorRepository.findById(99)).willReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            autorService.actualizarAutor(99, new Autor());
        });
        verify(autorRepository, times(1)).findById(99);
        verify(autorRepository, never()).save(any());
    }

    // ============================================================
    // TESTS PARA eliminarAutor
    // ============================================================

    @Test
    @DisplayName("Eliminar autor: debe eliminar cuando existe")
    void eliminarAutor_CuandoExiste_DebeEliminar() {
        // Arrange
        given(autorRepository.findById(1)).willReturn(Optional.of(autorBase));
        doNothing().when(autorRepository).delete(autorBase);

        // Act
        assertDoesNotThrow(() -> autorService.eliminarAutor(1));

        // Assert
        verify(autorRepository, times(1)).findById(1);
        verify(autorRepository, times(1)).delete(autorBase);
    }

    @Test
    @DisplayName("Eliminar autor: debe lanzar excepción cuando no existe")
    void eliminarAutor_CuandoNoExiste_DebeLanzarExcepcion() {
        // Arrange
        given(autorRepository.findById(99)).willReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> autorService.eliminarAutor(99));
        verify(autorRepository, times(1)).findById(99);
        verify(autorRepository, never()).delete(any());
    }

    // ============================================================
    // TESTS PARA contarLibrosPorAutor (reporte)
    // ============================================================

    @Test
    @DisplayName("Contar libros por autor: debe retornar lista de objetos con conteo")
    void contarLibrosPorAutor_DebeRetornarReporte() {
        // Arrange
        Object[] fila1 = new Object[]{"Gabriel García Márquez", 2L};
        Object[] fila2 = new Object[]{"Julio Cortázar", 1L};
        given(autorRepository.contarLibrosPorAutor()).willReturn(Arrays.asList(fila1, fila2));

        // Act
        List<Object[]> resultado = autorService.contarLibrosPorAutor();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Gabriel García Márquez", resultado.get(0)[0]);
        assertEquals(2L, resultado.get(0)[1]);
        verify(autorRepository, times(1)).contarLibrosPorAutor();
    }
}