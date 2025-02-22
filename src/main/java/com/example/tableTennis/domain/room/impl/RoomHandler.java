package com.example.tableTennis.domain.room.impl;

import com.example.tableTennis.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomHandler {

    void save(Room room);

    Page<Room> findAll(Pageable pageable);

    Room findById(String roomId);

}
