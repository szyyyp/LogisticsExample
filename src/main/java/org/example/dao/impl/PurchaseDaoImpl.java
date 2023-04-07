package org.example.dao.impl;

import org.example.dao.PurchaseDao;
import org.example.pojo.Purchase;
import org.springframework.stereotype.Repository;

/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/7  23:24
 */

@Repository
public class PurchaseDaoImpl extends BaseDaoImpl <Purchase,Integer> implements PurchaseDao {
}
