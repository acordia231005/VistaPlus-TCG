import { Component, inject, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { ObrasService } from '../../services/obras.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile {
  authService = inject(AuthService);
  obrasService = inject(ObrasService);
  
  user = this.authService.currentUser;

  // Calculamos las estadísticas directamente como señales para asegurar reactividad
  vistos = computed(() => this.obrasService.vistosCount());
  miLista = computed(() => this.obrasService.miLista().length);
  comentarios = computed(() => this.obrasService.comentariosCount());

  editProfile() {
    console.log('Editar perfil');
  }
}
