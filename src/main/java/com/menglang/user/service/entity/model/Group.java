package com.menglang.user.service.entity.model;

import com.menglang.user.service.entity.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_groups")
@AllArgsConstructor
@NoArgsConstructor
public class Group extends AuditEntity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @JoinTable(
            name = "tbl_group_roles",
            joinColumns = @JoinColumn(name = "group_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tt_group_permissions",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private Set<Permission> permissions = new HashSet<>();

//    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
//    private Set<User> users = new HashSet<>();
}
