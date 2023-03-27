package org.example.service;

import org.example.pojo.Course;
import org.example.pojo.Goods;

public interface GoodsService extends BaseService<Goods, Integer>{

    public boolean edit(Goods goods, Integer goodsTypePid);

    public boolean add(Goods goods, Integer goodsTypePid);
}
