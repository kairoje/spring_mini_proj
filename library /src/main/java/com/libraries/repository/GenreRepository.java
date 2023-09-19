package com.libraries.repository;

import com.libraries.model.GenreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreModel, Long>{

    GenreModel findByName(String genreModelName);
}
