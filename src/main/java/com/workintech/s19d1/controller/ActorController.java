package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.ActorRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.service.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/actor")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<Actor>> findAll() {

        log.info("Fetching all actors");

        return ResponseEntity.ok(
                actorService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> findById(
            @PathVariable Long id) {

        log.info("Fetching actor with id: {}", id);

        return ResponseEntity.ok(
                actorService.findById(id)
        );
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Actor> save(
            @Valid @RequestBody ActorRequest request) {

        Actor actor = request.getActor();

        actor.setMovies(request.getMovies());

        Actor savedActor = actorService.save(actor);

        return ResponseEntity.ok(savedActor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> update(
            @PathVariable Long id,
            @Valid @RequestBody Actor actor) {

        log.info("Updating actor with id: {}", id);

        Actor existingActor = actorService.findById(id);

        existingActor.setFirstName(actor.getFirstName());
        existingActor.setLastName(actor.getLastName());
        existingActor.setGender(actor.getGender());
        existingActor.setBirthDate(actor.getBirthDate());

        Actor updatedActor = actorService.save(existingActor);

        return ResponseEntity.ok(updatedActor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Actor> delete(@PathVariable Long id) {

        Actor actor = actorService.findById(id);

        actorService.delete(actor);

        return ResponseEntity.ok(actor);
    }

}