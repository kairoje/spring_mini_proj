package com.libraries.controller;

import com.libraries.model.BookModel;
import com.libraries.model.GenreModel;
import com.libraries.service.GenreService;
import org.ietf.jgss.GSSName;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(path = "/genres/") // => http://localhost:9096/api/genres/
    public List<GenreModel> getGenres() { return genreService.getGenres(); }

    @GetMapping(path = "/genres/{genreId}/") // => http://localhost:9096/api/genres/{genreId}/
    public Optional<GenreModel> getGenre(@PathVariable(value = "genreId") Long genreId){
        return genreService.getGenre(genreId);
    }

    @PostMapping(path = "/genres/") // => http://localhost:9096/api/genres/
    public GenreModel createGenre(@RequestBody GenreModel genreObject){
        return genreService.createGenre(genreObject);
    }

    @PutMapping(path = "/genres/{genreId}/") // => http://localhost:9096/api/genres/{genreId}/
    public GenreModel updateGenre(@PathVariable(value = "genreId") Long genreId, @RequestBody GenreModel genre){
        return genreService.updateGenre(genreId, genre);
    }

    @DeleteMapping(path = "/genres/{genreId}/") // => http://localhost:9096/api/genres/{genreId}/
    public Optional<GenreModel> deleteGenre(@PathVariable(value = "genreId") Long genreId){
        return genreService.deleteGenre(genreId);
    }

    @GetMapping(path = "/genres/{genreId}/books/")
    public List<BookModel> getGenreBooks(@PathVariable(value = "genreId") Long genreId){
        return genreService.getGenreBooks(genreId);
    }


}
