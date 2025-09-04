package com.simuladorbanco.BancoDigital.repository;

import com.simuladorbanco.BancoDigital.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
