CREATE TABLE file(
    id  bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name character varying
);

ALTER TABLE question ADD COLUMN file_id bigint;