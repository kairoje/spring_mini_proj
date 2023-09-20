package com.libraries.repository;

import com.libraries.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByEmailAddress(String userEmailAddress); //used to register

    UserModel findUserByEmailAddress(String emailAddress); //to login
}
