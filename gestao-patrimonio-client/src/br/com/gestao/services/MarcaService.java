package br.com.gestao.services;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gestao.connectionHttp.ConnectionHttpRestServer;
import br.com.gestao.entity.Marca;

public class MarcaService {
	
	private static final String REST_URI = "http://localhost:8080/projeto-gestao-patrimonio/rest/";
	private static final String REST_ENTITY_MARCA = "marcas/";
	private static final String REST_ENDPOINT = REST_URI + REST_ENTITY_MARCA;
	
	/* 
	 * 	Objetivo: Pesquisar marca por id
	 * */
	public static Marca getMarcaById(int idMarca) throws JsonParseException, JsonMappingException, IOException {
		String url = REST_ENDPOINT + idMarca;
		String jsonDeResposta = ConnectionHttpRestServer.connectionGET(url, "application/json;charset=utf-8");
		Marca marca = new ObjectMapper().readValue(jsonDeResposta.toString(), Marca.class);
		return marca;
	}
	
	/*
	 * Objetivo: Listar Marcas
	 * */
	public static List<Marca> listMarcas() throws JsonParseException, JsonMappingException, IOException {
		String url = REST_ENDPOINT;
		String jsonDeResposta = ConnectionHttpRestServer.connectionGET(url, "application/json;charset=utf-8");
		List<Marca> arrayMarcas = new ObjectMapper().readValue(jsonDeResposta.toString(), List.class);
		return arrayMarcas;
	}
	
	/*
	 * Objetivo: Editar uma Marca
	 * */
	public static String editMarca (Marca marca)  {
		String url = REST_ENDPOINT + marca.getId();
		String jsonBodyString = "{ \"nome\": \"" + marca.getNome() + "\" }";
		String jsonDeResposta = ConnectionHttpRestServer.connectionPUT(url, jsonBodyString);
		return jsonDeResposta.toString();
	}
	
	/*
	 * Objetivo: Adicionar uma Marca
	 * */
	public static String addMarca(Marca marca) throws JsonParseException, JsonMappingException, IOException {
		String url = REST_ENDPOINT;
		String jsonBodyString = "{ \"nome\": \"" + marca.getNome() + "\" }";
		String jsonDeResposta = ConnectionHttpRestServer.connectionPOST(url, jsonBodyString);
		return jsonDeResposta.toString();
	}
	
	/*
	 * Objetivo: Remover uma Marca
	 * */
	public static String deleteMarca (Marca marca)  {
		String url = REST_ENDPOINT + marca.getId();
		String jsonDeResposta = ConnectionHttpRestServer.connectionDELETE(url, "application/json;charset=utf-8");
		return jsonDeResposta.toString();
	}
	
}
