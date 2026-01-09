package io.github.nuvemazul.backend.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.nuvemazul.backend.dtos.DownloadGameDTO;
import io.github.nuvemazul.backend.dtos.DownloadResponseDTO;
import io.github.nuvemazul.backend.entity.Download;

public interface DownloadRepository extends JpaRepository<Download, Long> {

      @Query("""
        SELECT new io.github.nuvemazul.backend.dtos.DownloadGameDTO(
            d.game.id,
            d.service,
            d.url
        )
        FROM Download d
        WHERE d.game.id IN :gameIds
    """)
    List<DownloadGameDTO> findByGameIds(List<Long> gameIds);

    List<DownloadResponseDTO> findByGame_Id(Long id);

}
