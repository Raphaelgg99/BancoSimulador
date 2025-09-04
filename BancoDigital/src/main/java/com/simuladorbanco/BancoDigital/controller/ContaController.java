package com.simuladorbanco.BancoDigital.controller;

import com.simuladorbanco.BancoDigital.exception.NomeNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaRepetidaException;
import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.repository.ContaRepository;
import com.simuladorbanco.BancoDigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @PutMapping("/{numeroDaConta}/depositar")
    @Transactional
    public Conta depositar(@RequestParam Optional<Double> valor, @PathVariable Long numeroDaConta) {
        Conta conta = contaRepository.findById(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSaldo(conta.getSaldo() + valor.orElse(0.0));
        System.out.println("Conta atualizada: " + conta);
        return contaRepository.save(conta);
    }

    @PutMapping("/{numeroDaConta}/sacar")
    public Conta sacar(double valor, @PathVariable Long numeroDaConta){
        Conta conta = contaRepository.findById(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSaldo(conta.getSaldo() + valor);
        return contaRepository.save(conta);
    }

    @PutMapping("/{numeroDaConta}/tranferencia")
    public void tranferencia(double valor, @PathVariable Long numeroContaRemetente
            , Long numeroContaDestinatario){
        Conta contaRemetente = contaRepository.findById(numeroContaRemetente)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        Conta contaDestinario = contaRepository.findById(numeroContaRemetente)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        contaRemetente.setSaldo(contaRemetente.getSaldo() - valor);
        contaDestinario.setSaldo(contaDestinario.getSaldo() + valor);
        contaRepository.save(contaRemetente);
        contaRepository.save(contaDestinario);
    }


    @PostMapping("/adicionar")
    public void adicionarConta(@RequestBody Conta conta) {
        contaService.criarConta(conta);
    }

    @PutMapping("/{numeroDaConta}/atualizar")
    public Conta atualizarConta(@PathVariable Long numeroDaConta,Conta contaExistente){
        Conta conta = contaRepository.findById(numeroDaConta).
                orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        conta.setNome(contaExistente.getNome());
        conta.setSenha(contaExistente.getSenha());
        return contaRepository.save(conta);
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
