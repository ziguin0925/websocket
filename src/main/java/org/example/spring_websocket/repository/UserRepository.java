package org.example.spring_websocket.repository;


import java.util.Optional;
import org.example.spring_websocket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
