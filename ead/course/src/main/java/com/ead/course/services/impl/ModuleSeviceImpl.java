package com.ead.course.services.impl;

import com.ead.course.models.LesonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LesonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ModuleSeviceImpl implements ModuleSevice {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LesonRepository lesonRepository;

    @Transactional
    @Override
    public void delete(ModuleModel module) {
        List<LesonModel> lesonModelList = lesonRepository.findAllLessonsIntoModule(module.getModuleId());
        if (!lesonModelList.isEmpty()){
            lesonRepository.deleteAll(lesonModelList);
        }
        moduleRepository.delete(module);
    }
}
