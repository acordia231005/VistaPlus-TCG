import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ObrasService } from '../../services/obras.service';

@Component({
  selector: 'app-lista',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="list-container">
      <div class="list-header glass">
        <h1>Mi Lista</h1>
        <p>Tus títulos guardados para ver más tarde</p>
      </div>

      <!-- Grid de items -->
      <div class="list-grid" *ngIf="items().length > 0">
        @for (obra of items(); track obra.id) {
          <div class="card glass" [routerLink]="['/obra', obra.id]">
            <div class="card-image">
              <img [src]="obra.imagen" [alt]="obra.titulo">
              <div class="card-overlay">
                <span class="badge">{{ obra.tipo | uppercase }}</span>
              </div>
            </div>
            <div class="card-content">
              <h3>{{ obra.titulo }}</h3>
            </div>
          </div>
        }
      </div>

      <!-- Estado vacío -->
      <div class="empty-state" *ngIf="items().length === 0">
        <div class="empty-icon">
          <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1" stroke-linecap="round" stroke-linejoin="round">
            <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"/>
          </svg>
        </div>
        <h2>Tu lista está vacía</h2>
        <p>Añade películas, series o libros para tenerlos siempre a mano.</p>
        <button class="btn-primary" routerLink="/">Explorar contenido</button>
      </div>
    </div>
  `,
  styles: [`
    .list-container {
      padding: 40px 20px;
      max-width: 1200px;
      margin: 0 auto;
      min-height: 80vh;
    }
    .list-header {
      padding: 30px;
      border-radius: 20px;
      margin-bottom: 40px;
      text-align: center;
    }
    .list-header h1 { margin: 0; font-size: 2.5rem; color: white; }
    .list-header p { color: rgba(255,255,255,0.6); margin-top: 10px; }
    
    .glass {
      background: rgba(255, 255, 255, 0.03);
      backdrop-filter: blur(12px);
      border: 1px solid rgba(255, 255, 255, 0.1);
      transition: all 0.3s ease;
    }

    .list-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
      gap: 25px;
      animation: fadeIn 0.5s ease-out;
    }

    .card {
      border-radius: 16px;
      overflow: hidden;
      cursor: pointer;
    }

    .card:hover {
      transform: translateY(-8px);
      background: rgba(255, 255, 255, 0.08);
      border-color: rgba(255, 255, 255, 0.2);
    }

    .card-image {
      position: relative;
      aspect-ratio: 2/3;
      overflow: hidden;
    }

    .card-image img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.5s ease;
    }

    .card:hover .card-image img {
      transform: scale(1.1);
    }

    .card-overlay {
      position: absolute;
      top: 10px;
      left: 10px;
    }

    .badge {
      background: rgba(0, 0, 0, 0.6);
      backdrop-filter: blur(4px);
      color: white;
      padding: 4px 8px;
      border-radius: 6px;
      font-size: 10px;
      font-weight: 700;
      letter-spacing: 0.5px;
    }

    .card-content {
      padding: 15px;
      text-align: center;
    }

    .card-content h3 {
      margin: 0;
      font-size: 1.1rem;
      color: white;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .empty-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 60px;
      color: white;
      text-align: center;
    }
    .empty-icon {
      margin-bottom: 24px;
      color: rgba(255,255,255,0.2);
    }
    .empty-state h2 { font-size: 1.8rem; margin-bottom: 12px; }
    .empty-state p { color: rgba(255,255,255,0.5); max-width: 400px; margin-bottom: 30px; }
    
    .btn-primary {
      padding: 12px 30px;
      background: #3b82f6;
      color: white;
      border: none;
      border-radius: 12px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s;
    }
    .btn-primary:hover {
      background: #2563eb;
      transform: translateY(-2px);
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }
  `]
})
export class Lista {
  obrasService = inject(ObrasService);
  items = this.obrasService.miLista;
}
