package com.libraries.service;

import com.libraries.exceptions.InformationNotFoundException;
import com.libraries.model.GenreModel;
import com.libraries.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            throw new InformationNotFoundException("No genres found.");
        } else {
            return genreRepository.findAll();
        }
    }

    public Optional<GenreModel> getGenre(Long genreId){
        GenreModel genre = GenreRepository.findById(genreId);
        if(genre == null){
            throw new InformationNotFoundException("Genre not found");
        } else {
            return Optional.of(genre);
        }
    }
}
