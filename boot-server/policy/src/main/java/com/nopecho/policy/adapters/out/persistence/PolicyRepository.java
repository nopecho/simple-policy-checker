package com.nopecho.policy.adapters.out.persistence;

import com.nopecho.policy.domain.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    @EntityGraph(attributePaths = {
            "statements",
            "statements.actions",
            "statements.conditions",
            "statements.conditions.specs"
    })
    @Query("SELECT p FROM Policy p WHERE p.id IN :ids")
    Set<Policy> findByIds(@Param("ids") List<Long> ids);

    @EntityGraph(attributePaths = {
            "statements",
            "statements.actions",
            "statements.conditions",
            "statements.conditions.specs"
    })
    Optional<Policy> findById(Long id);

    @EntityGraph(attributePaths = {
            "statements",
            "statements.actions",
            "statements.conditions",
            "statements.conditions.specs"
    })
    Page<Policy> findAll(Pageable pageable);
}
