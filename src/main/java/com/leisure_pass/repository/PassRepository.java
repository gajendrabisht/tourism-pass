package com.leisure_pass.repository;

import com.leisure_pass.entity.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassRepository extends JpaRepository<Pass, UUID> {
}
