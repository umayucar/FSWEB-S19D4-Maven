package com.workintech.s19d1.dto;

import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.entity.Actor;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    private Movie movie;
    private List<Actor> actors;
}