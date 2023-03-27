package org.example.pojo.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
    CAN  简易表

    @Author szy
    @Date  2022-6-11

    @Table 注解包含一个schema和一个catelog 属性，使用@UniqueConstraints 可以定义表的唯一约束。
    如果是联合约束就用下面这种
    @Table(name="tbl_sky",
      uniqueConstraints = {@UniqueConstraint(columnNames={"month", "day"})})

    如果是单一字段约束可以用

    @Table(name="tbl_sky",
      uniqueConstraints = {@UniqueConstraint(columnNames="month")})
 */
@Data
@Entity
@Table(name = "t_canMeter",uniqueConstraints = {@UniqueConstraint(columnNames="canID")})
public class CanMeter implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @JoinColumn(name="pid")
    @ManyToOne(targetEntity=Scale.class,fetch = FetchType.LAZY)
    @JsonIgnore
    Scale scale;

    @Column(name = "canID")
    Long canID;         //仪表序列号，ID

    @Column(name = "alias")
    String alias;   //别名，显示用

    @Column(name = "totalrange")
    Integer totalRange;

    @Column(name = "displayUnit")
    Integer displayUnit;    //仪表显示单位

    @Column(name = "accuracy")
    Integer accuracy;          // 量程精度，以万分之为单位

    @Column(name = "maxCollectInterval")
    Integer maxCollectInterval;          // 实时数据采集最长间隔(毫秒)

    @Column(name = "acquisitionCycle")
    Integer acquisitionCycle;          // 数据采集周期(毫秒)

    @Column(name = "analyseDataSize")
    Integer analyseDataSize;          // 重量数据分析的 重量点 的数目

    @Column(name = "zooTrack")
    Integer zooTrack;

    @Column(name = "orderId")
    Integer orderId;            // 生成二级称排序用

    @Column(name = "recordRealWeight")
    Integer recordRealWeight;   // 数据库是否记录实时重量, 1 记录，0 不记录

    @Transient
    public Integer getScaleId(){
        if (scale!=null)
            return scale.getId();
        else
            return -1;
    }

    @Transient
    public Integer getWhenOpenScale(){
        if (scale!=null)
            return scale.getOpenSecondScale();
        else
            return -1;
    }

    @Transient
    public String getScaleStr(){
        if (scale!=null)
            if (scale.getScaleType()== 1)
                return "ID: " + scale.getId() + ";&nbsp;&nbsp;&nbsp;" + scale.getAlias() + ";&nbsp;&nbsp;&nbsp; --> 二级称开启条件："
                    + scale.getOpenSecondScale();
            else return "ID: " + scale.getId() + ";&nbsp;&nbsp;&nbsp;" + scale.getAlias() + ";&nbsp;&nbsp;&nbsp; --> 普通称";
        else
            return "";
    }

}
