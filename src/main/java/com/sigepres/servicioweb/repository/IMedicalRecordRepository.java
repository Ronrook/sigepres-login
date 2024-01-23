package com.sigepres.servicioweb.repository;


import com.sigepres.servicioweb.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IMedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MedicalRecord m WHERE m.customer.id = :customerId")
    boolean existsByCustomerId(@Param("customerId") Integer customerId);

    Optional<com.sigepres.servicioweb.entities.MedicalRecord> findByCustomerId(Integer customerId);
}
