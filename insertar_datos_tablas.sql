insert into facultades(idFacultad,     nombreFac)
				values(   		1, 'Informatica');

insert into titulaciones(idTitulacion,                nombreTit, idFacultad, numCursos,    tipo)
				  values(			1, 'Ingenieria Informatica',          1,         4, 'grado');

insert into semestres (idSemestre, fechaIniClase, fechaFinClase, fechaIniExam, fechaFinExam)
			   values (         5,  '30-01-2017',  '20-05-2017', '21-05-2017', '20-06-2017');

insert into asignaturas(idAsig, curso, 				 nombreAsignatura, idSemestre, idTitulacion)
				 values(     1,     4,  'Etica y Derecho Informatico',          5,            1);

insert into asignaturas(idAsig, curso, 				 nombreAsignatura, idSemestre, idTitulacion)
				 values(     2,     4,  'Servicios y Plataformas Web',          5,            1);

insert into asignaturas(idAsig, curso, 				 nombreAsignatura, idSemestre, idTitulacion)
				 values(     3,     4,    'Arquitectura del Software',          5,            1);

insert into aulas(idAula, nombreAu,      edificio)
		   values(     1,   'S-51', 'informatica');

insert into aulas(idAula, nombreAu,      edificio)
		   values(     2,   'S-52', 'informatica');

insert into examenes(idExam, idAsig,    fechaExam, horaExam, idAula,   teoria)
		 	  values(	  1,      1, '02-06-2017',  '10:00',      1, 'teoria');

insert into examenes(idExam, idAsig,    fechaExam, horaExam, idAula,   teoria)
		 	  values(	  2,      2, '09-06-2017',  '09:00',      2, 'teoria');

insert into examenes(idExam, idAsig,    fechaExam, horaExam, idAula,     teoria)
		 	  values(	  3,      3, '08-06-2017',  '16:00',      1, 'practica');

insert into alumnos(expediente,  nombreAl, apellidosAl)
			 values(       123, 'prueba1',     'Perez');

			 insert into alumnos(expediente,  nombreAl, apellidosAl)
			 values(       66, 'prueba2',     'Mengano');
insert into matriculas(expediente, idAsig)
				values(       123,      1);

insert into matriculas(expediente, idAsig)
				values(       123,      2);

insert into matriculas(expediente, idAsig)
				values(       123,      3);

insert into horarios(    dia,    hora,   teoria, idAsig, idAula)
			  values('lunes', '10:00', 'teoria',      1,      1);

insert into horarios(    dia,    hora,   teoria, idAsig, idAula)
			  values('lunes', '11:00', 'teoria',      1,      1);

insert into horarios(    dia,    hora,     teoria, idAsig, idAula)
			  values('lunes', '12:00', 'practica',      3,      1);

insert into horarios(    dia,    hora,     teoria, idAsig, idAula)
			  values('lunes', '13:00', 'practica',      3,      1);

insert into horarios(    dia,    hora,      teoria, idAsig, idAula)
			  values('martes', '09:00', 'practica',      2,      2);

insert into horarios(    dia,    hora,      teoria, idAsig, idAula)
			  values('martes', '10:00', 'practica',      2,      2);

insert into horarios(    dia,    hora,      teoria, idAsig, idAula)
			  values('martes', '11:00', 'practica',      3,      1);

insert into horarios(    dia,    hora,      teoria, idAsig, idAula)
			  values('martes', '12:00', 'practica',      3,      1);

insert into horarios(        dia,    hora,   teoria, idAsig, idAula)
			  values('miercoles', '09:00', 'teoria',      1,      1);

insert into horarios(        dia,    hora,     teoria, idAsig, idAula)
			  values('miercoles', '10:00', 'practica',      2,      2);

insert into horarios(        dia,    hora,     teoria, idAsig, idAula)
			  values('miercoles', '11:00', 'practica',      2,      2);

insert into horarios(        dia,    hora,   teoria, idAsig, idAula)
			  values('miercoles', '12:00', 'teoria',      1,      1);

insert into cambiosHora(  fechaNueva, horaNueva,         tipo, idAula, idAsig,   teoria)
				 values('24-03-2017',   '12:00', 'recuperada',      1,      1, 'teoria');

insert into cambiosHora(  fechaNueva, horaNueva,        tipo, idAula, idAsig,   teoria)
				 values('12-03-2017',   '10:00', 'cancelada',      1,      1, 'teoria');

insert into profesores(idProf,  nombreProf,    apellidosProf,                email)
				values(     4, 'Ana Maria', 'Fermoso Garcia', 'afermosoga@upsa.es');

insert into profesores(idProf,  nombreProf,  apellidosProf,               email)
				values(     5,   'Mariano', 'Raboso Mateo', 'mrabosoma@upsa.es');

insert into profesores(idProf,  nombreProf,     apellidosProf,                email)
				values(     6,   'Alberto', 'Pedrero Esteban', 'apedreroes@upsa.es');

insert into impartidas(idProf, idAsig,     teoria,     fechaIni,      fechaFin)
				values(     4,      3, 'practica', '30-01-2017',  '20-05-2017');

insert into impartidas(idProf, idAsig,   teoria,     fechaIni,      fechaFin)
				values(     5,      1, 'teoria', '30-01-2017',  '20-05-2017');

insert into impartidas(idProf, idAsig,     teoria,     fechaIni,      fechaFin)
				values(     6,      2, 'practica', '30-01-2017',  '20-05-2017');

insert into impartidas(idProf, idAsig,     teoria,     fechaIni,      fechaFin)
				values(     4,      3, 'teoria', '30-01-2017',  '20-05-2017');