# LogisticsExample

本系统为“物流信息系统设计” 课程设计的 开发框架 及范例

为springboot + hibernate + easyUI + mysql

大家可以自由下载 学习

但请不要在本仓库上进行代码更新，或者至少不能主分支上进行更新


一、lombok 与 hibernate 的 jpa（pojo）
1 Lombok 是一个非常受欢迎和有用的工具。尽管如此，请注意Lombok @EqualsAndHashCode对实体的影响可能会带来严重问题。
实体应实施equals()和hashCode()。主要问题是Hibernate要求实体在其所有状态转换（瞬态，附加，分离 和删除）中等于自身。使用Lombok @EqualsAndHashCode不会尊重此要求

建议：在Hibernate/JPA实体中避免使用Lombok，尽量避免直接使用其equals()和hashCode(),尤其是在有OneToMany（一对多）或者多对一（ManyToOne）
这样的情况下，则需自己覆盖这两个函数：
 建议使用ide提供的自动生成这两个函数后，去掉其中的关联对象部分。如本框架中的jpa（pojo）的Entity的equals()和hashCode()

二、OneToMany（一对多）或者多对一（ManyToOne）的实体的主控端
    在实体的描述中，有@JoinColumn为主控端，也即这个字段上即是没有声明级联更新/级联删除，也会自动级联操作进行更新，由many端维护一对多的这张关系
如果要从MapBy端（被控端），更新保存从表数据，则应两端维护一对多的关系，并在主表（一端）设置级联更新/级联删除后，用save函数保存数据。
    
三、OneToMany（一对多）或者多对一（ManyToOne）的实体的ToString函数
    由于有级联，在建立联系的两个对的ToString()中，必须至少有一个的ToString()中去掉有关关联对象的输出