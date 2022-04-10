package com.ead.course.services;

import com.ead.course.models.ModuleModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleSevice {

    void delete(ModuleModel module);

    ModuleModel save(ModuleModel moduleModel);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseID, UUID moduleId);

    List<ModuleModel> findAllByCourse(UUID courseId);

    Optional<ModuleModel> findById(UUID moduleId);
}
