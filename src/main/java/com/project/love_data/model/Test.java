package com.project.love_data.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="test")
@ToString
//@Builder
@Getter
//@AllArgsConstructor
//@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 2, nullable = false)
    private String country;

    public Test() {
        this.seq=0;
        this.name=null;
        this.country=null;
    }

    public Test(int seq, String name, String country) {
        this.seq = seq;
        this.name = name;
        this.country = country;
    }
}
