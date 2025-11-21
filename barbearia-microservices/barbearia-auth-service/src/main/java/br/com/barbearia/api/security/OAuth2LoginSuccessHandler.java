package br.com.barbearia.api.security;

import br.com.barbearia.api.domain.User;
import br.com.barbearia.api.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

/**
 * Handler customizado que é executado após o login bem-sucedido via OAuth2 (ex: Google).
 * Responsável por:
 * 1. Mapear o usuário OAuth2 para a entidade User no MongoDB (criação ou atualização).
 * 2. Gerar o Token JWT.
 * 3. Redirecionar para o frontend com o token.
 */
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. Extrai os dados do usuário OAuth2
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String imageUrl = oauth2User.getAttribute("picture");

        // 2. Procura o usuário no banco de dados
        Optional<User> userOpt = userRepository.findByEmail(email);

        User user;
        if (userOpt.isPresent()) {
            // Se o usuário já existe -> Atualiza dados e timestamps
            user = userOpt.get();
            user.setName(name);
            user.setImage(imageUrl);
            user.setUpdatedAt(Instant.now());
        } else {
            // Se ele é novo -> Cria um novo usuário
            user = User.builder()
                    .name(name)
                    .email(email)
                    .emailVerified(true)
                    .image(imageUrl)
                    .provider("google") // Define o provedor de autenticação
                    .role("USER")      // Define o perfil padrão
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    // O campo 'password' será nulo, pois é um usuário OAuth2
                    .build();
        }

        // 3. Salva ou atualiza o usuário no MongoDB
        userRepository.save(user);

        // 4. Gera o Token JWT para esse usuário
        String token = jwtTokenProvider.generateToken(authentication);

        // 5. Redireciona para o frontend com o token na URL (http://localhost:3000)
        String targetUrl = "http://localhost:3000/?token=" + token;

        response.sendRedirect(targetUrl);
    }
}