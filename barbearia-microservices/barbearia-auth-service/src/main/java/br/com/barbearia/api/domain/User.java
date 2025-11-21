package br.com.barbearia.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "users")
@Data
@Builder // Adicionando Builder para facilitar a criação de objetos
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    // Campos adicionados/ajustados para Autenticação e Login Tradicional
    private String password; // Hash da senha para login tradicional
    private String provider; // Ex: "google", "facebook", ou "local"

    private Boolean emailVerified;
    private String image; // URL da imagem de perfil
    private String role; // Ex: "USER", "ADMIN" (importante para autorização)

    private Instant createdAt;
    private Instant updatedAt;
}