package com.menglang.user.service.repository;

import com.menglang.user.service.entity.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long>, JpaSpecificationExecutor<Group> {

    List<Group> findAllByNameIn(Set<String> groupNames);

    @Query(value = """
            SELECT CASE WHEN COUNT(r)>0 THEN true ELSE false END FROM Group r WHERE r.name=?1 AND r.id!=?2
            """)
    boolean existsByName(String name,Long id);
}
