begin ;
CREATE SEQUENCE public.parcela_id_seq START 1;

ALTER TABLE public.parcela ALTER COLUMN id SET DEFAULT nextval('parcela_id_seq'::regclass);
commit;