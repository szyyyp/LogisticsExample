package org.example.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_Student")
public class Student  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    Integer id;

    @Column(name = "Sno")
    String Sno;

    @Column(name = "SName")
    String SName;

    @Column(name = "SSex")
    Byte SSex;

    @Column(name = "SDept")
    String SDept;

    @Column(name = "age")
    Integer age;

}
