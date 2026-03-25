package com.daniel.wec_ssvp.repository;

import com.daniel.wec_ssvp.model.ConselhoParticular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConselhoParticularRepository extends JpaRepository<ConselhoParticular, Integer> {
}