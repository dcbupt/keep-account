package com.bupt.dc.dao.jpa;

import com.bupt.dc.object.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByNameIn(List<String> names);
}
