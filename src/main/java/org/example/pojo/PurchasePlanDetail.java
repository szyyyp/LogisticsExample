package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 *  订单明细表
 *  作者：szy
 *  日期：  2023-3-27
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Transient
    public Integer getGoodsPid(){
        if (goods!=null)
            return goods.getId();
        else
            return null;
    }

    @Transient
    public Integer purchasePlanPid(){
        if (purchasePlan!=null)
            return purchasePlan.getId();
        else
            return null;
    }

    @Transient
    public String getGoodsName(){
        if (goods!=null)
            return goods.getSname();
        else
            return "";
    }

    @Transient
    public String getGoodsTypeName(){
        if (goods!=null) {
            if (goods.getGoodsType() != null){
                GoodsType type = goods.getGoodsType();
                return type.getSname();
            }
            //return goods.getGoodType().getSname();
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchasePlanDetail)) return false;
        return id != null && id.equals(((PurchasePlan) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, num, price, goods);
    }

    @Override
    public String toString() {
        return "PurchasePlanDetail{" +
                "id=" + id +
                ", num=" + num +
                ", price=" + price +
                ", goods=" + goods +
                '}';
    }
}
