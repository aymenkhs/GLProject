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
	matriculeEns integer,
	userName varchar2(30),
	grade varchar2(20),
	dommaine varchar2(20),
	constraint ensPersFK foreign key (userName) references Personne,	
	constraint ensPK primary key (matriculeEns)
);

create table Etudiant(
	matriculeEtud integer,
	userName varchar2(30),
	specialite varchar2(20),
	anneeCour varchar2(5),
	constraint etudPersFK foreign key (userName) references Personne,
	constraint etudPK primary key (matriculeEtud)
);

create table Formation(
	numFormation integer,
	nomFormation varchar2(30),
	matriculeEns integer,
	description varchar2(150),
	constraint formEnsFK foreign key (matriculeEns) references Enseignant,
	constraint formPK primary key (numFormation)	
);

create table MembreFormation(
	numFormation integer,
	matriculeEtud integer,
	constraint memFormFK foreign key (numFormation) references Formation,
	constraint memEtudFK foreign key (matriculeEtud) references Etudiant,
	constraint memPK primary key (matriculeEtud, numFormation)	
);

create table Historique(
	numAction integer,
	numFormation integer,
	matriculeEtud integer,	
	constraint hstMemFK foreign key (matriculeEtud, numFormation) references MembreFormation,
	constraint hstPK primary key (numFormation,matriculeEtud,numAction)	
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

create table PasseDevoir(
	numDevoir integer,
	numFormation integer,
	matriculeEtud integer,
	pathReponseD varchar2(200),
	noteD integer,
	constraint chknoteD check(noteD>=0 and noteD<=20),
	constraint psdDevFK foreign key (numFormation,numDevoir) references Devoir,	
	constraint psdMemFK foreign key (matriculeEtud, numFormation) references MembreFormation,
	constraint psdPK primary key (numFormation,numDevoir,matriculeEtud)	
);

create table Test(
	numTest integer,
	numFormation integer,
	nbQuestion integer,
	constraint testFormFK foreign key (numFormation) references Formation,
	constraint testPK primary key (numFormation,numTest)	
);

create table Question(
	numFormation integer,
	numTest integer,
	numQusetion integer,
	enoncerQuestion varchar2(200),
	constraint qstTestFK foreign key (numFormation,numTest) references Test,
	constraint qstPK primary key (numFormation,numTest,numQusetion)	
);

create table ChoixQuestion(
	numFormation integer,
	numTest integer,
	numQusetion integer,
	numChoixQ integer,
	contenueReponse varchar2(100),
	isTrue integer,
	constraint chktrue check(isTrue>=0 and isTrue<=1),
	constraint choixqQstPK foreign key (numFormation,numTest,numQusetion) references Question,
	constraint choixqPK primary key (numFormation,numTest,numQusetion,numChoixQ)	
);

create Table PasseTest(
	numTest integer,
	numFormation integer,
	matriculeEtud integer,
	noteT integer,
	constraint chknoteT check(noteT>=0 and noteT<=20),
	constraint pstTestFK foreign key (numFormation,numTest) references Test,	
	constraint pstMemFK foreign key (matriculeEtud, numFormation) references MembreFormation,
	constraint pstPK primary key (numFormation,numTest,matriculeEtud)
);

create table ReponseQuestion(
	numTest integer,
	numFormation integer,
	matriculeEtud integer,
	numQusetion integer,
	numChoixQ integer,
	constraint repqQstPK foreign key (numFormation,numTest,numQusetion) references Question,
	constraint repqChoixqPK foreign key (numFormation,numTest,numQusetion,numChoixQ) references ChoixQuestion,
	constraint repqPstPK foreign key (numFormation,numTest,matriculeEtud) references PasseTest,
	constraint repqPK primary key (numFormation,numTest,matriculeEtud)
);

create table Sondage(
	numSondage integer,
	userName varchar2(30),
	description varchar2(500),
	typeParticipant varchar2(20),
	constraint sondPersFK foreign key (userName) references Personne,
	constraint sondPK primary key (numSondage)
);

create table Choix(
	numChoix integer,
	numSondage integer,
	nomChoix varchar2(50),
	constraint choixSondFK foreign key (numSondage) references Sondage,
	constraint choixPK primary key (numSondage, numChoix)	
);

create table Participer(
	numSondage integer,
	userName varchar2(30),
	reponse integer,
	constraint partPersFK foreign key (userName) references Personne,
	constraint partSondFK foreign key (numSondage) references Sondage,
	constraint partPK primary key (numSondage,userName)
);
