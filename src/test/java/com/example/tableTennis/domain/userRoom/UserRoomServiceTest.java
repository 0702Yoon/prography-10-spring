package com.example.tableTennis.domain.userRoom;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.impl.RoomHandler;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.impl.UserHandler;
import com.example.tableTennis.domain.userRoom.controller.dto.request.ChangeTeamRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.EnterRoomRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.ExitRoomRequestDto;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import com.example.tableTennis.domain.userRoom.impl.UserRoomHandler;
import com.example.tableTennis.domain.userRoom.service.UserRoomService;
import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRoomServiceTest {

    @InjectMocks
    private UserRoomService userRoomService;
    @Mock
    private UserHandler userHandler;
    @Mock
    private RoomHandler roomHandler;
    @Mock
    private UserRoomHandler userRoomHandler;


    @Test
    void 유저가_팀을_변경할_수_있으면_성공한다() {
        // given
        String roomId = "1";
        ChangeTeamRequestDto requestDto = new ChangeTeamRequestDto(1);

        User user = mock(User.class);
        Room room = mock(Room.class);
        UserRoom userRoom = mock(UserRoom.class);
        Team currentTeam = Team.RED;
        Team otherTeam = Team.BLUE;

        when(userHandler.findByUser(requestDto.userId())).thenReturn(user);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userRoomHandler.checkUserInRoom(room, user)).thenReturn(userRoom);
        when(userRoom.getTeam()).thenReturn(currentTeam);
        doNothing().when(room).checkStatusIsWait();
        when(userRoomHandler.isTeamCapacityAvailable(room, otherTeam)).thenReturn(true);

        // when
        userRoomService.updateTeam(roomId, requestDto);

        // then
        verify(userRoom).changeTeam(otherTeam);
    }

    @Test
    void 팀에_자리가_없으면_팀_변경을_실패한다() {
        // given
        String roomId = "1";
        ChangeTeamRequestDto requestDto = new ChangeTeamRequestDto(1);

        User user = mock(User.class);
        Room room = mock(Room.class);
        UserRoom userRoom = mock(UserRoom.class);
        Team currentTeam = Team.RED;
        Team otherTeam = Team.BLUE;

        when(userHandler.findByUser(requestDto.userId())).thenReturn(user);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userRoomHandler.checkUserInRoom(room, user)).thenReturn(userRoom);
        when(userRoom.getTeam()).thenReturn(currentTeam);
        doNothing().when(room).checkStatusIsWait();
        when(userRoomHandler.isTeamCapacityAvailable(room, otherTeam)).thenReturn(false);

        // when & then
        assertThrows(CustomException.class, () -> userRoomService.updateTeam(roomId, requestDto));
    }

    @Test
    void 대기가_아닌_방에_참여할_시_실패한다() {
        // given
        String roomId = "1";
        EnterRoomRequestDto enterRoomRequestDto = new EnterRoomRequestDto(1);
        Room room = mock(Room.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        doThrow(new CustomException(ResponseType.FAIL)).when(room).checkStatusIsWait();

        // when & then
        assertThrows(CustomException.class,
            () -> userRoomService.createUserRoom(roomId, enterRoomRequestDto));
        verify(room).checkStatusIsWait();
    }

    @Test
    void 이미_방에_참여한_유저가_방_참가_시_실패() {
        // given
        String roomId = "1";
        EnterRoomRequestDto enterRoomRequestDto = new EnterRoomRequestDto(1);
        Room room = mock(Room.class);
        User user = mock(User.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userHandler.findByUser(enterRoomRequestDto.userId())).thenReturn(user);
        doThrow(new CustomException(ResponseType.FAIL)).when(userRoomHandler)
            .checkAndThrowIfUserInRoom(user);

        // when & then
        assertThrows(CustomException.class,
            () -> userRoomService.createUserRoom(roomId, enterRoomRequestDto));
        verify(userRoomHandler).checkAndThrowIfUserInRoom(user);
    }

    @Test
    void 비활성유저가_방_참가_시_실패() {
        // given
        String roomId = "1";
        EnterRoomRequestDto enterRoomRequestDto = new EnterRoomRequestDto(1);
        Room room = mock(Room.class);
        User user = mock(User.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userHandler.findByUser(enterRoomRequestDto.userId())).thenReturn(user);
        doThrow(new CustomException(ResponseType.FAIL)).when(user).checkStatusIsActive();
        // when & then
        assertThrows(CustomException.class,
            () -> userRoomService.createUserRoom(roomId, enterRoomRequestDto));
    }

    @Test
    void 방의_정원이_꽉찬_방에_참가_시_실패() {
        // given
        String roomId = "1";
        EnterRoomRequestDto enterRoomRequestDto = new EnterRoomRequestDto(1);
        Room room = mock(Room.class);
        User user = mock(User.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userHandler.findByUser(enterRoomRequestDto.userId())).thenReturn(user);
        doThrow(new CustomException(ResponseType.FAIL)).when(userRoomHandler)
            .checkCanEnterRoom(room);
        // when & then
        assertThrows(CustomException.class,
            () -> userRoomService.createUserRoom(roomId, enterRoomRequestDto));
    }

    @Test
    void 미참여자가_나갈_경우_실패() {
        // given
        String roomId = "1";
        ExitRoomRequestDto exitRoomRequestDto = new ExitRoomRequestDto(1);
        Room room = mock(Room.class);
        User user = mock(User.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userHandler.findByUser(exitRoomRequestDto.userId())).thenReturn(user);
        doThrow(new CustomException(ResponseType.FAIL)).when(userRoomHandler)
            .checkUserInRoom(room, user);
        // when & then
        assertThrows(CustomException.class,
            () -> userRoomService.deleteRoomParticipant(roomId, exitRoomRequestDto));
        verify(room, times(0)).checkStatusIsWait();
    }

    @Test
    void 대기_상태가_아닌_방을_나갈_경우_실패() {
        // given
        String roomId = "1";
        ExitRoomRequestDto exitRoomRequestDto = new ExitRoomRequestDto(1);
        Room room = mock(Room.class);
        User user = mock(User.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userHandler.findByUser(exitRoomRequestDto.userId())).thenReturn(user);
        doThrow(new CustomException(ResponseType.FAIL)).when(room)
            .checkStatusIsWait();
        // when & then
        assertThrows(CustomException.class,
            () -> userRoomService.deleteRoomParticipant(roomId, exitRoomRequestDto));
        verify(room, times(0)).isHost(user);
    }

    @Test
    void 호스트가_나갈_시_방은_완료가_된다() {
        // given
        String roomId = "1";
        ExitRoomRequestDto exitRoomRequestDto = new ExitRoomRequestDto(1);
        Room room = mock(Room.class);
        User user = mock(User.class);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userHandler.findByUser(exitRoomRequestDto.userId())).thenReturn(user);
        doReturn(true).when(room)
            .isHost(user);
        // when
        userRoomService.deleteRoomParticipant(roomId, exitRoomRequestDto);

        // then
        verify(room).finsh();
    }


    @Test
    void 미참여자가_팀_변경을_실패한다() {
        // given
        String roomId = "1";
        ChangeTeamRequestDto requestDto = new ChangeTeamRequestDto(1);

        User user = mock(User.class);
        Room room = mock(Room.class);
        UserRoom userRoom = mock(UserRoom.class);
        Team currentTeam = Team.RED;
        Team otherTeam = Team.BLUE;

        when(userHandler.findByUser(requestDto.userId())).thenReturn(user);
        when(roomHandler.findById(roomId)).thenReturn(room);
        doThrow(new CustomException(ResponseType.FAIL)).when(
            userRoomHandler).checkUserInRoom(room, user);

        // when & then
        assertThrows(CustomException.class, () -> userRoomService.updateTeam(roomId, requestDto));
    }

    @Test
    void 대기_상태가_아닌_방에선_팀변경을_실패한다() {
        // given
        String roomId = "1";
        ChangeTeamRequestDto requestDto = new ChangeTeamRequestDto(1);

        User user = mock(User.class);
        Room room = mock(Room.class);
        when(userHandler.findByUser(requestDto.userId())).thenReturn(user);
        when(roomHandler.findById(roomId)).thenReturn(room);
        doThrow(new CustomException(ResponseType.FAIL)).when(
            room).checkStatusIsWait();

        // when & then
        assertThrows(CustomException.class, () -> userRoomService.updateTeam(roomId, requestDto));
    }

    @Test
    void 다른팀_정원이_찼을_경우_팀변경을_실패한다() {
        // given
        String roomId = "1";
        ChangeTeamRequestDto requestDto = new ChangeTeamRequestDto(1);

        User user = mock(User.class);
        Room room = mock(Room.class);
        UserRoom userRoom = mock(UserRoom.class);
        when(userHandler.findByUser(requestDto.userId())).thenReturn(user);
        when(roomHandler.findById(roomId)).thenReturn(room);
        when(userRoomHandler.checkUserInRoom(room, user)).thenReturn(userRoom);
        when(userRoom.getTeam()).thenReturn(Team.RED);

        doReturn(false).when(userRoomHandler).isTeamCapacityAvailable(eq(room), any(Team.class));

        // when & then
        assertThrows(CustomException.class, () -> userRoomService.updateTeam(roomId, requestDto));
    }
}

