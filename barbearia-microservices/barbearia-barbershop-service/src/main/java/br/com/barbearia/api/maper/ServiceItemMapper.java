package br.com.barbearia.api.mapper;

import br.com.barbearia.api.domain.ServiceItem;
import br.com.barbearia.api.dto.ServiceItemDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Componente de mapeamento para conversão entre a Entidade ServiceItem e o DTO ServiceItemDTO.
 */
@Component
public class ServiceItemMapper {

    /**
     * Converte a Entidade ServiceItem para o DTO de resposta.
     * @param entity A entidade ServiceItem.
     * @return O DTO ServiceItemDTO.
     */
    public ServiceItemDTO toDto(ServiceItem entity) {
        if (entity == null) {
            return null;
        }
        return ServiceItemDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .durationMinutes(entity.getDurationMinutes())
                .build();
    }

    /**
     * Converte o DTO ServiceItemDTO para a Entidade ServiceItem.
     * Útil na hora de salvar um novo serviço.
     * @param dto O DTO ServiceItemDTO.
     * @return A entidade ServiceItem.
     */
    public ServiceItem toEntity(ServiceItemDTO dto) {
        if (dto == null) {
            return null;
        }
        return ServiceItem.builder()
                .id(dto.getId()) // Se o ID vier preenchido, significa que é uma atualização de um serviço aninhado
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .durationMinutes(dto.getDurationMinutes())
                .build();
    }

    /**
     * Converte uma lista de Entidades para uma lista de DTOs.
     * @param entities Lista de ServiceItem.
     * @return Lista de ServiceItemDTO.
     */
    public List<ServiceItemDTO> toDtoList(List<ServiceItem> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Converte uma lista de DTOs para uma lista de Entidades.
     * @param dtos Lista de ServiceItemDTO.
     * @return Lista de ServiceItem.
     */
    public List<ServiceItem> toEntityList(List<ServiceItemDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}