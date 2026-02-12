package back.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//  .password("$2a$10$w1ez6/kxNRmuPHE/G3LOpO2Tdt/6OGaGn2Le6BHk70RFxIC36IyXC")

@Configuration
@EnableWebSecurity
public class WebConfigSecurity {

    @Autowired
    private ImplementacaoUserDatailService implementacaoUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Libera explicitamente a página de login e recursos estáticos
                        .requestMatchers("/login", "/materialize/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()

                        // Restringe o cadastro apenas para ADMIN
                        .requestMatchers("/cadastropessoa/**").hasRole("ADMIN")

                        // Qualquer outra requisição exige login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/cadastropessoa", true)
                        .failureUrl("/login?error=true")
                        .permitAll() // Essencial para que a página de login seja acessível
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    // A maneira correta de ignorar recursos estáticos no Spring Boot 3:
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/materialize/**", "/static/**", "/resources/**", "/css/**", "/js/**");
    }
}

