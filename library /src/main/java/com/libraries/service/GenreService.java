package com.libraries.service;

import com.libraries.model.GenreModel;
import com.libraries.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private GenreRepository genreRepository;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreModel> getGenres(){
        return genreRepository.findAll();
    }
}
