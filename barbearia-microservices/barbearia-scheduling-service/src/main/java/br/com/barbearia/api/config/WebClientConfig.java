package br.com.barbearia.api.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuração do Bean RestTemplate para comunicação com outros microsserviços.
 * A anotação @LoadBalanced permite que o RestTemplate use o nome do serviço
 * (ex: BARBERSHOP-SERVICE) em vez de URLs estáticas.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced // Necessário para integração com Service Discovery (ex: Eureka)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}