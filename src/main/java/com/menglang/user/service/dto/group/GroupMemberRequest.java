package com.menglang.user.service.dto.group;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record GroupMemberRequest(
        @NotNull(message = "Group Members Must not be null")
        List<Long> userIds
) {
}
