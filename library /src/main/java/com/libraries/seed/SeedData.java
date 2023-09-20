package com.libraries.seed;

import com.libraries.model.GenreModel;
import com.libraries.model.UserModel;
import com.libraries.repository.BookRepository;
import com.libraries.repository.GenreRepository;
import com.libraries.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private  final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    public SeedData(@Lazy PasswordEncoder passwordEncoder,
                    UserRepository userRepository,
                    BookRepository bookRepository,
                    GenreRepository genreRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        UserModel user = new UserModel();
        user.setUserName("KaiLibrary");
        user.setEmailAddress("kaibooks@gmail.com");
        user.setPassword(passwordEncoder.encode("Books4U"));
        userRepository.save(user);

        GenreModel genre = new GenreModel();
        genre.setName("Fiction");
        genre.setDescription("Imagination");
        genre.setUser(user);
        genreRepository.save(genre);

    }
}
