--bioskopbaza

CREATE TABLE film (
    id            INTEGER      PRIMARY KEY AUTOINCREMENT
                               NOT NULL,
    naziv         VARCHAR (40) NOT NULL,
    reziser       VARCHAR (20) NOT NULL,
    glumci        VARCHAR (40) NOT NULL,
    zanrovi       VARCHAR (30),
    trajanje      INT          NOT NULL,
    distributer   VARCHAR (20) NOT NULL,
    zemljaPorekla VARCHAR (20) NOT NULL,
    godina        INT          NOT NULL,
    opis          VARCHAR (50) NOT NULL,
    obrisan       BOOLEAN      DEFAULT (false) 
                               NOT NULL
);

INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (1,'Wolf of wall street','Martin Scorsese','Leonardo DiCaprio, Jonah Hill','Biography, Crime, Drama',180,'a','USA',2013,'Based on the true story of Jordan Belfort');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (2,'Catch Me If You Can','Steven Spielberg','Leonardo DiCaprio, Tom Hanks','Biography, Crime, Drama',144,'a','USA',2002,'Falsifikator');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (3,'Rush Hour','Brett Ratner','Jackie Chan,Chris Tucker','Action, Comedy, Crime',98,'b','USA',1998,'Policajac');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (4,'Rush Hour 2','Brett Ratner','Jackie Chan,Chris Tucker','Action, Comedy, Crime',90,'b','USA',2001,'Policajac2');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (5,'Rush Hour 3','Brett Ratner','Jackie Chan,Chris Tucker','Action, Comedy, Crime',91,'b','USA',2007,'Policajac3');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (6,'Ocean s Eleven','Steven Soderbergh','George Clooney, Brad Pitt','Crime, Thriller',116,'d','USA',2001,'Danny Ocean and his ten accomplices plan');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (7,'Oceans Twelve','Steven Soderbergh','George Clooney, Brad Pitt','Crime, Thriller',125,'d','USA',2004,'Daniel Ocean recruits one more team member');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (8,'Oceans Thirteen','Steven Soderbergh','George Clooney, Brad Pitt','Crime, Thriller',122,'d','USA',2007,'Danny Ocean rounds up the boys for a third heist');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (9,'21 Jump Street','Phil Lord','Jonah Hill, Channing Tatum','Action, Comedy, Crime',109,'f','USA',2012,'A pair of underachieving cops are sent back');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (10,'22 Jump Street','Phil Lord','Jonah Hill, Channing Tatum','Action, Comedy, Crime',112,'f','USA',2014,'After making their way through high school');


CREATE TABLE korisnik (
    korisnickoIme VARCHAR (20) PRIMARY KEY
                               NOT NULL,
    lozinka       VARCHAR (20) NOT NULL,
    datum         STRING,
    uloga         VARCHAR (20) NOT NULL,
    obrisan       BOOLEAN      DEFAULT (false) 
);

INSERT INTO korisnik (korisnickoIme,lozinka,datum,uloga) VALUES ('a','a','2020/02/01 21:35','ADMIN');
INSERT INTO korisnik (korisnickoIme,lozinka,datum,uloga) VALUES ('b','b','2020/02/01 21:35','KORISNIK');
INSERT INTO korisnik (korisnickoIme,lozinka,datum,uloga) VALUES ('c','c','2020/02/01 21:35','KORISNIK');

CREATE TABLE tipoviProjekcije (
    id    INTEGER     PRIMARY KEY AUTOINCREMENT
                      NOT NULL,
    naziv VARCHAR (3) NOT NULL
);

INSERT INTO tipoviProjekcije (id,naziv) VALUES (1,'2D');
INSERT INTO tipoviProjekcije (id,naziv) VALUES (2,'3D');
INSERT INTO tipoviProjekcije (id,naziv) VALUES (3,'4D');

CREATE TABLE sala (
    id    INTEGER      PRIMARY KEY AUTOINCREMENT
                       NOT NULL,
    naziv VARCHAR (20) NOT NULL
);

INSERT INTO sala (id,naziv) VALUES (1,'sala1');
INSERT INTO sala (id,naziv) VALUES (2,'sala2');
INSERT INTO sala (id,naziv) VALUES (3,'sala3');
INSERT INTO sala (id,naziv) VALUES (4,'sala4');
INSERT INTO sala (id,naziv) VALUES (5,'sala5');

