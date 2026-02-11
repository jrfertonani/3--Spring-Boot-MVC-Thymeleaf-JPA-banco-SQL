package back.security;import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity {

    //para cryptografas a senha
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 1. Configuração de Usuário (Substitui o AuthenticationManagerBuilder)
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("ademir")
                .password("$2a$10$w1ez6/kxNRmuPHE/G3LOpO2Tdt/6OGaGn2Le6BHk70RFxIC36IyXC") // O {noop} evita a necessidade de BCrypt para testes
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    // 2. Configuração de Filtros e URLs (Substitui o WebSecurityConfigurerAdapter)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita para facilitar o uso de formulários POST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/").permitAll() // Home liberada
                        .requestMatchers("/materializa/**", "/css/**", "/js/**").permitAll() // Estáticos liberados
                        .anyRequest().authenticated() // Todo o resto exige login
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true) // Para onde vai após o login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Substitui o AntPathRequestMatcher
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}