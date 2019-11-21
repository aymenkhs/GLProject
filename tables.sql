#alternative one

create table Personne(
	userName varchar2(30),
	nom varchar2(30),
	prenom varchar2(30),
	DateN date,
	email varchar2(50),
	password varchar2(30),
	langue varchar2(20),
	type varchar2(15),
	pathPhoto varchar2(200),
	constraint PersPK primary key (userName)
);

create table Enseignant(
	matriculeEns varchar2(15),
	userName varchar2(30),
	grade varchar2(20),
	dommaine varchar2(20),
	constraint ensPersFK foreign key (userName) references Personne,	
	constraint ensPK primary key (matriculeEns)
);

create table Etudiant(
	matriculeEtud varchar2(15),
	userName varchar2(30),
	specialite varchar2(20),
	anneeCour varchar2(5),
	constraint etudPersFK foreign key (userName) references Personne,
	constraint etudPK primary key (matriculeEtud)
);

create table Formation(
	numFormation integer,
	nomFormation varchar2(30),
	matriculeEns varchar2(15),
	description varchar2(150),
	constraint formEnsFK foreign key (matriculeEns) references Enseignant,
	constraint formPK primary key (numFormation)	
);

create table Cour(
	numFormation integer,
	nomCour varchar2(30),
	pathContenue varchar2(200),
	constraint courFormFK foreign key (numFormation) references Formation,
	constraint courPK primary key (numFormation,nomCour)			
);

create table Devoir(
	numDevoir integer,
	numFormation integer,
	enoncer varchar2(1000),
	constraint devFormFK foreign key (numFormation) references Formation,
	constraint devPK primary key (numFormation,numDevoir)	
);

create table Test(
	numTest integer,
	numFormation integer,
	pathQuestions varchar2(200),
	constraint testFormFK foreign key (numFormation) references Formation,
	constraint testPK primary key (numFormation,numTest)	
);

#########################################################################

create table Sondage(
	numSondage integer,
	userName varchar2(30),
	description varchar2(500),
	pathChoix varchar2(200),
	typeParticipant varchar2(20),
	constraint sondPersFK foreign key (userName) references Personne,
	constraint sondPK primary key (numSondage)
);

create table Participer(
	numSondage integer,
	userName varchar2(30),
	reponse integer,
	constraint partPersFK foreign key (userName) references Personne,
	constraint partSondFK foreign key (numSondage) references Sondage,
	constraint partPK primary key (numSondage,userName)
);

insert into Personne(userName, nom, prenom, DateN, email, password, langue, type) values();
insert into Enseignant(matriculeEns ,userName, grade, dommaine) values();
insert into Etudiant(matriculeEtud ,userName, specialite, anneeCour) values();

select * from Formation where numFormation = '';