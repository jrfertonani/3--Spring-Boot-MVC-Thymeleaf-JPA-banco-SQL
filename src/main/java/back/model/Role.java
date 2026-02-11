package back.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeRole;

    @Override
    public @Nullable String getAuthority() {  //ROLE_ADMIN, ROLE_USUARIO, ROLE_GERENTE, ROLE_SECRETARIO...
        return this.nomeRole;
    }

    public Long getId(){
        return this.id = id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNomeeRole(){
        return nomeRole;
    }

    public void setNomeRole(String nomeRole){
        this.nomeRole = nomeRole;
    }



}
