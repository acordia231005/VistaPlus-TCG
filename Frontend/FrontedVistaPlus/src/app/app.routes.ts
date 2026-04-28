import { Routes } from '@angular/router';
import { Main } from './components/main/main';
import { ObraDetalle } from './components/obra-detalle/obra-detalle';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Profile } from './components/profile/profile';
import { Lista } from './components/lista/lista';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  // Rutas públicas (sin guard)
  { path: 'login', component: Login },
  { path: 'register', component: Register },

  // Rutas protegidas (requieren autenticación)
  { path: '', component: Main, canActivate: [authGuard] },
  { path: 'peliculas', component: Main, canActivate: [authGuard] },
  { path: 'series', component: Main, canActivate: [authGuard] },
  { path: 'libros', component: Main, canActivate: [authGuard] },
  { path: 'obra/:id', component: ObraDetalle, canActivate: [authGuard] },
  { path: 'perfil', component: Profile, canActivate: [authGuard] },
  { path: 'lista', component: Lista, canActivate: [authGuard] },

  // Cualquier ruta no encontrada redirige a login
  { path: '**', redirectTo: 'login' },
];
