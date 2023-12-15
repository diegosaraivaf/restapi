begin;

CREATE TABLE public.imovel_imagens (
	id serial4 ,
	imovel_id serial4 ,
	identificador varchar ,
	nome_arquivo varchar ,
	CONSTRAINT imovel_imagens_pk PRIMARY KEY (id),
	CONSTRAINT imovel_imagens_fk FOREIGN KEY (imovel_id) REFERENCES public.imovel(id)
);

commit;