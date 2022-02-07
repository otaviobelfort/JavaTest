package com.spring.frete.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.frete.model.Frete;
import com.spring.frete.repository.FreteRepository;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

@Service
public class FreteService {
	double valorFreteTotal;
	double valorPorKilo = 1.0;
	double desconto50 = 0.5;
	double desconto75 = 0.25;
	
	
	// consulta a api CEP e retorna um json
	public JSONObject respostJson(Long cep) throws IOException, IOException {
		
		String url = String.format("https://viacep.com.br/ws/%s/json", cep);
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    
	    if (conn.getResponseCode() != 200) {
	        System.out.println("[Erro] " + conn.getResponseCode() + " ao obter dados da [url] " + url);
	    }
		
	    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	    String outputJson = "";
	    String line;
	    while ((line = br.readLine()) != null) {
	        outputJson += line;
	    }
	    conn.disconnect();
	
	    JSONObject jsonObject = (JSONObject) JSONValue.parse(outputJson);
		return jsonObject;
	}
	
	// obtem o cep
	public String getCep(Long cep) throws IOException, IOException {
		return respostJson(cep).get("cep").toString().replace("-", "");
		
	}
	
	// obtem o o estado
	public String getUf(Long cep) throws IOException, IOException {
		return respostJson(cep).get("uf").toString().toLowerCase();
		
	}
	
	// obtem o ddd
	public String getDdd(Long cep) throws IOException, IOException {
		return respostJson(cep).get("ddd").toString();
	}
	
	// calcula o frete total
	public double vlTotalFrete(Long cepOrigem, Long cepDestino, double peso) throws IOException {
		
		// CEPs com estados  DDDs iguais e/ou Estados iguas
		// aplica desconto de 50%
		if(getDdd(cepOrigem).equals(getDdd(cepDestino))) {
			valorFreteTotal = (valorPorKilo*peso) * desconto50;
		}
		
		// CEPs com estados iguais e DDDs diferentes
		// aplica desconto de 75%
		else if(getUf(cepOrigem).equals(getUf(cepDestino)) && 
				!(getDdd(cepOrigem).equals(getDdd(cepDestino)))) {
			valorFreteTotal = (valorPorKilo*peso) * desconto75;
		}
		
		else {
			// caso contrario(estados diferentes), não tem desconto
			valorFreteTotal = (valorPorKilo*peso);	

		}
		return valorFreteTotal;	
	}
	
	// obtem o data prevista de entrega
	public LocalDate dataPrevistaEntrega(Long cepOrigem, Long cepDestino) throws IOException {
		
		LocalDate newDataEntrega = null;
		
		// se o cep for igual, a data prevista de entrega será de 1 dia
		if(getDdd(cepOrigem).equals(getDdd(cepDestino))) {
			newDataEntrega = dataPrevistaEntregaPlus(1);
			
		}
		
		// se o cep for igual(e ddd diferente) a data prevista de entrega será de 3 dias
		else if(getUf(cepOrigem).equals(getUf(cepDestino)) && 
				!(getDdd(cepOrigem).equals(getDdd(cepDestino)))) {
			newDataEntrega = dataPrevistaEntregaPlus(3);
		}
		
		else {
			// caso contrario a data prevista de entrega será de 10 dias
			newDataEntrega = dataPrevistaEntregaPlus(10);
		}
		return newDataEntrega;	
	}
	
	
	// response/output
	public JSONObject output(Frete frete) throws IOException {
		JSONObject json = new JSONObject();
		json.put("vlTotalFrete",frete.getVlTotalFrete());
		json.put("dataPrevistaEntrega",frete.getDataPrevistaEntrega());
		json.put("cepOrigem",frete.getCepOrigem());
		json.put("cepDestino", frete.getCepDestino());
		return json;
	}
	
	// obtem a data da consulta
	public LocalDate dataConsulta() {
		 return LocalDate.now() ;
	}
	
	// obtem a data prevista de entrega a partir da data de consulta
	public LocalDate dataPrevistaEntregaPlus(int data) {
		 return LocalDate.now().plusDays(data);
	
	}

}
