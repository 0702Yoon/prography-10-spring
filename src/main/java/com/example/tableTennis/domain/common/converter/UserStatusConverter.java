package com.example.tableTennis.domain.common.converter;

import com.example.tableTennis.domain.user.entity.enums.UserStatus;
import jakarta.persistence.AttributeConverter;


public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        if (userStatus == null) {
            return null;
        }
        return userStatus.getDescription();
    }

    @Override
    public UserStatus convertToEntityAttribute(String userStatus) {
        return UserStatus.getInstance(userStatus);
    }
}
