package com.libraries.service;

import com.libraries.exceptions.InformationNotFoundException;
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
        List<GenreModel> genreList = genreRepository.findAll();
        if (genreList.isEmpty()){
            throw new InformationNotFoundException("Genre not found.");
        } else {
            return genreRepository.findAll();
        }
    }
}
