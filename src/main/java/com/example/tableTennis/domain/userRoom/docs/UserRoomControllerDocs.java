package com.example.tableTennis.domain.userRoom.docs;

import com.example.tableTennis.domain.userRoom.controller.dto.request.ChangeTeamRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.EnterRoomRequestDto;
import com.example.tableTennis.domain.userRoom.controller.dto.request.ExitRoomRequestDto;
import com.example.tableTennis.global.response.ApiResponse;
import com.example.tableTennis.global.response.ResponseType;
import com.example.tableTennis.global.response.example.error.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserRoom API", description = "만들어진 방에 대한 API")
public interface UserRoomControllerDocs {

    @Operation(summary = "방에 참가하기", description = "해당 방에 들어가 있지 않은 회원이 방에 참가한다.")
    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    ApiResponse<?> enterRoom(
        String roomId, EnterRoomRequestDto enterRoomRequestDto
    );

    @Operation(summary = "방에서 나가기",
        description = "방에 참가한 참여자가 방을 나간다. 만약 나간 참여자가 호스트라면 방은 FINSH 상태가 된다.")
    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    ApiResponse<?> exitRoom(
        String roomId, ExitRoomRequestDto exitRoomRequestDto
    );

    @Operation(summary = "팀 바꾸기",
        description = "참가자가 현재 팀에서 반대 팀으로 넘어간다.")
    @ApiErrorCodeExamples({ResponseType.FAIL, ResponseType.ERROR})
    ApiResponse<?> changeTeam(
        String roomId, ChangeTeamRequestDto changeTeamRequestDto
    );
}
