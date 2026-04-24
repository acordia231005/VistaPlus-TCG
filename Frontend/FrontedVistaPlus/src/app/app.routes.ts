import { Routes } from '@angular/router';
import { Main } from './components/main/main';
import { ObraDetalle } from './components/obra-detalle/obra-detalle';

export const routes: Routes = [
  { path: '', component: Main },
  { path: 'peliculas', component: Main },
  { path: 'series', component: Main },
  { path: 'libros', component: Main },
  { path: 'obra/:id', component: ObraDetalle },
];
