package back.model;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String senha;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_role",
                joinColumns = @JoinColumn(name = "usuario_id",
                referencedColumnName = "id",
                table = "usuario"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                referencedColumnName = "id",
                table = "role"))
    private List<Role> roles;


    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(){
        this.login = login;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }


    @Override     // acessos
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override     //senha
    public @Nullable String getPassword() {
        return senha;
    }

    @Override    //login
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
