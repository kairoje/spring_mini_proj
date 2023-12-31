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

    /**
     * Gets the current logged-in user
     * @return the user details based on current logged-in user
     */
    public static UserModel getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    /**
     * Get a list of genres per get request
     * @return the list of genres
     */
    public List<GenreModel> getGenres(){
        List<GenreModel> genreList = genreRepository.findByUserId(GenreService.getCurrentLoggedInUser().getId());
        if (genreList.isEmpty()){
            throw new InformationNotFoundException("No genres found for user id " + GenreService.getCurrentLoggedInUser().getId());
        } else {
            return genreList;
        }
    }

    /**
     * Optional retrival of genre through get request
     * @param genreId
     * @return optional genre entry
     */
    public Optional<GenreModel> getGenre(Long genreId){
        GenreModel genre = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
        if(genre == null){
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        } else {
            return Optional.of(genre);
        }
    }

    /**
     * Creates a genre object with post request
     * @param genreObject
     * @return the saved genre object
     */
    public GenreModel createGenre(GenreModel genreObject) {
        String name = genreObject.getName();
        GenreModel genre = genreRepository.findByName(name);
        if(genre != null){
            throw new InformationExistException("Genre with name " + genreObject.getName() + " already exists.");
        } else {
            return genreRepository.save(genreObject);
        }
    }

    /**
     * Updates the genre per put request
     * @param genreId
     * @param genreObject
     * @return updated genre object
     */
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

    /**
     * Delete a genre per delete request
     * @param genreId
     * @return verification of deletion of genre
     */
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

    /**
     * Creating an individual book per post request
     * @param genreId
     * @param bookObject
     * @return a saved book entry
     */
    public BookModel createGenreBook(Long genreId, BookModel bookObject) {
        try{
            Optional<GenreModel> genreOptional = Optional.ofNullable(genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId()));
            bookObject.setGenre(genreOptional.get());
            return bookRepository.save(bookObject);
        } catch(NoSuchElementException e){
            throw new InformationNotFoundException("Genre with id " + genreId + " not found"); //404 error
        }
    }


    /**
     * Get a list of books per get request
     * @param genreId
     * @return the optional book list
     */
    public List<BookModel> getGenreBooks(Long genreId) {
        Optional<GenreModel> genreOptional = genreRepository.findById(String.valueOf(genreId));
        if(genreOptional.isPresent()){
            return genreOptional.get().getBookList();
        } else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }


    /**
     * Get an individual book per get request
     * @param genreId
     * @param bookId
     * @return
     */
    public BookModel getGenreBook(Long genreId, Long bookId) {
        Optional<GenreModel> genreOptional = genreRepository.findById(String.valueOf(genreId));
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

    /**
     * Update an individual book per post request
     * @param genreId
     * @param bookId
     * @param bookObject
     * @return the updated book entry
     */
    public BookModel updateGenreBook(Long genreId, Long bookId, BookModel bookObject) {
        GenreModel genre = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
        if(genre == null) {
            throw new InformationNotFoundException("Genre " + genreId + " does not exist.");
            } else {
            Optional<BookModel> bookOptional = genre.findBookById(bookObject.getId());

            if (bookOptional.isPresent()){
                BookModel updateBook = bookOptional.get();
                updateBook.setTitle(bookObject.getTitle());
                updateBook.setGenre(bookObject.getGenre());
                updateBook.setAuthor(bookObject.getAuthor());
                return bookRepository.save(updateBook);
            } else {
                throw new InformationNotFoundException("Book with id " + bookId + " not found in genre " + genreId);
            }
        }
    }


    /**
     * Delete a book entry per delete request
     * @param genreId
     * @param bookId
     * @return verification of deletion of book entry
     */
    public Optional<BookModel> deleteGenre(Long genreId, Long bookId) {
        Optional<GenreModel> genreOptional = genreRepository.findById(String.valueOf(genreId));
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
