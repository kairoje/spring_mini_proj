package com.libraries.service;

import com.libraries.exceptions.InformationExistException;
import com.libraries.exceptions.InformationNotFoundException;
import com.libraries.model.BookModel;
import com.libraries.model.GenreModel;
import com.libraries.model.UserModel;
import com.libraries.repository.BookRepository;
import com.libraries.repository.GenreRepository;
import com.libraries.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GenreService {

    private GenreRepository genreRepository;

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public static UserModel getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    public List<GenreModel> getGenres(){
        List<GenreModel> genreList = genreRepository.findByUserId(GenreService.getCurrentLoggedInUser().getId());
        if (genreList.isEmpty()){
            throw new InformationNotFoundException("No genres found for user id " + GenreService.getCurrentLoggedInUser().getId());
        } else {
            return genreList;
        }
    }

    public Optional<GenreModel> getGenre(Long genreId){
        GenreModel genre = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
        if(genre == null){
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        } else {
            return Optional.of(genre);
        }
    }

    public GenreModel createGenre(GenreModel genreObject) {
        String name = genreObject.getName();
        GenreModel genre = genreRepository.findByName(name);
        if(genre != null){
            throw new InformationExistException("Genre with name " + genreObject.getName() + " already exists.");
        } else {
            return genreRepository.save(genreObject);
        }
    }

    public GenreModel updateGenre(Long genreId, GenreModel genreObject) {
        GenreModel genre = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
        if (genre == null) {
            throw new InformationNotFoundException("Genre " + genreId + " doesn't exists");
        } else {
            GenreModel updateGenre = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
            updateGenre.setName(genreObject.getName());
            updateGenre.setDescription(genreObject.getDescription());
            return genreRepository.save(updateGenre);
        }
    }

    public Optional<GenreModel> deleteGenre(Long genreId) {
        GenreModel genre = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
        if(genre != null){
            genreRepository.deleteById(String.valueOf(genreId));
            return Optional.of(genre);
        }
        else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }

    public BookModel createGenreBook(Long genreId, BookModel bookObject) {
        try{
            Optional<GenreModel> genreOptional = Optional.ofNullable(genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId()));
            bookObject.setGenre(genreOptional.get());
            return bookRepository.save(bookObject);
        } catch(NoSuchElementException e){
            throw new InformationNotFoundException("Genre with id " + genreId + " not found"); //404 error
        }
    }

    public List<BookModel> getGenreBooks(Long genreId) {
        Optional<GenreModel> genreOptional = genreRepository.findById(genreId);
        if(genreOptional.isPresent()){
            return genreOptional.get().getBookList();
        } else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }


    public BookModel getGenreBook(Long genreId, Long bookId) {
        Optional<GenreModel> genreOptional = genreRepository.findById(genreId);
        if (genreOptional.isPresent()) {
            Optional<BookModel> bookOptional = genreOptional.get().findBookById(bookId);
            if (bookOptional.isPresent()){
                return bookOptional.get();
            }
            else {
                throw new InformationNotFoundException("Book with id " + bookId + " not found");
            }
        }
        else {
            throw new InformationNotFoundException("Book with id " + bookId + " not found");
        }
    }

    public BookModel updateGenreBook(Long genreId, Long bookId, BookModel book) {
        GenreModel genre = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
        if(genre == null) {
            throw new InformationNotFoundException("Genre " + genreId + " does not exist.");
            } else {
            BookModel updateBook = genreRepository.findByIdAndUserId(bookId, GenreService.getCurrentLoggedInUser().getId());
            updateBook.setTitle();
                return bookRepository.save(genreRepository.update(book));
        }
    }


    public Optional<BookModel> deleteGenre(Long genreId, Long bookId) {
        Optional<GenreModel> genreOptional = Optional.ofNullable(genreRepository.findById(genreId));
        if (genreOptional.isPresent()){
            Optional<BookModel> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isPresent()){
                bookRepository.deleteById(bookId);
                return bookOptional;
            }
            else {
                throw new InformationNotFoundException("Book with id " + bookId + " not found");
            }
        } else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }
}
