package org.example.pojo.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/*
    电子称

    @Author szy
    @Date  2022-6-27

 */

@Data
@Entity
@Table(name = "t_scale")

public class Scale  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    Integer id;

    @Column(name = "scalecode")
    String scaleCode;

    @Column(name = "scaletype")
    Integer scaleType;          // 0: 普通称； 1：二级称

    @Column(name = "openSecondScale")
    Integer openSecondScale;            //二级称开启条件，重量大于某个值

    @Column(name = "location")
    String location;

    @Column(name = "minWorkWeight")
    Integer minWorkWeight;      // 最小工作重量阈值

    @Column(name = "alias")
    String alias;

    @Column(name = "wmsPlatform")
    String wmsPlatform;         // wms 称台号

    // 一对多映射,懒加载,级联保存更新，一方作为被控方
    @OneToMany(mappedBy = "scale", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<CanMeter> canMeters =  new HashSet<CanMeter>();

    @Column(name = "palletNo")
    String palletNo;            //  主要控制sany一层的 新入库回库入库 区分标准
}
