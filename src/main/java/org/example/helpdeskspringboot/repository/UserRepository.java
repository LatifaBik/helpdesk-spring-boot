package org.example.helpdeskspringboot.repository;

import org.example.helpdeskspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
