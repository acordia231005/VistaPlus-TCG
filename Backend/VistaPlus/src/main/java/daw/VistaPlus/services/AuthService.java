package daw.VistaPlus.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import daw.VistaPlus.persistence.entities.Usuario;
import daw.VistaPlus.persistence.repositories.UsuarioRepository;
import daw.VistaPlus.services.dto.LoginRequest;
import daw.VistaPlus.services.dto.LoginResponse;
import daw.VistaPlus.services.dto.RefreshDto;
import daw.VistaPlus.services.dto.RegisterRequest;
import daw.VistaPlus.web.config.JwtUtils;

@Service
public class AuthService {

	private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

	public AuthService(UsuarioService usuarioService,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtil,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
		this.usuarioService = usuarioService;
		this.authenticationManager  = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
	}
	
	public Usuario createAccount(String username, String email, String password, String rol, String nacionalidad, String fechaNac) {
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setEmail(email);
		usuario.setPassword(this.passwordEncoder.encode(password));
		usuario.setNacionalidad(nacionalidad);
		
		if (fechaNac != null && !fechaNac.isEmpty()) {
			try {
				usuario.setFechaNac(java.time.LocalDate.parse(fechaNac).atStartOfDay());
			} catch (Exception e) {
				
			}
		}

		// Solo permitimos USER y AUTOR por registro público. ADMIN debe ser manual.
		if (rol != null && (rol.equals("AUTOR") || rol.equals("USER"))) {
			usuario.setRol(rol);
		} else {
			usuario.setRol("USER");
		}
		return usuarioRepository.saveAndFlush(usuario);
	}

	@Transactional
	public String registrar(RegisterRequest request) {
		// Verificamos si el usuario ya existe para dar un error claro
		if (usuarioRepository.findByUsername(request.getUsername()) != null) {
			throw new RuntimeException("El nombre de usuario '" + request.getUsername() + "' ya está en uso.");
		}

		// Creamos la cuenta
		Usuario usuario = this.createAccount(request.getUsername(), request.getEmail(), request.getPassword(), request.getRol(), request.getNacionalidad(), request.getFechaNac());
		
		// Construimos UserDetails manualmente para evitar problemas de sincronización con el AuthenticationManager
		UserDetails userDetails = User.builder()
				.username(usuario.getUsername())
				.password(usuario.getPassword())
				.authorities(usuario.getRol())
				.build();
				
		return jwtUtil.generateAccessToken(userDetails);
	}

	public LoginResponse login(LoginRequest request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		String accessToken = jwtUtil.generateAccessToken(userDetails);
		String refreshToken = jwtUtil.generateRefreshToken(userDetails);

		return new LoginResponse(accessToken, refreshToken);
	}

	public LoginResponse refresh(RefreshDto dto) {
		String accessToken = jwtUtil.generateAccessToken(dto.getRefresh());
		String refreshToken = jwtUtil.generateRefreshToken(dto.getRefresh());

		return new LoginResponse(accessToken, refreshToken);
	}
}
