package com.busleiman.petexamle.controller;

import com.busleiman.petexamle.model.dtos.BreedDTO;
import com.busleiman.petexamle.model.dtos.BreedResponse;
import com.busleiman.petexamle.service.BreedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/breeds")
@Slf4j
public class BreedController {

    @Autowired
    BreedService breedService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BreedResponse> findById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(breedService.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BreedResponse>> findAll() {

        return ResponseEntity.ok(breedService.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BreedResponse> createBreed(@RequestBody BreedDTO breedDTO) {

        BreedResponse breedResponse = breedService.createBreed(breedDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(breedResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(breedResponse);
    }


    @CrossOrigin(origins = "*")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateBreed(@PathVariable("id") Long id, @RequestBody BreedDTO breedDTO) {

        breedService.updateBreed(id, breedDTO);

        return ResponseEntity.status(204).build();
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteBreed(@PathVariable("id") Long id) {

        breedService.deleteBreed(id);

        return ResponseEntity.status(204).build();

    }
}
