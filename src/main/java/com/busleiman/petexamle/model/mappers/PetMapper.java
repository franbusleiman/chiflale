package com.busleiman.petexamle.model.mappers;

import com.busleiman.petexamle.model.Pet;
import com.busleiman.petexamle.model.dtos.PetDTO;
import com.busleiman.petexamle.model.dtos.PetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PetMapper{

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    @Mapping(target = "id", ignore = true)
    Pet PetDTOToPet(PetDTO petDTO);


    @Mapping(target = "breedId", source = "breed.id")
    PetResponse PetToPetResponse(Pet pet);
}
