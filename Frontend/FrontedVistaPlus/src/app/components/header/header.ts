import { Component, signal, HostListener } from '@angular/core';
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
