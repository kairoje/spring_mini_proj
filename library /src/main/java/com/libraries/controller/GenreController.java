package com.libraries.controller;

import com.libraries.model.GenreModel;
import com.libraries.service.GenreService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GenreController {

    @GetMapping(path = "/genres/") // => http://localhost:9096/api/genres/
    public List<GenreModel> getGenres() { return GenreService.getGenres(); }

}
