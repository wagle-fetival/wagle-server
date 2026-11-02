package com.wagle.wagleserver.repository;

import com.wagle.wagleserver.entity.BadWording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadWordingRepository extends JpaRepository<BadWording, Long> {
}