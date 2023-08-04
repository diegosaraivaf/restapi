begin;
CREATE TABLE public.nfse
(
   id serial primary key,
   prestador_id int, 
   tomador_id int, 
   local_prestacao character varying(80), 
   valor_servico numeric(12,2),
   lista_items character varying(80),
   data_emissao date,
   situacao_nfse varchar(20)
); 


ALTER TABLE public.nfse ADD CONSTRAINT fk_tomador FOREIGN KEY (tomador_id) REFERENCES contribuinte (id);
ALTER TABLE public.nfse ADD CONSTRAINT fk_prestador FOREIGN KEY (prestador_id) REFERENCES contribuinte (id);

commit;