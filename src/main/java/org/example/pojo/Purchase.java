package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 功能描述：采购订单
 * 作者: Szy
 * 日期: 2023/4/7  19:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_purchase")
public class Purchase extends BaseEntity implements Serializable {
    @Column(name = "supplier")
    String supplier;

    @Column(name = "address")
    String address;     // 收货地址

    @Column(name = "dueDate")
    String dueDate;     //  要求的送货日期

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JsonIgnore
    Set<PurchaseDetail> purchaseDetailSet = new HashSet<>();

}
