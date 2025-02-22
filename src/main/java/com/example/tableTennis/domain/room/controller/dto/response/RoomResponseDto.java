package com.example.tableTennis.domain.room.controller.dto.response;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.entity.enums.RoomStatus;
import com.example.tableTennis.domain.room.entity.enums.RoomType;

public record RoomResponseDto(
    int id,
    String title,
    int hostId,
    RoomType roomType,
    RoomStatus status
) {

    public static RoomResponseDto from(Room room) {
        return new RoomResponseDto(
            room.getId().intValue(),
            room.getTitle(),
            room.getHostId(),
            room.getRoomType(),
            room.getStatus()
        );
    }
}
