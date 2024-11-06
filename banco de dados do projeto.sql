use projetofarmacia;

desc medicamento;
desc classificacao;
desc fornecedor;
desc lote;
desc marca;
desc ajuste;
desc ajustewindow;


select * from medicamento;
select * from classificacao;
select * from fornecedor;
select * from lote;
select * from marca;
select * from ajuste;
select * from ajustewindow;

/* query pra puxar os registros do estoque || view relatorio_cadastro */
select med.idmedicamento, med.nome, med.validade, l.lote, c.classif, mar.nomemarca
from medicamento med 
inner join lote l 
on med.IDMEDICAMENTO = l.ID_MEDICAMENTO
inner join classificacao c 
on med.IDMEDICAMENTO = c.ID_MEDICAMENTO
inner join marca mar
on med.IDMEDICAMENTO = mar.ID_MEDICAMENTO;

/* query pra puxar os registros do estoque para o ajuste */
select med.idmedicamento, med.nome, med.quantidade, med.validade, l.lote, c.classif
from medicamento med 
inner join lote l
on med.IDMEDICAMENTO = l.ID_MEDICAMENTO
inner join classificacao c
on med.IDMEDICAMENTO = c.ID_MEDICAMENTO;

select med.idmedicamento, med.nome, med.quantidade, med.validade, l.lote, c.classif
from medicamento med 
inner join lote l
on med.IDMEDICAMENTO = l.ID_MEDICAMENTO
inner join classificacao c
on med.IDMEDICAMENTO = c.ID_MEDICAMENTO
where nome = "teste8";

UPDATE medicamento
set quantidade = 0 where nome = "teste17";

/* isso desativa o modo seguro para usar o DELETE e UPDATE */
SET SQL_SAFE_UPDATES = 0;
DELETE FROM medicamento;
DELETE FROM classificacao;
DELETE FROM fornecedor;
DELETE FROM lote;
DELETE FROM marca;
DELETE FROM ajuste;
DELETE FROM ajustewindow;

ALTER TABLE AJUSTE MODIFY COLUMN ACAO VARCHAR(15);

select count(*) from medicamento;