package com.example.tableTennis.domain.user.entity;

import com.example.tableTennis.domain.common.entity.BaseTimeEntity;
import com.example.tableTennis.domain.common.converter.UserStatusConverter;
import com.example.tableTennis.domain.user.entity.enums.UserStatus;
import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_table")
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private Integer fakerId;
    @Column
    private String name;
    @Column
    private String email;

    @Convert(converter = UserStatusConverter.class)
    private UserStatus userStatus;

    @Builder
    public User(Integer fakerId, String name, String email, UserStatus userStatus) {
        this.fakerId = fakerId;
        this.name = name;
        this.email = email;
        this.userStatus = userStatus;
    }

    public void checkStatusIsActive() {
        if (userStatus != UserStatus.ACTIVE) {
            throw new CustomException(ResponseType.FAIL);
        }
    }

}
