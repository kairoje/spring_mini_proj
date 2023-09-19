package com.libraries.controller;

import com.libraries.model.GenreModel;
import com.libraries.service.GenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GenreController {

    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(path = "/genres/") // => http://localhost:9096/api/genres/
    public List<GenreModel> getGenres() { return genreService.getGenres(); }

}
