package com.cestec.consolidacao;

import java.sql.Date;

import com.cestec.parametros.Setores;
import com.cestec.parametros.prestadores;
import com.cestec.parametros.prm001;

public class Consolidacao {
	private Integer id;
	private Integer situacao;
	private String descricao;
	private String listOfQtd;	
	private Setores setor;
	private Date dataReq;
	private prestadores prest;
	
	public Consolidacao(Integer id, Integer situacao, String descricao, String nomeSetor, Date dataReq, String listOfQtd) {
		this.id        = id;
		this.situacao  = situacao;
		this.descricao = descricao;
		this.setor     = new Setores();
		setNomeSetor(nomeSetor);
		this.dataReq   = dataReq;
		this.listOfQtd = listOfQtd;
	}
	public Consolidacao(Integer situacao, String descricao, Integer id,  String nomePrest, Date dataReq, String listOfQtd) {
		this.id        = id;
		this.situacao  = situacao;
		this.descricao = descricao;
		this.prest = new prestadores();
		setNomePrest(nomePrest);
		this.dataReq   = dataReq;
		this.listOfQtd = listOfQtd;
	}

	public String getNomeSetor() {
		return this.setor.getNomeSetor();
	}
	
	public void setNomeSetor(String setor) {
	    this.setor.setNomeSetor(setor);
	}

	public String getNomePrest() {
		return this.prest.getnomePrestador();
	}

    public void setNomePrest(String prestador){
		this.prest.setnomePrestador(prestador);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataReq() {
		return prm001.fmtDataBR(dataReq);
	}

	public void setDataReq(Date dataReq) {
		this.dataReq = dataReq;
	}

	public String getSituacao() {
		return prm001.getConsolidadoReq(situacao);
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getListOfQtd() {
		return listOfQtd;
	}
	
	public void setListOfQtd(String listOfQtd) {
		this.listOfQtd = listOfQtd;
	}
	
}
