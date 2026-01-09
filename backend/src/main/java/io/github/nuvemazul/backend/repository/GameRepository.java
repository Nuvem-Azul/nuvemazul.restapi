package io.github.nuvemazul.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.nuvemazul.backend.dtos.GameResponseDTO;
import io.github.nuvemazul.backend.entity.Games;
import io.lettuce.core.dynamic.annotation.Param;

public interface GameRepository extends JpaRepository<Games, Long> {

    @Query("""
        SELECT new io.github.nuvemazul.backend.dtos.GameResponseDTO(
            g.id,
            g.name,
            g.description,
            g.type,
            g.imageUrl,
            null
        )
        FROM Games g
        WHERE g.type = :type
    """)
    Page<GameResponseDTO> findAllGamesByTypeBase(@Param("type") String type,Pageable pageable);

    
    Optional<Games> findById(Long id);
}
