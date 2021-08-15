begin ;
	
CREATE SEQUENCE public.contribuinte_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.contribuinte_id_seq
  OWNER TO postgres;
  
ALTER TABLE public.contribuinte ALTER COLUMN id SET DEFAULT nextval('contribuinte_id_seq'::regclass);

commit;