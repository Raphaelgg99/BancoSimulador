package com.simuladorbanco.BancoDigital;

import com.simuladorbanco.BancoDigital.view.TelaInicial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.simuladorbanco.BancoDigital.repository")
@EntityScan(basePackages = "com.simuladorbanco.BancoDigital.model")
public class BancoDigitalApplication implements CommandLineRunner {

	@Autowired
	private TelaInicial telaInicial;

	public static void main(String[] args) {
		SpringApplication.run(BancoDigitalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		telaInicial.iniciar();
	}

}
