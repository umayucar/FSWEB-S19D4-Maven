package com.workintech.s19d1.service;

import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findAll();

    Movie findById(Long id);

    Movie save(Movie movie, List<Actor> actors);

    Movie update(Long id, Movie movie);

    Movie delete(Long id);

    Movie save(Movie movie);

    Movie delete(Movie movie);
}