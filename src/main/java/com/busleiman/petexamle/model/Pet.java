package com.busleiman.petexamle.model;


import lombok.*;
import org.hibernate.annotations.Generated;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "pets")
@EqualsAndHashCode
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "race")
    private String race;

    @Column(name = "age")
    private Integer age;
}
