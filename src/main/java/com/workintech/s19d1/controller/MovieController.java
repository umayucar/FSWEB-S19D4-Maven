package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.ActorRequest;
import com.workintech.s19d1.dto.MovieRequest;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<Movie>> findAll() {

        log.info("Fetching all movies");

        return ResponseEntity.ok(
                movieService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(
            @PathVariable Long id) {

        log.info("Fetching movie with id: {}", id);

        return ResponseEntity.ok(
                movieService.findById(id)
        );
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Movie> save(
            @Valid @RequestBody MovieRequest request) {

        log.info("Creating movie and actors");

        Movie movie = movieService.save(
                request.getMovie(),
                request.getActors()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(
            @PathVariable Long id,
            @Valid @RequestBody Movie movie) {

        log.info("Updating movie with id: {}", id);

        return ResponseEntity.ok(
                movieService.update(id, movie)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable Long id) {

        log.info("Deleting movie with id: {}", id);

        Movie movie = movieService.findById(id);

        Movie deletedMovie = movieService.delete(movie);

        return ResponseEntity.ok(deletedMovie);
    }
}
