DROP DATABASE IF EXISTS home_library_server CASCADE;

CREATE DATABASE home_library_server;
CREATE USER hls_admin WITH ENCRYPTED PASSWORD 'HlsAdmin1';
GRANT ALL PRIVILEGES ON DATABASE home_library_server TO hls_admin;

DROP SCHEMA IF EXISTS home_library_server CASCADE;

CREATE SCHEMA hls;
ALTER SCHEMA hls OWNER TO postgres;

SET search_path TO home_library, "$user", public;

-- tworzenie tabel

-- DROP TABLE IF EXISTS positions CASCADE;

CREATE TABLE positions (
	id integer NOT NULL DEFAULT NEXTVAL(('seq_positions'::text)::regclass), -- unikalny identyfikator
	title varchar(255) NOT NULL, -- tytuł pozycji
	id_author integer NOT NULL, -- identyfikator autora pozycji
	id_publisher integer NOT NULL, -- identyfikator wydawnictwa
	volume integer NULL, -- numer tomu (jeśli wydawany w wielu tomach)
	series_number integer NULL, -- numer w serii (jeśli należy do jakiejś serii)
	series_name varchar(255) NULL, -- nazwa serii (jeśli należy do jakiejś serii)
	ISBN varchar(13) NOT NULL, -- ISBN pozycji
	year_published integer NOT NULL, -- rok wydania
--	cover bytea NULL, -- okładka książki (TODO: sprawdzić, czy zamiast bytea nie użyć varchar)
	creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP -- czas utworzenia
);

-- DROP TABLE IF EXISTS publishers CASCADE;

CREATE TABLE publishers (
	id integer NOT NULL DEFAULT NEXTVAL(('seq_publishers'::text)::regclass), -- unikalny identyfikator
	name varchar(255) NOT NULL, -- nazwa wydawnictwa
	creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP -- czas utworzenia
);

-- DROP TABLE IF EXISTS authors CASCADE;

CREATE TABLE authors (
	id integer NOT NULL DEFAULT NEXTVAL(('seq_authors'::text)::regclass), -- unikalny identyfikator
	name varchar(255) NOT NULL, -- imię
	surname varchar(255) NOT NULL, -- nazwisko
	alias varchar(255) NULL, -- alias
	creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP -- czas utworzenia
);

-- DROP TABLE IF EXISTS rooms CASCADE;

CREATE TABLE rooms (
	id integer NOT NULL DEFAULT NEXTVAL(('seq_rooms'::text)::regclass), -- unikalny identyfikator
	id_user integer NOT NULL, -- identyfikator użytkownika
	name varchar(255) NOT NULL, -- nazwa pokoju
	color varchar(7) NOT NULL, -- color przypisany do pokoju (postać: "#xxxxxx", gdzie x to znak z zakresu 0-9 i a-f)
	creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP -- czas utworzenia
);

-- DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
	id integer NOT NULL DEFAULT NEXTVAL(('seq_users'::text)::regclass), -- unikalny identyfikator
	login varchar(50) NOT NULL, -- unikalny login
	password varchar(32) NOT NULL, -- hasło uzytkownika
	password_method varchar(10) NOT NULL DEFAULT 'MD5', -- metoda kodowanie hasła
	email varchar(255) NOT NULL, -- email (potrzebny w przypadku resetu hasła/wykupienia premium)
	name varchar(30) NULL, -- imię
	surname varchar(50) NULL, -- nazwisko
	has_premium boolean NOT NULL DEFAULT false, -- czy użytkownik posiada wykupione premium (TODO: zastanowić się, czy powinno być czasowe - dodać czas wygaśnięcia)
	creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP -- czas utworzenia
);

-- DROP TABLE IF EXISTS binds CASCADE;

CREATE TABLE binds (
	id integer NOT NULL DEFAULT NEXTVAL(('seq_binds'::text)::regclass), -- unikalny identyfikator
	id_position integer NOT NULL, -- identyfikator pozycji
	id_user integer NOT NULL, -- identyfikator użytkownika
	id_room integer NOT NULL, -- identyfikator pokoju
	user_comment varchar(1000) NULL, -- dowolny komentarz użytkownika (max 1000 znaków)
	score real NULL, -- ocena książki wg użytkownika
	is_read boolean NOT NULL DEFAULT false, -- czy książka została już przeczytana
	creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP -- czas utworzenia
);

