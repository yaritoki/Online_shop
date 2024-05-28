package org.jp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "user_management",name="t_user")
public class UserClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "c_username")
    private String username;
    @Column(name="c_password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            schema = "user_management",name="t_user_authority",
            joinColumns = @JoinColumn(name="id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_authority"))
    private List<Authority> Authorities;
}
