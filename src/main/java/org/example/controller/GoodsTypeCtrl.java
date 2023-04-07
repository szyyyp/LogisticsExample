package org.example.controller;

import org.example.pageModel.Json;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pojo.GoodsType;
import org.example.service.GoodsTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 商品类别维护
 * 作者: Szy
 * 日期: 2023/3/28  6:53
 */

@RestController
@RequestMapping("/goodsType")
public class GoodsTypeCtrl {

    @Resource(name="goodsTypeServiceImpl")
    GoodsTypeService goodsTypeService;

    @RequestMapping("/getGoodsType")
    public Page<GoodsType> getGoodsTypeInfo(Pageable page, GoodsType goodsType){

        return goodsTypeService.findPage(page,goodsType);
    }


    @RequestMapping("/edit")
    public Json edit(GoodsType goodsType){
        Json json = new Json();
        try {
            GoodsType sc = goodsTypeService.find(goodsType.getId());
            BeanUtils.copyProperties(goodsType,sc);
            goodsTypeService.update(sc);
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
    public Json add(GoodsType goodsType){
        Json json = new Json();
        try {
            goodsTypeService.save(goodsType);
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
            goodsTypeService.delete(id);
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
