package com.busleiman.petexamle.model.dtos;

import com.busleiman.petexamle.model.Pet;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class PetResponse extends PetDTO {
    private Long id;
}
