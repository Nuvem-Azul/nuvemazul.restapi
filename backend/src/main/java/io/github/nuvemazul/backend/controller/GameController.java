package io.github.nuvemazul.backend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.nuvemazul.backend.dtos.GameRequestDTO;
import io.github.nuvemazul.backend.service.GameService;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/games")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createGame(@RequestPart("data") String data,
                                           @RequestPart("image") MultipartFile image) {

        ObjectMapper objectMapper = new ObjectMapper();
        GameRequestDTO dto = objectMapper.readValue(data, GameRequestDTO.class);
        gameService.createGame(dto, image);
        return ResponseEntity.ok().build();
    }

   
}
