package com.spring.frete.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Frete {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // atribue ao banco de dados criar o id
	Long id;
	double peso;
	Long cepOrigem;
	Long cepDestino;
	String nomeDestinatario;
	double vlTotalFrete;
	LocalDate dataPrevistaEntrega;
	LocalDate dataConsulta;
	

	public Frete(){
		   super();
		}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public Long getCepOrigem() {
		return cepOrigem;
	}
	public void setCepOrigem(Long cepOrigem) {
		this.cepOrigem = cepOrigem;
	}
	public Long getCepDestino() {
		return cepDestino;
	}
	public void setCepDestino(Long cepDestino) {
		this.cepDestino = cepDestino;
	}
	public String getNomeDestinatario() {
		return nomeDestinatario;
	}
	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}
	public double getVlTotalFrete() {
		return vlTotalFrete;
	}
	public void setVlTotalFrete(double vlTotalFrete) {
		this.vlTotalFrete = vlTotalFrete;
	}
	
	public LocalDate getDataPrevistaEntrega() {
		return dataPrevistaEntrega;
	}
	public void setDataPrevistaEntrega(LocalDate dataPrevistaEntrega) {
		this.dataPrevistaEntrega = dataPrevistaEntrega;
	}
	public LocalDate getDataConsulta() {
		return dataConsulta;
	}
	public void setDataConsulta(LocalDate dataConsulta) {
		this.dataConsulta = dataConsulta;
	}


	
	
}
