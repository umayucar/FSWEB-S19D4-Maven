package com.workintech.s19d1.service;

import com.workintech.s19d1.repository.ActorRepository;
import com.workintech.s19d1.repository.MovieRepository;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.exceptions.ApiException;
import com.workintech.s19d1.exceptions.HollywoodValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;


    @Autowired
    public ActorServiceImpl(
            ActorRepository actorRepository,
            MovieRepository movieRepository) {

        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    // Hazır testin kullandığı constructor
    public ActorServiceImpl(ActorRepository actorRepository) {
        this(actorRepository, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actor> findAll() {

        return actorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Actor findById(Long id) {

        HollywoodValidation.validateId(id);

        return actorRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                HttpStatus.NOT_FOUND,
                                "actor is not found with id: " + id
                        )
                );
    }

    @Override
    public Actor save(Actor actor) {

        HollywoodValidation.validateNotNull(
                actor,
                "Actor cannot be null"
        );

        return actorRepository.save(actor);
    }

    @Override
    public Actor update(Long id, Actor actor) {

        Actor existingActor = findById(id);

        existingActor.setFirstName(actor.getFirstName());
        existingActor.setLastName(actor.getLastName());
        existingActor.setGender(actor.getGender());
        existingActor.setBirthDate(actor.getBirthDate());

        return actorRepository.save(existingActor);
    }

    @Override
    public Actor delete(Actor actor) {

        HollywoodValidation.validateNotNull(
                actor,
                "Actor cannot be null"
        );

        for (Movie movie : new ArrayList<>(actor.getMovies())) {
            movie.removeActor(actor);
        }

        actorRepository.delete(actor);

        return actor;
    }

    @Override
    public Actor delete(Long id) {

        Actor actor = findById(id);

        for (Movie movie : new ArrayList<>(actor.getMovies())) {
            movie.removeActor(actor);

            if (movieRepository != null) {
                movieRepository.save(movie);
            }
        }

        if (movieRepository != null) {
            movieRepository.flush();
        }

        actorRepository.delete(actor);

        return actor;
    }
}
