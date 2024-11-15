package com.tcc.dtos.request.user;

import com.tcc.models.UserRole;

public record UserRequest(String login, String password, UserRole role) {
}
