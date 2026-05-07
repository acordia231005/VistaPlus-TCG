import { Component, signal, HostListener, computed } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  menuOpen = signal(false);

  // Solo ADMIN y AUTOR pueden crear obras
  canCreate = computed(() => {
    const user = this.authService.currentUser();
    if (!user) return false;
    
    const rol = user.rol?.toUpperCase() || '';
    // Log para depuración (opcional, se puede quitar después)
    console.log('Verificando rol para creación:', rol);
    
    return rol === 'ADMIN' || rol === 'AUTOR' || rol === 'ROLE_ADMIN' || rol === 'ROLE_AUTOR';
  });

  constructor(private router: Router, public authService: AuthService) {}

  buscar(term: string) {
    const urlTree = this.router.parseUrl(this.router.url);
    const path = '/' + (urlTree.root.children['primary'] ? urlTree.root.children['primary'].segments.map(s => s.path).join('/') : '');
    
    if (term.trim()) {
      this.router.navigate([path], { queryParams: { q: term.trim() } });
    } else {
      this.router.navigate([path]);
    }
  }

  toggleMenu(): void {
    this.menuOpen.update(v => !v);
  }

  closeMenu(): void {
    this.menuOpen.set(false);
  }

  logout(): void {
    this.menuOpen.set(false);
    this.authService.logout();
  }

  /** Cierra el menú si se hace clic fuera */
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.user-menu-container')) {
      this.menuOpen.set(false);
    }
  }
}
