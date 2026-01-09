package io.github.nuvemazul.backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.nuvemazul.backend.dtos.CreateGameEvent;
import io.github.nuvemazul.backend.dtos.DownloadResponseDTO;
import io.github.nuvemazul.backend.dtos.GameRequestDTO;
import io.github.nuvemazul.backend.dtos.GameResponseDTO;
import io.github.nuvemazul.backend.dtos.PageCacheDTO;
import io.github.nuvemazul.backend.entity.Games;
import io.github.nuvemazul.backend.infra.ImageStorageService;
import io.github.nuvemazul.backend.infra.PublisherEvent;
import io.github.nuvemazul.backend.mapper.GameAssembler;
import io.github.nuvemazul.backend.repository.DownloadRepository;
import io.github.nuvemazul.backend.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GameService {
    private ImageStorageService imageStorageService;
    private PublisherEvent publisherEvent;
    private GameRepository gameRepository;
    private DownloadRepository downloadRepository;
    private GameAssembler gameAssembler;
    public GameService(ImageStorageService imageStorageService, PublisherEvent publisherEvent,
            GameRepository gameRepository, DownloadRepository downloadRepository, GameAssembler gameAssembler) {
        this.imageStorageService = imageStorageService;
        this.publisherEvent = publisherEvent;
        this.gameRepository = gameRepository;
        this.downloadRepository = downloadRepository;
        this.gameAssembler = gameAssembler;
    }

    @Caching(evict = {
        @CacheEvict(value = "game_pages", allEntries = true)
    })
    public void createGame(GameRequestDTO dto, MultipartFile image) {
        try {
            String imageUrl = imageStorageService.url(image);
            CreateGameEvent event = new CreateGameEvent(
                    dto.name(),
                    dto.description(),
                    dto.type(),
                    imageUrl,
                    dto.download());
            publisherEvent.publish(event);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Cacheable(value = "game_pages", key = "#type + '-' + #pageable.pageNumber")
    public PageCacheDTO<GameResponseDTO> findAllFast(String type, Pageable pageable) {

        Page<GameResponseDTO> page = gameRepository.findAllGamesByTypeBase(type,pageable);

        if (page.isEmpty()) {
            return new PageCacheDTO<>(
                    Collections.emptyList(),
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    0);
        }

        List<Long> gameIds = gameAssembler.extractIds(page.getContent());

        Map<Long, List<DownloadResponseDTO>> downloads = downloadRepository.findByGameIds(gameIds)
                .stream()
                .collect(gameAssembler.groupDownloads());

        List<GameResponseDTO> merged = gameAssembler.attachDownloads(
                page.getContent(),
                downloads);

        return new PageCacheDTO<>(
                merged,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements());
    }

    @Cacheable(value = "game_info", key = "#id")
    public GameResponseDTO findGameById(Long id){
       Games game = gameRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Game não encontrado"));

    List<DownloadResponseDTO> downloads =
            downloadRepository.findByGame_Id(id);

    return gameAssembler.toResponse(game, downloads);
    }

   @Caching(evict = {
    @CacheEvict(value = "game_pages", allEntries = true),
    @CacheEvict(value = "game_info", key = "#id")
})
    @Transactional
    public void deleteGame(Long id){
        gameRepository.deleteById(id);
    }
}
