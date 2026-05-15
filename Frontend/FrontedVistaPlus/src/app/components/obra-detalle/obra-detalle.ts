import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ObrasService, Obra, Opinion } from '../../services/obras.service';
import { AuthService } from '../../services/auth.service';
import { ModalService } from '../../services/modal.service';

@Component({
  selector: 'app-obra-detalle',
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './obra-detalle.html',
  styleUrl: './obra-detalle.css',
})
export class ObraDetalle implements OnInit {
  obra: Obra | undefined;
  
  // Usar el estado compartido del servicio
  enLista = computed(() => this.obra ? this.obrasService.estaEnLista(this.obra.id) : false);
  visto = computed(() => this.obra ? this.obrasService.estaVisto(this.obra.id) : false);

  // Opiniones del backend
  opiniones = signal<Opinion[]>([]);
  mediaGlobal = signal<string>('-');
  nuevaPuntuacion = 0;
  nuevoComentario = '';
  enviando = signal<boolean>(false);
  eliminando = signal<boolean>(false);

  // Usuario actual
  user = computed(() => this.authService.currentUser());

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private obrasService: ObrasService,
    private authService: AuthService,
    private modalService: ModalService
  ) {}

  async ngOnInit() {
    this.route.paramMap.subscribe(async params => {
      const id = Number(params.get('id'));
      if (id) {
        this.obra = await this.obrasService.getObraById(id);
        this.cargarOpiniones();
      }
    });
  }

  async cargarOpiniones() {
    if (this.obra) {
      const ops = await this.obrasService.getOpinionesDeObra(this.obra.id);
      this.opiniones.set(ops);
      
      if (ops && ops.length > 0) {
        const sum = ops.reduce((acc, op) => acc + (op.puntuacion || 0), 0);
        this.mediaGlobal.set((sum / ops.length).toFixed(1));
      } else {
        this.mediaGlobal.set('-');
      }
      
      // Si el usuario ya tiene puntuación, mostrarla
      const miOp = ops.find(o => o.usuarioId === this.user()?.id);
      if (miOp) {
        this.nuevaPuntuacion = miOp.puntuacion;
        this.nuevoComentario = miOp.comentario || '';
      }
    }
  }

  async guardarPuntuacion(stars: number) {
    if (!this.obra || !this.user()) return;
    
    this.nuevaPuntuacion = stars;
    try {
      await this.obrasService.puntuarObra(this.user()!.id, this.obra.id, stars);
      await this.cargarOpiniones();
    } catch (err) {
      console.error(err);
    }
  }

  async enviarOpinion() {
    if (!this.obra || !this.user() || !this.nuevoComentario.trim()) return;

    this.enviando.set(true);
    try {
      // Primero enviar el comentario
      await this.obrasService.comentarObra(this.user()!.id, this.obra.id, this.nuevoComentario);
      
      // Si hay puntuación, asegurarnos de que se guarde (opcional si ya se hizo en guardarPuntuacion)
      if (this.nuevaPuntuacion > 0) {
        await this.obrasService.puntuarObra(this.user()!.id, this.obra.id, this.nuevaPuntuacion);
      }
      
      this.nuevoComentario = '';
      await this.cargarOpiniones();
    } catch (err) {
      console.error(err);
    } finally {
      this.enviando.set(false);
    }
  }

  toggleLista() {
    if (this.obra) {
      this.obrasService.toggleEnLista(this.obra);
    }
  }

  toggleVisto() {
    if (this.obra) {
      this.obrasService.toggleVisto(this.obra.id);
    }
  }

  enviarComentario(texto: string) {
    if (this.obra && texto.trim()) {
      this.obrasService.agregarComentario(this.obra.id, texto.trim());
    }
  }

  esAutorOrAdmin(): boolean {
    const currentUser = this.user();
    if (!currentUser || !this.obra) return false;
    
    const rol = currentUser.rol?.toUpperCase() || '';
    if (rol === 'ADMIN' || rol === 'ROLE_ADMIN') return true;
    
    // Si es autor, solo puede editar/borrar sus propias obras
    const obraUserId = this.obra.id_usuario || this.obra.idUsuario;
    return currentUser.id === obraUserId;
  }

  async eliminarObra() {
    if (!this.obra) return;
    
    // Abrir modal de confirmación
    const confirmed = await this.modalService.confirm({
      title: 'Eliminar Obra',
      message: '¿Estás seguro de que quieres eliminar esta obra? Esta acción no se puede deshacer.',
      confirmText: 'Sí, eliminar',
      cancelText: 'Cancelar',
      type: 'danger'
    });

    // Si confirma en el modal, ejecutamos el borrado
    if (confirmed) {
      this.eliminando.set(true);
      try {
        await this.obrasService.eliminarObra(this.obra.id);
        this.router.navigate(['/']);
      } catch (err) {
        console.error('Error al eliminar:', err);
        // Usar alert simple solo si falla el proceso crítico
        alert('Hubo un error al eliminar la obra. Por favor, inténtalo de nuevo.');
      } finally {
        this.eliminando.set(false);
      }
    }
  }
}
