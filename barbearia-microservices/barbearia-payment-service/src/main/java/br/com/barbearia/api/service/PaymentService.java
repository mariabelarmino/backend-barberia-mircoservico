package br.com.barbearia.api.service;

import br.com.barbearia.api.domain.Payment;
import br.com.barbearia.api.dto.PaymentRequestDTO;
import br.com.barbearia.api.dto.PaymentResponseDTO;
import br.com.barbearia.api.mapper.PaymentMapper;
import br.com.barbearia.api.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public PaymentService(PaymentRepository repository, PaymentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PaymentResponseDTO processar(PaymentRequestDTO requestDTO) {
        // 1. Converte DTO para Entidade
        Payment pagamento = mapper.toEntity(requestDTO);
        pagamento.setDataHora(LocalDateTime.now());

        // 2. Regra de Negócio FAKE
        if (pagamento.getValor() > 1000) {
            pagamento.setStatus("RECUSADO_SALDO_INSUFICIENTE");
        } else {
            pagamento.setStatus("APROVADO");
        }

        // 3. Salva no Banco
        Payment pagamentoSalvo = repository.save(pagamento);

        // 4. Retorna convertido para DTO de resposta
        return mapper.toResponse(pagamentoSalvo);
    }
}