package com.example.tableTennis.domain.user.controller.dto.response;

import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.entity.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record UserResponseDto(
    Integer id,
    Integer fakerId,
    String name,
    String email,
    UserStatus status,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt
) {

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getFakerId(),
            user.getName(),
            user.getEmail(),
            user.getUserStatus(),
            user.getCreated_at(),
            user.getUpdated_at()
        );
    }
}
