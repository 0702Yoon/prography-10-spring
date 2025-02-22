package com.example.tableTennis.domain.room;

import static com.example.tableTennis.global.response.ResponseType.FAIL;
import static com.example.tableTennis.global.response.ResponseType.SUCCESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.tableTennis.common.support.IntegrationTestSupport;
import com.example.tableTennis.common.support.TestDataHelper;
import com.example.tableTennis.domain.room.controller.dto.request.CreateRoomRequestDto;
import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.room.entity.enums.RoomType;
import com.example.tableTennis.domain.userRoom.entity.UserRoom;
import com.example.tableTennis.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


public class RoomControllerTest extends IntegrationTestSupport {

    @Autowired
    private TestDataHelper testDataHelper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void 이미_참여한_유저가_방을_만들려고_하면_실패한다() throws Exception {
        // given
        User user = testDataHelper.saveActiveUser();
        Room room = testDataHelper.saveRoom(user, RoomType.SINGLE);
        UserRoom userRoom = testDataHelper.saveRoomParticipantByHost(room, user);

        CreateRoomRequestDto createRoomRequestDto = new CreateRoomRequestDto(user.getId(),
            RoomType.SINGLE, "test2 Room");
        //when & then
        mockMvc.perform(post("/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRoomRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 활성_유저가_방을_만들려고_하면_성공한다() throws Exception {
        // given
        User user = testDataHelper.saveActiveUser();
        CreateRoomRequestDto createRoomRequestDto = new CreateRoomRequestDto(user.getId(),
            RoomType.SINGLE, "test Tile");
        //when & then
        mockMvc.perform(post("/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRoomRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(SUCCESS.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @Test
    void 비활성_유저가_방을_만들려고_하면_실패한다() throws Exception {
        // given
        User user = testDataHelper.saveNonActiveUser();
        CreateRoomRequestDto createRoomRequestDto = new CreateRoomRequestDto(user.getId(),
            RoomType.SINGLE, "test Tile");
        //when & then
        mockMvc.perform(post("/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRoomRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }

    @Test
    void 존재하지_않는_방의_ID로_조회하면_실패한다() throws Exception {
        int roomId = 2;

        mockMvc.perform(get("/room/{roomId}", roomId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(FAIL.getHttpStatusCode()))
            .andExpect(jsonPath("$.message").value(FAIL.getMessage()));
    }
}
