begin;
CREATE SEQUENCE endereco_id_seq AS bigint START 1 OWNED BY endereco.id;
ALTER TABLE endereco ALTER COLUMN id SET DEFAULT nextval('endereco_id_seq');
commit;