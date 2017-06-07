insert into facultades(idFacultad,     nombreFac)
				values(   		1, 'Informática');

insert into titulaciones(idTitulacion,                nombreTit, idFacultad, numCursos,    tipo)
				  values(			1, 'Ingeniería Informática',          1,         4, 'grado');

insert into semestres (idSemestre, fechaIniClase, fechaFinClase, fechaIniExam, fechaFinExam)
			   values (         5,  '30-01-2017',  '20-05-2017', '21-05-2017', '20-07-2017');				  

insert into asignaturas(idAsig, curso,   		 nombreAsignatura, idSemestre, idTitulacion)
				 values(     1,     4,  'Sistemas de Información',          5,            1);

insert into asignaturas(idAsig, curso, 				        nombreAsignatura, idSemestre, idTitulacion)
				 values(     2,     2,  'Desarrollo de aplicaciones Android',          5,            1);

insert into asignaturas(idAsig, curso,  nombreAsignatura, idSemestre, idTitulacion)
				 values(     3,     2,    'Programación',          5,            1);

insert into asignaturas(idAsig, curso,       nombreAsignatura, idSemestre, idTitulacion)
				 values(     5,     3,  'Tecnologías Móviles',          5,            1);

insert into asignaturas(idAsig, curso, 				    nombreAsignatura, idSemestre, idTitulacion)
				 values(     6,     3,  'Redes Locales y Metropolitanas',          5,            1);

insert into asignaturas(idAsig, curso,         nombreAsignatura, idSemestre, idTitulacion)
				 values(     7,     1,  'Tarjetas inteligentes',          5,            1);


insert into aulas(idAula, nombreAu,      edificio)
		   values(     1,   'S-51', 'informatica');

insert into aulas(idAula, nombreAu,      edificio)
		   values(     2,   'S-52', 'informatica');

insert into aulas(idAula, nombreAu,      edificio)
		   values(     3,   'S-40', 'informatica');

insert into aulas(idAula, nombreAu,      edificio)
		   values(     4,   'S-41', 'informatica');

insert into aulas(idAula, nombreAu,      edificio)
		   values(     5,  'S-466', 'informatica');

insert into aulas(idAula, nombreAu,      edificio)
		   values(     6,  'S-453', 'informatica');

insert into alumnos(expediente, nombreAl,    apellidosAl)
			 values(       123, 'Estela',  'Santos Peña');

insert into alumnos(expediente,  nombreAl,     apellidosAl)
			 values(       321,  'Manolo', 'Perez Sanchez');

insert into examenes(idExam, idAsig,    fechaExam, horaExam, idAula,     teoria)
		 	  values(	  1,      1, '07-06-2017',  '11:00',      1, 'practica');

insert into examenes(idExam, idAsig,    fechaExam, horaExam, idAula,     teoria)
		 	  values(	  2,      6, '07-06-2017',  '09:00',      1, 'practica');

insert into examenes(idExam, idAsig,    fechaExam, horaExam, idAula,     teoria)
		 	  values(	  3,      5, '07-06-2017',  '16:00',      1, 'practica');

insert into examenes(idExam, idAsig,    fechaExam, horaExam, idAula,     teoria)
		 	  values(	  4,      2, '07-06-2017',  '11:00',      1, 'practica');

insert into matriculas(expediente, idAsig)
				values(       123,      1);

insert into matriculas(expediente, idAsig)
				values(       123,      3);

insert into matriculas(expediente, idAsig)
				values(       123,      5);

insert into matriculas(expediente, idAsig)
				values(       123,      6);


insert into horarios(        dia,    hora,     teoria, idAsig, idAula)
			  values('miercoles',  '09:00', 'practica',      7,      1);

insert into horarios(        dia,    hora,     teoria, idAsig, idAula)
			  values('miercoles', '10:00', 'practica',      6,      2);

insert into horarios(        dia,    hora,     teoria, idAsig, idAula)
			  values('miercoles', '10:00', 'practica',      2,      4);

insert into horarios(        dia,    hora,     teoria, idAsig, idAula)
			  values('miercoles', '11:00', 'practica',      3,      2);

insert into horarios(        dia,    hora,     teoria, idAsig, idAula)
			  values('miercoles', '12:00', 'practica',      5,      3);


insert into cambiosHora(  fechaNueva, horaNueva,        tipo, idAula, idAsig,     teoria)
				 values('31-05-2017',    '10:00', 'cancelada',      3,      6, 'practica');

insert into cambiosHora(  fechaNueva, horaNueva,         tipo, idAula, idAsig,     teoria)
				 values('31-05-2017',   '13:00', 'recuperada',      2,      1, 'practica');
				 
insert into cambiosHora(  fechaNueva, horaNueva,        tipo, idAula, idAsig,     teoria)
				 values('30-05-2017',    '10:00', 'cancelada',      3,      6, 'practica');

insert into cambiosHora(  fechaNueva, horaNueva,         tipo, idAula, idAsig,     teoria)
				 values('30-05-2017',   '13:00', 'recuperada',      2,      1, 'practica');


insert into profesores(idProf,   nombreProf,     apellidosProf,                email)
				values(     1, 'Montserrat',   'Mateos Sánchez', 'mmateossa@upsa.es');

insert into profesores(idProf,  nombreProf,      apellidosProf,               email)
				values(     2,    'Roberto', 'Berjón Gallinas', 'rberjonga@upsa.es');


insert into impartidas(idProf, idAsig,     teoria,     fechaIni,      fechaFin)
				values(     1,      5, 'practica', '30-01-2017',  '20-06-2017');

insert into impartidas(idProf, idAsig,     teoria,     fechaIni,      fechaFin)
				values(     1,      6, 'practica', '30-01-2017',  '20-06-2017');

insert into impartidas(idProf, idAsig,     teoria,     fechaIni,      fechaFin)
				values(     1,      7, 'practica', '30-01-2017',  '20-06-2017');

insert into impartidas(idProf, idAsig,     teoria,     fechaIni,      fechaFin)
				values(     2,      1, 'practica', '30-01-2017',  '20-06-2017');

insert into impartidas(idProf, idAsig,     teoria,     fechaIni,      fechaFin)
				values(     2,      2, 'practica', '30-01-2017',  '20-06-2017');

insert into impartidas(idProf, idAsig,     teoria,     fechaIni,      fechaFin)
				values(     2,      3, 'practica', '30-01-2017',  '20-06-2017');

insert into tutorias(idT, idProf, despacho,   horaT,   diaSemana)
			  values(  1,      1,        5, '18:00', 'miercoles');

insert into tutorias(idT, idProf, despacho,   horaT,   diaSemana)
			  values(  2,      2,        6, '09:00', 'miercoles');
