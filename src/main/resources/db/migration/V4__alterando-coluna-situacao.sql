begin ;
ALTER TABLE lancamento 
ADD COLUMN tipo_lancamento VARCHAR(100);

ALTER TABLE lancamento 
ADD COLUMN situacao_lancamento VARCHAR(100);

ALTER TABLE lancamento DROP COLUMN  situacao;
ALTER TABLE lancamento DROP COLUMN  situacaolancamento;
commit;