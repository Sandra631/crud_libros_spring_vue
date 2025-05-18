package com.sandra.libros.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandra.libros.model.LibrosModel;
import com.sandra.libros.repository.LibrosRepository;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/libros")
public class LibrosController {

    @Autowired
    private LibrosRepository librosRepository;

    @GetMapping("/traer-libros")
    public List<LibrosModel> TraerLibros() {
        return librosRepository.findAll();
       
    }
    



}
