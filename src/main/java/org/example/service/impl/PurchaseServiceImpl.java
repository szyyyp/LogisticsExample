package org.example.service.impl;

import org.example.pojo.Purchase;
import org.example.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/7  23:32
 */

@Service
@Transactional
public class PurchaseServiceImpl extends BaseServiceImpl<Purchase, Integer> implements PurchaseService {
}
