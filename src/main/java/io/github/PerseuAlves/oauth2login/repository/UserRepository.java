package io.github.PerseuAlves.oauth2login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.PerseuAlves.oauth2login.model.UserLogin;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, String>{

}
