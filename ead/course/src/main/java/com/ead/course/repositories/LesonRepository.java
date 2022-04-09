package com.ead.course.repositories;

import com.ead.course.models.LesonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LesonRepository extends JpaRepository<LesonModel, UUID> {
}
