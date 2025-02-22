package com.example.tableTennis.common.support;

import com.example.tableTennis.common.fixture.RoomFixture;
import com.example.tableTennis.common.fixture.RoomParticipantFixture;
import com.example.tableTennis.common.fixture.UserFixture;
import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.entity.enums.RoomType;
import com.example.tableTennis.domain.room.repository.RoomJpaRepository;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import com.example.tableTennis.domain.userRoom.impl.UserRoomHandler;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.repository.UserJpaRepository;
import com.example.tableTennis.domain.userRoom.repository.UserRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataHelper {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private RoomJpaRepository roomJpaRepository;
    @Autowired
    private UserRoomRepository userRoomRepository;
    @Autowired
    private UserRoomHandler userRoomHandler;

    public User saveActiveUser() {
        User user = UserFixture.createActiveUser();
        return userJpaRepository.save(user);
    }

    public User saveNonActiveUser() {
        User user = UserFixture.createNonActiveUser();
        return userJpaRepository.save(user);
    }

    public Room saveRoom(User host, RoomType roomType) {
        Room room = RoomFixture.createRoom(host, roomType);
        return roomJpaRepository.save(room);
    }

    public UserRoom saveRoomParticipantByHost(Room room, User host) {
        UserRoom userRoom = RoomParticipantFixture.createByHost(room, host);
        return userRoomRepository.save(userRoom);
    }

    public UserRoom saveRoomParticipant(Room room, User user, Team team) {
        UserRoom userRoom = RoomParticipantFixture.create(room, user, team);
        return userRoomRepository.save(userRoom);
    }

    public void fillRoom(Room room) {
        int count = userRoomRepository.findByRoom(room).size();
        int capacity = room.getRoomType().getRoomCapacity();
        for (int i = count; i < capacity; i++) {
            User user = saveActiveUser();
            Team team = userRoomHandler.checkCanEnterRoom(room);
            saveRoomParticipant(room, user, team);
        }
    }
}