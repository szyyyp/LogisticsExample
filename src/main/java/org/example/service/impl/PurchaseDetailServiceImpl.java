package org.example.service.impl;

import org.example.dao.PurchaseDetailDao;
import org.example.pojo.PurchaseDetail;
import org.example.service.PurchaseDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/7  23:35
 */
@Service
@Transactional
public class PurchaseDetailServiceImpl extends BaseServiceImpl<PurchaseDetail, Integer> implements PurchaseDetailService {
    @Resource(name = "purchaseDetailDaoImpl")
    public void setBaseDao(PurchaseDetailDao dao) {
        super.setBaseDao(dao);
    }
}
