ALTER TABLE public.parcela ADD COLUMN  lancamento_id bigint;

ALTER TABLE parcela 
ADD CONSTRAINT fk_lancamento 
FOREIGN KEY (lancamento_id) 
REFERENCES lancamento (id);
