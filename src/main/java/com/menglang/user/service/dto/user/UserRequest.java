package com.menglang.user.service.dto.user;
import java.util.List;
import jakarta.validation.constraints.*;
public record UserRequest(
        @NotBlank(message = "Username must not be blank")
        @NotNull(message = "Username cannot be null")
        @Size(min = 3, max = 50, message = "Username must be between 3-50 characters")
        String username,

        @NotBlank(message = "First name must not be blank")
        @Size(max = 100, message = "First name must be ≤ 100 characters")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        @Size(max = 100, message = "Last name must be ≤ 100 characters")
        String lastName,

        String userImg,  // Optional (no validation)

        @NotBlank(message = "Password must not be blank")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "User type must not be blank")
        @Pattern(regexp = "^(ADMIN|USER|MODERATOR)$", message = "User type must be ADMIN, USER, or MODERATOR")
        String userType,

        @NotBlank(message = "Gender must not be blank")
        @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Gender must be MALE, FEMALE, or OTHER")
        String gender,

        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth must be in YYYY-MM-DD format")
        String dateOfBirth,  // Could also use @Past if using LocalDate
//
//        String lastLogin,  // No validation (auto-set by system)
//
////        @PositiveOrZero(message = "Login attempts must be ≥ 0")
//        Integer loginAttempt,
//
//        @Positive(message = "Max attempts must be > 0")
//        Integer maxAttempt,

//        Boolean enableAllocate,  // No validation (boolean can be null)

        @NotBlank(message = "Status must not be blank")
        @Pattern(regexp = "^(ACTIVE|INACTIVE|LOCKED)$", message = "Status must be ACTIVE, INACTIVE, or LOCKED")
        String status,

        @NotEmpty(message = "At least one role must be assigned")
        List<Long> roles,

        List<Long> groups  // Optional (no validation)
) {}
