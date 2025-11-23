package br.com.barbearia.api.mapper;

import br.com.barbearia.api.dto.ServiceItemDTO;
import br.com.barbearia.api.entity.ServiceItem;
import org.springframework.stereotype.Component;

/**
 * Componente responsável por mapear (converter) a Entidade ServiceItem
 * para o DTO (Data Transfer Object) e vice-versa.
 */
@Component
public class ServiceItemMapper {

    /**
     * Converte a Entidade ServiceItem em ServiceItemDTO.
     * @param entity A entidade ServiceItem vinda do banco.
     * @return O DTO de resposta.
     */
    public ServiceItemDTO toDTO(ServiceItem entity) {
        if (entity == null) {
            return null;
        }
        return ServiceItemDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .duration(entity.getDuration())
                .barbershopId(entity.getBarbershopId())
                .build();
    }

    /**
     * Converte o DTO de Requisição (que seria usado em um POST/PUT) em Entidade.
     * (Este método é útil para futuros endpoints de criação/atualização).
     * @param dto O DTO de Requisição/Resposta.
     * @return A entidade ServiceItem.
     */
    public ServiceItem toEntity(ServiceItemDTO dto) {
        if (dto == null) {
            return null;
        }
        return ServiceItem.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .barbershopId(dto.getBarbershopId())
                .build();
    }
}