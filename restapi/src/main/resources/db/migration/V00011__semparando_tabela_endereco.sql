begin ;
	
CREATE TABLE IF NOT EXISTS public.endereco
(	
	id serial primary key,
    rua character varying(255),
    bairro character varying ,
    numero character varying ,
    cep character varying ,
	contribuinte_id bigint ,
	CONSTRAINT endereco_contribuinte_fk FOREIGN KEY(contribuinte_id) REFERENCES contribuinte(id)
);

commit;