package com.example.tableTennis.domain.user.controller.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPageResponse {

    private Integer totalElements;
    private Integer totalPages;
    private List<UserResponseDto> userList;

}
