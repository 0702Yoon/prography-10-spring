package com.example.tableTennis.domain.room.controller;

import com.example.tableTennis.domain.room.controller.dto.request.CreateRoomRequestDto;
import com.example.tableTennis.domain.room.controller.dto.request.StartRoomRequestDto;
import com.example.tableTennis.domain.room.controller.dto.response.RoomDetailResponseDto;
import com.example.tableTennis.domain.room.controller.dto.response.RoomPageResponseDto;
import com.example.tableTennis.domain.room.docs.RoomControllerDocs;
import com.example.tableTennis.domain.room.service.RoomService;
import com.example.tableTennis.global.response.ApiResponse;
import com.example.tableTennis.global.response.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController implements RoomControllerDocs {

    private final RoomService roomService;

    @Override
    @PostMapping
    public ApiResponse<?> createRoom(
        @RequestBody CreateRoomRequestDto createRoomRequestDto
    ) {
        roomService.createRoom(createRoomRequestDto);
        return ApiResponse.create(ResponseType.SUCCESS);
    }

    @Override
    @GetMapping
    public ApiResponse<RoomPageResponseDto> getAllRoom(
        @RequestParam(name = "page", required = false) Integer page,
        @RequestParam(name = "size", required = false) Integer size
    ) {
        Pageable pageable = (page == null || size == null)
            ? Pageable.unpaged()
            : PageRequest.of(page, size, Sort.by("id").ascending());
        RoomPageResponseDto roomPageResponseDtoList = roomService.getAllRoom(pageable);
        return ApiResponse.create(ResponseType.SUCCESS, roomPageResponseDtoList);
    }

    @Override
    @GetMapping("/{roomId}")
    public ApiResponse<RoomDetailResponseDto> getRoomDetail(
        @PathVariable(name = "roomId") String roomId
    ) {
        RoomDetailResponseDto roomDetailResponseDto = roomService.getRoomDetail(roomId);
        return ApiResponse.create(ResponseType.SUCCESS, roomDetailResponseDto);
    }

    @Override
    @PutMapping("/start/{roomId}")
    public ApiResponse<?> startRoom(
        @PathVariable(name = "roomId") String roomId,
        @RequestBody StartRoomRequestDto startRoomRequestDto
    ) {
        roomService.startRoom(roomId, startRoomRequestDto);
        return ApiResponse.create(ResponseType.SUCCESS);
    }
}
