package br.com.barbearia.api.controller;

import br.com.barbearia.api.dto.ServiceItemDTO;
import br.com.barbearia.api.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável pela API de itens de serviço (corte, barba, etc.).
 * Expõe o endpoint para que outros microsserviços (como o de agendamento)
 * possam consultar os detalhes dos serviços.
 */
@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
@Slf4j // Usado para logging, garantindo que os logs tenham o Trace ID
public class ServiceItemController {

    private final ServiceItemService serviceItemService;

    /**
     * Endpoint para buscar um item de serviço pelo seu ID.
     * Este é o método que será consultado pelo barbearia-scheduling-service.
     *
     * @param serviceId O ID do item de serviço.
     * @return ResponseEntity contendo o ServiceItemDTO.
     */
    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceItemDTO> getServiceItemById(@PathVariable String serviceId) {
        log.info("Requisição para buscar ServiceItem com ID: {}", serviceId);

        // A lógica de negócio está no Service, incluindo o tratamento de 404
        ServiceItemDTO dto = serviceItemService.findServiceItemById(serviceId);

        log.info("ServiceItem encontrado e retornado com sucesso.");
        return ResponseEntity.ok(dto);
    }
}