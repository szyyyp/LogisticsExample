package org.example.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "mDescribe")
    String mDescribe;
}
