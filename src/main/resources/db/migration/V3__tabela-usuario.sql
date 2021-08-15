CREATE TABLE public.usuario
(
   id serial primary key,
   nome character varying(80), 
   sobre_nome character varying(80), 
   email character varying(80), 
   senha character varying(50)
) 
WITH (
  OIDS = FALSE
);