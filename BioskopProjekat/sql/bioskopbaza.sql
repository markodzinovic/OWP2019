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

CREATE TABLE korisnik (
    korisnickoIme VARCHAR (20) PRIMARY KEY
                               NOT NULL,
    lozinka       VARCHAR (20) NOT NULL,
    datum         STRING,
    uloga         VARCHAR (20) NOT NULL,
    obrisan       BOOLEAN      DEFAULT (false) 
                               NOT NULL
);
