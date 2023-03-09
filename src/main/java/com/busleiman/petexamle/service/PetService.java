package com.busleiman.petexamle.service;

import com.busleiman.petexamle.model.dtos.PetDTO;
import com.busleiman.petexamle.model.dtos.PetResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface PetService {

    List<PetResponse> findAll();
    PetResponse findById(Long id);
    PetResponse createPet(PetDTO petDTO, String username);
    void updatePet(Long id, PetDTO petDTO, String username);
    void deletePet(Long id);
    URL uploadImage(MultipartFile file, Long animalId) throws IOException;
}
