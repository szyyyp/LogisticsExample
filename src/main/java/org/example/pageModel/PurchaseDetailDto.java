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

    Integer num;

    Float price;

    Integer purchasePid;

    Integer purchasePlanDetailPid;
}
