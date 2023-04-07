package org.example.service;

import org.example.pojo.Goods;

public interface GoodsService extends BaseService<Goods, Integer>{

    boolean edit(Goods goods, Integer goodsTypePid);

    boolean add(Goods goods, Integer goodsTypePid);
}
