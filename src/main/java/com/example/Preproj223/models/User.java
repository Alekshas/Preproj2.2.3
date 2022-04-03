package com.example.Preproj223.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String lastname;

    @Column
    private int age;

    @Column
    private String email;

    public User(String name, String lastname, int age, String email) {
        this.name = name;
        this.age = age;
        this.lastname = lastname;
        this.email = email;
    }
}
