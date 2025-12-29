package io.github.nuvemazul.backend.infra;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    String url(MultipartFile image);
}
