package org.example.controller;

import org.example.pageModel.Json;
import org.example.pageModel.Order;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pojo.device.CanMeter;
import org.example.pojo.device.Scale;
import org.example.service.device.CanMeterService;
import org.example.service.device.ScaleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/*
    仪表参数设置及相关管理
    @Author szy
    @Date  2022-6-28
 */
@RestController
@RequestMapping("/meter")
public class MeterInfoController {
    @Resource(name="canMeterServiceImpl")
    CanMeterService canMeterService;

    @Resource(name="scaleServiceImpl")
    ScaleService scaleService;

    @RequestMapping("/getData")
    public Page<CanMeter> getData(Pageable page, CanMeter cans){
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order();
        order.setProperty("scale");
        order.setDirection(Order.Direction.asc);
        lstOrder.add(order);
        Order order1 = new Order();
        order1.setProperty("orderId");
        order1.setDirection(Order.Direction.desc);
        lstOrder.add(order1);
     /*   page.setOrderProperty("scale");
        page.setOrderDirection(Order.Direction.asc);*/
        page.setOrders(lstOrder);

        return canMeterService.findPage(page,cans);
    }

    @RequestMapping("/edit")
    public Json edit(CanMeter meter, Integer scaleId){
        Json json = new Json();
        boolean hasEdit = canMeterService.edit(meter,scaleId);
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
    public Json add(CanMeter meter,Integer scaleId){
        Json json = new Json();
        try {
            Scale scale = scaleService.find(scaleId);
            meter.setScale(scale);
            canMeterService.save(meter);
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
            canMeterService.delete(id);
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
