CREATE SEQUENCE IF NOT EXISTS public.medicos_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1;

create table medicos(

    id bigint NOT NULL DEFAULT nextval('medicos_id_seq'::regclass),
    nome varchar(100) not null,
    email varchar(100) not null unique,
    crm varchar(6) not null unique,
    especialidade varchar(100) not null,
    logradouro varchar(100) not null,
    bairro varchar(100) not null,
    cep varchar(9) not null,
    complemento varchar(100),
    numero varchar(20),
    uf char(2) not null,
    cidade varchar(100) not null,

    primary key(id)

);