package io.github.nuvemazul.backend.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.nuvemazul.backend.dtos.GameRequestDTO;
import io.github.nuvemazul.backend.dtos.GameResponseDTO;
import io.github.nuvemazul.backend.dtos.PageCacheDTO;
import io.github.nuvemazul.backend.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createGame(@RequestPart("data") String data,
            @RequestPart("image") MultipartFile image) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        GameRequestDTO dto = objectMapper.readValue(data, GameRequestDTO.class);
        gameService.createGame(dto, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<GameResponseDTO> getGames(@RequestParam String type, Pageable pageable) {

        PageCacheDTO<GameResponseDTO> cached = gameService.findAllFast(type, pageable);

        return new PageImpl<>(
                cached.content(),
                PageRequest.of(cached.page(), cached.size()),
                cached.totalElements());
    }

    @GetMapping("/{id}")
    public GameResponseDTO getGamesById(@PathVariable("id") Long id) {
        return gameService.findGameById(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") Long id){
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }

}
