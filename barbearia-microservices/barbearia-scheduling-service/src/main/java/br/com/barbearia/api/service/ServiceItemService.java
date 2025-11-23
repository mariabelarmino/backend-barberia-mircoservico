package br.com.barbearia.api.service;

import br.com.barbearia.api.domain.ServiceItem;
import br.com.barbearia.api.dto.ServiceItemDTO;
import br.com.barbearia.api.exception.ResourceNotFoundException;
import br.com.barbearia.api.mapper.ServiceItemMapper;
import br.com.barbearia.api.repository.ServiceItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Serviço de lógica de negócio para a entidade ServiceItem.
 * Responsável por buscar serviços e converter o resultado para o DTO.
 */
@Service
@RequiredArgsConstructor
@Slf4j // Usado para logging, que agora incluirá o Trace ID
public class ServiceItemService {

    private final ServiceItemRepository serviceItemRepository;
    private final ServiceItemMapper serviceItemMapper; // Injeção do Mapper

    /**
     * Busca um item de serviço pelo ID e o mapeia para um DTO de resposta.
     * Esta é a função principal consumida pelo Controller.
     *
     * @param serviceId O ID único do serviço.
     * @return O DTO do serviço encontrado.
     * @throws ResourceNotFoundException Se o serviço não for encontrado.
     */
    public ServiceItemDTO findServiceItemById(String serviceId) {
        log.info("Buscando ServiceItem com ID: {}", serviceId);

        // 1. Busca o ServiceItem ou lança a exceção 404
        ServiceItem serviceItem = serviceItemRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceItem", "id", serviceId));

        log.info("ServiceItem encontrado. Mapeando para DTO.");

        // 2. Mapeia a entidade para DTO
        return serviceItemMapper.toDto(serviceItem);
    }
}