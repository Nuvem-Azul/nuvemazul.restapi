package io.github.nuvemazul.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Games {
    @Id
    private Long id;
    private String name;
    private String description;
    private String type; //PC or Mobile
    private String imageUrl;

}
