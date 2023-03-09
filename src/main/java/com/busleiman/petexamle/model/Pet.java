package com.busleiman.petexamle.model;


import lombok.*;
import org.hibernate.annotations.Generated;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.io.File;
import java.net.URL;

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

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name="breed_id",nullable = false)
    private Breed breed;

    @Column(name = "age")
    private Integer age;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private URL resourceUrl;

    @Column(name="owner_id", nullable = false)
    private Long ownerId;
}
