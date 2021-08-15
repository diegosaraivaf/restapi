begin ;
ALTER TABLE public.contribuinte ADD COLUMN documento character varying NOT NULL;
commit