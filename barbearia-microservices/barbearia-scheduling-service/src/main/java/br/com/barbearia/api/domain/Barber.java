package br.com.barbearia.api.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Entidade de Domínio para um Barbeiro (Barber).
 * Representa um profissional que pode receber agendamentos.
 * Armazena o ID do usuário (Auth Service) e a lista de barbearias onde ele trabalha.
 */
@Data
@Builder
@Document(collection = "barbers")
public class Barber {

    // ID gerado pelo MongoDB
    @Id
    private String id;

    // ID do usuário correspondente no Auth Service (chave principal para o profissional)
    private String userId;

    // Nome completo do barbeiro
    private String name;

    // Lista de IDs das barbearias onde o barbeiro trabalha
    private List<String> barbershopIds;

    // Descrição/biografia do barbeiro
    private String bio;

    // Outros campos como foto, nota, etc., podem ser adicionados aqui
}