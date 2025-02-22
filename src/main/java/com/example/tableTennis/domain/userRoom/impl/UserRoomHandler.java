package com.example.tableTennis.domain.userRoom.impl;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import com.example.tableTennis.domain.user.entity.User;
import java.util.List;
import org.springframework.stereotype.Component;

public interface UserRoomHandler {

    void checkAndThrowIfUserInRoom(User user);

    Team checkCanEnterRoom(Room room);

    void save(UserRoom userRoom);

    UserRoom checkUserInRoom(Room room, User user);

    void delete(UserRoom userRoom);

    void deleteAll(Room room);

    void checkFullRoom(Room room);

    boolean isTeamCapacityAvailable(Room room, Team team);

}
