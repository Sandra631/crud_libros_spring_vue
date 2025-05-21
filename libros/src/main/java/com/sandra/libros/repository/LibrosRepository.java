package com.sandra.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sandra.libros.model.LibrosModel;

public interface LibrosRepository extends JpaRepository<LibrosModel, Long> {

}
