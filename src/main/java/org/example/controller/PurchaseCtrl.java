package org.example.controller;

import org.example.pageModel.*;
import org.example.pojo.Purchase;
import org.example.pojo.PurchaseDetail;
import org.example.service.PurchaseDetailService;
import org.example.service.PurchaseService;
import org.example.util.JsonUtils;
import org.example.webSocket.PurchaseWebSocket;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/9  21:48
 */

@RestController
@RequestMapping("/purchase")
public class PurchaseCtrl {
    @Resource(name="purchaseServiceImpl")
    PurchaseService purchaseService;

    @Resource(name="purchaseDetailServiceImpl")
    PurchaseDetailService purchaseDetailService;

    @Resource(name="purchaseWebSocket")
    PurchaseWebSocket purchaseWebSocket;


    @RequestMapping("/getData")
    public Page<Purchase> getData(Pageable page, Purchase purchase, String start, String end){
        page.setOrderProperty("createTime");
        page.setOrderDirection(Order.Direction.desc);

        List<Filter> filters = new ArrayList<>();

        addStartAndEndRestrict(start, end, filters);
        page.setFilters(filters);

        return purchaseService.findPage(page,purchase);
    }

    static void addStartAndEndRestrict(String start, String end, List<Filter> filters) {
        if (start!=null && !start.isEmpty()){
            Filter ft = new Filter();
            ft.setProperty("createTime");
            ft.setValue(start);
            ft.setOperator(Filter.Operator.ge);
            filters.add(ft);
        }
        if (end!=null && !end.isEmpty()){
            Filter ft = new Filter();
            ft.setProperty("createTime");
            ft.setValue(end);
            ft.setOperator(Filter.Operator.le);
            filters.add(ft);
        }
    }

    @RequestMapping("/getSinglePurchase")
    public Purchase getSinglePurchase(Integer id){
        return purchaseService.find(id);
    }


    @RequestMapping("/getPurchaseDetails/{purchasePid}")
    public Page<PurchaseDetail> getPurchaseDetails(Pageable page, @PathVariable("purchasePid") Integer purchasePid){

        if (purchasePid!=null ){
            List<Filter> filters = new ArrayList<>();
            Filter ft = new Filter();
            ft.setProperty("purchase");
            ft.setValue(purchasePid);
            ft.setOperator(Filter.Operator.eq);
            filters.add(ft);
            page.setFilters(filters);
            return purchaseDetailService.findPage(page);
        }else
            return null;
    }

    @RequestMapping("/edit")
    public Json edit(Purchase purchase,
                     @RequestParam(value = "inserted", required = false) String inserted,
                     @RequestParam(value = "updated", required = false) String updated,
                     @RequestParam(value = "deleted", required = false) String deleted
    ){
        Json json = new Json();
        List<PurchaseDetailDto> lstInserted = new ArrayList<>();
        List<PurchaseDetailDto> lstUpdated = new ArrayList<>();
        List<PurchaseDetailDto> lstDeleted = new ArrayList<>();


        if (!inserted.isEmpty()){
            lstInserted = JsonUtils.getListBeans(inserted, PurchaseDetailDto.class);
        }
        if (!updated.isEmpty()){
            lstUpdated = JsonUtils.getListBeans(updated, PurchaseDetailDto.class);
        }
        if (!deleted.isEmpty()){
            lstDeleted = JsonUtils.getListBeans(deleted, PurchaseDetailDto.class);
        }

        String errStr="";
        try {
            boolean hasEdit = purchaseService.editPurchase(purchase, lstInserted, lstUpdated, lstDeleted);
            if (hasEdit) {
                json.setSuccess(true);
                json.setMsg("采购计划单更新成功！");
                return  json;
            }
        }catch (Exception e){
            errStr = e.getMessage();
        }
        json.setSuccess(false);
        json.setMsg("采购计划单更新失败！\n\n" + errStr);
        return  json;
    }

}
