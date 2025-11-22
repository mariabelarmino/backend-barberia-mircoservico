package br.com.barbearia.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal de inicialização do microsserviço de Barbearia/Catálogo.
 * A anotação exclude desliga a auto-configuração do Spring Security para este microserviço,
 * pois a segurança será gerenciada pelo Auth Service.
 */
@SpringBootApplication
public class BarbershopServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarbershopServiceApplication.class, args);
    }

}