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
    private String race;
    private Integer age;
}
