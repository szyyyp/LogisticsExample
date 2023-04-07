package org.example.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 *  商品类别表
 *  作者：szy
 *  日期：  2023-3-27
 */

@Data
@Entity
@Table(name = "t_GoodsType")
public class GoodsType extends BaseEntity implements Serializable {

    @Column(name = "sname")
    String sname;

    @Column(name = "addition")
    String addition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodsType)) return false;
        if (!super.equals(o)) return false;
        GoodsType goodsType = (GoodsType) o;
        return Objects.equals(sname, goodsType.sname) && Objects.equals(addition, goodsType.addition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sname, addition);
    }
}
