import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ObrasService, Obra } from '../../services/obras.service';

@Component({
  selector: 'app-obra-detalle',
  imports: [CommonModule, RouterLink],
  templateUrl: './obra-detalle.html',
  styleUrl: './obra-detalle.css',
})
export class ObraDetalle implements OnInit {
  obra: Obra | undefined;
  
  // Usar el estado compartido del servicio
  enLista = computed(() => this.obra ? this.obrasService.estaEnLista(this.obra.id) : false);
  visto = computed(() => this.obra ? this.obrasService.estaVisto(this.obra.id) : false);

  constructor(
    private route: ActivatedRoute,
    private obrasService: ObrasService
  ) {}

  async ngOnInit() {
    this.route.paramMap.subscribe(async params => {
      const id = Number(params.get('id'));
      if (id) {
        this.obra = await this.obrasService.getObraById(id);
      }
    });
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
