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
select * from estabelecimento;
select * from setores;

UPDATE medicamento
set quantidade = 3, nome = "testando dnv" where idmedicamento = 18 ;
UPDATE medicamento
set quantidade = 3 where idmedicamento = 18 ;
UPDATE medicamento
set quantidade = 3 where idmedicamento = 18 ;
UPDATE medicamento
set quantidade = 3 where idmedicamento = 18 ;


/* query pra puxar os registros do estoque || view relatorio_cadastro */
select med.idmedicamento, med.nome, med.validade, med.quantidade, l.lote, c.classif, mar.nomemarca
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

/* query para puxar  os registros para a edicao no estoque*/
select med.idmedicamento, med.ativo, med.nome, med.quantidade, med.validade, med.medida, med.addHours, l.lote, c.classif, f.FORN, mar.NOMEMARCA, perm.PERM_ESTOQUE, perm.PERM_REQ_PRESTAD, perm.PERM_REQ_SETOR, perm.PERM_INVENTARIO, perm.PERM_DEVOLUCAO
from medicamento med 
inner join lote l
on med.IDMEDICAMENTO = l.ID_MEDICAMENTO
inner join classificacao c
on med.IDMEDICAMENTO = c.ID_MEDICAMENTO
inner join fornecedor f
on med.IDMEDICAMENTO = f.ID_MEDICAMENTO
inner join marca mar
on med.IDMEDICAMENTO = mar.ID_MEDICAMENTO
inner join permdemovimentacao perm
on med.IDMEDICAMENTO = perm.ID_MEDICAMENTO
where IDMEDICAMENTO = ?;

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
DELETE FROM setores;

ALTER TABLE AJUSTE MODIFY COLUMN ACAO VARCHAR(15);
/*adicionar duas coluna para o medicamentos, uma de ativo(boolean) e data de adicao no estoque */
select count(*) from medicamento;

select  se.NOMESETORES, se.IDSETORES
from ESTABELECIMENTO est
inner join setores se 
on est.IDESTABELECIMENTO = se.ID_ESTABELECIMENTO
where IDESTABELECIMENTO = 1;