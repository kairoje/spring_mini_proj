package com.libraries.service;

import com.libraries.exceptions.InformationExistException;
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
        GenreModel genre = genreRepository.findById(genreId);
        if(genre == null){
            throw new InformationNotFoundException("Genre not found");
        } else {
            return Optional.of(genre);
        }
    }

    public GenreModel createGenre(GenreModel genreObject) {
        GenreModel genre = genreRepository.findByName(genreObject.getName());
        if(genre != null){
            throw new InformationExistException("Genre with name " + genreObject.getName() + " already exists.");
        } else {
            return genreRepository.save(genreObject);
        }
    }

    public GenreModel updateGenre(Long genreId, GenreModel genreObject) {
        GenreModel genre = genreRepository.findById(genreId);
        if(genre == null){
            throw new InformationNotFoundException("Genre not found");
        } else {
            GenreModel updateGenre = genreRepository.findById(genreId);
            updateGenre.setName(genreObject.getName());
            updateGenre.setDescription(genreObject.getDescription());
            return genreRepository.save(updateGenre);
        }
    }

    public Optional<GenreModel> deleteGenre(Long genreId) {
        GenreModel genre = genreRepository.findById(genreId);
        if(genre != null){
            genreRepository.deleteById(String.valueOf(genreId));
            return Optional.of(genre);
        } else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found.");
        }
    }
}
