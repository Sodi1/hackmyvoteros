CREATE TABLE IF NOT EXISTS question (
    id  bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    external_id BIGINT,
    name character varying
);

CREATE TABLE IF NOT EXISTS answer (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    external_id BIGINT,
    count_answer INT,
    cluster character varying,
    sentiment character varying,
    answer character varying
);
