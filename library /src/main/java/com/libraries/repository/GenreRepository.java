package com.libraries.repository;

import com.libraries.model.BookModel;
import com.libraries.model.GenreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreModel, String>{

    GenreModel findByName(String genreName);

    GenreModel findByIdAndUserId(Long categoryId, Long userId);

    List<GenreModel> findByUserId(Long id);

}
