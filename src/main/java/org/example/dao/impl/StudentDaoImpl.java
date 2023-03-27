package org.example.dao.impl;

import org.example.dao.StudentDao;
import org.example.pojo.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDaoImpl extends BaseDaoImpl <Student,Integer> implements StudentDao {
}
