package org.example.service;

import org.example.pageModel.PurchaseDetailDto;
import org.example.pojo.Purchase;

import java.util.List;


/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/7  23:28
 */
public interface PurchaseService extends BaseService<Purchase, Integer>{

    public boolean editPurchase(Purchase purchase, List<PurchaseDetailDto> lstInserted, List<PurchaseDetailDto> lstUpdated,
                                List<PurchaseDetailDto> lstDeleted);

    public boolean addPurchase(Purchase purchase, List<PurchaseDetailDto> lstInserted);
}
