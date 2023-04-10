package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *  采购计划单
 *  作者：szy
 *  日期： 2023-3-27
 */
@Data
@Entity
@Table(name = "t_PurchasePlan")
public class PurchasePlan extends BaseEntity implements Serializable {
    @Column(name = "warehouse")
    String warehouse;
    @Column(name = "dueDate")
    String dueDate;

    @Column(name = "addition")
    String addition;   //附加说明

    @OneToMany(mappedBy = "purchasePlan", fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JsonIgnore
    Set<PurchasePlanDetail> purchasePlanDetailSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchasePlan)) return false;
        if (!super.equals(o)) return false;
        PurchasePlan that = (PurchasePlan) o;
        return Objects.equals(warehouse, that.warehouse) && Objects.equals(dueDate, that.dueDate) && Objects.equals(addition, that.addition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), warehouse, dueDate, addition);
    }
}
