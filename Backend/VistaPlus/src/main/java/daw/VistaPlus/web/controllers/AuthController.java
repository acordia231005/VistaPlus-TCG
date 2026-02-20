//package daw.VistaPlus.web.controllers;
//
//
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import daw.VistaPlus.services.AuthService;
//import daw.VistaPlus.services.dto.LoginRequest;
//import daw.VistaPlus.services.dto.RefreshDto;
//
//public class AuthController {
//	
//	@Autowired
//	private AuthService authService;
//
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//		return ResponseEntity.ok(this.authService.login(request));
//	}
//
//	@PostMapping("/register")
//	public ResponseEntity<?> register(@RequestBody LoginRequest request) {
//		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, this.authService.registrar(request)).build();
//	}
//
//	@PostMapping("/refresh")
//	public ResponseEntity<?> refresh(@RequestBody RefreshDto request) {
//		return ResponseEntity.ok(this.authService.refresh(request));
//	} 
//}
