package io.github.nuvemazul.backend.dtos;

import java.util.List;

public record PageCacheDTO<T>(
        List<T> content,
        int page,
        int size,
        long totalElements) {
}
