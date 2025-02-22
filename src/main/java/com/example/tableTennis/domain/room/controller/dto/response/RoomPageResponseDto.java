package com.example.tableTennis.domain.room.controller.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomPageResponseDto {

    private int totalElements;
    private int totalPages;
    private List<RoomResponseDto> roomList;
}

