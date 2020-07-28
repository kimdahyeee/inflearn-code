package dev.inflearn.restapi.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {
    @Test
    void builder() {
        Event event = Event.builder().build();
        assertThat(event).isNotNull();
    }

    @Test
    void javaBean() {
        Event event = new Event();
        String name = "Event";
        String description = "spring";
        event.setName(name);
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @Test
    void free() {
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        event.update();

        assertThat(event.isFree()).isTrue();
    }

    @Test
    void free_false() {
        Event event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        event.update();

        assertThat(event.isFree()).isFalse();
    }

    @Test
    void free_false_when_exist_max() {
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        event.update();

        assertThat(event.isFree()).isFalse();
    }

    @Test
    void offline_false() {
        Event event = Event.builder()
                .location("gangnam")
                .build();

        event.update();

        assertThat(event.isOffline()).isTrue();
    }

    @Test
    void offline_true() {
        Event event = Event.builder().build();

        event.update();

        assertThat(event.isOffline()).isFalse();
    }
}