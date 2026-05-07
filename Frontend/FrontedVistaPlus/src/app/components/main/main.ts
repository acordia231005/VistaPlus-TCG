import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd, RouterLink } from '@angular/router';
import { filter } from 'rxjs/operators';
import { ObrasService, Obra } from '../../services/obras.service';

@Component({
  selector: 'app-main',
  imports: [CommonModule, RouterLink],
  templateUrl: './main.html',
  styleUrl: './main.css',
})
export class Main implements OnInit {
  obrasFiltradas: Obra[] = [];

  constructor(private router: Router, private obrasService: ObrasService) {
    // Cuando cambia la ruta refiltrar
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.filtrarObras();
    });
  }

  ngOnInit() {
    // Si las obras ya están cargadas filtramos; si no esperamos a que carguen
    if (this.obrasService.obras().length > 0) {
      this.filtrarObras();
    } else {
      this.obrasService.cargarObras().then(() => this.filtrarObras());
    }
  }

  get cargando() { return this.obrasService.cargando(); }
  get error()    { return this.obrasService.error(); }

  filtrarObras() {
    const urlTree = this.router.parseUrl(this.router.url);
    const path = urlTree.root.children['primary']
      ? urlTree.root.children['primary'].segments.map(s => s.path).join('/')
      : '';
    const q = urlTree.queryParams['q']?.toLowerCase() || '';

    // El backend devuelve tipos en MAYÚSCULAS: PELICULA, SERIE, LIBRO
    let filtradas = this.obrasService.obras();

    if (path.includes('peliculas')) {
      filtradas = filtradas.filter(o => o.tipo === 'PELICULA');
    } else if (path.includes('series')) {
      filtradas = filtradas.filter(o => o.tipo === 'SERIE');
    } else if (path.includes('libros')) {
      filtradas = filtradas.filter(o => o.tipo === 'LIBRO');
    }

    if (q) {
      filtradas = filtradas.filter(o =>
        o.titulo.toLowerCase().includes(q) ||
        (o.sinopsis?.toLowerCase().includes(q) ?? false)
      );
    }

    this.obrasFiltradas = filtradas;
  }
}
