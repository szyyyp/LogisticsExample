package org.example.service.impl;

import org.example.dao.CourseDao;
import org.example.pojo.Course;
import org.example.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceImpl<Course, Integer> implements CourseService {

    @Resource(name = "courseDaoImpl")
    public void setBaseDao(CourseDao dao) {
        super.setBaseDao(dao);
    }

    /**
     *
     * @param course    前端传来的对象，非数据库中的带关联的对象
     */
    public boolean edit(Course course, Integer cpNo){
        try {
            Course co = this.find(cpNo);    // 先行课
            Course courseIn = this.find(course.getId());    // 数据库中已经保存了的课程对象
            BeanUtils.copyProperties(course,courseIn,"course");
            courseIn.setCourse(co);
            this.update(courseIn);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
