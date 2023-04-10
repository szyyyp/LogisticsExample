package org.example.service.impl;

import org.example.dao.*;
import org.example.pageModel.PurchaseDetailDto;
import org.example.pojo.*;
import org.example.service.PurchaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/7  23:32
 */

@Service
@Transactional
public class PurchaseServiceImpl extends BaseServiceImpl<Purchase, Integer> implements PurchaseService {

    @Resource(name = "purchaseDetailDaoImpl")
    PurchaseDetailDao purchaseDetailDao;

    @Resource(name = "purchasePlanDetailDaoImpl")
    PurchasePlanDetailDao purchasePlanDetailDao;

    @Resource(name = "purchaseDaoImpl")
    public void setBaseDao(PurchaseDao dao) {
        super.setBaseDao(dao);
    }
    public boolean editPurchase(Purchase purchase, List<PurchaseDetailDto> lstInserted, List<PurchaseDetailDto> lstUpdated,
                                List<PurchaseDetailDto> lstDeleted){
        //更新表头
        Purchase pur_in_dataBase = this.find(purchase.getId());
        BeanUtils.copyProperties(purchase,pur_in_dataBase,"purchaseDetailSet");
        this.update(pur_in_dataBase);

        //新删除的表细数据
        if (lstDeleted.size()>0){
            for (PurchaseDetailDto d : lstDeleted){
                purchaseDetailDao.remove(purchaseDetailDao.find(d.getId()));
            }
        }

        //新增的表细数据
        if (lstInserted.size() > 0){
            for (PurchaseDetailDto d : lstInserted) {
                PurchaseDetail purchaseDetail = new PurchaseDetail();
                purchaseDetail.setPurchase(pur_in_dataBase);                // 设置关联
                PurchasePlanDetail purchasePlanDetail = purchasePlanDetailDao.find(d.getPurchasePlanDetailPid());
                purchaseDetail.setPurchasePlanDetail(purchasePlanDetail);       // 设置关联
                BeanUtils.copyProperties(d,purchaseDetail, "purchasePid","purchasePlanDetailPid");
                purchaseDetailDao.persist(purchaseDetail);
            }
        }

        //更新的表细数据
        if (lstUpdated.size() > 0){
            for (PurchaseDetailDto d : lstUpdated) {
                PurchaseDetail purchaseDetail = purchaseDetailDao.find(d.getId());
                PurchasePlanDetail purchasePlanDetail = purchasePlanDetailDao.find(d.getPurchasePlanDetailPid());
                purchaseDetail.setPurchasePlanDetail(purchasePlanDetail);       // 设置关联
                BeanUtils.copyProperties(d,purchaseDetail, "purchasePid","purchasePlanDetailPid");
                purchaseDetailDao.persist(purchaseDetail);
            }
        }

        return true;

    }

    public boolean addPurchase(Purchase purchase, List<PurchaseDetailDto> lstInserted){
        //更新表头
        purchase.setId(null);

        Set<PurchaseDetail> purchaseDetailSet = new HashSet<>();
        if (lstInserted.size() > 0){
            for (PurchaseDetailDto d : lstInserted) {
                PurchaseDetail purchaseDetail = new PurchaseDetail();
                PurchasePlanDetail purchasePlanDetail = purchasePlanDetailDao.find(d.getPurchasePlanDetailPid());
                purchaseDetail.setPurchasePlanDetail(purchasePlanDetail);       // 设置关联
                purchaseDetail.setPurchase(purchase);
                BeanUtils.copyProperties(d, purchasePlanDetail, "purchasePid", "purchasePlanDetailPid");
                purchaseDetailSet.add(purchaseDetail);
            }
        }
        purchase.setPurchaseDetailSet(purchaseDetailSet);

        this.save(purchase);
        return true;
    }
}
