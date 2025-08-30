package com.simuladorbanco.BancoDigital.controller;

import com.simuladorbanco.BancoDigital.exception.NomeNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaRepetidaException;
import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    ContaRepository contaRepository;

    @PutMapping("/{numeroDaConta}/depositar")
    public Conta depositar(double valor, @PathVariable Long numeroDaConta) {
        Conta conta = contaRepository.findById(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSaldo(conta.getSaldo() + valor);
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
    public Conta adicionarConta(@RequestBody Conta conta) {
        if(conta.getNome() == null ){
            throw new NomeNullException();
        }
        if (conta.getSenha() == null){
            throw new SenhaNullException();
        }
        if(contaRepository.existsBySenha(conta.getSenha())){
            throw new SenhaRepetidaException();
        }
        return contaRepository.save(conta);
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

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/listartodas")
    public List<Conta> listarTodos(){
        return contaRepository.findAll();
    }
}
