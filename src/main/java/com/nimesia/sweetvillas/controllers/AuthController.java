package com.nimesia.sweetvillas.controllers;

import com.nimesia.sweetvillas.bean.ApiError;
import com.nimesia.sweetvillas.dto.LoginDTO;
import com.nimesia.sweetvillas.dto.LoginResultDTO;
import com.nimesia.sweetvillas.models.UserEntity;
import com.nimesia.sweetvillas.mappers.UserMapper;
import com.nimesia.sweetvillas.providers.JwtProvider;
import com.nimesia.sweetvillas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * AuthController
 */
@RestController
class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper mapper;

    /**
     * Login request
     *
     * @param login
     */
    @PostMapping("/public/auth/login")
    public ResponseEntity login(
            @Valid @RequestBody LoginDTO login,
            HttpServletResponse response
    ) {
        UserEntity user = userService.getByEmailAndPassword(login.getEmail(), login.getPwd());

        if (user == null) {
            ApiError error = new ApiError().builder("InvalidLogin", null, null);

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(error);
        }

        String jwt = JwtProvider.createJwt(user, 60800);
        LoginResultDTO dto = new LoginResultDTO();
        dto.setUser(mapper.map(user));

        Cookie token = new Cookie("token", jwt);
        token.setPath("/");
        response.addCookie(token);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);

    }
}