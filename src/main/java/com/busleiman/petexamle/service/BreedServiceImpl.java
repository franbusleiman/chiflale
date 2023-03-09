package com.busleiman.petexamle.service;

import com.busleiman.petexamle.exceptions.NotFoundException;
import com.busleiman.petexamle.model.Breed;
import com.busleiman.petexamle.model.dtos.BreedDTO;
import com.busleiman.petexamle.model.dtos.BreedResponse;
import com.busleiman.petexamle.model.mappers.BreedMapper;
import com.busleiman.petexamle.persistance.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BreedServiceImpl implements BreedService {


    @Autowired
    BreedRepository breedRepository;

    BreedMapper breedMapper = BreedMapper.INSTANCE;

    @Override
    public List<BreedResponse> findAll() {
        return breedRepository.findAll().stream().map(breed ->
                breedMapper.BreedToBreedResponse(breed)).collect(Collectors.toList());
    }

    @Override
    public BreedResponse findById(Long id) {
        return breedMapper.BreedToBreedResponse(breedRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public BreedResponse createBreed(BreedDTO breedDTO) {

        Breed breed = breedMapper.BreedDTOToBreed(breedDTO);

        return breedMapper.BreedToBreedResponse(breedRepository.save(breed));
    }


    @Override
    public void updateBreed(Long id, BreedDTO breedDTO) {
        Breed breed = breedRepository.findById(id).orElseThrow(NotFoundException::new);

        if (breedDTO.getName() != null) {
            breed.setName(breedDTO.getName());
        }

        breedRepository.save(breed);
    }

    @Override
    public void deleteBreed(Long id) {

        Breed breed = breedRepository.findById(id).orElseThrow(NotFoundException::new);
        breedRepository.delete(breed);

    }
}
