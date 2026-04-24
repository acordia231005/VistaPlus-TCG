import { Component } from '@angular/core';
import { RouterLink, Router } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  constructor(private router: Router) {}

  buscar(term: string) {
    const urlTree = this.router.parseUrl(this.router.url);
    const path = '/' + (urlTree.root.children['primary'] ? urlTree.root.children['primary'].segments.map(s => s.path).join('/') : '');
    
    if (term.trim()) {
      this.router.navigate([path], { queryParams: { q: term.trim() } });
    } else {
      this.router.navigate([path]);
    }
  }
}
