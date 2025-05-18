package com.sandra.libros.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandra.libros.model.LibrosModel;
import com.sandra.libros.repository.LibrosRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




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

        
        
    }
    



