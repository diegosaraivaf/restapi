
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
ALTER TABLE public.pessoa
  OWNER TO postgres;
