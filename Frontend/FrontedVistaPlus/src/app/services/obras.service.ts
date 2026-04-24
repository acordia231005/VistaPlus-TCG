import { Injectable } from '@angular/core';

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

@Injectable({
  providedIn: 'root'
})
export class ObrasService {
  private obras: Obra[] = [
    // Películas
    { id: 1, tipo: 'pelicula', titulo: 'Inception', descripcion: 'Un experto en el arte de apropiarse, durante el sueño, de los secretos del subconsciente ajeno, tiene la oportunidad de recuperar su antigua vida a cambio de realizar un trabajo casi imposible.', imagen: 'https://picsum.photos/seed/inc/400/600', anio: 2010, director: 'Christopher Nolan', genero: 'Ciencia Ficción' },
    { id: 2, tipo: 'pelicula', titulo: 'Interstellar', descripcion: 'Al ver que la vida en la Tierra está llegando a su fin, un grupo de exploradores viaja a través de un agujero de gusano para encontrar un nuevo hogar para la humanidad.', imagen: 'https://picsum.photos/seed/int/400/600', anio: 2014, director: 'Christopher Nolan', genero: 'Ciencia Ficción' },
    { id: 7, tipo: 'pelicula', titulo: 'El Padrino', descripcion: 'Don Vito Corleone es el respetado y temido jefe de una de las cinco familias de la mafia de Nueva York. Cuando se niega a participar en el negocio de las drogas, el jefe de otra banda ordena su asesinato.', imagen: 'https://picsum.photos/seed/pad/400/600', anio: 1972, director: 'Francis Ford Coppola', genero: 'Crimen' },
    { id: 8, tipo: 'pelicula', titulo: 'Pulp Fiction', descripcion: 'La vida de un boxeador, dos sicarios, la esposa de un gángster y dos bandidos se entrelazan en una historia de violencia y redención.', imagen: 'https://picsum.photos/seed/pulp/400/600', anio: 1994, director: 'Quentin Tarantino', genero: 'Crimen' },
    { id: 9, tipo: 'pelicula', titulo: 'El Caballero Oscuro', descripcion: 'Con la ayuda del teniente Jim Gordon y el fiscal del distrito Harvey Dent, Batman se propone destruir el crimen organizado en Gotham. Su mayor desafío es el Joker.', imagen: 'https://picsum.photos/seed/bat/400/600', anio: 2008, director: 'Christopher Nolan', genero: 'Acción' },
    { id: 10, tipo: 'pelicula', titulo: 'Gladiator', descripcion: 'El general romano Máximo es traicionado y su familia asesinada. Tras ser convertido en esclavo, se convierte en gladiador para vengar su muerte.', imagen: 'https://picsum.photos/seed/glad/400/600', anio: 2000, director: 'Ridley Scott', genero: 'Épico' },
    { id: 18, tipo: 'pelicula', titulo: 'Matrix', descripcion: 'Un programador informático descubre que la realidad es una simulación creada por máquinas, y se une a la rebelión para liberar a la humanidad.', imagen: 'https://picsum.photos/seed/mat/400/600', anio: 1999, director: 'Lana y Lilly Wachowski', genero: 'Ciencia Ficción' },

    // Series
    { id: 3, tipo: 'serie', titulo: 'Breaking Bad', descripcion: 'Un profesor de química diagnosticado con cáncer inoperable recurre a la venta de metanfetamina para asegurar el futuro financiero de su familia.', imagen: 'https://picsum.photos/seed/brb/400/600', anio: 2008, director: 'Vince Gilligan', genero: 'Drama' },
    { id: 4, tipo: 'serie', titulo: 'Stranger Things', descripcion: 'A raíz de la desaparición de un niño, un pequeño pueblo descubre un misterio que involucra experimentos secretos, fuerzas sobrenaturales y a una niña muy extraña.', imagen: 'https://picsum.photos/seed/sth/400/600', anio: 2016, director: 'The Duffer Brothers', genero: 'Misterio' },
    { id: 11, tipo: 'serie', titulo: 'Juego de Tronos', descripcion: 'Nueve familias nobles luchan por el control de las tierras míticas de Poniente, mientras un antiguo enemigo regresa tras estar inactivo durante milenios.', imagen: 'https://picsum.photos/seed/got/400/600', anio: 2011, director: 'David Benioff, D.B. Weiss', genero: 'Fantasía' },
    { id: 12, tipo: 'serie', titulo: 'Los Soprano', descripcion: 'Tony Soprano, un jefe de la mafia de Nueva Jersey, lidia con problemas personales y profesionales que afectan a su estado mental, lo que le lleva a buscar asesoramiento psiquiátrico.', imagen: 'https://picsum.photos/seed/sop/400/600', anio: 1999, director: 'David Chase', genero: 'Crimen' },
    { id: 13, tipo: 'serie', titulo: 'Dark', descripcion: 'La desaparición de dos niños muestra los vínculos entre cuatro familias y expone el pasado de un pequeño pueblo a través de varias generaciones.', imagen: 'https://picsum.photos/seed/drk/400/600', anio: 2017, director: 'Baran bo Odar', genero: 'Ciencia Ficción' },
    { id: 19, tipo: 'serie', titulo: 'The Office', descripcion: 'Un falso documental sobre el día a día de los oficinistas de la sucursal de Dunder Mifflin en Scranton, Pensilvania.', imagen: 'https://picsum.photos/seed/off/400/600', anio: 2005, director: 'Greg Daniels', genero: 'Comedia' },
    
    // Libros
    { id: 5, tipo: 'libro', titulo: 'Percy Jackson y el ladrón del rayo', descripcion: 'Un niño descubre que es descendiente de un dios griego y se embarca en una aventura para evitar una guerra entre los dioses del Olimpo.', imagen: 'https://picsum.photos/seed/pj/400/600', anio: 2005, director: 'Rick Riordan', genero: 'Fantasía' },
    { id: 6, tipo: 'libro', titulo: 'Harry Potter y la piedra filosofal', descripcion: 'Un niño huérfano descubre que es mago y asiste a una escuela de magia y hechicería, donde hace amigos y enemigos, y descubre la verdad sobre sus padres.', imagen: 'https://picsum.photos/seed/hp/400/600', anio: 1997, director: 'J.K. Rowling', genero: 'Fantasía' },
    { id: 14, tipo: 'libro', titulo: 'Cien años de soledad', descripcion: 'La historia de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo.', imagen: 'https://picsum.photos/seed/cas/400/600', anio: 1967, director: 'Gabriel García Márquez', genero: 'Realismo Mágico' },
    { id: 15, tipo: 'libro', titulo: '1984', descripcion: 'Una novela distópica sobre una sociedad totalitaria gobernada por el "Gran Hermano", donde la verdad y la historia son manipuladas.', imagen: 'https://picsum.photos/seed/1984/400/600', anio: 1949, director: 'George Orwell', genero: 'Distopía' },
    { id: 16, tipo: 'libro', titulo: 'El Señor de los Anillos', descripcion: 'Un joven hobbit hereda un anillo mágico y se embarca en un viaje épico para destruirlo antes de que caiga en manos del Señor Oscuro.', imagen: 'https://picsum.photos/seed/lotr/400/600', anio: 1954, director: 'J.R.R. Tolkien', genero: 'Fantasía Épica' },
    { id: 17, tipo: 'libro', titulo: 'Dune', descripcion: 'Un joven noble debe viajar al planeta desértico Arrakis para gobernar y proteger el recurso más valioso del universo.', imagen: 'https://picsum.photos/seed/dune/400/600', anio: 1965, director: 'Frank Herbert', genero: 'Ciencia Ficción' }
  ];

  getObras(): Obra[] {
    return this.obras;
  }

  getObraById(id: number): Obra | undefined {
    return this.obras.find(o => o.id === id);
  }
}
