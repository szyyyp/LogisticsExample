package org.example.service.impl;


import org.example.dao.GoodsTypeDao;
import org.example.pojo.GoodsType;
import org.example.service.GoodsTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class GoodsTypeServiceImpl extends BaseServiceImpl<GoodsType, Integer> implements GoodsTypeService {

    @Resource(name = "goodsTypeDaoImpl")
    public void setBaseDao(GoodsTypeDao dao) {
        super.setBaseDao(dao);
    }

}
