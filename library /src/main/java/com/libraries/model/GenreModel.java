package com.libraries.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class GenreModel {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "genre", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<BookModel> bookList;

    public Optional<BookModel> findBookById(Long bookId){
        return bookList.stream().filter((book)->book.getId().equals(bookId)).findFirst();

    }

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;
}
