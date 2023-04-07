package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/7  22:23
 */

@Data
@Entity
@Table(name = "t_PurchaseDetail")
public class PurchaseDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    Integer id;

    @Column(name = "num")
    Integer num;

    @Column(name = "price")
    Float price;

    @Column(name = "loaded")
    Integer loaded;     // 已入库数量

    @JoinColumn(name="purchasePid")
    @ManyToOne(targetEntity=Purchase.class,fetch = FetchType.LAZY)
    @JsonIgnore
    Purchase purchase;

    @JoinColumn(name="purchasePlanDetailPid")
    @ManyToOne(targetEntity=PurchasePlanDetail.class,fetch = FetchType.LAZY)
    @JsonIgnore
    PurchasePlanDetail purchasePlanDetail;

    @Override
    public String toString() {
        return "PurchaseDetail{" +
                "id=" + id +
                ", num=" + num +
                ", price=" + price +
                ", loaded=" + loaded +
                '}';
    }
}
