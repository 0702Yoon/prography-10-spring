package com.example.tableTennis.domain.room.controller.dto.request;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.entity.enums.RoomType;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import com.example.tableTennis.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateRoomRequestDto(
    @Schema(description = "방을 만들 유저 아이디")
    Integer userId,
    @Schema(description = "방 타입", example = "SINGLE or DOUBLE")
    RoomType roomType,
    @Schema(description = "방 제목")
    String title
) {

    public Room toRoomEntity(User host) {
        return Room.builder()
            .title(title)
            .host(host)
            .roomType(roomType)
            .build();
    }

    public UserRoom toRoomAttentionEntity(Room room, User host) {
        return UserRoom
            .builder()
            .user(host)
            .room(room)
            .team(Team.RED)
            .build();
    }
}
