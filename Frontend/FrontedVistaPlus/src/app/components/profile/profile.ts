import { Component, inject, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { ObrasService } from '../../services/obras.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile {
  authService = inject(AuthService);
  obrasService = inject(ObrasService);
  
  user = this.authService.currentUser;

  // Estadísticas originales sin lógica de privacidad
  vistos = computed(() => this.obrasService.vistosCount());
  miLista = computed(() => this.obrasService.miLista().length);
  comentarios = computed(() => this.obrasService.comentariosCount());

  editProfile() {
    console.log('Editar perfil');
  }

  misObras = computed(() => {
    const userId = this.user()?.id;
    return this.obrasService.obras().filter(o => (o.id_usuario || o.idUsuario) === userId);
  });

  esAutor() {
    const rol = this.user()?.rol?.toUpperCase() || '';
    return rol === 'AUTOR' || rol === 'ADMIN' || rol === 'ROLE_AUTOR' || rol === 'ROLE_ADMIN';
  }
}
