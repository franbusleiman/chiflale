package com.busleiman.petexamle.model.dtos;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class PetDTO {

    private String name;
    private Long breedId;
    private Integer age;
    private String description;
    private String latitude;
    private String longitude;
}
