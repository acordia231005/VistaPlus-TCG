import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ObrasService, Obra, Genero } from '../../services/obras.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-crear-obra',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-obra.html',
  styleUrl: './crear-obra.css'
})
export class CrearObra implements OnInit {
  obra: Partial<Obra> = {
    titulo: '',
    tipo: 'PELICULA',
    sinopsis: '',
    id_genero: 1,
    imagen: ''
  };

  yearInput: number = new Date().getFullYear();
  generos: Genero[] = [];
  submitting = false;
  errorMsg = '';

  constructor(
    private obrasService: ObrasService,
    private authService: AuthService,
    private router: Router
  ) {}

  async ngOnInit() {
    // Verificar permisos
    const user = this.authService.currentUser();
    const rol = user?.rol?.toUpperCase() || '';
    if (rol !== 'ADMIN' && rol !== 'AUTOR' && rol !== 'ROLE_ADMIN' && rol !== 'ROLE_AUTOR') {
      this.router.navigate(['/']);
      return;
    }

    // Cargar géneros
    this.generos = await this.obrasService.getGeneros();
    if (this.generos.length > 0) {
      this.obra.id_genero = this.generos[0].id;
    }
  }

  async onSubmit(event: Event) {
    event.preventDefault();
    this.submitting = true;
    this.errorMsg = '';

    const user = this.authService.currentUser();
    if (!user) {
      this.errorMsg = 'Debes estar autenticado para publicar.';
      this.submitting = false;
      return;
    }

    try {
      // Formato compatible con LocalDateTime/Timestamp en SQL
      const month = "01";
      const day = "01";
      const yearFormatted = `${this.yearInput}-${month}-${day} 00:00:00`;

      // Preparar objeto para el backend con snake_case
      const data = {
        titulo: this.obra.titulo,
        tipo: this.obra.tipo,
        sinopsis: this.obra.sinopsis,
        id_genero: Number(this.obra.id_genero), // Asegurar que sea número
        imagen: this.obra.imagen || null,
        year: yearFormatted,
        id_usuario: user.id
      };

      await this.obrasService.crearObra(data);
      this.router.navigate(['/']);
    } catch (err: any) {
      console.error('Detalle del error al crear obra:', err);
      
      if (err.error && typeof err.error === 'string') {
        this.errorMsg = err.error;
      } else if (err.error?.message) {
        this.errorMsg = err.error.message;
      } else {
        this.errorMsg = 'Error al crear la obra. Por favor, revisa los datos y la conexión.';
      }
    } finally {
      this.submitting = false;
    }
  }

  onCancel() {
    this.router.navigate(['/']);
  }
}
