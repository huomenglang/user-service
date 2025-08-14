package com.menglang.user.service.repository;

import com.menglang.user.service.entity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Optional<Role> findByName(String name);

    List<Role> findAllByNameIn(Set<String> roles);

    boolean existsByName(String name);

    List<Role> findAllByStatus(String status);

    Optional<Role> findFirstByName(String name);

    Optional<Role> findFirstById(Long id);

    List<Role> findAllByIdIn(Set<Long> ids);

    Optional<Role> findFirstByNameAndStatus(String name, String status);

    List<Role> findByNameIn(Set<String> roles);

    @Query(value = """
            SELECT CASE WHEN COUNT(r)>0 THEN true ELSE false END FROM tbl_roles r WHERE r.name=:name AND r.id!=:id
            """, nativeQuery = true)
    boolean findByNameAndIdNotIn(
            @Param("name") String name,
            @Param("id") Long id);
}
