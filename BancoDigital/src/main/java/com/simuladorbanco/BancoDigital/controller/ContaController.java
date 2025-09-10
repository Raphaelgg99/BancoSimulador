package com.simuladorbanco.BancoDigital.controller;

import com.simuladorbanco.BancoDigital.dtos.TransferenciaRequest;
import com.simuladorbanco.BancoDigital.exception.NomeNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaRepetidaException;
import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.repository.ContaRepository;
import com.simuladorbanco.BancoDigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    ContaService contaService;

    @Autowired
    private PasswordEncoder encoder;

    @PutMapping("/{numeroDaConta}/depositar")
    @Transactional
    public Conta depositar(@RequestBody Double valor, @PathVariable Long numeroDaConta) {
        Conta conta = contaRepository.findById(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSaldo(conta.getSaldo() + valor);
        System.out.println("Conta atualizada: " + conta);
        return contaRepository.save(conta);
    }

    @PutMapping("/{numeroDaConta}/sacar")
    public Conta sacar(@RequestBody double valor, @PathVariable Long numeroDaConta){
        Conta conta = contaRepository.findById(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSaldo(conta.getSaldo() - valor);
        return contaRepository.save(conta);
    }

    @PutMapping("/{numeroContaRemetente}/transferencia")
    public void tranferencia(@RequestBody TransferenciaRequest transferencia, @PathVariable Long numeroContaRemetente
            ){
        Conta contaRemetente = contaRepository.findById(numeroContaRemetente)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        Conta contaDestinario = contaRepository.findById(transferencia.getNumeroContaDestinatario())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        contaRemetente.setSaldo(contaRemetente.getSaldo() - transferencia.getValor());
        contaDestinario.setSaldo(contaDestinario.getSaldo() + transferencia.getValor());
        contaRepository.save(contaRemetente);
        contaRepository.save(contaDestinario);
    }


    @PostMapping("/adicionar")
    public void adicionarConta(@RequestBody Conta conta) {
        contaService.criarConta(conta);
    }

    @PutMapping("/{numeroDaConta}/atualizar")
    public void atualizarConta(@PathVariable Long numeroDaConta,@RequestBody Conta contaAtualizada){
        contaService.atualizarConta(numeroDaConta, contaAtualizada);
    }

    @DeleteMapping("/{numeroDaConta}")
    public void removerConta(@PathVariable Long numeroDaConta){
        Conta conta = contaRepository.findById(numeroDaConta).
                orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        contaRepository.delete(conta);
    }

    @GetMapping("/listartodas")
    public List<Conta> listarTodos(){
        return contaRepository.findAll();
    }
}
