package org.example.dao.impl;

import org.example.dao.GoodsDao;
import org.example.pojo.Goods;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsDaoImpl extends BaseDaoImpl <Goods,Integer> implements GoodsDao {

}
