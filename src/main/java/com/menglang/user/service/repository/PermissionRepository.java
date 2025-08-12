package com.menglang.user.service.repository;
import com.menglang.user.service.entity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    boolean existsByName(String name);
    List<Permission> findAllByStatusAndNameIn(String status, Set<String> names);
}
