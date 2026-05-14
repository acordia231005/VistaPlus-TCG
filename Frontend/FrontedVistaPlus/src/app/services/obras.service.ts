import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

export interface Obra {
  id: number;
  tipo: 'PELICULA' | 'SERIE' | 'LIBRO';
  titulo: string;
  sinopsis: string;
  year: string;           // Formato: YYYY-MM-DD HH:mm:ss.SSSSSS
  id_genero: number;
  genero_nombre: string;
  id_usuario: number;
  autor_username: string;
  imagen?: string | null;
}

export interface Opinion {
  id?: number;
  usuarioId: number;
  obraId: number;
  comentario: string;
  puntuacion: number;
  marcar: boolean;
  fecha?: string;
  usuarioUsername?: string;
}

export interface Genero {
  id: number;
  nombre: string;
}

const API_BASE = 'http://localhost:8085';

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
   * GET http://localhost:8085/obra
   */
  async cargarObras(): Promise<void> {
    this.cargandoSignal.set(true);
    this.errorSignal.set(null);
    try {
      const obras = await firstValueFrom(
        this.http.get<Obra[]>(`${API_BASE}/obra`)
      );
      this.obrasSignal.set(obras);
    } catch {
      this.errorSignal.set('No se pudieron cargar las obras. Comprueba la conexión al servidor.');
    } finally {
      this.cargandoSignal.set(false);
    }
  }

  /**
   * Filtra obras por tipo en el estado local (el backend las devuelve todas).
   */
  getObrasPorTipo(tipo: 'PELICULA' | 'SERIE' | 'LIBRO'): Obra[] {
    return this.obrasSignal().filter(o => o.tipo === tipo);
  }

  /**
   * Devuelve el array actual (síncronamente) para compatibilidad.
   */
  getObras(): Obra[] {
    return this.obrasSignal();
  }

  /**
   * Busca una obra por ID en el estado local. Si no está, la pide al backend.
   * GET http://localhost:8085/obra/:id
   */
  async getObraById(id: number): Promise<Obra | undefined> {
    const local = this.obrasSignal().find(o => o.id === id);
    if (local) return local;

    try {
      return await firstValueFrom(
        this.http.get<Obra>(`${API_BASE}/obra/${id}`)
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

  // ─── Backend Opinions ─────────────────────────────────────────────────────

  async getOpinionesDeObra(id_obra: number): Promise<Opinion[]> {
    try {
      return await firstValueFrom(
        this.http.get<Opinion[]>(`${API_BASE}/obra/${id_obra}/opiniones`)
      );
    } catch {
      return [];
    }
  }

  async puntuarObra(usuario_id: number, obra_id: number, puntuacion: number): Promise<void> {
    try {
      await firstValueFrom(
        this.http.post(`${API_BASE}/usuario/${usuario_id}/obras/${obra_id}/puntuacion`, puntuacion)
      );
    } catch (err) {
      console.error('Error al puntuar', err);
      throw err;
    }
  }

  async comentarObra(usuario_id: number, obra_id: number, comentario: string): Promise<void> {
    try {
      await firstValueFrom(
        this.http.post(`${API_BASE}/usuario/${usuario_id}/obras/${obra_id}/comentario`, comentario)
      );
      // Actualizamos el contador local para que el perfil se refresque
      this.comentariosSignal.update(c => [...c, { obraId: obra_id, texto: comentario }]);
    } catch (err) {
      console.error('Error al comentar', err);
      throw err;
    }
  }

  /**
   * Crea una nueva obra en el backend.
   * POST http://localhost:8085/obra
   */
  async crearObra(obra: Partial<Obra>): Promise<Obra> {
    try {
      const response = await firstValueFrom(
        this.http.post<Obra>(`${API_BASE}/obra`, obra)
      );
      // Recargamos las obras para que aparezca la nueva
      await this.cargarObras();
      return response;
    } catch (err) {
      console.error('Error al crear obra', err);
      throw err;
    }
  }

  /**
   * Obtiene la lista de géneros.
   * GET http://localhost:8085/genero
   */
  async getGeneros(): Promise<Genero[]> {
    try {
      return await firstValueFrom(
        this.http.get<Genero[]>(`${API_BASE}/genero`)
      );
    } catch {
      // Mock de seguridad si falla la red
      return [
        { id: 1, nombre: 'Acción' },
        { id: 2, nombre: 'Comedia' },
        { id: 3, nombre: 'Drama' },
        { id: 4, nombre: 'Terror' },
        { id: 5, nombre: 'Ciencia Ficción' },
        { id: 6, nombre: 'Fantasía' },
        { id: 7, nombre: 'Documental' }
      ];
    }
  }
}
