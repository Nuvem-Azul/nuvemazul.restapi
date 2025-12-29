package io.github.nuvemazul.backend.infra;

import io.github.nuvemazul.backend.dtos.CreateGameEvent;

public interface PublisherEvent {
    void publish(CreateGameEvent event);
}
