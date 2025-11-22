package br.com.barbearia.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;

/**
 * Entidade que representa uma Barbearia no Microsserviço de Catálogo.
 * Não contém lógica de agendamento (Booking), apenas informações estáticas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "barbershops")
public class Barbershop {

    @Id
    private String id;
    private String name;
    private String address;
    private String phone;
    private String description;

    // Horários de funcionamento
    private LocalTime openingTime;
    private LocalTime closingTime;

    // Lista de Ids de Serviços oferecidos por esta Barbearia
    // (O serviço em si é outra entidade: BarbershopService)
    private List<String> serviceIds;

    // Lista de URLs das fotos da barbearia
    private List<String> imageUrls;
}