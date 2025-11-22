package br.com.barbearia.api.client;

import br.com.barbearia.api.dto.ServiceDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

/**
 * Cliente para se comunicar com o Microsserviço de Barbearia (Barbershop-Service).
 * Responsável por buscar detalhes de serviços.
 */
@Component
public class BarbershopServiceClient {

    // Nome do serviço a ser usado na chamada com o @LoadBalanced RestTemplate
    private static final String BARBERSHOP_SERVICE_NAME = "BARBERSHOP-SERVICE";
    private static final String SERVICE_ENDPOINT = "/api/v1/services/{serviceId}";

    private final RestTemplate restTemplate;

    @Autowired
    public BarbershopServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Busca os detalhes de um serviço (preço e duração) no Barbershop-Service.
     * * @param serviceId O ID do serviço a ser consultado.
     * @return O DTO com os detalhes do serviço.
     * @throws ResponseStatusException Se o serviço não for encontrado ou houver erro de comunicação.
     */
    public ServiceDetailsDTO getServiceDetails(String serviceId) {
        String url = String.format("http://%s%s", BARBERSHOP_SERVICE_NAME, SERVICE_ENDPOINT);

        try {
            ResponseEntity<ServiceDetailsDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    ServiceDetailsDTO.class,
                    serviceId
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                // Se a resposta for 2xx mas sem corpo, ou status não for 2xx (ex: 404/500), lança exceção.
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Erro ao buscar detalhes do serviço. Status: " + response.getStatusCode()
                );
            }
        } catch (Exception e) {
            // Captura falhas de conexão ou 4xx/5xx que o RestTemplate lança.
            // Aqui você deve implementar uma lógica de Circuit Breaker (ex: Resilience4j) para produção.
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, // Usando 400, pois o problema é com a entrada do usuário (serviceId inválido/inexistente).
                    "Falha na comunicação com o Barbershop-Service ou ServiceId inválido: " + serviceId + ". Causa: " + e.getMessage(),
                    e
            );
        }
    }
}