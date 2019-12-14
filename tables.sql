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
	constraint ensPersFK foreign key (userName) references Personne On DELETE CASCADE,
	constraint ensPK primary key (matriculeEns)
);

create table Etudiant(
	matriculeEtud integer,
	userName varchar2(30),
	specialite varchar2(20),
	anneeCour varchar2(5),
	constraint etudPersFK foreign key (userName) references Personne On DELETE CASCADE,
	constraint etudPK primary key (matriculeEtud)
);

create table Formation(
	numFormation integer,
	nomFormation varchar2(30),
	matriculeEns integer,
	description varchar2(150),
	constraint formEnsFK foreign key (matriculeEns) references Enseignant ON DELETE SET NULL,
	constraint formPK primary key (numFormation)
);

create table MembreFormation(
	numFormation integer,
	matriculeEtud integer,
	constraint memFormFK foreign key (numFormation) references Formation ON DELETE CASCADE,
	constraint memEtudFK foreign key (matriculeEtud) references Etudiant ON DELETE CASCADE,
	constraint memPK primary key (matriculeEtud, numFormation)
);

create table Historique(
	numAction integer,
	numFormation integer,
	matriculeEtud integer,
	action varchar2(100),
	constraint hstMemFK foreign key (matriculeEtud, numFormation) references MembreFormation ON DELETE CASCADE,
	constraint hstPK primary key (numFormation,matriculeEtud,numAction)
);

create table Cour(
	numFormation integer,
	nomCour varchar2(30),
	pathContenue varchar2(200),
	constraint courFormFK foreign key (numFormation) references Formation ON DELETE CASCADE,
	constraint courPK primary key (numFormation,nomCour)
);

create table Devoir(
	numDevoir integer,
	numFormation integer,
	enoncer varchar2(1000),
	constraint devFormFK foreign key (numFormation) references Formation ON DELETE CASCADE,
	constraint devPK primary key (numFormation,numDevoir)
);

create table PasseDevoir(
	numDevoir integer,
	numFormation integer,
	matriculeEtud integer,
	pathReponseD varchar2(200),
	noteD integer,
	constraint chknoteD check(noteD>=0 and noteD<=20),
	constraint psdDevFK foreign key (numFormation,numDevoir) references Devoir ON DELETE CASCADE,
	constraint psdMemFK foreign key (matriculeEtud, numFormation) references MembreFormation ON DELETE CASCADE,
	constraint psdPK primary key (numFormation,numDevoir,matriculeEtud)
);

create table Test(
	numTest integer,
	numFormation integer,
	isDisponible integer,
	constraint chkDispTest check(isDisponible>=0 and isDisponible<=1),
	constraint testFormFK foreign key (numFormation) references Formation ON DELETE CASCADE,
	constraint testPK primary key (numFormation,numTest)
);

create table Question(
	numFormation integer,
	numTest integer,
	numQusetion integer,
	enoncerQuestion varchar2(200),
	validerQuestion integer check (validerQuestion<=1 and validerQuestion>=0) Default 0,
	constraint qstTestFK foreign key (numFormation,numTest) references Test ON DELETE CASCADE,
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
	constraint choixqQstPK foreign key (numFormation,numTest,numQusetion) references Question ON DELETE CASCADE,
	constraint choixqPK primary key (numFormation,numTest,numQusetion,numChoixQ)
);

create Table PasseTest(
	numTest integer,
	numFormation integer,
	matriculeEtud integer,
	noteT integer,
	constraint chknoteT check(noteT>=0 and noteT<=20),
	constraint pstTestFK foreign key (numFormation,numTest) references Test ON DELETE CASCADE,
	constraint pstMemFK foreign key (matriculeEtud, numFormation) references MembreFormation ON DELETE CASCADE,
	constraint pstPK primary key (numFormation,numTest,matriculeEtud)
);

create table ReponseQuestion(
	numTest integer,
	numFormation integer,
	matriculeEtud integer,
	numQusetion integer,
	numChoixQ integer,
	autreChoix1 integer,
	autreChoix2 integer,
	autreChoix3 integer,
	constraint repqQstPK foreign key (numFormation,numTest,numQusetion) references Question ON DELETE CASCADE,
	constraint repqChoixqPK foreign key (numFormation,numTest,numQusetion,numChoixQ) references ChoixQuestion ON DELETE CASCADE,
	constraint repqChoixqPK foreign key (numFormation,numTest,numQusetion,autreChoix1) references ChoixQuestion ON DELETE SET NULL,
	constraint repqChoixqPK foreign key (numFormation,numTest,numQusetion,autreChoix2) references ChoixQuestion ON DELETE SET NULL,
	constraint repqChoixqPK foreign key (numFormation,numTest,numQusetion,autreChoix3) references ChoixQuestion ON DELETE SET NULL,
	constraint repqPstPK foreign key (numFormation,numTest,matriculeEtud) references PasseTest ON DELETE CASCADE,
	constraint repqPK primary key (numFormation,numTest,matriculeEtud, numQusetion)
);

create table Sondage(
	numSondage integer,
	userName varchar2(30),
	description varchar2(500),
	typeParticipant varchar2(20),
	constraint sondPersFK foreign key (userName) references Personne ON DELETE SET NULL,
	constraint sondPK primary key (numSondage)
);

create table Choix(
	numChoix integer,
	numSondage integer,
	nomChoix varchar2(50),
	constraint choixSondFK foreign key (numSondage) references Sondage ON DELETE CASCADE,
	constraint choixPK primary key (numSondage, numChoix)
);

create table Participer(
	numSondage integer,
	userName varchar2(30),
	reponse integer,
	constraint partPersFK foreign key (userName) references Personne ON DELETE SET NULL,
	constraint partSondFK foreign key (numSondage) references Sondage,
	constraint partPK primary key (numSondage,userName)
);


create table Wiki(
	numWiki integer,
	nomWiki varchar2(50),
	userNameCreateur varchar2(30),
	pathImageWiki varchar2(200),
);

create table SectionWiki(
	numWiki integer,
	numSection integer,
	nomSection varchar2(50),
	pathContenueSection varchar2(200),
	pathImageSection varchar2(200),
);

create table ModifWiki(
	numWiki integer,
	numModif integer,
	userNameModif varchar2(30),
	/*date and time of the modif*/
	/*the modif*/
	constraint modWikiFK foreign Key (numWiki) references Wiki ON DELETE CASCADE,
	constraint modPerFK foreign Key (userNameModif) references Personne ON DELETE SET NULL,
);

create table Chat(
	expediteur varchar2(30),
	recepteur varchar2(30),
	message varchar2(100),
	/*date and time when the message has been send*/
	constraint chatPerFK foreign Key (expediteur) references Personne ON DELETE SET NULL,
	constraint chatPerFK2 foreign Key (recepteur) references Personne ON DELETE SET NULL,
);

create table Forum(
	numForum integer,
	nomForum varchar2(40),
);
