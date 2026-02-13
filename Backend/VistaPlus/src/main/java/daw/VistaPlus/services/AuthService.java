package daw.VistaPlus.services;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import daw.VistaPlus.services.dto.LoginRequest;
import daw.VistaPlus.services.dto.LoginResponse;
import daw.VistaPlus.services.dto.RefreshDto;
import daw.VistaPlus.web.config.JwtUtils;

public class AuthService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtil;
	
	public String registrar(LoginRequest request) {
		this.usuarioService.create(request.getUsername(), request.getPassword());
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtUtil.generateAccessToken(userDetails);

		return token;
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
