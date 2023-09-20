package com.libraries.repository;

import com.libraries.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    /**
     * Take the user's email address and verifies its existence in the database
     * @param userEmailAddress
     * @returns boolean to check if user exist by emailAddress
     */
    boolean existsByEmailAddress(String userEmailAddress);

    /**
     * Takes the user's email and locates them
     * @param emailAddress
     *
     */
    UserModel findUserByEmailAddress(String emailAddress);
}
