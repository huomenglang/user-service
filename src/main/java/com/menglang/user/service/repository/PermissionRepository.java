package com.menglang.user.service.repository;
import com.menglang.user.service.entity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> , JpaSpecificationExecutor<Permission> {
    @Query(value = """
            SELECT CASE WHEN COUNT(r)>0 THEN true ELSE false END FROM Permission r WHERE r.name=?1
            """)
    boolean existsByName(String name);

    List<Permission> findAllByStatusAndNameIn(String status, Set<String> names);
}
