package com.simuladorbanco.BancoDigital.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TelaInicial {

    @Autowired
    private Login login;

    @Autowired
    private CriarConta criarConta;

    public void iniciar(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha a opção:");
        System.out.println("1-Fazer Login  2-Criar Conta");
        int opcao = scanner.nextInt();
        if (opcao == 1){
            login.iniciar();
        }
        if(opcao == 2){
            criarConta.iniciar();
        }
    }
}
