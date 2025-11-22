package br.com.barbearia.api.repository;

import br.com.barbearia.api.domain.Barbershop;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repositório para operações de CRUD da entidade Barbershop.
 */
public interface BarbershopRepository extends MongoRepository<Barbershop, String> {
}