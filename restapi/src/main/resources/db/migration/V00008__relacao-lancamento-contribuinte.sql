begin ;
ALTER TABLE public.lancamento ADD contribuinte_id bigint NULL;
ALTER TABLE public.lancamento ADD CONSTRAINT lancamento_contribuinte_fk FOREIGN KEY (contribuinte_id) REFERENCES contribuinte(id);
commit;