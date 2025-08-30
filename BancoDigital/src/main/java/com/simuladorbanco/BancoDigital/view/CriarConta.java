package com.simuladorbanco.BancoDigital.view;

import com.simuladorbanco.BancoDigital.controller.ContaController;
import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CriarConta {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaController contaController;


    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o seu nome completo");
        String nome = scanner.nextLine();
        System.out.println("Digite o qual será o seu saldo inicial");
        System.out.print("R$");
        Double saldo = scanner.nextDouble();
        scanner.nextLine();
        String senha;
        while (true) {
            System.out.println("Digite a sua senha: A senha deve conter pelo menos 8 caracteres, sendo no mínimo 4 letras e 4 números");
            senha = scanner.nextLine();

            // Expressão regular para verificar a senha
            if (senha.matches("^(?=(.*[a-zA-Z]){4})(?=(.*\\d){4}).{8,}$")) {
                if(!contaRepository.existsBySenha(senha)) {
                    break;
                }
                else{
                    System.out.println("A senha digitada já existe");
                    continue;
                }// Se a senha for válida, sai do loop
            } else {
                System.out.println("Senha inválida. A senha deve ter no mínimo 8 caracteres, sendo no mínimo 4 letras e 4 números.");
            }
        }
        String senhaRepetida;
        while (true){
            System.out.println("Digite a sua senha novamente:");
            senhaRepetida = scanner.nextLine();
            if (senha.equals(senhaRepetida)){
                break;
            }
            System.out.println("Senha incorreta!");
        }
        Conta conta = new Conta();
        conta.setNome(nome);
        conta.setSenha(senha);
        conta.setSaldo(saldo);
        contaController.adicionarConta(conta);
    }
}
