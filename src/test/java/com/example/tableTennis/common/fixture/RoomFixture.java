package com.example.tableTennis.common.fixture;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.entity.enums.RoomType;
import com.example.tableTennis.domain.user.entity.User;

public class RoomFixture {

    public static Room createRoom(User user, RoomType roomType) {
        return Room.builder()
            .roomType(roomType)
            .title("테스트용 방제목")
            .host(user)
            .build();
    }
}
