package io.github.nuvemazul.backend.dtos;

public record DownloadGameDTO(
    Long gameId,
    String service,
    String url
) {}
