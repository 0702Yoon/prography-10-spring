package com.example.tableTennis.domain.userRoom.repository;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {

    Optional<UserRoom> findByUser(User user);

    List<UserRoom> findByRoom(Room room);

    Optional<UserRoom> findByRoomAndUser(Room room, User user);

    void deleteAllByRoom(Room room);

    List<UserRoom> findByRoomAndTeam(Room room, Team team);
}
