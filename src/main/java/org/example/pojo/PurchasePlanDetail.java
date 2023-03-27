package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  订单明细表
 *  作者：szy
 *  日期：  2023-3-27
 */

@Data
@Entity
@Table(name = "t_PurchasePlanDetail")
public class PurchasePlanDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    Integer id;

    @Column(name = "num")
    Integer num;

    @Column(name = "price")
    Float price;

    @JoinColumn(name="purchasePlanPid")
    @ManyToOne(targetEntity=PurchasePlan.class,fetch = FetchType.LAZY)
    @JsonIgnore
    PurchasePlan purchasePlan;

    @JoinColumn(name="goodsPid")
    @ManyToOne(targetEntity=Goods.class,fetch = FetchType.LAZY)
    @JsonIgnore
    Goods goods;

}
