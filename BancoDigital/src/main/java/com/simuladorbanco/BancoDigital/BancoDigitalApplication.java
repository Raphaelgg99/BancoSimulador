package com.simuladorbanco.BancoDigital;

import com.simuladorbanco.BancoDigital.controller.ContaController;
import com.simuladorbanco.BancoDigital.model.Conta;
import com.simuladorbanco.BancoDigital.repository.ContaRepository;
import com.simuladorbanco.BancoDigital.view.TelaInicial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.simuladorbanco.BancoDigital.repository")
@EntityScan(basePackages = "com.simuladorbanco.BancoDigital.model")
public class BancoDigitalApplication implements CommandLineRunner {

	@Autowired
	private TelaInicial telaInicial;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ContaController contaController;

	public static void main(String[] args) {
		SpringApplication.run(BancoDigitalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Conta conta = contaRepository.findByEmail("admin@email.com");

		if(conta == null){
			System.out.println("Criando novo usuário ADMIN...");
			conta = new Conta();
			conta.setNome("ADMIN");
			conta.setEmail("admin@email.com");
			conta.setSaldo(100.0);
			conta.setSenha("master123");
			// Adicionando as roles como lista de strings
			List<String> roles = new ArrayList<>();
			roles.add("ADMIN");
			conta.setRoles(roles);
			System.out.println("Roles da conta: " + conta.getRoles());
			contaController.adicionarConta(conta);
		}
		Conta contaSalva = contaRepository.findByEmail("admin@email.com");
		if(contaSalva==null){
			System.out.println("Conta não encontrada");
		}
		else {
			System.out.println("Conta encontrada: " + contaSalva.getEmail());
			System.out.println("Roles da conta : " + contaSalva.getRoles());
		}

		conta = contaRepository.findByEmail("user@email.com");

		if(conta == null){
			conta = new Conta();
			conta.setNome("USER");
			conta.setEmail("user@email.com");
			conta.setSenha("user123");
			conta.setSaldo(100.0);
			// Adicionando as roles como lista de strings
			List<String> roles = new ArrayList<>();
			roles.add("USER");
			conta.setRoles(roles);
			contaController.adicionarConta(conta);
		}
		telaInicial.iniciar();
	}
}

