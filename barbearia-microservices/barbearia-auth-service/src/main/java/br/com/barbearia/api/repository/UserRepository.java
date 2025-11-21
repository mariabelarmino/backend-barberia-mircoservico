package br.com.barbearia.api.repository;

import br.com.barbearia.api.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para a entidade User, responsável pela persistência e consulta no MongoDB.
 * Essencial para o processo de autenticação (buscando usuários por email).
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Encontra um usuário pelo seu endereço de e-mail.
     * Utilizado principalmente pela lógica de Spring Security e OAuth2.
     * @param email O email do usuário a ser procurado.
     * @return Um Optional contendo o User, se encontrado.
     */
    Optional<User> findByEmail(String email);
}