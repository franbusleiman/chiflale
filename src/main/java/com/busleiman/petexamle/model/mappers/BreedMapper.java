package com.busleiman.petexamle.model.mappers;

import com.busleiman.petexamle.model.Breed;
import com.busleiman.petexamle.model.dtos.BreedDTO;
import com.busleiman.petexamle.model.dtos.BreedResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BreedMapper {

    BreedMapper INSTANCE = Mappers.getMapper(BreedMapper.class);

    @Mapping(target = "id", ignore = true)
    Breed BreedDTOToBreed(BreedDTO breedDTO);

    BreedResponse BreedToBreedResponse(Breed breed);
}
