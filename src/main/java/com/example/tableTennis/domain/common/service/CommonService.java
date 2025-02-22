package com.example.tableTennis.domain.common.service;

import com.example.tableTennis.domain.common.controller.dto.request.InitializeRequestDto;
import com.example.tableTennis.domain.common.impl.OutApiClient;
import com.example.tableTennis.domain.common.repository.CommonRepository;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.entity.enums.UserStatus;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonService {

    private final CommonRepository commonRepository;
    private final OutApiClient outApiClient;

    public void initialize(InitializeRequestDto initializeRequestDto) {
        commonRepository.deleteAllData();

        JsonNode dataNode = outApiClient.fakerApi(initializeRequestDto.seed(),
            initializeRequestDto.quantity());

        List<User> userList = new ArrayList<>();
        for (JsonNode node : dataNode) {
            int fakerId = node.get("id").asInt();
            String userName = node.get("username").asText();
            String email = node.get("email").asText();

            User user = User.
                builder()
                .fakerId(fakerId)
                .name(userName)
                .email(email)
                .userStatus(UserStatus.byFakerId(fakerId))
                .build();

            userList.add(user);
        }
        
        userList.sort(Comparator.comparingInt(User::getFakerId));
        commonRepository.saveUserList(userList);
    }

}
