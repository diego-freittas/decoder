package com.ead.course.services.impl;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleSeviceImpl implements ModuleSevice {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lesonRepository;

    @Transactional
    @Override
    public void delete(ModuleModel module) {
        List<LessonModel> lesonModelList = lesonRepository.findAllLessonsIntoModule(module.getModuleId());
        if (!lesonModelList.isEmpty()){
            lesonRepository.deleteAll(lesonModelList);
        }
        moduleRepository.delete(module);
    }

    @Override
    public ModuleModel save(ModuleModel moduleModel) {
        return moduleRepository.save(moduleModel);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID courseID, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseID,moduleId);
    }

    @Override
    public List<ModuleModel> findAllByCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }


}
