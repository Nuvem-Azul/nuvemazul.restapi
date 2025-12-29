package io.github.nuvemazul.backend.dtos;

import java.io.Serializable;

public record DownloadRequestDTO(
    String service,
    String url
) implements Serializable{

}
