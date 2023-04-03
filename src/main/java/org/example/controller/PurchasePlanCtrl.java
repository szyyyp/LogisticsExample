package org.example.controller;

import org.example.pageModel.Filter;
import org.example.pageModel.Order;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pojo.PurchasePlan;
import org.example.pojo.PurchasePlanDetail;
import org.example.service.PurchasePlanDetailService;
import org.example.service.PurchasePlanService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购计划单 控制器
 * @Author: Szy
 * @Date: 2023/3/28  14:22
 */

@RestController
@RequestMapping("/purchasePlan")
public class PurchasePlanCtrl {

    @Resource(name="purchasePlanServiceImpl")
    PurchasePlanService purchasePlanService;

    @Resource(name="purchasePlanDetailServiceImpl")
    PurchasePlanDetailService purchasePlanDetailService;

    @RequestMapping("/getData")
    public Page<PurchasePlan> getData(Pageable page, PurchasePlan purchasePlan, String start, String end){
        page.setOrderProperty("createTime");
        page.setOrderDirection(Order.Direction.desc);

        List<Filter> filters = new ArrayList<Filter>();

        if (start!=null && start.trim().length()>0){
            Filter ft = new Filter();
            ft.setProperty("createTime");
            ft.setValue(start);
            ft.setOperator(Filter.Operator.ge);
            filters.add(ft);
        }
        if (end!=null && end.trim().length()>0){
            Filter ft = new Filter();
            ft.setProperty("createTime");
            ft.setValue(end);
            ft.setOperator(Filter.Operator.le);
            filters.add(ft);
        }
        page.setFilters(filters);

        return purchasePlanService.findPage(page,purchasePlan);
    }


    @RequestMapping("/getSinglePurchasePlan")
    public PurchasePlan getSinglePurchasePlan(Integer id){
        return purchasePlanService.find(id);
    }

    @RequestMapping("/getPurchasePlanDetails/{purchasePlanPid}")
    public Page<PurchasePlanDetail> getPurchasePlanDetails(Pageable page,@PathVariable("purchasePlanPid") Integer purchasePlanPid){
        if (purchasePlanPid!=null ){
            List<Filter> filters = new ArrayList<Filter>();
            Filter ft = new Filter();
            ft.setProperty("purchasePlan");
            ft.setValue(purchasePlanPid);
            ft.setOperator(Filter.Operator.eq);
            filters.add(ft);
            page.setFilters(filters);
            return purchasePlanDetailService.findPage(page);
        }else
            return null;
    }

}
