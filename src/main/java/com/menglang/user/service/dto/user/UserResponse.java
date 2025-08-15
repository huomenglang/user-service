package com.menglang.user.service.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record UserResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("username") String username,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("user_img") String userImage,
        @JsonProperty("gender") String gender,
        @JsonProperty("date_of_birth") String dateOfBirth,
        @JsonProperty("email") String email,
        @JsonProperty("full_name") String fullName,
        @JsonProperty("created_on") LocalDateTime createdAt,
        @JsonProperty("last_login") LocalDateTime lastLogin,
        @JsonProperty("login_attempt") Integer loginAttempt,
        @JsonProperty("max_attempt") Integer maxAttempt,
        @JsonProperty("status") String status,
        @JsonProperty("roles") Set<String> roles,
        @JsonProperty("groups") Set<String> groups
) {
}
