import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { filter } from 'rxjs/operators';
import { ObrasService, Obra, Genero } from '../../services/obras.service';

@Component({
  selector: 'app-main',
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './main.html',
  styleUrl: './main.css',
})
export class Main implements OnInit {
  obrasFiltradas: Obra[] = [];
  medias: { [obraId: number]: string } = {};
  generos: Genero[] = [];

  // Señales para los filtros
  generoFiltro = signal<number>(0);
  anioFiltro = signal<string>('');
  valoracionFiltro = signal<number>(0);

  // Lista de años para el selector (últimos 50 años)
  aniosDisponibles: number[] = [];

  constructor(private router: Router, private obrasService: ObrasService) {
    const currentYear = new Date().getFullYear();
    for (let i = currentYear; i >= currentYear - 50; i--) {
      this.aniosDisponibles.push(i);
    }

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.filtrarObras();
    });
  }

  async ngOnInit() {
    // Cargar géneros para el selector
    this.generos = await this.obrasService.getGeneros();

    if (this.obrasService.obras().length > 0) {
      this.filtrarObras();
    } else {
      this.obrasService.cargarObras().then(() => this.filtrarObras());
    }
  }

  get cargando() { return this.obrasService.cargando(); }
  get error()    { return this.obrasService.error(); }

  async filtrarObras() {
    const urlTree = this.router.parseUrl(this.router.url);
    const pathSegments = urlTree.root.children['primary']?.segments.map(s => s.path) || [];
    const q = urlTree.queryParams['q']?.toLowerCase() || '';

    let filtradas = this.obrasService.obras();

    // 1. Filtrar por tipo (ruta)
    if (pathSegments.includes('peliculas')) {
      filtradas = filtradas.filter(o => o.tipo === 'PELICULA');
    } else if (pathSegments.includes('series')) {
      filtradas = filtradas.filter(o => o.tipo === 'SERIE');
    } else if (pathSegments.includes('libros')) {
      filtradas = filtradas.filter(o => o.tipo === 'LIBRO');
    }

    // 2. Filtrar por búsqueda de texto
    if (q) {
      filtradas = filtradas.filter(o =>
        o.titulo.toLowerCase().includes(q) ||
        (o.sinopsis?.toLowerCase().includes(q) ?? false)
      );
    }

    // 3. Filtrar por Género
    if (Number(this.generoFiltro()) > 0) {
      const idBuscado = Number(this.generoFiltro());
      filtradas = filtradas.filter(o => {
        const idObraGen = Number(o.id_genero || o.idGenero || 0);
        return idObraGen === idBuscado;
      });
    }

    // 4. Filtrar por Año
    if (this.anioFiltro()) {
      filtradas = filtradas.filter(o => {
        if (!o.year) return false;
        return o.year.startsWith(this.anioFiltro());
      });
    }

    // 5. Filtrar por Valoración (necesitamos las medias cargadas)
    await this.calcularMediasParaLista(filtradas);
    if (this.valoracionFiltro() > 0) {
      filtradas = filtradas.filter(o => {
        const media = parseFloat(this.medias[o.id]);
        return !isNaN(media) && media >= this.valoracionFiltro();
      });
    }

    this.obrasFiltradas = filtradas;
  }

  async calcularMediasParaLista(lista: Obra[]) {
    for (const obra of lista) {
      if (this.medias[obra.id] === undefined) {
        try {
          const opiniones = await this.obrasService.getOpinionesDeObra(obra.id);
          if (opiniones && opiniones.length > 0) {
            const sum = opiniones.reduce((acc, op) => acc + (op.puntuacion || 0), 0);
            this.medias[obra.id] = (sum / opiniones.length).toFixed(1);
          } else {
            this.medias[obra.id] = '-';
          }
        } catch {
          this.medias[obra.id] = '-';
        }
      }
    }
  }

  resetFiltros() {
    this.generoFiltro.set(0);
    this.anioFiltro.set('');
    this.valoracionFiltro.set(0);
    this.filtrarObras();
  }
}
