package com.example.tableTennis.domain.user.impl;

import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.repository.UserJpaRepository;
import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHandlerImpl implements UserHandler {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByUser(int userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ResponseType.FAIL));
    }
}
