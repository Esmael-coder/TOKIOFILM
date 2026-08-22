package com.tokio.filme.repositories;

import com.tokio.filme.entities.Role;
import com.tokio.filme.entities.User;
import com.tokio.filme.enuns.RoleValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByAuthority(RoleValue authority);
}
