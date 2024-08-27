CREATE SEQUENCE IF NOT EXISTS public.consultas_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1;

create table consultas(

    id bigint NOT NULL DEFAULT nextval('consultas_id_seq'::regclass),
    medico_id bigint not null,
    paciente_id bigint not null,
    data date not null,

    primary key(id),
    foreign key (medico_id) references medicos(id),
    foreign key (paciente_id) references pacientes(id)

);