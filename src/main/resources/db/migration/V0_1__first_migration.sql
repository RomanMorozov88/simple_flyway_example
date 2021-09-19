CREATE TABLE packs (
    id SERIAL NOT NULL PRIMARY KEY,
	name character varying(250) not NULL
);

CREATE TABLE blocks (
    id SERIAL NOT NULL PRIMARY KEY,
    id_pack integer NOT NULL REFERENCES packs (id) 
    ON UPDATE RESTRICT ON DELETE CASCADE,
	name character varying(250),
	type_code integer not NULL
);

CREATE TABLE local_date_blocks (
    id integer NOT NULL PRIMARY KEY REFERENCES blocks (id) 
    ON UPDATE RESTRICT ON DELETE CASCADE,
    first_date date not NULL,
    second_date date not NULL
);

CREATE TABLE text_blocks (
    id integer NOT NULL PRIMARY KEY REFERENCES blocks (id) 
    ON UPDATE RESTRICT ON DELETE CASCADE,
    text character varying(100) not NULL
);
