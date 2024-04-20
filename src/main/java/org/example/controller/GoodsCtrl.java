package org.example.controller;

import org.example.pageModel.*;
import org.example.pojo.Goods;
import org.example.service.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 商品参数设置及相关管理
 作者 szy
 日期  2023-3-28
 */

@RestController
//@RequestMapping("/goods")
@RequestMapping("${GoodCtrl.url}")
public class GoodsCtrl {

    @Resource(name="goodsServiceImpl")
    GoodsService goodsService;

    //@RequestMapping("/getGoods")
    @RequestMapping("${GoodCtrl.menuList[0].url}")
    public Page<Goods> getGoodsInfo(Pageable page, Goods goods, Integer goodsTypePid, Integer yxqq, Integer yxqz,String start, String end ){
        page.setOrderProperty("createTime");
        page.setOrderDirection(Order.Direction.desc);

        List<Filter> filters = new ArrayList<>();

        PurchasePlanCtrl.addStartAndEndRestrict(start, end, filters);
        if (yxqq!=null ){
            Filter ft = new Filter();
            ft.setProperty("lifespan");
            ft.setValue(yxqq);
            ft.setOperator(Filter.Operator.ge);
            filters.add(ft);
        }
        if (yxqz!=null ){
            Filter ft = new Filter();
            ft.setProperty("lifespan");
            ft.setValue(yxqz);
            ft.setOperator(Filter.Operator.le);
            filters.add(ft);
        }
        if(goodsTypePid!=null){
            Filter ft = new Filter();
            ft.setProperty("goodsType");
            ft.setValue(goodsTypePid);
            ft.setOperator(Filter.Operator.eq);
            filters.add(ft);
        }
        page.setFilters(filters);
        return goodsService.findPage(page,goods);
    }

    //@RequestMapping("/edit")
    @RequestMapping("${GoodCtrl.menuList[1].url}")
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
