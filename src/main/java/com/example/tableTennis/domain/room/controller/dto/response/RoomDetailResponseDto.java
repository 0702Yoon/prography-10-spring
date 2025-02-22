package com.example.tableTennis.domain.room.controller.dto.response;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.entity.enums.RoomStatus;
import com.example.tableTennis.domain.room.entity.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record RoomDetailResponseDto(
    int id,
    String title,
    int hostId,
    RoomType roomType,
    RoomStatus roomStatus,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt
) {

    public static RoomDetailResponseDto from(Room room) {
        return new RoomDetailResponseDto(
            room.getId().intValue(),
            room.getTitle(),
            room.getHostId(),
            room.getRoomType(),
            room.getStatus(),
            room.getCreated_at(),
            room.getUpdated_at()
        );
    }
}
