package com.zine.inventory.DAO;

import com.zine.inventory.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserDAO extends JpaRepository<Users,Long> {

    Users findByUsername(String username);
}
