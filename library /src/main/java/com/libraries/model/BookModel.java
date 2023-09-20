package com.libraries.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class BookModel {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Integer pages;

    @Column
    private String author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private GenreModel genre;


    public Object update(BookModel bookRequest) {
        this.setTitle(bookRequest.getTitle());
        this.setPages(bookRequest.getPages());
        this.setAuthor(bookRequest.getAuthor());
        return this;
    }
}
