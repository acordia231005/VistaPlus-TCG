import { Component, OnInit } from '@angular/core';
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
  obras: Obra[] = [];
  obrasFiltradas: Obra[] = [];

  constructor(private router: Router, private obrasService: ObrasService) {
    this.obras = this.obrasService.getObras();
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.filtrarObras();
    });
  }

  ngOnInit() {
    this.filtrarObras();
  }

  filtrarObras() {
    const urlTree = this.router.parseUrl(this.router.url);
    const path = urlTree.root.children['primary'] ? urlTree.root.children['primary'].segments.map(s => s.path).join('/') : '';
    const q = urlTree.queryParams['q']?.toLowerCase() || '';

    let filtradas = this.obras;

    if (path.includes('peliculas')) {
      filtradas = filtradas.filter(o => o.tipo === 'pelicula');
    } else if (path.includes('series')) {
      filtradas = filtradas.filter(o => o.tipo === 'serie');
    } else if (path.includes('libros')) {
      filtradas = filtradas.filter(o => o.tipo === 'libro');
    }

    if (q) {
      filtradas = filtradas.filter(o => 
        o.titulo.toLowerCase().includes(q) || 
        o.descripcion.toLowerCase().includes(q)
      );
    }

    this.obrasFiltradas = filtradas;
  }
}
