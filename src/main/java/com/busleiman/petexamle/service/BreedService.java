package com.busleiman.petexamle.service;

import com.busleiman.petexamle.model.dtos.BreedDTO;
import com.busleiman.petexamle.model.dtos.BreedResponse;
import com.busleiman.petexamle.model.dtos.PetDTO;
import com.busleiman.petexamle.model.dtos.PetResponse;

import java.util.List;

public interface BreedService {

    List<BreedResponse> findAll();
    BreedResponse findById(Long id);
    BreedResponse createBreed(BreedDTO breedDTO);
    void updateBreed(Long id, BreedDTO breedDTO);
    void deleteBreed(Long id);
}
