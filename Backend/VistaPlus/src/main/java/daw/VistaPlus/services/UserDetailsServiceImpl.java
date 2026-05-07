package daw.VistaPlus.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import daw.VistaPlus.services.dto.UsuarioDTO;
import daw.VistaPlus.services.exceptions.UsuarioNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioService usuarioService;

    public UserDetailsServiceImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UsuarioDTO usuario = this.usuarioService.findByUsername(username);
            return User.builder()
                    .username(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(usuario.getRol())
                    .build();
        } catch (UsuarioNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
