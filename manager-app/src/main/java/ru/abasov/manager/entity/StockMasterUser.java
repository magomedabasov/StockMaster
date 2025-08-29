package ru.abasov.manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user", schema = "user_management")
public class StockMasterUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_username")
    @NotNull
    private String username;

    @Column(name = "c_password")
    @NotNull
    private String password;


    // Указываем список прав которые доступны пользователю
    @ManyToMany
    @JoinTable(schema = " user_manaegement", name = "user_authority",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_authority"))
    private List<Authority> authorities;
}
