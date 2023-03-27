package org.example.dao.impl;

import org.example.dao.CourseDao;
import org.example.pojo.Course;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImpl extends BaseDaoImpl <Course,Integer> implements CourseDao {
}
