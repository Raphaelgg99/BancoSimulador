package com.simuladorbanco.BancoDigital.view;

import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@EntityScan
public class Login {

    @Autowired
    private ContaRepository contaRepository;

    public void iniciar(){
        Scanner scanner = new Scanner(System.in);
        boolean contaEncontrada = false;
        boolean senhaEncontrada = false;
        while(!contaEncontrada){
        System.out.print("Digite o numero da sua conta:");
        Long numeroDaConta = scanner.nextLong();

        if (contaRepository.existsById(numeroDaConta)) {
            while (!senhaEncontrada) {
                System.out.println("Digite a sua senha:");
                String senha = scanner.next();
                Conta conta = contaRepository.findById(numeroDaConta)
                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
                if (senha.equals(conta.getSenha())) {
                    senhaEncontrada = true;
                    contaEncontrada = true;
                }
                System.out.print("Senha incorreta");
            }
        }
        System.out.println("Conta não encontrada. Verifique o número da conta e tente novamente.");
        }
    }
}
