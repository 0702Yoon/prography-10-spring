package com.example.tableTennis.domain.common.converter;

import com.example.tableTennis.domain.room.entity.enums.RoomType;
import jakarta.persistence.AttributeConverter;

public class RoomTypeConverter implements AttributeConverter<RoomType, String> {

    @Override
    public String convertToDatabaseColumn(RoomType roomType) {
        if (roomType == null) {
            return null;
        }
        return roomType.getDescription();
    }

    @Override
    public RoomType convertToEntityAttribute(String roomType) {
        return RoomType.getInstance(roomType);
    }
}
