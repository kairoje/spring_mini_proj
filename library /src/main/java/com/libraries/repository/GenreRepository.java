package com.libraries.repository;

import com.libraries.model.BookModel;
import com.libraries.model.GenreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreModel, String>{

    /**
     * For finding genre by name
     * @param genreName
     *
     */
    GenreModel findByName(String genreName);

    /**
     * Find genre by genre id and user id
     * @param categoryId
     * @param userId
     *
     */
    GenreModel findByIdAndUserId(Long categoryId, Long userId);

    /**
     * Find the list of user's genres with the user id
     * @param id
     *
     */
    List<GenreModel> findByUserId(Long id);

}
