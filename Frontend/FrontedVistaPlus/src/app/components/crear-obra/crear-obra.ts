import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
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
    private router: Router,
    private route: ActivatedRoute
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

    // Comprobar si estamos en modo edición
    this.route.paramMap.subscribe(async params => {
      const id = Number(params.get('id'));
      if (id) {
        const obraToEdit = await this.obrasService.getObraById(id);
        if (obraToEdit) {
          // Verificar si es el autor o admin (como medida extra de seguridad)
          const obraUserId = obraToEdit.id_usuario || obraToEdit.idUsuario;
          if (rol !== 'ADMIN' && rol !== 'ROLE_ADMIN' && user?.id !== obraUserId) {
            this.router.navigate(['/']);
            return;
          }
          this.obra = { ...obraToEdit };
          // Asegurar que id_genero esté poblado para el <select>
          if (obraToEdit.idGenero && !obraToEdit.id_genero) {
            this.obra.id_genero = obraToEdit.idGenero;
          }
          if (obraToEdit.year) {
            // Extraer solo el año de "YYYY-MM-DD..."
            this.yearInput = new Date(obraToEdit.year).getFullYear();
          }
        } else {
          // Obra no encontrada, volver a inicio
          this.router.navigate(['/']);
        }
      }
    });
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
      // Formato ISO-8601 compatible con Java LocalDateTime
      const yearFormatted = `${this.yearInput}-01-01T00:00:00`;

      // Preparar objeto para el backend con camelCase (Java DTO)
      const data = {
        titulo: this.obra.titulo,
        tipo: this.obra.tipo,
        sinopsis: this.obra.sinopsis,
        idGenero: Number(this.obra.id_genero),
        imagen: this.obra.imagen || null,
        year: yearFormatted,
        idUsuario: user.id
      };

      if (this.obra.id) {
        // Modo edición: Incluimos el ID en el body por si el backend lo requiere
        const updateData = { ...data, id: this.obra.id };
        await this.obrasService.actualizarObra(this.obra.id, updateData);
        this.router.navigate(['/obra', this.obra.id]);
      } else {
        // Modo creación
        await this.obrasService.crearObra(data);
        this.router.navigate(['/']);
      }
    } catch (err: any) {
      console.error('Detalle del error al guardar obra:', err);
      
      if (err.error && typeof err.error === 'string') {
        this.errorMsg = err.error;
      } else if (err.error?.message) {
        this.errorMsg = err.error.message;
      } else {
        this.errorMsg = 'Error al guardar la obra. Por favor, revisa los datos y la conexión.';
      }
    } finally {
      this.submitting = false;
    }
  }

  onCancel() {
    this.router.navigate(['/']);
  }
}
