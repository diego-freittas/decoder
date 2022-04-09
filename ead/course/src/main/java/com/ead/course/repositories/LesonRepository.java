package com.ead.course.repositories;

import com.ead.course.models.LesonModel;
import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LesonRepository extends JpaRepository<LesonModel, UUID> {

    @Query(value = "select * from tb_lessons where module_module_id = :moduleID", nativeQuery = true)
    List<LesonModel> findAllLessonsIntoModule(@Param("moduleID") UUID courseId);
}
