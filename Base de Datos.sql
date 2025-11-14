create database project;
use project;

create table Autor(
	id int auto_increment primary key,
    nombre varchar(20) not null,
    password varchar(20) not null, 
	fecha_nacimiento date,
    nacionalidad varchar(50),
    email varchar(40)
);

create table usuario(
	id int auto_increment primary key,
    nombre varchar(20) not null,
    contrasena varchar(20) not null, 
	fecha_nacimiento date,
    nacionalidad varchar(50),
    email varchar(40)
);

create table Opinion(
	id int primary key auto_increment,
    comentario text,
    puntuacion int,
    marcar tinyint,
    fecha date,
	usuario int,
    constraint fk_Opinion_Usuario foreign key (usuario) references Usuario(id)
);

create table Obra(
	id int primary key auto_increment,
    tipo enum('PELICULA', 'SERIE', 'LIBRO'),
    titulo varchar(100),
    year year,
    genero enum('ACCION', 'AVENTURA', 'CIENCIA FICCION', 'COMEDIA', 'DRAMA', 'FANTASIA', 'MUSICAL', 'ROMANCE', 'SUSPENSE', 'TERROR', 'DOCUMENTAL'),
	sinopsis text,
    opinion int,
    constraint fk_Obra_Opinion foreign key (opinion) references Opinion(id)
);

create table AutorObra(
	autor int,
    obra int,
    constraint pk_AutorObra primary key (autor, obra),
    constraint fk_AutorObra_Autor foreign key (autor) references Autor(id),
    constraint fk_AutorObra_Obra foreign key (obra) references Obra(id)
);