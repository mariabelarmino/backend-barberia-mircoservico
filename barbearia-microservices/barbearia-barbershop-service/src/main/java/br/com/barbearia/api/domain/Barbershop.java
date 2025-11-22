package br.com.barbearia.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalTime;
import java.util.List;

/**
 * Entidade que representa uma Barbearia no sistema.
 * Armazenada na coleção 'barbershops' do MongoDB.
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
    private String email;
    private String description;

    // Horário de funcionamento (ex: 08:00)
    private LocalTime openingTime;

    // Horário de fechamento (ex: 19:00)
    private LocalTime closingTime;

    // Lista de serviços oferecidos pela barbearia.
    // Usamos @Field("services") para mapear explicitamente, embora não seja estritamente necessário no MongoDB.
    @Field("services")
    private List<ServiceItem> offeredServices;

    // ID do usuário/dono que gerencia esta barbearia (conexão com o Auth Service)
    private String ownerId;
}