package br.com.barbearia.api.service;

import br.com.barbearia.api.domain.Barbershop;
import br.com.barbearia.api.repository.BarbershopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Camada de Serviço para a lógica de negócio das Barbearias.
 * Esta camada atua como intermediário entre o Controller e o Repository.
 */
@Service
@RequiredArgsConstructor
public class BarbershopService {

    private final BarbershopRepository barbershopRepository;

    /**
     * Busca todas as barbearias.
     * @return Lista de Barbershop.
     */
    public List<Barbershop> findAll() {
        // Lógica de negócio (ex: filtragem, cache, validações) seria adicionada aqui.
        return barbershopRepository.findAll();
    }

    /**
     * Busca uma barbearia pelo ID.
     * @param id ID da barbearia.
     * @return Optional contendo a Barbershop ou vazio.
     */
    public Optional<Barbershop> findById(String id) {
        return barbershopRepository.findById(id);
    }
}