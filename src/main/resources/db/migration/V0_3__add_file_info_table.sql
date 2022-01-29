CREATE TABLE file_infos (
    id SERIAL NOT NULL PRIMARY KEY,
	name character varying(250) not NULL,
	first_file_path character varying(250),
	second_file_path character varying(250)
);