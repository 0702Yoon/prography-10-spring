package com.example.tableTennis.domain.user.controller;

import com.example.tableTennis.domain.user.controller.dto.response.UserPageResponse;
import com.example.tableTennis.domain.user.docs.UserControllerDocs;
import com.example.tableTennis.domain.user.service.UserService;
import com.example.tableTennis.global.response.ApiResponse;
import com.example.tableTennis.global.response.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @Override
    @GetMapping
    public ApiResponse<UserPageResponse> getAllUser(
        @RequestParam(name = "page", required = false) Integer page,
        @RequestParam(name = "size", required = false) Integer size
    ) {
        Pageable pageable = (page == null || size == null)
            ? Pageable.unpaged()
            : PageRequest.of(page, size, Sort.by("id").ascending());
        UserPageResponse userPageResponse = userService.getAllUsers(pageable);
        return ApiResponse.create(ResponseType.SUCCESS, userPageResponse);
    }
}
