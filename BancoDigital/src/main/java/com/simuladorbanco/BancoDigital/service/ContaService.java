package com.simuladorbanco.BancoDigital.service;

import com.simuladorbanco.BancoDigital.dtos.TransferenciaRequest;
import com.simuladorbanco.BancoDigital.exception.*;
import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PasswordEncoder encoder;


    public void criarConta(Conta conta){
        if(conta.getNome() == null ){
            throw new NomeNullException();
        }
        if (conta.getSenha() == null){
            throw new SenhaNullException();
        }
        if(contaRepository.existsByEmail(conta.getEmail())){
            throw new EmailRepetidoException();
        }
        if(contaRepository.existsBySenha(conta.getSenha())){
            throw new SenhaRepetidaException();
        }
        String pass = conta.getSenha();
        conta.setSenha(encoder.encode(pass));
        contaRepository.save(conta);
        contaRepository.flush();
    }

    public void atualizarConta(Long numeroDaConta, Conta contaAtualizada){
        Conta conta = contaRepository.findById(numeroDaConta).
                orElseThrow(() -> new RuntimeException("Conta não encontrado"));
        conta.setNome(contaAtualizada.getNome());
        conta.setEmail(contaAtualizada.getEmail());
        String senhaCriptografada = encoder.encode(contaAtualizada.getSenha());
        conta.setSenha(senhaCriptografada);
        conta.setSaldo(contaAtualizada.getSaldo());
        contaRepository.save(conta);
    }

    public Conta depositar(Double valor, Long numeroDaConta) {
        Conta conta = contaRepository.findById(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSaldo(conta.getSaldo() + valor);
        System.out.println("Conta atualizada: " + conta);
        return contaRepository.save(conta);
    }

    public Conta sacar(double valor, Long numeroDaConta){
        Conta conta = contaRepository.findById(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        if(conta.getSaldo()<valor){
            throw new SaldoInsuficienteException();
        }
        conta.setSaldo(conta.getSaldo() - valor);
        return contaRepository.save(conta);
    }

    public Conta tranferencia(TransferenciaRequest transferencia, Long numeroContaRemetente
    ){
        Conta contaRemetente = contaRepository.findById(numeroContaRemetente)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        Conta contaDestinario = contaRepository.findById(transferencia.getNumeroContaDestinatario())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        if(contaRemetente.getSaldo()<transferencia.getValor()){
            throw new SaldoInsuficienteException();
        }
        contaRemetente.setSaldo(contaRemetente.getSaldo() - transferencia.getValor());
        contaDestinario.setSaldo(contaDestinario.getSaldo() + transferencia.getValor());
        contaRepository.save(contaRemetente);
        contaRepository.save(contaDestinario);
        return contaRemetente;
    }

    public void removerConta(@PathVariable Long numeroDaConta){
        Conta conta = contaRepository.findById(numeroDaConta).
                orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        contaRepository.delete(conta);
    }

    public List<Conta> listarTodos(){
        return contaRepository.findAll();
    }
}

