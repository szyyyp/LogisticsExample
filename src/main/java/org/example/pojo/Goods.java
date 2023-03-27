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
    String manufacturer;

    @Column(name = "lifespan")
    Integer lifespan;

    @Column(name = "mDescribe")
    String mDescribe;

}
