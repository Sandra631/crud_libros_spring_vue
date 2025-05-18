package com.sandra.libros.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandra.libros.model.LibrosModel;
import com.sandra.libros.repository.LibrosRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;




@RestController
@RequestMapping("/libros")
public class LibrosController {

    @Autowired
    private LibrosRepository librosRepository;

    //Metodo Get para traer todos los datos de la base de datos
    @GetMapping("/traer-libros")
    public List<LibrosModel> TraerLibros() {
        return librosRepository.findAll();
       
    }
    //Metodo para insertar un libro a la base de datos
    @PostMapping("/insertar-libros")
public LibrosModel insertarLibros(@RequestBody LibrosModel libro) {
    return librosRepository.save(libro);
}

//Metodo para editar un libro a la base de datos
    @PutMapping("/editar-libro/{id}")
public ResponseEntity<LibrosModel> editarLibro(@PathVariable Long id, @RequestBody LibrosModel libroActualizado) {
    Optional<LibrosModel> libroExistente = librosRepository.findById(id);

    if (libroExistente.isPresent()) {
        LibrosModel libro = libroExistente.get();
        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setCategoria(libroActualizado.getCategoria());
        libro.setDescripcion(libroActualizado.getDescripcion());
        libro.setImagen(libroActualizado.getImagen());

        LibrosModel libroGuardado = librosRepository.save(libro);
        return ResponseEntity.ok(libroActualizado);
    } else {
        return ResponseEntity.notFound().build();
    }
}

 // Método para eliminar un libro 
@DeleteMapping("/eliminar-libro/{id}")
public void eliminarLibro(@PathVariable Long id){
    librosRepository.deleteById(id);
}

    }
    
        
    
    



