package com.example.tableTennis.common.fixture;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import com.example.tableTennis.domain.user.entity.User;

public class RoomParticipantFixture {

    public static UserRoom createByHost(Room room, User user) {
        return UserRoom.builder()
            .room(room)
            .user(user)
            .team(Team.RED)
            .build();
    }

    public static UserRoom create(Room room, User user, Team team) {
        return UserRoom.builder()
            .room(room)
            .user(user)
            .team(team)
            .build();
    }
}
