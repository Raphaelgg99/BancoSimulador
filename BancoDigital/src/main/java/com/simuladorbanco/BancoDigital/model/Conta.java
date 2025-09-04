package com.simuladorbanco.BancoDigital.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@Table(name = "tb_conta")
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroDaConta;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private double saldo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tab_conta_roles", // Tabela intermediária
            joinColumns = @JoinColumn(name = "conta_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
/*@ElementCollection(fetch = FetchType.EAGER)
@CollectionTable(name = "tab_conta_roles", joinColumns = @JoinColumn(name = "conta_id"))
@Column(name = "role_id")
private List<String> roles = new ArrayList<>(); */

