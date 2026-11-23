package com.workintech.s19d1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "Gender cannot be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @JsonIgnore
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie) {
        if (this.movies == null) {
            this.movies = new ArrayList<>();
        }

        if (!this.movies.contains(movie)) {
            this.movies.add(movie);
        }

        if (movie.getActors() == null) {
            movie.setActors(new ArrayList<>());
        }

        if (!movie.getActors().contains(this)) {
            movie.getActors().add(this);
        }
    }
}
