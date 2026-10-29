package com.wagle.backend.repository;

import com.wagle.backend.entity.BadWording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadWordingRepository extends JpaRepository<BadWording, Long> {
}