package com.example.tableTennis.domain.room;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.tableTennis.domain.room.controller.dto.request.CreateRoomRequestDto;
import com.example.tableTennis.domain.room.controller.dto.request.StartRoomRequestDto;
import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.entity.enums.RoomType;
import com.example.tableTennis.domain.room.impl.RoomHandler;
import com.example.tableTennis.domain.room.service.RoomService;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.impl.UserHandler;
import com.example.tableTennis.domain.userRoom.impl.UserRoomHandler;
import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;
    @Mock
    private UserHandler userHandler;
    @Mock
    private RoomHandler roomHandler;
    @Mock
    private UserRoomHandler userRoomHandler;

    @Test
    void 비활성_유저면_방_생성_실패() {
        // given
        User user = mock(User.class);
        CreateRoomRequestDto createRoomRequestDto = new CreateRoomRequestDto(1, RoomType.SINGLE,
            "title");

        when(userHandler.findByUser(createRoomRequestDto.userId())).thenReturn(user);
        doThrow(new CustomException(ResponseType.FAIL)).when(user).checkStatusIsActive();

        // when & then
        assertThrows(CustomException.class, () -> roomService.createRoom(createRoomRequestDto));
    }

    @Test
    void 이미_방에_참여한_유저가_방_생성_시_실패() {
        // given
        User user = mock(User.class);
        CreateRoomRequestDto createRoomRequestDto = new CreateRoomRequestDto(1, RoomType.SINGLE,
            "title");

        when(userHandler.findByUser(createRoomRequestDto.userId())).thenReturn(user);
        doThrow(new CustomException(ResponseType.FAIL)).when(userRoomHandler)
            .checkAndThrowIfUserInRoom(user);

        // when & then
        assertThrows(CustomException.class, () -> roomService.createRoom(createRoomRequestDto));
    }

    @Test
    void 호스트가_아닌_유저가_시작_시_실패() {
        // given
        String roomId = "1";
        StartRoomRequestDto startRoomRequestDto = new StartRoomRequestDto(1);
        Room room = mock(Room.class);
        User user = mock(User.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userHandler.findByUser(startRoomRequestDto.userId())).thenReturn(user);
        doReturn(false).when(room)
            .isHost(user);
        // when & then
        assertThrows(CustomException.class,
            () -> roomService.startRoom(roomId, startRoomRequestDto));
        verify(room, times(0)).startGame();
    }

    @Test
    void 정원이_차지않은_방_시작_시_실패() {
        // given
        String roomId = "1";
        StartRoomRequestDto startRoomRequestDto = new StartRoomRequestDto(1);
        Room room = mock(Room.class);
        User user = mock(User.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userHandler.findByUser(startRoomRequestDto.userId())).thenReturn(user);
        doReturn(true).when(room)
            .isHost(user);
        doThrow(new CustomException(ResponseType.FAIL)).when(userRoomHandler).checkFullRoom(room);
        // when & then
        assertThrows(CustomException.class,
            () -> roomService.startRoom(roomId, startRoomRequestDto));
        verify(room, times(0)).startGame();
    }

    @Test
    void 대기_상태인_방_시작_시_실패() {
        // given
        String roomId = "1";
        StartRoomRequestDto startRoomRequestDto = new StartRoomRequestDto(1);
        Room room = mock(Room.class);
        User user = mock(User.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userHandler.findByUser(startRoomRequestDto.userId())).thenReturn(user);
        doReturn(true).when(room)
            .isHost(user);
        doThrow(new CustomException(ResponseType.FAIL)).when(room).checkStatusIsWait();
        // when & then
        assertThrows(CustomException.class,
            () -> roomService.startRoom(roomId, startRoomRequestDto));
        verify(room, times(0)).startGame();
    }
}
