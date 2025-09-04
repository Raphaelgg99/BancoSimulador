package com.simuladorbanco.BancoDigital.service;

import com.simuladorbanco.BancoDigital.exception.NomeNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaRepetidaException;
import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if(contaRepository.existsBySenha(conta.getSenha())){
            throw new SenhaRepetidaException();
        }
        String pass = conta.getSenha();
        conta.setSenha(encoder.encode(pass));
        contaRepository.save(conta);
        contaRepository.flush();
    }
}

