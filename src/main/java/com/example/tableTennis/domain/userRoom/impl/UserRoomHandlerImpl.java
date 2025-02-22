package com.example.tableTennis.domain.userRoom.impl;

import static com.example.tableTennis.domain.room.entity.enums.RoomType.NUMBER_OF_TEAM;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.userRoom.repository.UserRoomRepository;
import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRoomHandlerImpl implements UserRoomHandler {

    private final UserRoomRepository userRoomRepository;

    @Override
    public void checkAndThrowIfUserInRoom(User user) {
        if (userRoomRepository.findByUser(user).isPresent()) {
            throw new CustomException(ResponseType.FAIL);
        }
    }

    public boolean isTeamCapacityAvailable(Room room, Team team) {
        List<UserRoom> teamParticipants = userRoomRepository.findByRoomAndTeam(
            room, team);
        return teamParticipants.size() < (room.getRoomType().getRoomCapacity() / NUMBER_OF_TEAM);
    }

    @Override
    public Team checkCanEnterRoom(Room room) {
        if (isTeamCapacityAvailable(room, Team.RED)) {
            return Team.RED;
        }
        if (isTeamCapacityAvailable(room, Team.BLUE)) {
            return Team.BLUE;
        }
        throw new CustomException(ResponseType.FAIL);
    }


    @Override
    public void save(UserRoom userRoom) {
        userRoomRepository.save(userRoom);
    }

    @Override
    public UserRoom checkUserInRoom(Room room, User user) {
        return userRoomRepository.findByRoomAndUser(room, user)
            .orElseThrow(() -> new CustomException(ResponseType.FAIL));
    }

    @Override
    public void delete(UserRoom userRoom) {
        userRoomRepository.delete(userRoom);
    }

    @Override
    public void deleteAll(Room room) {
        userRoomRepository.deleteAllByRoom(room);
    }

    @Override
    public void checkFullRoom(Room room) {
        if ((isTeamCapacityAvailable(room, Team.RED))
            || (isTeamCapacityAvailable(room, Team.BLUE))) {
            throw new CustomException(ResponseType.FAIL);
        }
    }
}
