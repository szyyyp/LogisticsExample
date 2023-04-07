package org.example.controller;

import org.example.pageModel.Json;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pojo.Student;
import org.example.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/student")
public class StudentCtrl {

    @Resource(name="studentServiceImpl")
    StudentService studentService;

    @RequestMapping("/getStudentInfo")
    public Page<Student> getStudentInfo(Pageable page, Student student){

        return studentService.findPage(page,student);
    }


    @RequestMapping("/edit")
    public Json edit(Student student){
        Json json = new Json();
        try {
            Student sc = studentService.find(student.getId());
            BeanUtils.copyProperties(student,sc);
            studentService.update(sc);
            json.setSuccess(true);
            json.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("修改失败");
        }
        return json;
    }

    @RequestMapping("/add")
    public Json add(Student student){
        Json json = new Json();
        try {
            studentService.save(student);
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
            studentService.delete(id);
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
