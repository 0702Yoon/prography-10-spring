package com.example.tableTennis.domain.userRoom;

import static com.example.tableTennis.global.response.ResponseType.FAIL;
import static com.example.tableTennis.global.response.ResponseType.SUCCESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.tableTennis.common.support.IntegrationTestSupport;
import com.example.tableTennis.common.support.TestDataHelper;
import com.example.tableTennis.domain.room.controller.dto.request.StartRoomRequestDto;
import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.entity.enums.RoomType;
import com.example.tableTennis.domain.userRoom.controller.dto.request.ChangeTeamRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.EnterRoomRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.ExitRoomRequestDto;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.userRoom.entity.enums.Team;
import com.example.tableTennis.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class UserRoomControllerTest extends IntegrationTestSupport {

    @Autowired
    private TestDataHelper testDataHelper;
    private User room1Host;
    private Room room1;
    private UserRoom userRoom1;

    @BeforeEach
    void setIp() {
        // 활성유저, 방, 참가까지 한 상태
        room1Host = testDataHelper.saveActiveUser();
        room1 = testDataHelper.saveRoom(room1Host, RoomType.SINGLE);
        userRoom1 = testDataHelper.saveRoomParticipantByHost(room1, room1Host);
    }

    @Test
    void 대기상태인_방에_참가하면_성공한다() throws Exception {
        // given
        User user = testDataHelper.saveActiveUser();
        Integer roomId = room1.getId();
        EnterRoomRequestDto requestDto = new EnterRoomRequestDto(user.getId());
        // when & then
        mockMvc.perform(post("/room/attention/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(SUCCESS.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @Test
    void 대기상태가_아닌_방에_참가하면_실패한다() throws Exception {
        // given
        User user = testDataHelper.saveActiveUser();
        room1.endGame();
        Integer roomId = room1.getId();
        EnterRoomRequestDto requestDto = new EnterRoomRequestDto(user.getId());

        // when & then
        mockMvc.perform(post("/room/attention/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 활성상태가_아닌_유저가_참가하면_실패한다() throws Exception {
        // given
        User user = testDataHelper.saveNonActiveUser();
        Integer roomId = room1.getId();
        EnterRoomRequestDto requestDto = new EnterRoomRequestDto(user.getId());
        // when & then
        mockMvc.perform(post("/room/attention/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 방에_참여한_유저가_다른방에_참가하면_실패한다() throws Exception {
        // given
        User user = testDataHelper.saveActiveUser();
        Room room2 = testDataHelper.saveRoom(user, RoomType.SINGLE);
        testDataHelper.saveRoomParticipantByHost(room2, user);

        Integer room2Id = room2.getId();
        EnterRoomRequestDto requestDto = new EnterRoomRequestDto(room1Host.getId());
        // when & then
        mockMvc.perform(post("/room/attention/{roomId}", room2Id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 정원이_다_찼을_때_방참가는_실패한다() throws Exception {
        // given
        testDataHelper.fillRoom(room1);

        User user2 = testDataHelper.saveActiveUser();
        EnterRoomRequestDto requestDto = new EnterRoomRequestDto(user2.getId());
        Integer roomId = room1.getId();

        // when & then
        mockMvc.perform(post("/room/attention/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 참여자가_아닌_유저가_방나가기하면_실패() throws Exception {
        // given
        User user = testDataHelper.saveActiveUser();
        Integer roomId = room1.getId();
        ExitRoomRequestDto exitRoomRequestDto = new ExitRoomRequestDto(user.getId());

        // when & then
        mockMvc.perform(post("/room/out/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exitRoomRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 시작된_방을_나기기하면_실패() throws Exception {
        // given
        testDataHelper.fillRoom(room1);
        room1.startGame();
        int roomId = room1.getId();
        ExitRoomRequestDto exitRoomRequestDto = new ExitRoomRequestDto(room1Host.getId());

        // when & then
        mockMvc.perform(post("/room/out/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exitRoomRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 호스트가_아닌_유저가_시작하면_실패한다() throws Exception {
        // given
        User room2Host = testDataHelper.saveActiveUser();
        testDataHelper.saveRoomParticipantByHost(room1, room2Host);
        testDataHelper.fillRoom(room1);

        StartRoomRequestDto startRoomRequestDto = new StartRoomRequestDto(room2Host.getId());
        int roomId = room1.getId();
        // when & then
        mockMvc.perform(put("/room/start/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(startRoomRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 정원이_차지않은_방이_시작하면_실패한다() throws Exception {
        // given
        StartRoomRequestDto startRoomRequestDto = new StartRoomRequestDto(room1Host.getId());
        int room1Id = room1.getId();
        // when & then
        mockMvc.perform(put("/room/start/{roomId}", room1Id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(startRoomRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 대기가_아닐_때_시작하면_실패한다() throws Exception {
        // given
        testDataHelper.fillRoom(room1);
        room1.startGame();
        StartRoomRequestDto startRoomRequestDto = new StartRoomRequestDto(room1Host.getId());
        int roomId = room1.getId();

        // when & then
        mockMvc.perform(put("/room/start/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(startRoomRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 참가자가_아닌_유저가_팀_변경을_하면_실패한다() throws Exception {
        //given
        User user = testDataHelper.saveActiveUser();
        ChangeTeamRequestDto changeTeamRequestDto = new ChangeTeamRequestDto(user.getId());
        int roomId = room1.getId();
        // when & then
        mockMvc.perform(put("/team/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeTeamRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 변경되려는_팀이_정원이_찼다면_팀_변경을_실패한다() throws Exception {
        //given
        User user = testDataHelper.saveActiveUser();
        testDataHelper.saveRoomParticipant(room1, user, Team.BLUE);

        ChangeTeamRequestDto changeTeamRequestDto = new ChangeTeamRequestDto(user.getId());
        int roomId = room1.getId();
        // when & then
        mockMvc.perform(put("/team/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeTeamRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 대기상태가_아닌_방에선_팀_변경을_실패한다() throws Exception {
        // given
        User room2Host = testDataHelper.saveActiveUser();
        Room room2 = testDataHelper.saveRoom(room2Host, RoomType.DOUBLE);
        testDataHelper.saveRoomParticipantByHost(room2, room2Host);
        User user = testDataHelper.saveActiveUser();
        testDataHelper.saveRoomParticipant(room2, user, Team.BLUE);
        room2.startGame();
        ChangeTeamRequestDto changeTeamRequestDto = new ChangeTeamRequestDto(user.getId());
        int roomId = room2.getId();
        // when&then
        mockMvc.perform(put("/team/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeTeamRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 대기상태인_방에선_팀_변경을_성공한다() throws Exception {
        // given
        User room2Host = testDataHelper.saveActiveUser();
        Room room2 = testDataHelper.saveRoom(room2Host, RoomType.DOUBLE);
        testDataHelper.saveRoomParticipantByHost(room2, room2Host);
        User user = testDataHelper.saveActiveUser();
        testDataHelper.saveRoomParticipant(room2, user, Team.BLUE);
        ChangeTeamRequestDto changeTeamRequestDto = new ChangeTeamRequestDto(user.getId());
        int roomId = room2.getId();
        // when&then
        mockMvc.perform(put("/team/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeTeamRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(SUCCESS.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }
}
