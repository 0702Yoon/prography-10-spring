package com.example.tableTennis.domain.room.entity;

import com.example.tableTennis.domain.common.entity.BaseTimeEntity;
import com.example.tableTennis.domain.common.converter.RoomStatusConverter;
import com.example.tableTennis.domain.common.converter.RoomTypeConverter;
import com.example.tableTennis.domain.room.entity.enums.RoomStatus;
import com.example.tableTennis.domain.room.entity.enums.RoomType;
import com.example.tableTennis.domain.user.entity.User;
import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private User host;

    @Convert(converter = RoomTypeConverter.class)
    private RoomType roomType;

    @Convert(converter = RoomStatusConverter.class)
    private RoomStatus status;

    @Builder
    public Room(String title, User host, RoomType roomType) {
        this.title = title;
        this.host = host;
        this.roomType = roomType;
        this.status = RoomStatus.WAIT;
    }

    public int getHostId() {
        return host.getId();
    }

    public void checkStatusIsWait() {
        if (status != RoomStatus.WAIT) {
            throw new CustomException(ResponseType.FAIL);
        }
    }

    public void finsh() {
        this.status = RoomStatus.FINISH;
    }

    public boolean isHost(User user) {
        return this.host.getId().equals(user.getId());
    }

    public void startGame() {
        this.status = RoomStatus.PROGRESS;
    }

    public void endGame() {
        this.status = RoomStatus.FINISH;
    }
}
