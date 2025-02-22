package com.example.tableTennis.domain.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.tableTennis.domain.user.entity.enums.UserStatus;
import org.junit.jupiter.api.Test;

public class CommonServiceTest {

    @Test
    void fakerId가_30이하면_ACTIVE_반환() {
        assertEquals(UserStatus.ACTIVE, UserStatus.byFakerId(1));
        assertEquals(UserStatus.ACTIVE, UserStatus.byFakerId(30));
    }

    @Test
    void fakerId가_31에서_60사이면_WAIT_반환() {
        assertEquals(UserStatus.WAIT, UserStatus.byFakerId(31));
        assertEquals(UserStatus.WAIT, UserStatus.byFakerId(60));
    }

    @Test
    void fakerId가_61이상이면_NON_ACTIVE_반환() {
        assertEquals(UserStatus.NON_ACTIVE, UserStatus.byFakerId(61));
    }

}
