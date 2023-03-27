package org.example.service.impl;

import org.example.dao.StudentDao;
import org.example.pojo.Student;
import org.example.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class StudentServiceImpl extends BaseServiceImpl<Student, Integer> implements StudentService {

    @Resource(name = "studentDaoImpl")
    public void setBaseDao(StudentDao dao) {
        super.setBaseDao(dao);
    }
}
