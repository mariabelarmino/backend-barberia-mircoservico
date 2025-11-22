package br.com.barbearia.api.repository;

import br.com.barbearia.api.domain.Barber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações CRUD e consultas personalizadas na coleção 'barbers'.
 */
@Repository
public interface BarberRepository extends MongoRepository<Barber, String> {

    /**
     * Encontra um barbeiro pelo seu ID de usuário, que é a chave do Auth Service.
     */
    Optional<Barber> findByUserId(String userId);

    /**
     * Encontra todos os barbeiros que trabalham em uma determinada barbearia.
     */
    List<Barber> findByBarbershopIdsContaining(String barbershopId);
}