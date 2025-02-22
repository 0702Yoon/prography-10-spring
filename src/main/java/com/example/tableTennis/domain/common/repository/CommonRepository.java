package com.example.tableTennis.domain.common.repository;

import com.example.tableTennis.domain.room.repository.RoomJpaRepository;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.domain.user.repository.UserJpaRepository;
import com.example.tableTennis.domain.userRoom.repository.UserRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonRepository {

    private final UserJpaRepository userJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final UserRoomRepository userRoomRepository;

    public void deleteAllData() {
        userJpaRepository.deleteAll();
        roomJpaRepository.deleteAll();
        userRoomRepository.deleteAll();
    }

    public void saveUserList(List<User> userList) {
        userJpaRepository.saveAll(userList);
    }
}
