package com.busleiman.petexamle.service;

import com.busleiman.petexamle.exceptions.NotFoundException;
import com.busleiman.petexamle.model.Pet;
import com.busleiman.petexamle.model.dtos.PetDTO;
import com.busleiman.petexamle.model.dtos.PetResponse;
import com.busleiman.petexamle.model.mappers.PetMapper;
import com.busleiman.petexamle.persistance.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    PetRepository petRepository;

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
    public PetResponse createPet(PetDTO petDTO) {
        Pet pet = petMapper.PetDTOToPet(petDTO);
        petRepository.save(pet);
        return  petMapper.PetToPetResponse(pet);
    }

    @Override
    public void updatePet(Long id, PetDTO petDTO) {
        Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);

        if(petDTO.getName() != null){
            pet.setName(petDTO.getName());
        }
        if(petDTO.getRace() != null){
            pet.setRace(petDTO.getRace());
        }
        if(petDTO.getAge() != null){
            pet.setAge(petDTO.getAge());
        }

        petRepository.save(pet);
    }

    @Override
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);

        petRepository.delete(pet);
    }


     public void uploadImage( MultipartFile files) {

        if(!files.isEmpty()){
            try {
                String fileName = files.getOriginalFilename();
                String dirLocation ="C:\\Users\\fbusleiman\\Desktop\\files";
                if(!new File(dirLocation).exists()){
                    File file = new File(dirLocation);

                    file.mkdirs();
                }
                System.out.println(files.getName());
                System.out.println(files.getOriginalFilename());
                Files.copy(files.getInputStream(), Path.of(dirLocation, files.getOriginalFilename()), new CopyOption[]{
                        StandardCopyOption.REPLACE_EXISTING});
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
