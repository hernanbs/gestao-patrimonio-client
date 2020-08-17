package br.com.gestao.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.gestao.connectionHttp.ConnectionHttpRestServer;
import br.com.gestao.entity.Marca;
import br.com.gestao.entity.Patrimonio;

public class UtilService {
	private static final String REST_URI = "http://localhost:8080/projeto-gestao-patrimonio/rest/";
	private static final String REST_ENTITY_MARCA = "marcas/";
	private static final String REST_ENTITY_PATRIMONIO = "/patrimonios";
	private static final String REST_ENDPOINT = REST_URI + REST_ENTITY_MARCA;
	
	/*
	 * Objetivo: Listar Patrimônios que possuem uma marca escolhida
	 * */
	public static List<Patrimonio> pesquisarPatrimonioByMarca (int idMarca) throws JsonParseException, JsonMappingException, IOException {
		String url = REST_ENDPOINT + idMarca + REST_ENTITY_PATRIMONIO;
		String jsonDeResposta = ConnectionHttpRestServer.connectionGET(url, "application/json;charset=utf-8");
		List<Patrimonio> arrayPatrimonios = new ObjectMapper().readValue(jsonDeResposta.toString(), List.class);
		return arrayPatrimonios;
	}
	
}
