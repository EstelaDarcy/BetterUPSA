create table semestres
(
idSemestre VARCHAR(9),
fechaIniClase DATE,
fechaFinClase DATE,
fechaIniExam DATE,
fechaFinExam DATE,
constraint "PK_SEMESTRES" primary key (idSemestre)
);


create table facultades
(
idFacultad VARCHAR(9),
nombreFac VARCHAR(50),
constraint "PK_FACULTADES" primary key (idFacultad)
);


create table titulaciones
(
idTitulacion VARCHAR(9),
nombreTit VARCHAR(50),
idFacultad VARCHAR(9),
numCursos NUMBER(2),
tipo VARCHAR(10),
constraint "PK_TITULACIONES" primary key (idTitulacion),
CONSTRAINT "FK_FACULTAD_TITULACION" FOREIGN KEY (idFacultad)
                                REFERENCES facultades(idFacultad)
);



create table asignaturas
(
idAsig VARCHAR(9),
curso NUMBER(1),
nombreAsignatura VARCHAR(50),
idSemestre VARCHAR(9),
idTitulacion VARCHAR(9),
constraint "PK_ASIGNATURAS" primary key (idAsig),
CONSTRAINT "FK_SESMESTRE_ASIGNATURAS" FOREIGN KEY (idSemestre)
                                REFERENCES semestres(idSemestre),
CONSTRAINT "FK_TITULACION_ASIGNATURAS" FOREIGN KEY (idTitulacion)
                                REFERENCES titulaciones(idTitulacion)
);

create table examenes
(
idExam VARCHAR(9),
idAsig VARCHAR(9),
fechaExam DATE,
horaExam VARCHAR(6),
idAula VARCHAR(9),
teoria VARCHAR(20),
constraint "PK_EXAMENES" primary key (idExam),
CONSTRAINT "FK_ASIGNATURA_EXAMENES" FOREIGN KEY (idAsig)
                                REFERENCES asignaturas(idAsig)
);

create table alumnos
(
expediente VARCHAR(9),
nombreAl VARCHAR(20),
apellidosAl VARCHAR(50),
constraint "PK_ALUMNOS" primary key (expediente)
);

create table matriculas
(
expediente VARCHAR(9),
idAsig VARCHAR(9),
constraint "PK_MATRICULAS" primary key (expediente, idAsig),
CONSTRAINT "FK_ASIGNATURA_MATRICULAS" FOREIGN KEY (expediente)
                                REFERENCES alumnos(expediente),
CONSTRAINT "FK_ALUMNO_MATRICULAS" FOREIGN KEY (idAsig)
                                   REFERENCES asignaturas(idAsig)
);

create table aulas
(
idAula VARCHAR(9),
nombreAu VARCHAR(20),
edificio VARCHAR(50),
constraint "PK_AULAS" primary key (idAula)
);



create table horarios
(
dia VARCHAR(10),
hora VARCHAR(6),
teoria VARCHAR(20),
idAsig VARCHAR(9),
idAula VARCHAR(9),

CONSTRAINT "FK_ASIGNATURA_HORARIOS" FOREIGN KEY (idAsig)
                                REFERENCES asignaturas(idAsig),
CONSTRAINT "FK_AULA_HORARIOS" FOREIGN KEY (idAula)
                                   REFERENCES aulas(idAula)
);

create table cambiosHora
(
fechaNueva DATE,
horaNueva VARCHAR(6),
tipo VARCHAR(20),
idAula VARCHAR(9),
idAsig VARCHAR(9),
teoria VARCHAR(20),

CONSTRAINT "FK_ASIGNATURA_CAMBIOSH" FOREIGN KEY (idAsig)
                                REFERENCES asignaturas(idAsig),
CONSTRAINT "FK_AULA_CAMBIOSH" FOREIGN KEY (idAula)
                                REFERENCES aulas(idAula)
);

create table profesores
(
idProf VARCHAR(9),
nombreProf VARCHAR(20),
apellidosProf VARCHAR(50),
email VARCHAR(20),
constraint "PK_PROFESORES" primary key (idProf)
);

create table impartidas
(
idProf VARCHAR(9),
idAsig VARCHAR(9),
teoria VARCHAR(10),
fechaIni DATE,
fechaFin DATE,

CONSTRAINT "FK_ASIGNATURA_IMPARTIDAS" FOREIGN KEY (idAsig)
                                REFERENCES asignaturas(idAsig),
CONSTRAINT "FK_PROFESOR_IMPARTIDAS" FOREIGN KEY (idProf)
                                REFERENCES profesores(idProf)
);

create table tutorias
(
idT VARCHAR(9),
idProf VARCHAR(9),
despacho VARCHAR(20),
horaT VARCHAR(6),
diaSemana VARCHAR(20),
constraint "PK_TUTORIAS" primary key (idT),
CONSTRAINT "FK_PROFESOR_TUTORIAS" FOREIGN KEY (idProf)
                                REFERENCES profesores(idProf)
);

create table cambiost
(
idT VARCHAR(9),
horaNuevaT VARCHAR(6),
diaNuevoT VARCHAR(20),
tipo VARCHAR(10),

CONSTRAINT "FK_TUTORIAS_CAMBIOST" FOREIGN KEY (idT)
                                REFERENCES tutorias(idT)
);