package com.example.tableTennis.domain.user.service;

import com.example.tableTennis.domain.user.controller.dto.response.UserPageResponse;
import com.example.tableTennis.domain.user.controller.dto.response.UserResponseDto;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.repository.UserJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userRepository;

    @Transactional(readOnly = true)
    public UserPageResponse getAllUsers(Pageable pageable) {

        Page<User> page = userRepository.findAll(pageable);
        List<UserResponseDto> content = page.getContent()
            .stream()
            .map(UserResponseDto::from)
            .toList();

        return new UserPageResponse((int) page.getTotalElements(), page.getTotalPages(), content);
    }
}
