package org.example.pageModel;

import lombok.Data;

/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/9  22:10
 */

@Data
public class PurchaseDetailDto {
    Integer id;

    Integer num;        // purchase number

    Float price;        // purchase price

    Integer purchasePid;    //采购计划单的 id

    Integer purchasePlanPid;    //采购计划单的ID

    Integer purchasePlanDetailPid;  //采购计划明细单 的 id
}
