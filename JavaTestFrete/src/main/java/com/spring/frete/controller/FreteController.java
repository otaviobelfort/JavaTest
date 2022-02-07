package com.spring.frete.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spring.frete.model.Frete;
import com.spring.frete.repository.FreteRepository;
import com.spring.frete.service.FreteService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ExampleProperty;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/frete")
@Controller
public class FreteController {

	@Autowired
	private FreteRepository freteRepository;
	
	
	private final FreteService freteservice = new FreteService();
	
	@PostMapping
	@ApiOperation(value="Endpoint Calcula o frete")
	@ApiResponse(code=200, message="[OK] Frete Calculado!")
	@ResponseStatus(HttpStatus.CREATED)
	public JSONObject saveFrete(@RequestBody Frete frete) throws IOException  {
		
		try {
			frete.setVlTotalFrete(freteservice.vlTotalFrete(frete.getCepOrigem(),frete.getCepDestino(),frete.getPeso()));
			frete.setDataConsulta(freteservice.dataConsulta());
			frete.setDataPrevistaEntrega(freteservice.dataPrevistaEntrega(frete.getCepOrigem(),frete.getCepDestino()));
			freteRepository.save(frete);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return freteservice.output(frete);
	}
	
}
