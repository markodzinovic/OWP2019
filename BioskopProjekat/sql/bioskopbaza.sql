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
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (2,'Rush Hour','Brett Ratner','Jackie Chan,Chris Tucker','Action, Comedy, Crime',98,'a','USA',1998,'Policajac');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (3,'Catch Me If You Can','Steven Spielberg','Leonardo DiCaprio, Tom Hanks','Biography, Crime, Drama',144,'c','USA',2002,'a');
INSERT INTO film (id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis) VALUES (4,'Ocean s Eleven','Steven Soderbergh','George Clooney, Brad Pitt','Crime, Thriller',116,'d','USA',2001,'Danny Ocean and his ten accomplices plan');


CREATE TABLE korisnik (
    korisnickoIme VARCHAR (20) PRIMARY KEY
                               NOT NULL,
    lozinka       VARCHAR (20) NOT NULL,
    datum         STRING,
    uloga         VARCHAR (20) NOT NULL,
    obrisan       BOOLEAN      DEFAULT (false) 
);

INSERT INTO korisnik (korisnickoIme,lozinka,datum,uloga) VALUES ('a','a','17.01.2020','ADMIN');
INSERT INTO korisnik (korisnickoIme,lozinka,datum,uloga) VALUES ('b','b','18.01.2020','KORISNIK');
INSERT INTO korisnik (korisnickoIme,lozinka,datum,uloga) VALUES ('c','c','15.01.2020','KORISNIK');

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

CREATE TABLE salaTipProjekcije (
    sala          INTEGER REFERENCES sala (id) ON DELETE RESTRICT,
    tipProjekcije INTEGER REFERENCES tipoviProjekcije (id) ON DELETE RESTRICT
                          NOT NULL
);

INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (1,1);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (1,3);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (2,1);
INSERT INTO salaTipProjekcije (sala,tipProjekcije) VALUES (3,3);

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

INSERT INTO projekcije (nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (1,1,1,'29.01.2020',15,'a');
INSERT INTO projekcije (nazivFilma,tipProjekcije,sala,datum,cena,admin) VALUES (2,4,1,'01.02.2020',25,'a');

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
