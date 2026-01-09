package io.github.nuvemazul.backend.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.github.nuvemazul.backend.dtos.DownloadGameDTO;
import io.github.nuvemazul.backend.dtos.DownloadResponseDTO;
import io.github.nuvemazul.backend.dtos.GameResponseDTO;
import io.github.nuvemazul.backend.entity.Games;

@Component
public class GameAssembler {
       public List<Long> extractIds(List<GameResponseDTO> games) {
        return games.stream()
                .map(GameResponseDTO::id)
                .toList();
    }

    public Collector<DownloadGameDTO, ?, Map<Long, List<DownloadResponseDTO>>> groupDownloads() {
        return Collectors.groupingBy(
            DownloadGameDTO::gameId,
            Collectors.mapping(
                d -> new DownloadResponseDTO(d.service(), d.url()),
                Collectors.toList()
            )
        );
    }

    public List<GameResponseDTO> attachDownloads(
            List<GameResponseDTO> games,
            Map<Long, List<DownloadResponseDTO>> downloadsByGame) {

        return games.stream()
                .map(game -> game.withDownloads(
                        downloadsByGame.getOrDefault(game.id(), List.of())
                ))
                .toList();
    }

    public GameResponseDTO toResponse(
        Games game,
        List<DownloadResponseDTO> downloads) {

    return new GameResponseDTO(
            game.getId(),
            game.getName(),
            game.getDescription(),
            game.getType(),
            game.getImageUrl(),
            downloads
    );
}
}
