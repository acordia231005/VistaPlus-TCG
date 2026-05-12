import { Component, signal, HostListener, computed } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  menuOpen = signal(false);
  mobileMenuOpen = signal(false);

  // Solo ADMIN y AUTOR pueden crear obras
  canCreate = computed(() => {
    const user = this.authService.currentUser();
    if (!user) return false;
    
    const rol = user.rol?.toUpperCase() || '';
    return rol === 'ADMIN' || rol === 'AUTOR' || rol === 'ROLE_ADMIN' || rol === 'ROLE_AUTOR';
  });

  constructor(private router: Router, public authService: AuthService) {}

  buscar(term: string) {
    if (term.trim()) {
      this.router.navigate(['/'], { queryParams: { q: term.trim() } });
      this.closeMobileMenu();
    }
  }

  toggleMenu(): void {
    this.menuOpen.update(v => !v);
    if (this.menuOpen()) this.mobileMenuOpen.set(false);
  }

  closeMenu(): void {
    this.menuOpen.set(false);
  }

  toggleMobileMenu(): void {
    this.mobileMenuOpen.update(v => !v);
    if (this.mobileMenuOpen()) this.menuOpen.set(false);
  }

  closeMobileMenu(): void {
    this.mobileMenuOpen.set(false);
  }

  logout(): void {
    this.menuOpen.set(false);
    this.mobileMenuOpen.set(false);
    this.authService.logout();
  }

  /** Cierra los menús si se hace clic fuera */
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.user-menu-container') && !target.closest('.hamburger-btn') && !target.closest('.mobile-side-menu')) {
      this.menuOpen.set(false);
      this.mobileMenuOpen.set(false);
    }
  }
}
