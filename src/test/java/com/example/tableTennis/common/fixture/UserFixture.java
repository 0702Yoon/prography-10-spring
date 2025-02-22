package com.example.tableTennis.common.fixture;

import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.entity.enums.UserStatus;
import java.util.concurrent.atomic.AtomicInteger;

public class UserFixture {

    private static final AtomicInteger fakeId = new AtomicInteger(1);

    public static User createNonActiveUser() {
        return new User(
            fakeId.getAndIncrement(),
            "test이름",
            "test@email.com",
            UserStatus.NON_ACTIVE);
    }

    public static User createActiveUser() {
        return new User(
            fakeId.getAndIncrement(),
            "test이름",
            "test@email.com",
            UserStatus.ACTIVE);
    }
}
