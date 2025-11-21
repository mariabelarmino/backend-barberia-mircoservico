💈 Backend Barbearia Microsserviços

Este repositório contém o backend de um sistema de agendamento de serviços de barbearia, desenvolvido com Java 17, Spring Boot 3 e MongoDB.

A arquitetura foi migrada de um monolito para um modelo de Microsserviços, promovendo isolamento, escalabilidade e manutenibilidade.

🛠️ Arquitetura e Tecnologia

O projeto está dividido nos seguintes microsserviços:

1. barbearia-auth-service (Serviço de Autenticação)

Responsabilidade: Única fonte de verdade para a gestão de usuários e autenticação.

Funcionalidades: Login/Cadastro tradicional, Autenticação Social (OAuth2 com Google) e Geração/Validação de tokens JWT.

Tecnologias: Spring Security, JWT, MongoDB.

2. barbearia-barbershop-service (Serviço de Catálogo e Agendamentos)

Status: Em desenvolvimento (Inicializando a separação).

Responsabilidade: Gerenciar o catálogo de barbearias, a lista de serviços oferecidos e a lógica de horários e agendamentos.

Tecnologias: Spring Data MongoDB.

⚙️ Como Executar

Requisitos: Docker (para MongoDB), Java 17, Maven.

Configuração do Banco: Subir o MongoDB e garantir as URIs de conexão corretas em cada application.properties (ex: barbearia-auth-db e barbearia-catalog-db).

Build: Executar mvn clean install no diretório raiz.

Execução: Iniciar cada microsserviço individualmente (ex: porta 8081 para Auth e 8082 para Barbershop).