CREATE TABLE salaTipProjekcije (
    sala          INTEGER REFERENCES sala (id) ON DELETE RESTRICT,
    tipProjekcije INTEGER REFERENCES tipoviProjekcije (id) ON DELETE RESTRICT
                          NOT NULL
);

INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (1,1);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (1,3);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (2,1);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (3,3);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (4,2);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (4,3);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (5,1);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (5,2);

CREATE TABLE projekcije (
    id            INTEGER      PRIMARY KEY AUTOINCREMENT
                               NOT NULL,
    nazivFilma    INT          REFERENCES film (id) ON DELETE RESTRICT
                               NOT NULL,
    tipProjekcije INTEGER      REFERENCES tipoviProjekcije (id) ON DELETE RESTRICT
                               NOT NULL,
    sala          INTEGER      REFERENCES sala (id) ON DELETE RESTRICT
                               NOT NULL,
    datum         STRING       NOT NULL,
    cena          DOUBLE       NOT NULL,
    admin         VARCHAR (25) REFERENCES korisnik (korisnickoIme) ON DELETE RESTRICT
                               NOT NULL,
    obrisan       BOOLEAN      DEFAULT (false) 
                               NOT NULL
);

INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (1,1,1,1,'2020-02-10 15:00',15,'a');
INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (2,4,3,1,'2020-02-10 19:00',25,'a');
INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (3,5,1,2,'2020-02-10 12:00',25,'a');
INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (4,2,1,2,'2020-02-10 22:30',25,'a');

INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (5,6,3,3,'2020-02-11 14:00',15,'a');
INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (6,6,3,4,'2020-02-11 18:00',25,'a');
INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (7,7,3,3,'2020-02-11 11:00',25,'a');
INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (8,7,2,4,'2020-02-11 21:30',25,'a');

INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (9,9,3,1,'2020-02-12 17:00',15,'a');
INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (10,10,2,5,'2020-02-12 19:00',25,'a');
INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (11,1,1,5,'2020-02-12 14:00',25,'a');
INSERT INTO projekcije (id,nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (12,4,3,1,'2020-02-12 20:00',25,'a');

CREATE TABLE sedista (
    redniBroj INTEGER NOT NULL,
    sala      INTEGER REFERENCES sala (id) ON DELETE RESTRICT
                      NOT NULL
);

INSERT INTO sedista (redniBroj,sala) VALUES (15,1);
INSERT INTO sedista (redniBroj,sala) VALUES (8,2);
INSERT INTO sedista (redniBroj,sala) VALUES (10,3);
INSERT INTO sedista (redniBroj,sala) VALUES (20,4);
INSERT INTO sedista (redniBroj,sala) VALUES (5,5);

CREATE TABLE karte (
    id         INTEGER      PRIMARY KEY AUTOINCREMENT
                            NOT NULL,
    projekcija INTEGER      REFERENCES projekcije (id) ON DELETE RESTRICT
                            NOT NULL,
    sediste    INTEGER      NOT NULL,
    datum      STRING       NOT NULL,
    korisnik   VARCHAR (30) REFERENCES korisnik (korisnickoIme) ON DELETE RESTRICT
                            NOT NULL
);

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (1,1,1,'2020/02/05 21:35','b');
INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (2,1,2,'2020/02/05 21:35','b');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (3,2,1,'2020/02/05 21:35','c');
INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (4,2,2,'2020/02/05 21:35','c');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (5,3,1,'2020/02/05 21:35','b');
INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (6,3,2,'2020/02/05 21:35','c');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (7,4,1,'2020/02/05 21:35','c');
INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (8,4,2,'2020/02/05 21:35','b');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (9,5,1,'2020/02/05 21:35','b');
INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (10,5,2,'2020/02/05 21:35','c');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (11,6,2,'2020/02/05 21:35','a');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (12,7,1,'2020/02/05 21:35','b');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (13,8,2,'2020/02/05 21:35','c');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (14,9,1,'2020/02/05 21:35','b');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (15,10,2,'2020/02/05 21:35','a');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (16,11,1,'2020/02/05 21:35','c');

INSERT INTO karte (id,projekcija,sediste,datum,korisnik) VALUES (17,12,2,'2020/02/05 21:35','b');