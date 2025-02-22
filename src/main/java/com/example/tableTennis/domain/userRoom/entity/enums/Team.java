package com.example.tableTennis.domain.userRoom.entity.enums;

import lombok.Getter;

@Getter
public enum Team {
    RED, BLUE;

    public static Team getOtherTeam(Team team) {
        return switch (team) {
            case RED -> BLUE;
            case BLUE -> RED;
        };
    }
}
