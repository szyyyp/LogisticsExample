package org.example.pageModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述：
 *
 * @Author: Szy
 * @Date: 2023/4/4  15:08
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasePlanDetailDto {
    Integer id;

    Integer num;

    Float price;

    Integer purchasePlanPid;

    Integer goodsPid;
}
