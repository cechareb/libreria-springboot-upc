package edu.upc.sistemas.libreriaspringboot.service;

import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import edu.upc.sistemas.libreriaspringboot.repository.AutorRepository;
import edu.upc.sistemas.libreriaspringboot.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    @Autowired
    public AutorServiceImpl(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public Autor crearAutor(Autor autor)
    {
        return  autorRepository.save(autor);
    }

    @Override
    public Autor obtenerAutorPorId(Integer id)
    {
        return  autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor no encontrado con ID" + id));
    }

    @Override
    public Autor obtenerAutorConLibros(Integer id)
    {
        return autorRepository.findByIdWithLibros(id).orElseThrow(() -> new RuntimeException("Autor no encontrado con ID" + id));
    }

    @Override
    public List<Autor> listarTodos()
    {
        return autorRepository.findAll();
    }

    @Override
    public List<Autor> buscarPorNombre(String nombre)
    {
        return autorRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Autor> buscarPorNacionalidad(String nacionalidad)
    {
        return autorRepository.findByNacionalidadContainingIgnoreCase(nacionalidad);
    }

    @Override
    public Autor actualizarAutor(Integer id, Autor autorActualizar)
    {
        Autor autor = obtenerAutorPorId(id);
        autor.setNombre(autor.getNombre());
        autor.setNacionalidad(autor.getNacionalidad());
        autor.setFechaNacimiento(autor.getFechaNacimiento());
        return autorRepository.save(autor);
    }

    @Override
    public void eliminarAutor(Integer id)
    {
        Autor autor = obtenerAutorPorId(id);
        autorRepository.delete(autor);
    }

    @Override
    public List<Object[]> contarLibrosPorAutor()
    {
        return autorRepository.contarLibrosPorAutor();
    }
}
