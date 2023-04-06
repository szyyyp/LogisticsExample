package org.example.service.impl;

import org.example.dao.GoodsDao;
import org.example.dao.PurchasePlanDao;
import org.example.dao.PurchasePlanDetailDao;
import org.example.pageModel.PurchasePlanDetailDto;
import org.example.pojo.Goods;
import org.example.pojo.PurchasePlan;
import org.example.pojo.PurchasePlanDetail;
import org.example.service.PurchasePlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PurchasePlanServiceImpl extends BaseServiceImpl<PurchasePlan, Integer> implements PurchasePlanService {

    @Resource(name = "purchasePlanDetailDaoImpl")
    PurchasePlanDetailDao purchasePlanDetailDao;

    @Resource(name = "goodsDaoImpl")
    GoodsDao goodsDao;

    @Resource(name = "purchasePlanDaoImpl")
    PurchasePlanDao dao;

    @Resource(name = "purchasePlanDaoImpl")
    public void setBaseDao(PurchasePlanDao dao) {
        super.setBaseDao(dao);
        this.dao = dao;
    }

    public boolean editPurchasePlan(PurchasePlan purchasePlan, List<PurchasePlanDetailDto> lstInserted, List<PurchasePlanDetailDto> lstUpdated,
                                    List<PurchasePlanDetailDto> lstDeleted){
        //更新表头
        PurchasePlan pur_plan_in_dataBase = this.find(purchasePlan.getId());
        BeanUtils.copyProperties(purchasePlan,pur_plan_in_dataBase,"purchasePlanDetailSet");
        this.update(pur_plan_in_dataBase);

        //新删除的表细数据
        if (lstDeleted.size()>0){
            for (PurchasePlanDetailDto d : lstDeleted){
                purchasePlanDetailDao.remove(purchasePlanDetailDao.find(d.getId()));
            }
        }

        //新增的表细数据
        if (lstInserted.size() > 0){
            for (PurchasePlanDetailDto d : lstInserted) {
                PurchasePlanDetail purchasePlanDetail = new PurchasePlanDetail();
                purchasePlanDetail.setPurchasePlan(pur_plan_in_dataBase);
                Goods goods = goodsDao.find(d.getGoodsPid());
                purchasePlanDetail.setGoods(goods);
                BeanUtils.copyProperties(d,purchasePlanDetail,  new String[]{"purchasePlanPid","goodsPid"});
                purchasePlanDetailDao.persist(purchasePlanDetail);
            }

        }

        //更新的表细数据
        if (lstUpdated.size() > 0){
            for (PurchasePlanDetailDto d : lstUpdated) {
                PurchasePlanDetail purchasePlanDetail = purchasePlanDetailDao.find(d.getId());
                Goods goods = goodsDao.find(d.getGoodsPid());
                purchasePlanDetail.setGoods(goods);
                BeanUtils.copyProperties(d,purchasePlanDetail,  new String[]{"purchasePlanPid","goodsPid"});
                purchasePlanDetailDao.persist(purchasePlanDetail);
            }
        }

        return true;
    }

    public boolean addPurchasePlan(PurchasePlan purchasePlan, List<PurchasePlanDetailDto> lstInserted){
        //更新表头
        purchasePlan.setId(null);

        Set<PurchasePlanDetail> purchasePlanDetailSet = new HashSet<PurchasePlanDetail>();
        if (lstInserted.size() > 0){
            for (PurchasePlanDetailDto d : lstInserted) {
                PurchasePlanDetail purchasePlanDetail = new PurchasePlanDetail();
                Goods goods = goodsDao.find(d.getGoodsPid());
                purchasePlanDetail.setGoods(goods);
                purchasePlanDetail.setPurchasePlan(purchasePlan);
                BeanUtils.copyProperties(d, purchasePlanDetail, new String[]{"purchasePlanPid", "goodsPid"});
                purchasePlanDetailSet.add(purchasePlanDetail);
            }
        }
        purchasePlan.setPurchasePlanDetailSet(purchasePlanDetailSet);

        this.save(purchasePlan);
        return true;
    }
}
