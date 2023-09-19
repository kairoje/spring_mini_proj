package com.libraries.controller;

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

    @GetMapping(path = "/genres/{genreId}/") // => http://localhost:9096/api/genres/1/
    public Optional<GenreModel> getGenre(@PathVariable(value = "genreId") Long genreId){
        return genreService.getGenre(genreId);
    }

    @PostMapping(path = "/genres/") // => http://localhost:9096/api/genres/
    public GenreModel createGenre(@RequestBody GenreModel genreObject){
        return genreService.createGenre(genreObject);
    }

}
