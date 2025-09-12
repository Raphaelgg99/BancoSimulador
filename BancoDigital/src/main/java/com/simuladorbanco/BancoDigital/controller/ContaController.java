package com.simuladorbanco.BancoDigital.controller;

import com.simuladorbanco.BancoDigital.dtos.TransferenciaRequest;
import com.simuladorbanco.BancoDigital.exception.NomeNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaRepetidaException;
import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.repository.ContaRepository;
import com.simuladorbanco.BancoDigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Conta> depositar(@RequestBody Double valor, @PathVariable Long numeroDaConta) {
        Conta conta = contaService.depositar(valor, numeroDaConta);
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/{numeroDaConta}/sacar")
    public ResponseEntity <Conta> sacar(@RequestBody double valor, @PathVariable Long numeroDaConta){
        Conta conta = contaService.sacar(valor, numeroDaConta);
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/{numeroContaRemetente}/transferencia")
    public ResponseEntity <Conta> transferencia(@RequestBody TransferenciaRequest transferencia,
                                              @PathVariable Long numeroContaRemetente){
        Conta conta = contaService.tranferencia(transferencia, numeroContaRemetente);
        return ResponseEntity.ok(conta);
    }


    @PostMapping("/adicionar")
    public ResponseEntity<Conta> adicionarConta(@RequestBody Conta conta) {
        contaService.criarConta(conta);
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/{numeroDaConta}/atualizar")
    public ResponseEntity<Conta> atualizarConta(@PathVariable Long numeroDaConta,@RequestBody Conta contaAtualizada){
        contaService.atualizarConta(numeroDaConta, contaAtualizada);
        return ResponseEntity.ok(contaAtualizada);
    }

    @DeleteMapping("/{numeroDaConta}")
    public void removerConta(@PathVariable Long numeroDaConta){
        contaService.removerConta(numeroDaConta);
    }

    @GetMapping("/listartodas")
    public ResponseEntity <List<Conta>> listarTodos(){
        List<Conta> contas = contaService.listarTodos();
        return ResponseEntity.ok(contas);
    }
}
