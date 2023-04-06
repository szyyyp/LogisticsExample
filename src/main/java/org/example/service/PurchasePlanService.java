package org.example.service;

import org.example.pageModel.PurchasePlanDetailDto;
import org.example.pojo.PurchasePlan;

import java.util.List;

public interface PurchasePlanService extends BaseService<PurchasePlan, Integer>{
    public boolean editPurchasePlan(PurchasePlan purchasePlan, List<PurchasePlanDetailDto> lstInserted, List<PurchasePlanDetailDto> lstUpdated,
                                    List<PurchasePlanDetailDto> lstDeleted);

    public boolean addPurchasePlan(PurchasePlan purchasePlan, List<PurchasePlanDetailDto> lstInserted);
}
