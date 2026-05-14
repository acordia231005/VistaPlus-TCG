import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ObrasService, Obra, Opinion } from '../../services/obras.service';
import { AuthService } from '../../services/auth.service';

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

  // Usuario actual
  user = computed(() => this.authService.currentUser());

  constructor(
    private route: ActivatedRoute,
    private obrasService: ObrasService,
    private authService: AuthService
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
        this.nuevoComentario = miOp.comentario;
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
}
