package io.github.nuvemazul.backend.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "game_entity")
@Setter
@Getter
public class Games {
    @Id
    private Long id;
    private String name;
    private String description;
    private String type; //PC or Mobile
    private String imageUrl;
      @OneToMany(mappedBy = "game",
        cascade = CascadeType.REMOVE,
        orphanRemoval = true
    )
    List<Download> downloads;
}
