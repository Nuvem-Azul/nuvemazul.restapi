package io.github.nuvemazul.backend.dtos;

import java.util.List;

public record GameResponseDTO(
    Long id,
    String name,
    String description,
    String type,
    String imageUrl,
    List<DownloadResponseDTO> downloads
) {
    public GameResponseDTO withDownloads(List<DownloadResponseDTO> downloads) {
        return new GameResponseDTO(
            id,
            name,
            description,
            type,
            imageUrl,
            downloads
        );
    }
}
