begin ;
insert into contribuinte(id,nome,endereco,documento) values(1,'Diego','Rua principal, 100','0');
insert into contribuinte(id,nome,endereco,documento) values(2,'Pedro','Rua das flores, 4545','1');
insert into contribuinte(id,nome,endereco,documento) values(3,'Maria','Praça da estação, 130','20000000000');
insert into contribuinte(id,nome,endereco,documento) values(4,'Roberta','Avenida VII, 1212','30000000000');

insert into usuario(id,nome,sobre_nome,email,senha) values(1,'teste','teste','teste','$2a$10$lKqYc90i1G1v/5/BHKCkVeFkFFhV84C72lwXmHrjIzLMSAGtm8UKm');

insert into lancamento(id,valor,data_emissao,tipo_lancamento,situacao_lancamento,contribuinte_id) values(1,200,'2021-10-10','IPTU','EMITIDO',1);
insert into lancamento(id,valor,data_emissao,tipo_lancamento,situacao_lancamento,contribuinte_id) values(2,300,'2022-10-10','IPTU','EMITIDO',1);
insert into lancamento(id,valor,data_emissao,tipo_lancamento,situacao_lancamento,contribuinte_id) values(3,400,'2023-10-10','IPTU','EMITIDO',1);

insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(1,100,'EMITIDO',1,'2021-10-10');
insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(2,100,'EMITIDO',1,'2021-10-10');
insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(3,150,'EMITIDO',2,'2021-10-10');
insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(4,150,'EMITIDO',2,'2021-10-10');
insert into parcela(id,valor,situacao,lancamento_id,data_vencimento) values(5,400,'EMITIDO',3,'2021-10-10');

insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(1,1,2,'Fortaleza',200,'2021-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(2,1,2,'Maracanau',200,'2021-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(3,1,2,'Maracanau',200,'2021-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(4,1,2,'Maracanau',200,'2021-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(5,1,1,'Maracanau',200,'2021-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(6,2,1,'Maracanau',200,'2022-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(7,2,1,'Maracanau',200,'2022-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(8,2,1,'Maracanau',300,'2022-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(9,2,1,'Maracanau',300,'2022-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(10,2,1,'Maracanau',300,'2023-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(11,3,2,'Maracanau',400,'2023-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(12,3,2,'Maracanau',400,'2023-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(13,3,2,'Maracanau',400,'2023-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(14,3,2,'Maracanau',400,'2023-10-10','CANCELADA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(15,3,2,'Maracanau',400,'2023-10-10','CANCELADA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(16,1,1,'Maracanau',1200,'2020-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(17,2,1,'Maracanau',1200,'2020-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(18,2,1,'Maracanau',1200,'2020-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(19,2,1,'Maracanau',5300,'2020-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(20,2,1,'Maracanau',5300,'2020-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(21,2,1,'Maracanau',5300,'2020-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(22,3,2,'Maracanau',50,'2019-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(23,3,2,'Maracanau',50,'2019-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(24,3,2,'Maracanau',50,'2019-10-10','EMITIDA');
insert into nfse(id,prestador_id,tomador_id,local_prestacao,valor_servico,data_emissao,situacao_nfse) values(25,3,2,'Maracanau',50,'2019-10-10','CANCELADA');
select setval('nfse_id_seq', 25, true);


insert into item_nfse(id,descricao,valor,quantidade,nfse_id) values(1,'Troca de ar comdicionado',200,3,1);
insert into item_nfse(id,descricao,valor,quantidade,nfse_id) values(2,'Manutenção de ar comdicionado',200,3,1);
insert into item_nfse(id,descricao,valor,quantidade,nfse_id) values(3,'Limpeza do carro',100,1,2);
insert into item_nfse(id,descricao,valor,quantidade,nfse_id) values(4,'Troza de oleo',100,1,2);
select setval('item_nfse_id_seq', 4, true);

commit;