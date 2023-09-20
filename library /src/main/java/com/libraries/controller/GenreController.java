package com.libraries.controller;

import com.libraries.model.BookModel;
import com.libraries.model.GenreModel;
import com.libraries.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
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

    @PostMapping(path = "/genres/{genreId}/books/")
    public BookModel createGenreBook(@PathVariable(value = "genreId") Long genreId, @RequestBody BookModel bookObject){
        return genreService.createGenreBook(genreId, bookObject);
    }

    @GetMapping(path = "/genres/{genreId}/books/") // => http://localhost:9096/api/genres/{genreId}/books/
    public List<BookModel> getGenreBooks(@PathVariable(value = "genreId") Long genreId){
        return genreService.getGenreBooks(genreId);
    }

    @GetMapping(path = "/genres/{genreId}/books/{bookId}") // => http://localhost:9096/api/genres/{genreId}/books/{booksId}/
    public BookModel getGenreBook(@PathVariable(value = "genreId") Long genreId, @PathVariable(value = "bookId") Long bookId){
        return genreService.getGenreBook(genreId, bookId);
    }

    @PutMapping(path = "/genres/{genreId}/books/{bookId}") // => http://localhost:9096/api/genres/{genreId}/books/{booksId}/
    public BookModel updateGenreBook(@PathVariable(value = "genreId") Long genreId, @PathVariable(value = "bookId") Long bookId, @RequestBody BookModel bookRequest){
        return genreService.updateGenreBook(genreId, bookId, bookRequest);
    }

    @DeleteMapping(path = "/genres/{genreId}/books/{bookId}") // => http://localhost:9096/api/genres/{genreId}/books/{booksId}/
    public Optional<BookModel> deleteGenreBook(@PathVariable(value = "genreId") Long genreId, @PathVariable(value = "bookId") Long bookId){
        return genreService.deleteGenre(genreId, bookId);
    }
}
