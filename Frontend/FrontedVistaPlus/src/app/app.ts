import { Component, signal, computed } from '@angular/core';
import { RouterOutlet, Router, NavigationEnd } from '@angular/router';
import { Header } from './components/header/header';
import { Footer } from './components/footer/footer';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Header, Footer],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('FrontedVistaPlus');
  showLayout = signal(true);

  constructor(private router: Router) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event) => {
      const navEnd = event as NavigationEnd;
      const url = navEnd.urlAfterRedirects || navEnd.url;
      // Ocultar header y footer en las páginas de login y register
      const isAuthPage = url.includes('/login') || url.includes('/register');
      this.showLayout.set(!isAuthPage);
    });
  }
}
