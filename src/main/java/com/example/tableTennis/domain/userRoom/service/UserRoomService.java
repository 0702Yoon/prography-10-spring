package com.example.tableTennis.domain.userRoom.service;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.impl.RoomHandler;
import com.example.tableTennis.domain.userRoom.controller.dto.request.ChangeTeamRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.EnterRoomRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.ExitRoomRequestDto;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import com.example.tableTennis.domain.userRoom.impl.UserRoomHandler;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.impl.UserHandler;
import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRoomService {

    private final RoomHandler roomHandler;
    private final UserRoomHandler userRoomHandler;
    private final UserHandler userHandler;

    public void createUserRoom(String roomId,
        EnterRoomRequestDto enterRoomRequestDto) {
        Room room = roomHandler.findById(roomId);
        room.checkStatusIsWait();

        User user = userHandler.findByUser(enterRoomRequestDto.userId());
        user.checkStatusIsActive();

        userRoomHandler.checkAndThrowIfUserInRoom(user);
        Team team = userRoomHandler.checkCanEnterRoom(room);

        UserRoom userRoom = UserRoom
            .builder()
            .user(user)
            .room(room)
            .team(team)
            .build();

        userRoomHandler.save(userRoom);
    }

    public void deleteRoomParticipant(String roomId,
        ExitRoomRequestDto exitRoomRequestDto) {
        User user = userHandler.findByUser(exitRoomRequestDto.userId());
        Room room = roomHandler.findById(roomId);
        UserRoom userRoom = userRoomHandler.checkUserInRoom(room, user);
        room.checkStatusIsWait();
        if (room.isHost(user)) {
            userRoomHandler.deleteAll(room);
            room.finsh();
        } else {
            userRoomHandler.delete(userRoom);
        }

    }

    public void updateTeam(String roomId, ChangeTeamRequestDto changeTeamRequestDto) {
        User user = userHandler.findByUser(changeTeamRequestDto.userId());
        Room room = roomHandler.findById(roomId);

        UserRoom userRoom = userRoomHandler.checkUserInRoom(room, user);
        room.checkStatusIsWait();

        Team otherTeam = Team.getOtherTeam(userRoom.getTeam());
        if (userRoomHandler.isTeamCapacityAvailable(room, otherTeam)) {
            userRoom.changeTeam(otherTeam);
        } else {
            throw new CustomException(ResponseType.FAIL);
        }
    }
}
