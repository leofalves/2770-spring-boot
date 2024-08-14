CREATE SEQUENCE IF NOT EXISTS public.usuarios_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1;

create table usuarios(

    id bigint NOT NULL DEFAULT nextval('usuarios_id_seq'::regclass),
    login varchar(100) not null,
    senha varchar(255) not null,

    primary key(id)

);