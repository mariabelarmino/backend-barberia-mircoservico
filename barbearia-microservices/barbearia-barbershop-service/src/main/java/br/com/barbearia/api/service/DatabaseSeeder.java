package br.com.barbearia.api.service;

import br.com.barbearia.api.domain.Barbershop;
import br.com.barbearia.api.domain.ServiceItem;
import br.com.barbearia.api.repository.BarbershopRepository;
import br.com.barbearia.api.repository.ServiceItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * Componente que popula o banco de dados MongoDB com dados iniciais de teste
 * ao iniciar a aplicação, garantindo um catálogo base para testes.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final BarbershopRepository barbershopRepository;
    private final ServiceItemRepository serviceItemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Limpar dados existentes para garantir um estado limpo a cada inicialização (apenas para desenvolvimento)
        barbershopRepository.deleteAll();
        serviceItemRepository.deleteAll();

        log.info("Iniciando o Database Seeder para Barbershop Service...");

        // 1. Criar Serviços
        ServiceItem corteAdulto = ServiceItem.builder()
                .name("Corte de Cabelo Adulto")
                .description("Corte clássico ou moderno com máquina e tesoura.")
                .price(new BigDecimal("45.00"))
                .durationMinutes(45)
                .build();

        ServiceItem barbaCompleta = ServiceItem.builder()
                .name("Barba Completa")
                .description("Modelagem de barba com navalha e toalha quente.")
                .price(new BigDecimal("35.00"))
                .durationMinutes(30)
                .build();

        ServiceItem pezinho = ServiceItem.builder()
                .name("Acabamento / Pezinho")
                .description("Apenas ajuste rápido no pescoço e laterais.")
                .price(new BigDecimal("20.00"))
                .durationMinutes(15)
                .build();

        List<ServiceItem> services = serviceItemRepository.saveAll(Arrays.asList(corteAdulto, barbaCompleta, pezinho));
        log.info("Serviços iniciais salvos: {}", services.size());

        // 2. Criar Barbearias
        // Obs: O ownerId pode ser um ID fixo ou um UUID gerado. Usaremos um UUID simples para o seeder.
        String ownerId = "owner-dev-12345";

        Barbershop theGentleman = new Barbershop(
                null, // ID será gerado pelo Mongo
                "The Gentleman Barbershop",
                "Rua das Cavalarias, 123, Centro",
                "(11) 98765-4321",
                "contato@gentleman.com",
                "Especialistas em cortes clássicos e barbearia premium.",
                LocalTime.of(8, 0), // 08:00
                LocalTime.of(19, 0), // 19:00
                List.of(corteAdulto, barbaCompleta),
                ownerId
        );

        Barbershop retroCut = new Barbershop(
                null,
                "Retro Cut Studio",
                "Av. Principal, 500, Bairro Novo",
                "(21) 99999-8888",
                "retro@cutstudio.com",
                "Ambiente descontraído com foco em tendências modernas.",
                LocalTime.of(9, 30), // 09:30
                LocalTime.of(20, 0), // 20:00
                List.of(corteAdulto, pezinho),
                ownerId
        );

        barbershopRepository.saveAll(Arrays.asList(theGentleman, retroCut));
        log.info("Barbearias iniciais salvas: {}", 2);

        log.info("Database Seeder concluído com sucesso.");
    }
}