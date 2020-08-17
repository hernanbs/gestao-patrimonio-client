package br.com.gestao.services;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.gestao.entity.Patrimonio;

public class UtilService {
	
	public static List<Patrimonio> pesquisarPatrimonioByMarca (int idMarca) throws Exception {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/marcas/" + idMarca + "/patrimonios";
		URL obj = new URL(url);
		System.out.println(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json;charset=utf-8");
		con.setDoOutput(true);
		con.connect();

		String jsonDeResposta = new Scanner(con.getInputStream(),"utf-8").nextLine(); 
		System.out.println("RESPOSTA SERVIDOR ==== " + jsonDeResposta);
		List<Patrimonio> arrayPatrimonios = new ObjectMapper().readValue(jsonDeResposta.toString(), List.class);
		return arrayPatrimonios;
	}
	
}
