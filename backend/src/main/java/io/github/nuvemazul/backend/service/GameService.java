package io.github.nuvemazul.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.nuvemazul.backend.dtos.CreateGameEvent;
import io.github.nuvemazul.backend.dtos.GameRequestDTO;
import io.github.nuvemazul.backend.infra.ImageStorageService;
import io.github.nuvemazul.backend.infra.PublisherEvent;

@Service
public class GameService {
    private ImageStorageService imageStorageService;
    private PublisherEvent publisherEvent;

    public GameService(ImageStorageService imageStorageService, PublisherEvent publisherEvent) {
        this.imageStorageService = imageStorageService;
        this.publisherEvent = publisherEvent;
    }
    
    public void createGame(GameRequestDTO dto, MultipartFile image) {
        
        String imageUrl = imageStorageService.url(image);
        CreateGameEvent event = new CreateGameEvent(
            dto.name(),
            dto.description(),
            dto.type(),
            imageUrl,
            dto.download()
        );

        publisherEvent.publish(event);
    }

}
