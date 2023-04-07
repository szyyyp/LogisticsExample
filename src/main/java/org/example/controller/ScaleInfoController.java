package org.example.controller;

import org.example.pageModel.Json;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pojo.device.Scale;
import org.example.service.device.ScaleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
    称台参数设置及相关管理
    作者： szy
    日期：  2022-6-28
 */
@RestController
@RequestMapping("/scale")
public class ScaleInfoController {
    @Resource(name="scaleServiceImpl")
    ScaleService scaleService;

    // 获取称台参数
    @RequestMapping("/getScaleInfo")
    public Page<Scale> getScaleInfo(Pageable page, Scale scale){

        return scaleService.findPage(page,scale);
    }

    @RequestMapping("/edit")
    public Json edit(Scale scale){
        Json json = new Json();
        try {
            Scale sc = scaleService.find(scale.getId());
            BeanUtils.copyProperties(scale,sc,"canMeter");
            scaleService.update(sc);
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
    public Json add(Scale scale){
        Json json = new Json();
        try {
            scaleService.save(scale);
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
            scaleService.delete(id);
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
