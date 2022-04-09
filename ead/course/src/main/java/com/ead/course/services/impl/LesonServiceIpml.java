package com.ead.course.services.impl;

import com.ead.course.services.LesonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LesonServiceIpml implements LesonService {

    @Autowired
    LesonService lesonService;
}
