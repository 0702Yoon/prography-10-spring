package com.example.tableTennis.domain.room.entity.enums;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomStatus {
    WAIT("대기"), PROGRESS("진행중"), FINISH("완료");
    private final String description;

    public static RoomStatus getInstance(String roomStatus) {
        return Arrays.stream(values())
            .filter(one -> one.getDescription().equals(roomStatus))
            .findFirst()
            .orElseThrow(EntityNotFoundException::new);
    }

}
