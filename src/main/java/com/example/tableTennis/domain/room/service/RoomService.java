package com.example.tableTennis.domain.room.service;

import com.example.tableTennis.domain.room.controller.dto.request.CreateRoomRequestDto;
import com.example.tableTennis.domain.room.controller.dto.request.StartRoomRequestDto;
import com.example.tableTennis.domain.room.controller.dto.response.RoomDetailResponseDto;
import com.example.tableTennis.domain.room.controller.dto.response.RoomPageResponseDto;
import com.example.tableTennis.domain.room.controller.dto.response.RoomResponseDto;
import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.impl.RoomHandler;
import com.example.tableTennis.domain.room.impl.RoomStatusScheduler;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.impl.UserRoomHandler;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.impl.UserHandler;
import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final UserHandler userHandler;
    private final UserRoomHandler userRoomHandler;
    private final RoomHandler roomHandler;
    private final RoomStatusScheduler roomStatusScheduler;

    public void createRoom(CreateRoomRequestDto createRoomRequestDto) {

        User host = userHandler.findByUser(createRoomRequestDto.userId());
        host.checkStatusIsActive();

        userRoomHandler.checkAndThrowIfUserInRoom(host);

        Room room = createRoomRequestDto.toRoomEntity(host);
        roomHandler.save(room);
        UserRoom userRoom = createRoomRequestDto.toRoomAttentionEntity(room, host);
        userRoomHandler.save(userRoom);
    }

    @Transactional(readOnly = true)
    public RoomPageResponseDto getAllRoom(Pageable pageable) {

        Page<Room> page = roomHandler.findAll(pageable);
        List<RoomResponseDto> content = page.getContent()
            .stream()
            .map(RoomResponseDto::from)
            .toList();

        return new RoomPageResponseDto((int) page.getTotalElements(), page.getTotalPages(),
            content);
    }

    @Transactional(readOnly = true)
    public RoomDetailResponseDto getRoomDetail(String roomId) {

        Room room = roomHandler.findById(roomId);
        return RoomDetailResponseDto.from(room);
    }

    public void startRoom(String roomId, StartRoomRequestDto startRoomRequestDto) {
        User user = userHandler.findByUser(startRoomRequestDto.userId());
        Room room = roomHandler.findById(roomId);
        if (!room.isHost(user)) {
            throw new CustomException(ResponseType.FAIL);
        }
        userRoomHandler.checkFullRoom(room);
        room.checkStatusIsWait();
        room.startGame();

        roomStatusScheduler.scheduleRoomStateChange(roomId);
    }
}
