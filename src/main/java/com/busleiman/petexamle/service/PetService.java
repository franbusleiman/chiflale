package com.busleiman.petexamle.service;

import com.busleiman.petexamle.model.Pet;
import com.busleiman.petexamle.model.dtos.PetDTO;
import com.busleiman.petexamle.model.dtos.PetResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetService {

    List<PetResponse> findAll();
    PetResponse findById(Long id);
    PetResponse createPet(PetDTO petDTO);
    void updatePet(Long id, PetDTO petDTO);
    void deletePet(Long id);
    void uploadImage(MultipartFile file);
}
