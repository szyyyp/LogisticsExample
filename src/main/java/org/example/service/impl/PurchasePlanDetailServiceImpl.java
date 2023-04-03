package org.example.service.impl;

import org.example.dao.PurchasePlanDetailDao;
import org.example.pojo.PurchasePlanDetail;
import org.example.service.PurchasePlanDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: Szy
 * @Name: PurchasePlanDetailServiceImpl
 * @Date: 2023/3/30  23:55
 */
@Service
@Transactional
public class PurchasePlanDetailServiceImpl extends BaseServiceImpl<PurchasePlanDetail, Integer> implements PurchasePlanDetailService {
    @Resource(name = "purchasePlanDetailDaoImpl")
    public void setBaseDao(PurchasePlanDetailDao dao) {
        super.setBaseDao(dao);
    }
}
