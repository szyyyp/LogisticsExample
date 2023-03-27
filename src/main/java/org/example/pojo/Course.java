package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 课程表
 * 作者：szy
 * 日期：2023-3-27
 */
@Data
@Entity
@Table(name = "t_Course")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    Integer id;

    @JoinColumn(name="CPno")
    @ManyToOne(targetEntity=Course.class,fetch = FetchType.LAZY)
    @JsonIgnore
    Course course;

    @Column(name = "Cno")
    String cno;

    @Column(name = "CName")
    String cname;

    @Column(name = "CCredit")
    Integer ccredit;

    @Transient
    public String getCpName(){
        if (course!=null)
            return course.getCname();
        else
            return "";
    }
}
