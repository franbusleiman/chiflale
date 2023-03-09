package com.busleiman.petexamle.controller;

import com.busleiman.petexamle.model.dtos.FileResponse;
import com.busleiman.petexamle.model.dtos.PetDTO;
import com.busleiman.petexamle.model.dtos.PetResponse;
import com.busleiman.petexamle.service.PetService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pets")
@Slf4j
public class PetController {
    @Autowired
    PetService petService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetResponse> findById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(petService.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetResponse>> findAll() {

        return ResponseEntity.ok(petService.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetResponse> createPet(@RequestBody PetDTO petDTO, @RequestHeader(name = "Authorization") String token) {


        Claims claims;
        String username;

            claims = Jwts.parser()
                    .setSigningKey("codigo_secreto".getBytes())
                    .parseClaimsJws(token.substring(7))
                    .getBody();

            username = (String) claims.get("user_name");

        PetResponse petResponse = petService.createPet(petDTO, username);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(petResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(petResponse);
    }


    @PostMapping(value = "/mail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMail(@RequestParam("mail") String mail) throws IOException {

        Writer output;

        output = new BufferedWriter(new FileWriter("mails.txt", true));
        output.append(mail + "\n");
        output.close();

        return ResponseEntity.status(204).build();
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePet(@PathVariable("id") Long id, @RequestBody PetDTO petDTO, @RequestHeader(name = "Authorization") String token) {


        Claims claims;
        String username;

        claims = Jwts.parser()
                .setSigningKey("codigo_secreto".getBytes())
                .parseClaimsJws(token.substring(7))
                .getBody();

        username = (String) claims.get("user_name");

        petService.updatePet(id, petDTO, username);

        return ResponseEntity.status(204).build();
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletePet(@PathVariable("id") Long id) {

        petService.deletePet(id);

        return ResponseEntity.status(204).build();

    }


    @PostMapping(value = "/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("animalId") Long animalId) throws IOException {
        petService.uploadImage(file, animalId);

        return ResponseEntity.ok().body("");
    }

    @GetMapping(value = "/getFile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileResponse> getFile() {

        FileResponse fileResponse = new FileResponse("http://localhost:8080/meme-6.jpg");

        return ResponseEntity.ok().body(fileResponse);
    }


}
