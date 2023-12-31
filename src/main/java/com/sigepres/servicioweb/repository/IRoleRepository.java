package com.sigepres.servicioweb.repository;

import com.sigepres.servicioweb.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Byte> {

    //@Query("SELECT r FROM Role r WHERE r.name = :name")
    Optional<Role> findByName(@Param("name") String name);

}
