package com.libraries.repository;

import com.libraries.model.GenreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreModel, String>{

    GenreModel findById(Long genreId);

    GenreModel findByName(String genreName);
}
