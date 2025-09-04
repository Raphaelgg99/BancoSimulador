package com.simuladorbanco.BancoDigital.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<Conta> contas = new HashSet<>();
}
