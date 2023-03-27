package org.example.service.impl;

import org.example.dao.PurchasePlanDao;
import org.example.pojo.PurchasePlan;
import org.example.service.PurchasePlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PurchasePlanServiceImpl extends BaseServiceImpl<PurchasePlan, Integer> implements PurchasePlanService {

    @Resource(name = "purchasePlanDaoImpl")
    public void setBaseDao(PurchasePlanDao dao) {
        super.setBaseDao(dao);
    }

}
