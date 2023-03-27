package org.example.service.impl;

import org.example.dao.GoodsDao;
import org.example.dao.GoodsTypeDao;
import org.example.pojo.Goods;
import org.example.pojo.GoodsType;
import org.example.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class GoodsServiceImpl extends BaseServiceImpl<Goods, Integer> implements GoodsService {

    @Resource(name = "goodsTypeDaoImpl")
    GoodsTypeDao goodsTypeDao;

    @Resource(name = "goodsDaoImpl")
    public void setBaseDao(GoodsDao dao) {
        super.setBaseDao(dao);
    }

    public boolean edit(Goods goods, Integer goodsTypePid){
        try {
            GoodsType goodsType = goodsTypeDao.find(goodsTypePid);
            Goods goodsIn = this.find(goods.getId());
            BeanUtils.copyProperties(goods,goodsIn,"goodsType");
            goodsIn.setGoodsType(goodsType);
            this.update(goodsIn);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean add(Goods goods, Integer goodsTypePid){
        try {
            GoodsType goodsType = goodsTypeDao.find(goodsTypePid);
            goods.setGoodsType(goodsType);
            this.save(goods);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
