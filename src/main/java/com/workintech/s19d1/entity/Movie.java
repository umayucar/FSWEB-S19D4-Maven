package com.workintech.s19d1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Movie name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Director name cannot be blank")
    @Column(name = "director_name", nullable = false)
    private String directorName;

    @NotNull(message = "Rating cannot be null")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double rating;

    @NotNull(message = "Release date cannot be null")
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            schema = "hollywood",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();

    public void addActor(Actor actor) {
        actors.add(actor);
        actor.getMovies().add(this);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
        actor.getMovies().remove(this);
    }
}
