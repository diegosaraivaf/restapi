
--drop table public.pessoa;

CREATE TABLE public.pessoa
(
  id bigint NOT NULL ,
  nome character varying(100),
  endereco character varying
);

CREATE TABLE public.lancamento
(
   id serial primary key,
   valor numeric, 
   data_emissao date, 
   situacao character varying(50)
);

CREATE TABLE public.contribuinte
(
   id serial primary key,
   nome character varying(50),  
   endereco character varying(50)
); 

CREATE TABLE public.parcela
(
   id serial primary key,
   valor numeric, 
   situacao character varying(50)
);