-- dodawanie PK, FK, indeksów itp

-- pk
ALTER TABLE positions ADD CONSTRAINT PK_positions PRIMARY KEY (id);
ALTER TABLE publishers ADD CONSTRAINT PK_publishers PRIMARY KEY (id);
ALTER TABLE authors ADD CONSTRAINT PK_authors PRIMARY KEY (id);
ALTER TABLE rooms ADD CONSTRAINT PK_rooms PRIMARY KEY (id);
ALTER TABLE users ADD CONSTRAINT PK_users PRIMARY KEY (id);
ALTER TABLE binds ADD CONSTRAINT PK_binds PRIMARY KEY (id);

-- indeksy
CREATE INDEX IXFK_positions_authors ON positions (id_author ASC);
CREATE INDEX IXFK_positions_publishers ON positions (id_publisher ASC);
CREATE INDEX IXFK_rooms_users ON rooms (id_user ASC);
CREATE INDEX IXFK_binds_positions ON binds (id_position ASC);
CREATE INDEX IXFK_binds_users ON binds (id_user ASC);
CREATE INDEX IXFK_binds_rooms ON binds (id_room ASC);

-- fk
ALTER TABLE positions ADD CONSTRAINT FK_positions_authors
	FOREIGN KEY (id_author) REFERENCES authors(id) ON DELETE restrict ON UPDATE no action;

ALTER TABLE positions ADD CONSTRAINT FK_positions_publishers
	FOREIGN KEY (id_publisher) REFERENCES publishers(id) ON DELETE restrict ON UPDATE no action;

ALTER TABLE rooms ADD CONSTRAINT FK_rooms_users
	FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE cascade ON UPDATE no action;

ALTER TABLE binds ADD CONSTRAINT FK_binds_positions
	FOREIGN KEY (id_position) REFERENCES positions(id) ON DELETE cascade ON UPDATE no action;

ALTER TABLE binds ADD CONSTRAINT FK_binds_users
	FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE cascade ON UPDATE no action;

ALTER TABLE binds ADD CONSTRAINT FK_binds_rooms
	FOREIGN KEY (id_room) REFERENCES rooms(id) ON DELETE no action ON UPDATE no action;

-- unikalność pól
ALTER TABLE users ADD CONSTRAINT UQ_login UNIQUE (login);
ALTER TABLE users ADD CONSTRAINT UQ_email UNIQUE (email);
ALTER TABLE binds ADD CONSTRAINT UQ_binds UNIQUE (id_position, id_user, id_room);
ALTER TABLE authors ADD CONSTRAINT UQ_alias UNIQUE (alias);
ALTER TABLE positions ADD CONSTRAINT UQ_isbn UNIQUE (ISBN);

-- tworzenie sekwencji
-- DROP SEQUENCE IF EXISTS seq_positions;
-- DROP SEQUENCE IF EXISTS seq_publishers;
-- DROP SEQUENCE IF EXISTS seq_authors;
-- DROP SEQUENCE IF EXISTS seq_rooms;
-- DROP SEQUENCE IF EXISTS seq_users;
-- DROP SEQUENCE IF EXISTS seq_binds;

CREATE SEQUENCE seq_positions INCREMENT 1 START 1;
CREATE SEQUENCE seq_publishers INCREMENT 1 START 1;
CREATE SEQUENCE seq_authors INCREMENT 1 START 1;
CREATE SEQUENCE seq_rooms INCREMENT 1 START 1;
CREATE SEQUENCE seq_users INCREMENT 1 START 1;
CREATE SEQUENCE seq_binds INCREMENT 1 START 1;

-- tworzenie widoków

-- widok z tytułami i ocenami -- TODO: sprawdzić, czy jest to potrzebne
DROP VIEW position_avg_score;

CREATE VIEW position_avg_score AS
	SELECT p.id, p.title, AVG(b.score)
	FROM positions p LEFT JOIN binds b ON b.id_position = p.id
	WHERE b.score IS NOT NULL
	GROUP BY (p.id)
	ORDER BY AVG(b.score) DESC;