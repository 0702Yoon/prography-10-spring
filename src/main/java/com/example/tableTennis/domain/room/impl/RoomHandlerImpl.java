package com.example.tableTennis.domain.room.impl;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.repository.RoomJpaRepository;
import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomHandlerImpl implements RoomHandler {

    private final RoomJpaRepository roomRepository;

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Page<Room> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public Room findById(String roomId) {
        return roomRepository.findById(Integer.valueOf(roomId))
            .orElseThrow(() -> new CustomException(ResponseType.FAIL));
    }
}
