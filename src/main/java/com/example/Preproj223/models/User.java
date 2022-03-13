package com.example.Preproj223.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private String lastname;

    @Column
    private String Email;

    public User(String name, int age, String lastname, String email) {
        this.name = name;
        this.age = age;
        this.lastname = lastname;
        Email = email;
    }
}
