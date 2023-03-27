package org.example.service;

import org.example.pojo.Course;

public interface CourseService extends BaseService<Course, Integer>{

    public boolean edit(Course course, Integer cpno);
}
