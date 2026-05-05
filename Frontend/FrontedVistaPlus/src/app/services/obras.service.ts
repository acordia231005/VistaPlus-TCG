import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

export interface Obra {
  id: number;
  tipo: 'pelicula' | 'serie' | 'libro';
  titulo: string;
  descripcion: string;
  imagen: string;
  anio?: number;
  director?: string;
  genero?: string;
}

const API_BASE = 'http://localhost:8080/api';

@Injectable({
  providedIn: 'root'
})
export class ObrasService {

  // Estado reactivo
  private readonly obrasSignal = signal<Obra[]>([]);
  private readonly cargandoSignal = signal<boolean>(false);
  private readonly errorSignal = signal<string | null>(null);

  // Estado de usuario
  private readonly miListaSignal = signal<Obra[]>([]);
  private readonly vistosSignal = signal<number[]>([]);
  private readonly comentariosSignal = signal<{ obraId: number; texto: string }[]>([]);

  // Computed públicos
  readonly obras = computed(() => this.obrasSignal());
  readonly cargando = computed(() => this.cargandoSignal());
  readonly error = computed(() => this.errorSignal());
  readonly miLista = computed(() => this.miListaSignal());
  readonly vistosCount = computed(() => this.vistosSignal().length);
  readonly comentariosCount = computed(() => this.comentariosSignal().length);

  constructor(private http: HttpClient) {
    this.cargarObras();
  }

  /**
   * Carga todas las obras desde el backend.
   * GET http://localhost:8080/api/obras
   */
  async cargarObras(): Promise<void> {
    this.cargandoSignal.set(true);
    this.errorSignal.set(null);
    try {
      const obras = await firstValueFrom(
        this.http.get<Obra[]>(`${API_BASE}/obras`)
      );
      this.obrasSignal.set(obras);
    } catch {
      this.errorSignal.set('No se pudieron cargar las obras. Comprueba la conexión al servidor.');
    } finally {
      this.cargandoSignal.set(false);
    }
  }

  /**
   * Obtiene las obras filtradas por tipo.
   * GET http://localhost:8080/api/obras?tipo=pelicula
   */
  async cargarPorTipo(tipo: 'pelicula' | 'serie' | 'libro'): Promise<void> {
    this.cargandoSignal.set(true);
    this.errorSignal.set(null);
    try {
      const obras = await firstValueFrom(
        this.http.get<Obra[]>(`${API_BASE}/obras`, { params: { tipo } })
      );
      this.obrasSignal.set(obras);
    } catch {
      this.errorSignal.set('No se pudieron cargar las obras.');
    } finally {
      this.cargandoSignal.set(false);
    }
  }

  /**
   * Devuelve el array actual (síncronamente) para compatibilidad.
   */
  getObras(): Obra[] {
    return this.obrasSignal();
  }

  /**
   * Busca una obra por ID en el estado local. Si no está, la pide al backend.
   * GET http://localhost:8080/api/obras/:id
   */
  async getObraById(id: number): Promise<Obra | undefined> {
    const local = this.obrasSignal().find(o => o.id === id);
    if (local) return local;

    try {
      return await firstValueFrom(
        this.http.get<Obra>(`${API_BASE}/obras/${id}`)
      );
    } catch {
      return undefined;
    }
  }

  // ─── Gestión de Mi Lista ──────────────────────────────────────────────────

  toggleEnLista(obra: Obra) {
    const listaActual = this.miListaSignal();
    if (listaActual.some(o => o.id === obra.id)) {
      this.miListaSignal.set(listaActual.filter(o => o.id !== obra.id));
    } else {
      this.miListaSignal.set([...listaActual, obra]);
    }
  }

  estaEnLista(id: number): boolean {
    return this.miListaSignal().some(o => o.id === id);
  }

  // ─── Gestión de Vistos ────────────────────────────────────────────────────

  toggleVisto(id: number) {
    const vistos = this.vistosSignal();
    if (vistos.includes(id)) {
      this.vistosSignal.set(vistos.filter(vId => vId !== id));
    } else {
      this.vistosSignal.set([...vistos, id]);
    }
  }

  estaVisto(id: number): boolean {
    return this.vistosSignal().includes(id);
  }

  // ─── Gestión de Comentarios ───────────────────────────────────────────────

  agregarComentario(obraId: number, texto: string) {
    this.comentariosSignal.update(c => [...c, { obraId, texto }]);
  }
}
