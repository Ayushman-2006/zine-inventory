package com.zine.inventory.DAO;

import com.zine.inventory.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findByName(String name);
}