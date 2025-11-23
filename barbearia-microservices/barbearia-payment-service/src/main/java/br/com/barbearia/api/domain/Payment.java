package br.com.barbearia.api.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data // <--- cria todos os Getters e Setters invisivelmente
@Document(collection = "pagamentos")
public class Payment {

    @Id
    private String id;

    private Double valor;
    private String nomeCartao;
    private String numeroCartao;
    private String status;
    private LocalDateTime dataHora;
}