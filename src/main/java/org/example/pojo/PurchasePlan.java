package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *  采购计划单
 *  作者：szy
 *  日期： 2023-3-27
 */
@Data
@Entity
@Table(name = "t_GoodsType")
public class PurchasePlan extends BaseEntity implements Serializable {
    @Column(name = "warehouse")
    String warehouse;
    @Column(name = "dueDate")
    String dueDate;

    @OneToMany(mappedBy = "purchasePlan", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<PurchasePlanDetail> purchasePlanDetailSet = new HashSet<PurchasePlanDetail>();

}
