package br.com.barbearia.api.controller;

import br.com.barbearia.api.dto.PaymentRequestDTO;
import br.com.barbearia.api.dto.PaymentResponseDTO;
import br.com.barbearia.api.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController { // O nome da classe deve bater com o nome do arquivo

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDTO criarPagamento(@RequestBody PaymentRequestDTO request) {
        return service.processar(request);
    }
}