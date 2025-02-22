package com.example.tableTennis.domain.user.impl;

import com.example.tableTennis.domain.user.entity.User;

public interface UserHandler {

    User findByUser(int userId);
}
