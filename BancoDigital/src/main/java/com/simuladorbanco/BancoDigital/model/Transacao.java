package com.simuladorbanco.BancoDigital.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transacao")
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private Conta contaRementente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Conta contaDestinario;

    @Column(nullable = false)
    private double valor;  // Valor da transação

    @Column(nullable = false)
    private LocalDateTime dataHora;

}
