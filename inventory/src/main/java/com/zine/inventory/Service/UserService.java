package com.zine.inventory.Service;

import com.zine.inventory.DAO.RoleDAO;
import com.zine.inventory.DAO.UserDAO;
import com.zine.inventory.Model.Role;
import com.zine.inventory.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO repo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserDAO userDAO;

    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);

    public Users register(Users user){
        Users existingUser = repo.findByUsername(user.getUsername());
        if (existingUser!=null) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        List<Role> userRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleDAO.findByName(role.getName());
            if (existingRole == null) {
                throw new RuntimeException("Role does not exist: " + role.getName());
            }
            userRoles.add(existingRole);
        }

        user.setRoles(userRoles);
        return repo.save(user);
    }


    public String verify(Users user) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return JWTService.generateToken(user.getUsername());
            } else {
                throw new RuntimeException("Invalid login credentials");
            }

        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }

    }

    public void deleteUser(String username) {
        try {
            Users user = userDAO.findByUsername(username);
            userDAO.delete(user);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
