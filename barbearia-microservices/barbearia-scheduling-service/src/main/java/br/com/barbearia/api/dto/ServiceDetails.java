package br.com.barbearia.api.dto;

import java.math.BigDecimal;

/**
 * DTO para receber detalhes de um serviço do Barbershop-Service.
 * Contém o Preço e a Duração, essenciais para o Agendamento.
 */
public class ServiceDetailsDTO {

    private String id; // ID do serviço
    private String name; // Nome do serviço (ex: "Corte de Cabelo")
    private BigDecimal price; // Preço do serviço
    private Integer durationMinutes; // Duração em minutos

    // Construtor padrão (necessário para deserialização do RestTemplate)
    public ServiceDetailsDTO() {}

    // Construtor com todos os campos
    public ServiceDetailsDTO(String id, String name, BigDecimal price, Integer durationMinutes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String toString() {
        return "ServiceDetailsDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", durationMinutes=" + durationMinutes +
                '}';
    }
}