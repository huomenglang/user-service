package com.menglang.user.service.entity.model;

import com.menglang.user.service.entity.audit.AuditEntity;
import com.menglang.user.service.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditEntity<Long> {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_image")
    private String userImage;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "login_attempt")
    private Integer loginAttempt = 0;

    @Column(name = "max_attempt")
    private Integer maxAttempt = 3;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_type", nullable = false)
    private String userType;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Gender gender;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "status")
    private String status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<Role> roles=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_user_groups",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id",referencedColumnName = "id")
    )
    private Set<Group> groups=new HashSet<>();


}
