package com.menglang.user.service.entity.model;

import com.menglang.user.service.entity.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_permissions")
public class Permission extends AuditEntity<Long> implements Serializable {

    @Column(name = "name",length = 50,unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status",length = 30)
    private String status;

}
