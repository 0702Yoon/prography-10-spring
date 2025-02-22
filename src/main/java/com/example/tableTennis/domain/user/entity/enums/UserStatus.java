package com.example.tableTennis.domain.user.entity.enums;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    WAIT("대기"), ACTIVE("활성"), NON_ACTIVE("비활성");

    private final String description;

    public static UserStatus getInstance(String userStatus) {
        return Arrays.stream(values())
            .filter(one -> one.getDescription().equals(userStatus))
            .findFirst()
            .orElseThrow(EntityNotFoundException::new);
    }

    public static UserStatus byFakerId(int fakerId) {
        if (fakerId <= 30) {
            return ACTIVE;
        }
        if (fakerId <= 60) {
            return WAIT;
        }
        return NON_ACTIVE;
    }
}
