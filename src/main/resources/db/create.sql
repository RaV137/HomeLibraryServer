-- DROP DATABASE IF EXISTS home_library_server CASCADE;

-- CREATE DATABASE home_library_server;
-- CREATE USER hls_admin WITH ENCRYPTED PASSWORD 'HlsAdmin1';
-- GRANT ALL PRIVILEGES ON DATABASE home_library_server TO hls_admin;

-- DROP SCHEMA IF EXISTS hls CASCADE;

-- CREATE SCHEMA hls;
-- ALTER SCHEMA hls OWNER TO postgres;

-- SET search_path TO hls;

-- tworzenie tabel

DROP TABLE IF EXISTS books CASCADE;

CREATE TABLE hls.books (
	id integer NOT NULL DEFAULT NEXTVAL(('seq_books'::text)::regclass), -- unikalny identyfikator
	title varchar(255) NOT NULL, -- tytuł pozycji
	author varchar(255) NOT NULL, -- autor pozycji
	id_user integer NOT NULL, -- identyfikator użytkownika
	id_room integer NOT NULL, -- identyfikator pokoju
	shelf integer, -- numer półki
	rating integer, -- subiektywna ocena książki użytkownika
	favourite boolean NOT NULL DEFAULT false, -- czy użytkownik dodał do ulubionych
	is_read boolean NOT NULL DEFAULT false, -- czy użytkownik zaznaczył, że ją przeczytał
	thumbnail text, -- url obrazka pobranego z Google Books, wyświetlany w liście książek
	id_google_books_api varchar(255) NOT NULL -- id książki z Google Books
);

DROP TABLE IF EXISTS rooms CASCADE;

CREATE TABLE hls.rooms (
	id integer NOT NULL DEFAULT NEXTVAL(('seq_rooms'::text)::regclass), -- unikalny identyfikator
	id_user integer NOT NULL, -- identyfikator użytkownika
	name varchar(255) NOT NULL, -- nazwa pokoju
	short_name varchar(3) NOT NULL, -- skrót nazwy pokoju
	color varchar(7) NOT NULL -- kolor przypisany do pokoju (postać: "#xxxxxx", gdzie x to znak z zakresu 0-9 i a-f)
);

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE hls.users (
	id integer NOT NULL DEFAULT NEXTVAL(('seq_users'::text)::regclass), -- unikalny identyfikator
	login varchar(50) NOT NULL, -- unikalny login
	password varchar(32) NOT NULL, -- hasło uzytkownika (domyślnie kodowane w md5 - możliwa zmiana na bcrypt)
	email varchar(255) NOT NULL, -- email (potrzebny w przypadku resetu hasła/wykupienia premium)
	has_premium boolean NOT NULL DEFAULT false -- czy użytkownik posiada wykupione premium
);

-- dodawanie PK, FK, indeksów itp

-- pk
ALTER TABLE books ADD CONSTRAINT PK_books PRIMARY KEY (id);
ALTER TABLE rooms ADD CONSTRAINT PK_rooms PRIMARY KEY (id);
ALTER TABLE users ADD CONSTRAINT PK_users PRIMARY KEY (id);

-- fk
ALTER TABLE rooms ADD CONSTRAINT FK_rooms_users
	FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE cascade ON UPDATE no action;

ALTER TABLE books ADD CONSTRAINT FK_books_users
	FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE cascade ON UPDATE no action;

ALTER TABLE books ADD CONSTRAINT FK_books_rooms
	FOREIGN KEY (id_room) REFERENCES rooms(id) ON DELETE no action ON UPDATE no action;

-- unikalność pól
ALTER TABLE users ADD CONSTRAINT UQ_login UNIQUE (login);
ALTER TABLE users ADD CONSTRAINT UQ_email UNIQUE (email);
ALTER TABLE books ADD CONSTRAINT UQ_books UNIQUE (id_google_books_api, id_user, id_room);

-- tworzenie sekwencji
DROP SEQUENCE IF EXISTS seq_positions;
DROP SEQUENCE IF EXISTS seq_rooms;
DROP SEQUENCE IF EXISTS seq_users;

CREATE SEQUENCE seq_books INCREMENT 1 START 1;
CREATE SEQUENCE seq_rooms INCREMENT 1 START 1;
CREATE SEQUENCE seq_users INCREMENT 1 START 1;
