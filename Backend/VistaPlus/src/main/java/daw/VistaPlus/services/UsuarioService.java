//package daw.VistaPlus.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import daw.VistaPlus.persistence.entities.Usuario;
//import daw.VistaPlus.persistence.repositories.UsuarioRepository;
//
//@Service
//public class UsuarioService {
//
//	@Autowired
//	private UsuarioRepository usuariorepository;
//
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Usuario usuario = this.usuariorepository.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException("El Usuario(" + username  +") no ha sido encontrado"));
//		return User.builder()
//				.username(usuario.getNombre())
//				.password(usuario.getPassword())
//				.roles(usuario.getRol())
//				.build();
//	}
//	
//	public Usuario create(String username, String password) {
//		Usuario usuario = new Usuario();
//		usuario.setNombre(username);
//		usuario.setPassword(new BCryptPasswordEncoder().encode(password));
//		usuario.setRol("USER");
//		return usuariorepository.save(usuario);
//	}
//
//	public Usuario findByUsername(String username) {
//		return this.usuariorepository.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe. "));
//	}
//}
