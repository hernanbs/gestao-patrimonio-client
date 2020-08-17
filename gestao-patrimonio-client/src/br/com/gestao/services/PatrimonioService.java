package br.com.gestao.services;


import java.io.IOException;
import java.util.List;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gestao.connectionHttp.ConnectionHttpRestServer;
import br.com.gestao.entity.Patrimonio;

public class PatrimonioService {
	private static final String REST_URI = "http://localhost:8080/projeto-gestao-patrimonio/rest/";
	private static final String REST_ENTITY_PATRIMONIO = "patrimonios/";
	private static final String REST_ENDPOINT = REST_URI + REST_ENTITY_PATRIMONIO;
	
	/* 
	 * 	Objetivo: Pesquisar patrimônio por id
	 * */
	public static Patrimonio getPatrimonioById(int idPatrimonio) throws JsonParseException, JsonMappingException, IOException {
		String url = REST_ENDPOINT + idPatrimonio;
		String jsonDeResposta = ConnectionHttpRestServer.connectionGET(url, "application/json;charset=utf-8");
		Patrimonio patrimonio = new ObjectMapper().readValue(jsonDeResposta.toString(), Patrimonio.class);
		return patrimonio;
	}
	
	/*
	 * Objetivo: Listar Patrimônios
	 * */
	public static List<Patrimonio> listPatrimonios() throws JsonParseException, JsonMappingException, IOException {
		String url = REST_ENDPOINT;
		String jsonDeResposta = ConnectionHttpRestServer.connectionGET(url, "application/json;charset=utf-8");
		List<Patrimonio> arrayPatrimonios = new ObjectMapper().readValue(jsonDeResposta.toString(), List.class);
		return arrayPatrimonios;
	}
	
	/*
	 * Objetivo: Adicionar um Patrimônio
	 * */
	public static String addPatrimonio(Patrimonio patrimonio) throws JsonParseException, JsonMappingException, IOException {
		String url = REST_ENDPOINT;
		String jsonBodyString = "{ \"nome\": \"" + patrimonio.getNome() + "\","
								+ "\"idMarca\": \""+ patrimonio.getIdMarca() +"\","
								+ "\"descricao\": \"" + patrimonio.getDescricao() +"\" }";
		String jsonDeResposta = ConnectionHttpRestServer.connectionPOST(url, jsonBodyString);
		return jsonDeResposta.toString();
	}

	/*
	 * Objetivo: Editar um Patrimônio
	 * */
	public static String editPatrimonio (Patrimonio patrimonio)  {
		String url = REST_ENDPOINT + patrimonio.getId();
		String jsonBodyString = "{ \"nome\": \"" + patrimonio.getNome() + "\","
								+ "\"idMarca\": \""+ patrimonio.getIdMarca() +"\","
								+ "\"descricao\": \"" + patrimonio.getDescricao() +"\" }";
		
		String jsonDeResposta = ConnectionHttpRestServer.connectionPUT(url, jsonBodyString);
		return jsonDeResposta.toString();
	}

	/*
	 * Objetivo: Remover um Patrimônio
	 * */
	public static String deletePatrimonio (Patrimonio patrimonio)  {
		String url = REST_ENDPOINT + patrimonio.getId();
		String jsonDeResposta = ConnectionHttpRestServer.connectionDELETE(url, "application/json;charset=utf-8");
		return jsonDeResposta.toString();
	}
	
}
