package org.example.service.impl;

import org.example.dao.GoodsDao;
import org.example.pojo.Goods;
import org.example.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class GoodsServiceImpl extends BaseServiceImpl<Goods, Integer> implements GoodsService {

    @Resource(name = "goodsDaoImpl")
    public void setBaseDao(GoodsDao dao) {
        super.setBaseDao(dao);
    }

}
