package org.example.controller;

import org.example.pageModel.Json;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pojo.Course;
import org.example.pojo.Goods;
import org.example.service.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 商品参数设置及相关管理
 @Author szy
 @Date  2023-3-28
 */

@RestController
@RequestMapping("/goods")
public class GoodsCtrl {

    @Resource(name="goodsServiceImpl")
    GoodsService goodsService;

    @RequestMapping("/getGoods")
    public Page<Goods> getStudentInfo(Pageable page, Goods goods){

        return goodsService.findPage(page,goods);
    }

    @RequestMapping("/edit")
    public Json edit(Goods goods, Integer goodsTypePid){
        Json json = new Json();
        boolean hasEdit = goodsService.edit(goods,goodsTypePid);

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
    public Json add(Goods goods,Integer goodsTypePid){
        Json json = new Json();
        boolean hasEdit = goodsService.add(goods,goodsTypePid);

        if (hasEdit){
            json.setSuccess(true);
            json.setMsg("修改成功");
        }else{
            json.setSuccess(false);
            json.setMsg("修改失败");
        }
        return json;
    }

    @RequestMapping("/del")
    public Json del(Integer id){
        Json json = new Json();
        try {
            goodsService.delete(id);
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
