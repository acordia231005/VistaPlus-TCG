-- GÉNEROS
INSERT INTO genero (nombre) VALUES 
('Acción'),('Aventura'),('Comedia'),('Drama'),('Fantasía'),
('Ciencia Ficción'),('Terror'),('Romance'),('Misterio'),('Thriller'),
('Historia'),('Biografía'),('Deportes'),('Musical'),('Documental'),
('Anime'),('Manga'),('Superhéroes'),('Western'),('Noir'),
('Slice of Life'),('Isekai'),('Shounen'),('Shoujo'),('Seinen'),
('Josei'),('Mecha'),('Harem'),('Psicológico'),('Sobrenatural'),
('Magia'),('Post-apocalíptico'),('Cyberpunk'),('Steampunk'),
('Gore'),('Infantil'),('Familiar'),('Político'),('Filosófico');

-- USUARIOS (2 USER, 2 AUTOR, 1 ADMIN)
INSERT INTO usuario (id, username, nacionalidad, email, password, fecha_nac, rol) VALUES 
(1, 'hayao_miyazaki', 'Japonesa', 'miyazaki@ghibli.jp', 'pass1234', '1941-01-05', 'AUTOR'),
(2, 'akira_toriyama', 'Japonesa', 'toriyama@dragonball.jp', 'pass1234', '1955-04-05', 'AUTOR'),
(3, 'carlos_user', 'Española', 'carlos@mail.com', 'pass1234', '1995-03-15', 'USER'),
(4, 'cristina_user', 'Española', 'cristina@mail.com', 'pass1234', '1997-12-24', 'USER'),
(5, 'admin', 'Española', 'admin@mail.com', 'admin123', '1990-01-01', 'ADMIN');

-- OBRAS
INSERT INTO obra (id, tipo, titulo, sinopsis, year, id_genero, id_usuario) VALUES 
(1, 'PELICULA', 'El Viaje de Chihiro', 'Una niña atrapada en el mundo espiritual.', '2001-07-20', 5, 1),
(2, 'PELICULA', 'El Castillo Ambulante', 'Un castillo mágico y una maldición.', '2004-11-20', 5, 1),

(3, 'SERIE', 'Dragon Ball Z', 'Guerreros luchan por salvar la Tierra.', '1989-04-26', 1, 2),
(4, 'SERIE', 'Dr. Slump', 'Comedia absurda con robots y ciencia.', '1981-04-08', 3, 2),

(5, 'LIBRO', 'Manga Arte', 'Guía sobre cómo dibujar manga.', '2000-01-01', 17, 1),
(6, 'LIBRO', 'Dragon Ball Vol.1', 'Inicio de la aventura de Goku.', '1984-12-03', 23, 2);

-- OPINIONES (2 por cada obra)
INSERT INTO opinion (id_usuario, id_obra, comentario, puntuacion, marcar, fecha) VALUES 
(3, 1, 'Visualmente increíble y muy emotiva.', 10, true, '2024-01-10'),
(4, 1, 'Una historia mágica y única.', 9, true, '2024-01-12'),
(3, 2, 'Muy creativa y con gran mensaje.', 9, true, '2024-01-15'),
(4, 2, 'Me encantó el diseño del mundo.', 8, false, '2024-01-18'),
(3, 3, 'Un clásico del anime de acción.', 10, true, '2024-02-01'),
(4, 3, 'Muy entretenido aunque algo largo.', 8, true, '2024-02-05'),
(3, 4, 'Humor absurdo pero divertido.', 7, false, '2024-02-10'),
(4, 4, 'Muy original para su época.', 8, true, '2024-02-12'),
(3, 5, 'Perfecto para aprender dibujo.', 9, true, '2024-03-01'),
(4, 5, 'Muy útil pero algo básico.', 7, false, '2024-03-03'),
(3, 6, 'Gran inicio para una saga mítica.', 10, true, '2024-03-10'),
(4, 6, 'Divertido y lleno de aventuras.', 9, true, '2024-03-12');