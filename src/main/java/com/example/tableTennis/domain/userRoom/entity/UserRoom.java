package com.example.tableTennis.domain.userRoom.entity;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import com.example.tableTennis.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @Enumerated(EnumType.STRING)
    private Team team;

    @Builder
    public UserRoom(User user, Room room, Team team) {
        this.user = user;
        this.room = room;
        this.team = team;
    }

    public void changeTeam(Team team) {
        this.team = team;
    }
}

