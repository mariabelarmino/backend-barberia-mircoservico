package br.com.barbearia.api.config;

import br.com.barbearia.api.entity.ServiceItem;
import br.com.barbearia.api.repository.ServiceItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;

/**
 * Classe responsável por popular o banco de dados MongoDB com dados iniciais
 * (seeding) ao iniciar a aplicação.
 * Implementa CommandLineRunner para ser executada após o contexto Spring ser carregado.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final ServiceItemRepository serviceItemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Limpa a coleção antes de inserir, para garantir um estado limpo em cada reinício
        serviceItemRepository.deleteAll()
                .doOnSuccess(aVoid -> log.info("Coleção 'service_items' limpa com sucesso."))
                .block(); // Bloqueia para garantir que a limpeza termine antes da inserção

        // Dados de exemplo para itens de serviço
        ServiceItem corteAdulto = ServiceItem.builder()
                .id("servico-01") // ID fixo para fácil consulta
                .name("Corte de Cabelo (Adulto)")
                .description("Corte clássico ou moderno com tesoura e/ou máquina.")
                .price(new BigDecimal("45.00"))
                .duration(Duration.ofMinutes(40))
                .barbershopId("default-barbershop") // Será o ID da barbearia default (a ser criada depois)
                .build();

        ServiceItem barbaCompleta = ServiceItem.builder()
                .id("servico-02")
                .name("Barba Completa")
                .description("Barba desenhada e aparada, com toalha quente e massagem facial.")
                .price(new BigDecimal("35.00"))
                .duration(Duration.ofMinutes(30))
                .barbershopId("default-barbershop")
                .build();

        ServiceItem pezinho = ServiceItem.builder()
                .id("servico-03")
                .name("Pezinho e Contorno")
                .description("Aparagem rápida e definição do contorno do cabelo/pescoço.")
                .price(new BigDecimal("15.00"))
                .duration(Duration.ofMinutes(15))
                .barbershopId("default-barbershop")
                .build();

        // Insere os dados no banco usando o método reativo (Flux)
        serviceItemRepository.saveAll(Arrays.asList(corteAdulto, barbaCompleta, pezinho))
                .collectList()
                .subscribe(items -> log.info("Banco de dados populado com {} ServiceItems.", items.size()));
    }
}