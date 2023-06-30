CREATE TABLE public.nfse
(
   id serial primary key,
   prestador_id int, 
   tomador_id int, 
   local_prestacao character varying(80), 
   valor_servico numeric(12,2),
   lista_items character varying(80),
   data_emissao date
) 
