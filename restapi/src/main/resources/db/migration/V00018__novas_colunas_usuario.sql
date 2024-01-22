begin ;
ALTER TABLE public.usuario ADD codigo_recuperacao_senha varchar NULL;
ALTER TABLE public.usuario ADD data_geracao_codigo_recuperacao_senha timestamp NULL;
commit;