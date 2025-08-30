package com.simuladorbanco.BancoDigital.controller;

import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.model.Transacao;
import com.simuladorbanco.BancoDigital.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    TransacaoRepository transacaoRepository;

    @PutMapping("/adicionar")
    public Transacao adicionarTransacao(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    @GetMapping("/listartodas")
    public List<Transacao> listarTodas(){
        return  transacaoRepository.findAll();
    }
    @GetMapping("/{id}/listarconta")
    public List<Transacao> listarTodasPorConta(Conta conta){
        return transacaoRepository.findByConta(conta);
    }

}
