package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleSevice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    ModuleSevice moduleSevice;

    @Autowired
    CourseService courseService;
    //criar um método post chamado saveModule

    @PostMapping("/courses/{courceId}/modules")
    public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId,
                                             @RequestBody @Valid ModuleDto moduleDto){

        Optional<CourseModel> moduleModelOptional = courseService.findById(courseId);
        if(!moduleModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }

        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(moduleDto,moduleModel);
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        moduleModel.setCourse(moduleModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleSevice.save(moduleModel));
    }

    @DeleteMapping("/courses/{courceId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId){
        Optional<ModuleModel> moduleModelOptional = moduleSevice.findModuleIntoCourse(courseId,moduleId);
        if(!moduleModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this Course");
        }
        moduleSevice.delete(moduleModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully.");
    }


    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId,
                                               @RequestBody @Valid ModuleDto moduleDto) {

        Optional<ModuleModel> moduleModelOptional = moduleSevice.findModuleIntoCourse(courseId,moduleId);
        if(!moduleModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this Course");
        }

        var moduleModel = moduleModelOptional.get();
        moduleModel.setTitle(moduleDto.getTitle());
        moduleModel.setDescription(moduleDto.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(moduleSevice.save(moduleModel));
    }

    @GetMapping("/courses/{courceId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(@PathVariable(value = "courseId") UUID courseId,
                                                           @PathVariable(value = "moduleId") UUID moduleId){

        Optional<ModuleModel> moduleModelOptional = moduleSevice.findModuleIntoCourse(courseId,moduleId);
        if(!moduleModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this Course");
        }
        return ResponseEntity.status(HttpStatus.OK).body(moduleModelOptional.get());
    }



}
