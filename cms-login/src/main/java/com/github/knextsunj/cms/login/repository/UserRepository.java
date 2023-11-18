package com.github.knextsunj.cms.login.repository;

import com.github.knextsunj.cms.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

   User findByUsername(String username);
}
