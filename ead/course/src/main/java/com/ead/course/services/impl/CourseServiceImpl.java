package com.ead.course.services.impl;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LesonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LesonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LesonRepository lesonRepository;

    @Transactional
    @Override
    public void delete(CourseModel course) {

        List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(course.getCourseId());
        if (!moduleModelList.isEmpty()){
            for (ModuleModel module : moduleModelList){
                List<LesonModel> lesonModelList = lesonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lesonModelList.isEmpty()){
                    lesonRepository.deleteAll(lesonModelList);
                }
            }
            moduleRepository.deleteAll(moduleModelList);
        }
        courseRepository.delete(course);
    }

    @Override
    public CourseModel save(CourseModel courseModel) {
        return courseRepository.save(courseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public List<CourseModel> findAll() {
        return courseRepository.findAll();
    }
}
