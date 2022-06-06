begin ;
	
CREATE TABLE IF NOT EXISTS public.endereco
(	
	id bigint NOT NULL,
    rua character varying(255),
    bairro character varying ,
    numero character varying ,
    cep character varying ,
	contribuinte_id bigint ,
    CONSTRAINT endereco_pkey PRIMARY KEY (id),
	CONSTRAINT endereco_contribuinte_fk
      FOREIGN KEY(contribuinte_id) 
	  REFERENCES contribuinte(id)
);

commit;