package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  商品目录表
 *  作者：szy
 *  日期：  2023-3-27
 */

@Data
@Entity
@Table(name = "t_Goods")
public class Goods extends BaseEntity implements Serializable {

    @Column(name = "sname")
    String sname;

    @JoinColumn(name="goodsTypePid")
    @ManyToOne(targetEntity=GoodsType.class,fetch = FetchType.LAZY)
    @JsonIgnore
    GoodsType goodsType;

    @Column(name = "manufacturer")
    String manufacturer;    // 生产商

    @Column(name = "lifespan")
    Integer lifespan;       // 商品有效期

    @Column(name = "mDescribe")
    String mDescribe;   //描述

    @Transient
    public String getGoodType(){
        if (goodsType!=null)
            return goodsType.getSname();
        else
            return "";
    }
}
