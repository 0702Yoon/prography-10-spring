package com.example.tableTennis.domain.room.entity.enums;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomType {
    SINGLE("단식", 2), DOUBLE("복식", 4);

    @Getter
    private final String description;
    private final int roomCapacity;

    public static final int NUMBER_OF_TEAM = 2;

    public static RoomType getInstance(String roomType) {
        return Arrays.stream(values())
            .filter(one -> one.getDescription().equals(roomType))
            .findFirst()
            .orElseThrow(EntityNotFoundException::new);
    }

}
