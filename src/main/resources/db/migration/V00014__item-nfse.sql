begin;

CREATE TABLE item_nfse
(
  id serial primary key,
  descricao character varying(255),
  valor numeric,
  quantidade int,
  nfse_id bigint
);

ALTER TABLE item_nfse ADD CONSTRAINT fk_nfse FOREIGN KEY (nfse_id) REFERENCES nfse (id);

commit;