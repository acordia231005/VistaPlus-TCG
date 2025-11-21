use project;

insert into Autor (nombre, password, fecha_nacimiento, nacionalidad, email) values
('Juan Perez', 'pass1234', '1985-03-12', 'Mexicana', 'juan.perez@example.com'),
('Maria Lopez', 'clave5678', '1990-07-25', 'Argentina', 'maria.lopez@example.com'),
('Carlos Ruiz', 'segura910', '1978-11-02', 'Colombiana', 'carlos.ruiz@example.com'),
('Ana Torres', 'abc12345', '1992-01-18', 'Chilena', 'ana.torres@example.com'),
('Luis Gómez', 'luis2025', '1988-05-09', 'Peruana', 'luis.gomez@example.com'),
('Sara Medina', 'sara9876', '1995-09-30', 'Ecuatoriana', 'sara.medina@example.com'),
('Pedro Salas', 'pedro55', '1980-12-14', 'Mexicana', 'pedro.salas@example.com'),
('Lucía Rivas', 'lucia777', '1993-04-22', 'Española', 'lucia.rivas@example.com'),
('Diego Fernández', 'diegopass', '1986-06-03', 'Uruguaya', 'diego.fernandez@example.com'),
('Valeria Castro', 'val2024', '1991-10-11', 'Venezolana', 'valeria.castro@example.com');

insert into usuario (nombre, contrasena, fecha_nacimiento, nacionalidad, email) values
('Javier Soto', 'jav12345', '1994-02-17', 'Mexicana', 'javier.soto@example.com'),
('Laura Méndez', 'lau67890', '1989-08-05', 'Argentina', 'laura.mendez@example.com'),
('Andrés Silva', 'andres22', '1991-11-19', 'Colombiana', 'andres.silva@example.com'),
('Camila Reyes', 'cam9876', '1996-03-25', 'Chilena', 'camila.reyes@example.com'),
('Fernando Cruz', 'fer2024', '1985-07-14', 'Peruana', 'fernando.cruz@example.com'),
('Mónica Herrera', 'mon1010', '1993-09-08', 'Ecuatoriana', 'monica.herrera@example.com'),
('Héctor Rojas', 'hec555', '1987-12-02', 'Mexicana', 'hector.rojas@example.com'),
('Patricia Luna', 'pat3333', '1992-06-11', 'Española', 'patricia.luna@example.com'),
('Santiago Vega', 'san4444', '1990-04-29', 'Uruguaya', 'santiago.vega@example.com'),
('Daniela Campos', 'dan9898', '1995-10-21', 'Venezolana', 'daniela.campos@example.com');

insert into Obra (tipo, titulo, year, genero, sinopsis, opinion) values
('PELICULA', 'El Último Guerrero', 2015, 'ACCION',
 'Un ex‐soldado debe enfrentarse a una organización secreta que amenaza la ciudad.',
 1),
('SERIE', 'Mundos Paralelos', 2020, 'CIENCIA FICCION',
 'Un grupo de jóvenes descubre portales que conectan realidades alternativas.',
 2),
('LIBRO', 'El Jardín de las Sombras', 1998, 'SUSPENSE',
 'Una escritora investiga la misteriosa desaparición de su vecina.',
 3),
('PELICULA', 'Risas en el Metro', 2011, 'COMEDIA',
 'Un comediante amateur encuentra inspiración en los pasajeros del transporte público.',
 4),
('SERIE', 'Corazones Rotos', 2018, 'DRAMA',
 'Tres familias enfrentan el reto de reconstruir sus vidas después de una tragedia.',
 5),
('LIBRO', 'Aventuras en Arkanor', 2005, 'FANTASIA',
 'Un joven campesino descubre que tiene un destino mágico que cambiará su mundo.',
 6),
('PELICULA', 'La Canción del Recuerdo', 2010, 'MUSICAL',
 'Una cantante lucha por alcanzar la fama sin perder su esencia.',
 7),
('SERIE', 'Amor Entre Ruinas', 2019, 'ROMANCE',
 'Dos desconocidos se unen tras un desastre natural que cambia sus vidas.',
 8),
('PELICULA', 'El Susurro en la Noche', 2022, 'TERROR',
 'Un pueblo entero es acechado por una voz misteriosa que solo suena al anochecer.',
 9),
('DOCUMENTAL', 'Planeta Azul: Profundidades', 2021, 'DOCUMENTAL',
 'Un recorrido por los océanos del mundo y las criaturas que los habitan.',
 10);
 
 insert into Opinion (comentario, puntuacion, marcar, fecha, usuario) values
('Excelente obra, muy recomendable.', 9, 1, '2023-01-15', 1),
('Me gustó, pero podría mejorar en algunos aspectos.', 7, 0, '2023-02-20', 2),
('No fue lo que esperaba, un poco aburrida.', 4, 0, '2023-03-05', 3),
('Una experiencia increíble, volvería a verla.', 10, 1, '2023-04-12', 4),
('Historia interesante, personajes bien desarrollados.', 8, 0, '2023-05-18', 5),
('Regular, algunos detalles no me convencieron.', 5, 0, '2023-06-23', 6),
('Divertida y entretenida, para pasar el rato.', 7, 1, '2023-07-30', 7),
('Emocionante hasta el final, me encantó.', 9, 1, '2023-08-14', 8),
('Demasiado predecible, esperaba más.', 4, 0, '2023-09-09', 9),
('Documental muy educativo e informativo.', 10, 1, '2023-10-01', 10);

insert into AutorObra (autor, obra) values
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);
