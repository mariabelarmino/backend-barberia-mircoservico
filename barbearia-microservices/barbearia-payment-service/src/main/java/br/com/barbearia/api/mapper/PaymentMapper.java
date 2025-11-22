package br.com.barbearia.api.mapper;

import br.com.barbearia.api.domain.Payment;
import br.com.barbearia.api.dto.PaymentRequestDTO;
import br.com.barbearia.api.dto.PaymentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    // Converte o pedido (DTO) em um objeto do Banco (Domain)
    public Payment toEntity(PaymentRequestDTO dto) {
        Payment payment = new Payment();
        payment.setValor(dto.getValor());
        payment.setNomeCartao(dto.getNomeCartao());
        payment.setNumeroCartao(dto.getNumeroCartao());
        return payment;
    }

    // Converte o objeto do Banco (Domain) em resposta (DTO)
    public PaymentResponseDTO toResponse(Payment entity) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setDataHora(entity.getDataHora());
        return dto;
    }
}