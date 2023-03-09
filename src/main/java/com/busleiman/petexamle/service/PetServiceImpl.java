package com.busleiman.petexamle.service;

import com.busleiman.petexamle.clients.UserFeignClient;
import com.busleiman.petexamle.exceptions.NotFoundException;
import com.busleiman.petexamle.exceptions.UnauthorizedException;
import com.busleiman.petexamle.model.Breed;
import com.busleiman.petexamle.model.Pet;
import com.busleiman.petexamle.model.dtos.PetDTO;
import com.busleiman.petexamle.model.dtos.PetResponse;
import com.busleiman.petexamle.model.mappers.PetMapper;
import com.busleiman.petexamle.model.user.User;
import com.busleiman.petexamle.persistance.BreedRepository;
import com.busleiman.petexamle.persistance.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    BreedRepository breedRepository;

    @Autowired
    UserFeignClient userFeignClient;

    PetMapper petMapper = PetMapper.INSTANCE;

    @Override
    public List<PetResponse> findAll() {
        return petRepository.findAll().stream()
                .map(pet -> petMapper.PetToPetResponse(pet))
                .collect(Collectors.toList());
    }

    @Override
    public PetResponse findById(Long id) {
        return petMapper.PetToPetResponse(petRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public PetResponse createPet(PetDTO petDTO, String username) {

        Pet pet = petMapper.PetDTOToPet(petDTO);

        Breed breed = breedRepository.findById(petDTO.getBreedId()).orElseThrow(NotFoundException::new);
        pet.setBreed(breed);

        User user = getUser(username);

        pet.setOwnerId(user.getId());

        petRepository.save(pet);

        return petMapper.PetToPetResponse(pet);
    }

    @Override
    public void updatePet(Long id, PetDTO petDTO, String username) {

    Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);

        User user = getUser(username);

        if (validateUser(pet, user)) {

            if (petDTO.getName() != null) {
                pet.setName(petDTO.getName());
            }
            if (petDTO.getBreedId() != null) {
                Breed breed = breedRepository.findById(petDTO.getBreedId()).orElseThrow(NotFoundException::new);
                pet.setBreed(breed);
            }
            if (petDTO.getAge() != null) {
                pet.setAge(petDTO.getAge());
            }
            if (petDTO.getDescription() != null) {
                pet.setDescription(petDTO.getDescription());
            }
            if (petDTO.getLatitude() != null) {
                pet.setLatitude(petDTO.getLatitude());
            }
            if (petDTO.getLongitude() != null) {
                pet.setLongitude(petDTO.getLongitude());
            }

            petRepository.save(pet);
        }
        else{
            throw new UnauthorizedException("You are not the owner, neither an admin");
        }
    }

    @Override
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);

        petRepository.delete(pet);
    }


    public URL uploadImage(MultipartFile resource, Long animalId) throws IOException {

        Pet pet = petRepository.findById(animalId).orElseThrow(NotFoundException::new);

        String[] allowedTypes = {"image/jpeg", "image/png"};
        if (!Arrays.asList(allowedTypes).contains(resource.getContentType())) {
            throw new IllegalArgumentException("Invalid resource type, only JPG and PNG are allowed");
        }

        Path resourcePath = Paths.get("/ubuntu/files/" + animalId);
        Files.write(resourcePath, resource.getBytes());

        pet.setResourceUrl(resourcePath.toUri().toURL());

        return resourcePath.toUri().toURL();
    }


    private User getUser(String username) {

        ResponseEntity<User> response = userFeignClient.findUserByUsername(username);
        if (response.getStatusCode().is4xxClientError()) {
            throw new NotFoundException();
        }

        return response.getBody();
    }


    private boolean validateUser(Pet pet, User user) {
        boolean validUser = false;

        if (user.getId().equals(pet.getOwnerId())) {
            validUser = true;
        } else if (user.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN"))) {
            validUser = true;
        }

        return validUser;
    }

}
