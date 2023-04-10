package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Transient  // 采购单PId
    public Integer getPurchasePid(){
        if (purchase!=null)
            return purchase.getId();
        else
            return null;
    }

    @Transient      // 采购单号
    public String getPurchaseSno(){
        if (purchase!=null)
            return purchase.getSno();
        else
            return null;
    }

    @Transient      // 采购计划单 id
    public Integer getPurchasePlanPid(){
        if (purchasePlanDetail!=null)
            return purchasePlanDetail.getPurchasePlan().getId();
        else
            return null;
    }
    @Transient      // 采购计划单号
    public String getPurchasePlanSno(){
        if (purchasePlanDetail!=null)
            return purchasePlanDetail.getPurchasePlan().getSno();
        else
            return null;
    }

    @Transient      // 采购计划单仓库
    public String getPurchasePlanWareHouse(){
        if (purchasePlanDetail!=null)
            return purchasePlanDetail.getPurchasePlan().getWarehouse();
        else
            return null;
    }

    @Transient     //采购计划单Pid
    public Integer getPurchasePlanDetailPid(){
        if (purchasePlanDetail!=null)
            return purchasePlanDetail.getId();
        else
            return null;
    }

    @Transient      // 采购计划单明细对应的计划采购商品名称
    public String getGoodsName(){
        if (purchasePlanDetail!=null)
            return purchasePlanDetail.getGoodsName();
        else
            return null;
    }

    @Transient      // 采购计划单明细对应的计划采购商品名称
    public String getGoodsTypeName(){
        if (purchasePlanDetail!=null)
            return purchasePlanDetail.getGoodsTypeName();
        else
            return null;
    }

    @Transient      // 采购计划单明细对应的计划采购量
    public Integer getPurchasePlanDetailNum(){
        if (purchasePlanDetail!=null)
            return purchasePlanDetail.getNum();
        else
            return null;
    }

    @Transient      // 采购计划单明细对应的计划采购量 的 已采购量
    public Integer getPurchasePlanDetailAlreadyNum(){
        if (purchasePlanDetail!=null)
            return purchasePlanDetail.getAlreadyNum();
        else
            return null;
    }

    @Transient      // 采购计划单明细对应的计划采购量 的 已采购量
    public Float getPurchasePlanDetailPrice(){
        if (purchasePlanDetail!=null)
            return purchasePlanDetail.getPrice();
        else
            return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseDetail)) return false;
        PurchaseDetail that = (PurchaseDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(num, that.num) && Objects.equals(price, that.price) && Objects.equals(loaded, that.loaded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, num, price, loaded);
    }

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
