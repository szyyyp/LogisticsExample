package org.example.controller;

import org.example.pageModel.Json;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pojo.Course;
import org.example.service.CourseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/course")
public class CourseCtrl {

    @Resource(name="courseServiceImpl")
    CourseService courseService;

    @RequestMapping("/getCourseInfo")
    public Page<Course> getStudentInfo(Pageable page, Course course){

        return courseService.findPage(page,course);
    }

    @RequestMapping("/edit")
    public Json edit(Course course,Integer cpno){
        Json json = new Json();
        boolean hasEdit = courseService.edit(course,cpno);

        if (hasEdit){
            json.setSuccess(true);
            json.setMsg("修改成功");
        }else{
            json.setSuccess(false);
            json.setMsg("修改失败");
        }
        return json;

    }

    @RequestMapping("/add")
    public Json add(Course course){
        Json json = new Json();
        try {
            courseService.save(course);
            json.setSuccess(true);
            json.setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("添加失败");
        }
        return json;
    }

    @RequestMapping("/del")
    public Json del(Integer id){
        Json json = new Json();
        try {
            courseService.delete(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("删除失败");
        }
        return json;
    }
}
