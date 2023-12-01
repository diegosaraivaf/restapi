begin;

	CREATE TABLE public.imovel
	(
	   id serial primary key,
	   proprietario_id int
	); 
	ALTER TABLE public.imovel ADD CONSTRAINT fk_proprietario FOREIGN KEY (proprietario_id) REFERENCES contribuinte (id);
	
	CREATE TABLE public.caracteristica
	(
	   id serial primary key,
	   nome varchar,
	   tipo_caracteristica varchar
	); 
	
	CREATE TABLE public.opcao_caracteristica
	(
	   id serial primary key,
	   nome varchar, 
	   caracteristica_id int
	); 
	ALTER TABLE public.opcao_caracteristica ADD CONSTRAINT fk_caracteristica_id FOREIGN KEY (caracteristica_id) REFERENCES caracteristica (id);

commit;

