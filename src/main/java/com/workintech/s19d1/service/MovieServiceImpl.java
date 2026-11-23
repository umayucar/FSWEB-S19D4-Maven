package com.workintech.s19d1.service;

import com.workintech.s19d1.repository.ActorRepository;
import com.workintech.s19d1.repository.MovieRepository;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.exceptions.ApiException;
import com.workintech.s19d1.exceptions.HollywoodValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;


    @Autowired
    public MovieServiceImpl(
            ActorRepository actorRepository,
            MovieRepository movieRepository) {

        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    // Hazır testin kullandığı constructor
    public MovieServiceImpl(MovieRepository movieRepository) {
        this(null, movieRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> findAll() {

        return movieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Movie findById(Long id) {

        HollywoodValidation.validateId(id);

        return movieRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                HttpStatus.NOT_FOUND,
                                "Movie is not found with id: " + id
                        )
                );
    }

    @Override
    public Movie save(Movie movie, List<Actor> actors) {

        movie.setId(null);

        List<Actor> savedActors = new ArrayList<>();

        for (Actor actor : actors) {
            actor.setId(null);
            savedActors.add(actorRepository.save(actor));
        }

        movie.setActors(savedActors);

        Movie savedMovie = movieRepository.save(movie);

        for (Actor actor : savedActors) {
            actor.getMovies().add(savedMovie);
        }

        return savedMovie;
    }

    @Override
    public Movie update(Long id, Movie movie) {

        Movie existingMovie = findById(id);

        HollywoodValidation.validateRating(
                movie.getRating()
        );

        existingMovie.setName(movie.getName());
        existingMovie.setDirectorName(
                movie.getDirectorName()
        );
        existingMovie.setRating(movie.getRating());
        existingMovie.setReleaseDate(
                movie.getReleaseDate()
        );

        return movieRepository.save(existingMovie);
    }

    @Override
    public Movie delete(Long id) {

        Movie movie = findById(id);

        for (Actor actor :
                new ArrayList<>(movie.getActors())) {

            movie.removeActor(actor);
        }

        movieRepository.delete(movie);

        return movie;
    }

    @Override
    public Movie save(Movie movie) {

        HollywoodValidation.validateNotNull(
                movie,
                "Movie cannot be null"
        );

        return movieRepository.save(movie);
    }

    @Override
    public Movie delete(Movie movie) {

        HollywoodValidation.validateNotNull(
                movie,
                "Movie cannot be null"
        );

        movieRepository.delete(movie);

        return movie;
    }
}