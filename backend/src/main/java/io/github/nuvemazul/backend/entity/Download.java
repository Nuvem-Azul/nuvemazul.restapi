package io.github.nuvemazul.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Download {
    @Id
    private Long id;
    private String url;
    private String service; //e.g., Google Drive, Dropbox
    @JoinColumn(name = "game_id")
    @ManyToOne
    private Games game;

}
