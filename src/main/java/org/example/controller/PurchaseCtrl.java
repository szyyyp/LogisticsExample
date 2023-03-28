package org.example.controller;

import org.example.pageModel.Filter;
import org.example.pageModel.Order;
import org.example.pageModel.Page;
import org.example.pageModel.Pageable;
import org.example.pojo.PurchasePlan;
import org.example.service.PurchasePlanService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购计划单 控制器
 * @Author: Szy
 * @Name: PurchaseCtrl
 * @Date: 2023/3/28  14:22
 */

@RestController
@RequestMapping("/purchasePlan")
public class PurchaseCtrl {

    @Resource(name="purchasePlanServiceImpl")
    PurchasePlanService purchasePlanService;

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
}
