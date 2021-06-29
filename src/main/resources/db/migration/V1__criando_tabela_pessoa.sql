
--drop table public.pessoa;

CREATE TABLE public.pessoa
(
  id bigint NOT NULL ,
  nome character varying(100),
  endereco character varying
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.pessoa OWNER TO postgres;
  

CREATE TABLE public.lancamento
(
   id serial primary key,
   tipo character varying(50), 
   valor numeric, 
   data_emissao date, 
   situacao character varying(50)
) 
WITH (
  OIDS = FALSE
);


CREATE TABLE public.contribuinte
(
   id bigint primary key,
   nome character varying(50),  
   endereco character varying(50)
) 
WITH (
  OIDS = FALSE
);


CREATE TABLE public.parcela
(
   id bigint primary key,
   valor numeric, 
   situacao character varying(50)
) 
WITH (
  OIDS = FALSE
);

