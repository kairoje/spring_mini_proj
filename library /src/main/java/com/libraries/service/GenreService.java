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
    public GenreService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public static UserModel getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal is entire user object
        return userDetails.getUser();
    }

    public List<GenreModel> getGenres(){
        List<GenreModel> categoryList = genreRepository.findByUserId(GenreService.getCurrentLoggedInUser().getId());
        if (categoryList.isEmpty()){
            throw new InformationNotFoundException("No genres found for user id " + GenreService.getCurrentLoggedInUser().getId());
        } else {
            return categoryList;
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
        GenreModel genre = genreRepository.findByName(genreObject.getName());
        if(genre != null){
            throw new InformationExistException("Genre with name " + genreObject.getName() + " already exists.");
        } else {
            return genreRepository.save(genreObject);
        }
    }

    public GenreModel updateGenre(Long genreId, GenreModel genreObject) {
        GenreModel category = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
        // isPresent
        if (category == null) {
            throw new InformationNotFoundException("Genre " + genreId + " doesn't exists");
        } else {
            GenreModel updateGenre = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
            updateGenre.setName(genreObject.getName());
            updateGenre.setDescription(genreObject.getDescription());
            return genreRepository.save(updateGenre);
        }
    }

    public Optional<GenreModel> deleteGenre(Long genreId) {
        GenreModel category = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
        if(category != null){
            genreRepository.deleteById(genreId);
            return Optional.of(category);
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
            throw new InformationNotFoundException("category with id " + genreId + " not found"); //404 error
        }
    }

    public List<BookModel> getGenreBooks(Long genreId) {
        Optional<GenreModel> genreOptional = Optional.ofNullable(genreRepository.findById(genreId));
        if(genreOptional.isPresent()){
            return genreOptional.get().getBookList();
        } else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }


    public BookModel getGenreBook(Long genreId, Long bookId) {
        Optional<GenreModel> genreOptional = Optional.ofNullable(genreRepository.findById(genreId));
        if (genreOptional.isPresent()) {
            Optional<BookModel> bookOptional = genreOptional.get().findBookById(bookId);
            if (bookOptional.isPresent()){
                return bookOptional.get();
            }
            else {
                throw new InformationNotFoundException("book with id " + bookId + " not found");
            }
        }
        else {
            throw new InformationNotFoundException("book with id " + bookId + " not found");
        }
    }

    public BookModel updateGenreBook(Long genreId, Long bookId, BookModel book) {
        Optional<GenreModel> genreOptional = Optional.ofNullable(genreRepository.findById(genreId));
        if (genreOptional.isPresent()) {
            Optional<BookModel> bookOptional = genreOptional.get().findBookById(bookId);
            if (bookOptional.isPresent()) {
                return bookRepository.save(bookOptional.get().update(book));
            } else {
                throw new InformationNotFoundException("Book with id " + bookId + " not found");
            }
        } else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
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
