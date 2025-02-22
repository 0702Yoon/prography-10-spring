package com.example.tableTennis.domain.common.converter;

import com.example.tableTennis.domain.room.entity.enums.RoomStatus;
import jakarta.persistence.AttributeConverter;

public class RoomStatusConverter implements AttributeConverter<RoomStatus, String> {

    @Override
    public String convertToDatabaseColumn(RoomStatus roomStatus) {
        if (roomStatus == null) {
            return null;
        }
        return roomStatus.getDescription();
    }

    @Override
    public RoomStatus convertToEntityAttribute(String roomStatus) {
        return RoomStatus.getInstance(roomStatus);
    }
}
