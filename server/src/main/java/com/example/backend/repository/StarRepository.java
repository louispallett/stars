package com.example.backend.repository;

import com.example.backend.model.Star;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Long> {

}