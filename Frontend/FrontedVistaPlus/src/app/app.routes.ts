import { Routes } from '@angular/router';
import { Main } from './components/main/main';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { ObraDetalle } from './components/obra-detalle/obra-detalle';
import { CrearObra } from './components/crear-obra/crear-obra';
import { Profile } from './components/profile/profile';
import { Lista } from './components/lista/lista';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', component: Main },
  { path: 'peliculas', component: Main },
  { path: 'series', component: Main },
  { path: 'libros', component: Main },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'perfil', component: Profile, canActivate: [authGuard] },
  { path: 'lista', component: Lista, canActivate: [authGuard] },
  { path: 'obra/:id', component: ObraDetalle },
  { path: 'crear-obra', component: CrearObra, canActivate: [authGuard] },
  { path: 'editar-obra/:id', component: CrearObra, canActivate: [authGuard] },
  { path: '**', redirectTo: '' }
];
