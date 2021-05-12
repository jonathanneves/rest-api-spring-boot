package com.algaworks.osworks.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.osworks.domain.model.StatusOrdemServico;

//----REPRESENTATION MODEL----
//Como queremos que apareça corpo do Response HTTP em formato JSON
//Permite da manutenção no Model em Domain responsável pelos atributos do Banco sem prejudicar ou alterar o corpo do Response HTTP

/* EXEMPLO OLD:
 * {
    "id": 1,
    "cliente": {
        "id": 2,
        "nome": "Maria Milos",
        "email": "mariamilos@gmail.com",
        "telefone": "88888-4321"
    },
    "descricao": "Reparo de notebook Dell. Problema no HD",
    "preco": 250.50,
    "status": "ABERTA",
    "dataAbertura": "2021-05-11T14:05:34-03:00",
    "dataFinalizacao": null
 * }
 * 
 * --
 * 
 * EXEMPLO NOVO:
 * {
    "id": 1,
    "nomeCliente": "Maria Milos",
    "descricao": "Reparo de notebook Dell. Problema no HD",
    "preco": 250.50,
    "status": "ABERTA",
    "dataAbertura": "2021-05-11T14:05:34-03:00",
    "dataFinalizacao": null
 *	}
 */
public class OrdemServicoModel {

	private Long id;
	//private String nomeCliente;   Substituido por ClienteResumoModel
	private ClienteResumoModel cliente;
	private String descricao;
	private BigDecimal preco;
	private StatusOrdemServico status;
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataFinalizacao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public StatusOrdemServico getStatus() {
		return status;
	}
	public void setStatus(StatusOrdemServico status) {
		this.status = status;
	}
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public OffsetDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	public ClienteResumoModel getCliente() {
		return cliente;
	}
	public void setCliente(ClienteResumoModel cliente) {
		this.cliente = cliente;
	}
	/*public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}*/
		
}
