package back.security;

import back.model.Usuario;
import back.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplementacaoUserDatailService implements UserDetailsService{


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findUserByLogin(username);

        if(usuario == null){
            throw new UsernameNotFoundException("Usuario não foi encontrado.");
        }

        return new User(usuario.getLogin(), usuario.getPassword(),
                usuario.isEnabled(), true,
                true, true,
                        usuario.getAuthorities());
    }
}
