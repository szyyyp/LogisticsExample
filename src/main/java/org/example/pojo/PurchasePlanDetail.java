package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.util.DataUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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

    @Column(name = "alreadyNum")
    Integer alreadyNum;

    @Column(name = "isClose")
    int isClose;            // 1: 本采购项目已经完结， 否则为 0；

    @JoinColumn(name="purchasePlanPid")
    @ManyToOne(targetEntity=PurchasePlan.class,fetch = FetchType.LAZY)
    @JsonIgnore
    PurchasePlan purchasePlan;

    @JoinColumn(name="goodsPid")
    @ManyToOne(targetEntity=Goods.class,fetch = FetchType.LAZY)
    @JsonIgnore
    Goods goods;


    @PreUpdate
    public void preUpdate(){
        if (this.alreadyNum - this.num >=0)
            this.isClose = 1;
        else
            this.isClose = 0;
    }

    @Transient
    public Integer getGoodsPid(){
        if (goods!=null)
            return goods.getId();
        else
            return null;
    }

    @Transient
    public Integer getPurchasePlanPid(){
        if (purchasePlan!=null)
            return purchasePlan.getId();
        else
            return null;
    }

    @Transient
    public String getPurchasePlanSno(){
        if (purchasePlan!=null)
            return purchasePlan.getSno();
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
                ", alreadyNum=" + alreadyNum +
                '}';
    }
}
