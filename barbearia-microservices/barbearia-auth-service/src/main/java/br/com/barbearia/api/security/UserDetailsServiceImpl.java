package br.com.barbearia.api.security;

import br.com.barbearia.api.domain.User;
import br.com.barbearia.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Serviço responsável por carregar os dados do usuário (User) do MongoDB
 * e convertê-los para o formato UserDetails do Spring Security.
 * Essencial para validação de JWT e login tradicional.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo e-mail
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // Converte a Role do usuário (ex: "USER") para o formato GrantedAuthority do Spring Security
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())
        );

        // Retorna um objeto org.springframework.security.core.userdetails.User
        // Note: O campo password é usado apenas para autenticação por senha.
        // Se for OAuth2 ou JWT, é ignorado, mas deve estar presente para flexibilidade.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword() != null ? user.getPassword() : "", // Usa o password hash, se existir
                authorities // Adiciona as permissões do usuário
        );
    }
}