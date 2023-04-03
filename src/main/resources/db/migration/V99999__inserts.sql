begin ;
insert into contribuinte(id,nome,endereco,documento) values(1,'Diego','Rua principal, 100','00000000000');
insert into contribuinte(id,nome,endereco,documento) values(2,'Pedro','Rua das flores, 4545','10000000000');
insert into contribuinte(id,nome,endereco,documento) values(3,'Maria','Praça da estação, 130','20000000000');
insert into contribuinte(id,nome,endereco,documento) values(4,'Roberta','Avenida VII, 1212','30000000000');

insert into lancamento(id,valor,data_emissao,tipo_lancamento,situacao_lancamento,contribuinte_id) values(1,200,'2021-10-10','IPTU','EMITIDO',1);
insert into lancamento(id,valor,data_emissao,tipo_lancamento,situacao_lancamento,contribuinte_id) values(2,300,'2022-10-10','IPTU','EMITIDO',1);
insert into lancamento(id,valor,data_emissao,tipo_lancamento,situacao_lancamento,contribuinte_id) values(3,400,'2023-10-10','IPTU','EMITIDO',1);

insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(1,100,'EMITIDO',1,'2021-10-10');
insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(2,100,'EMITIDO',1,'2021-10-10');
insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(3,150,'EMITIDO',2,'2021-10-10');
insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(4,150,'EMITIDO',2,'2021-10-10');
insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(5,400,'EMITIDO',3,'2021-10-10');

insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico) values(1,1,2,'Fortaleza',200);
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico) values(2,1,2,'Maracanau',400);

insert into item_nfse(id,descricao,valor,quantidade,nfse_id) values(1,'Troca de ar comdicionado',200,3,1);
insert into item_nfse(id,descricao,valor,quantidade,nfse_id) values(2,'Manutenção de ar comdicionado',200,3,1);
insert into item_nfse(id,descricao,valor,quantidade,nfse_id) values(3,'Limpeza do carro',100,1,2);
insert into item_nfse(id,descricao,valor,quantidade,nfse_id) values(4,'Troza de oleo',100,1,2);

commit;