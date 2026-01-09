package io.github.nuvemazul.backend.dtos;

import java.util.List;

public record CreateGameEvent(
    String name, 
    String description, 
    String type,
    String imageUrl,
    List<DownloadRequestDTO> download
) {}
