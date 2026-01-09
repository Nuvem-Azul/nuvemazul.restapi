package io.github.nuvemazul.backend.dtos;

import java.util.List;

public record GameRequestDTO(
    String name, 
    String description, 
    String type,
    List<DownloadRequestDTO> download
){
}
