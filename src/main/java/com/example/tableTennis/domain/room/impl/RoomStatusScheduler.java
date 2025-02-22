package com.example.tableTennis.domain.room.impl;

import com.example.tableTennis.domain.room.entity.Room;
import com.example.tableTennis.domain.userRoom.impl.UserRoomHandler;
import java.time.Instant;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
@AllArgsConstructor
public class RoomStatusScheduler {

    private PlatformTransactionManager transactionManager;
    private final TaskScheduler taskScheduler;

    private final RoomHandler roomHandler;
    private final UserRoomHandler userRoomHandler;
    private final long GAME_TIME = 60000;

    public void scheduleRoomStateChange(String roomId) {
        taskScheduler.schedule(() -> scheduledTask(roomId),
            Instant.now().plusMillis(GAME_TIME));
    }

    public void scheduledTask(String roomId) {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(
            transactionDefinition);

        try {
            Room room = roomHandler.findById(roomId);
            room.endGame();
            userRoomHandler.deleteAll(room);
            roomHandler.save(room);
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            throw e;
        }
    }
}
