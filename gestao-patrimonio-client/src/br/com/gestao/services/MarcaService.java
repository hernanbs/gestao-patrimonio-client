package br.com.gestao.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.gestao.entity.Marca;

public class MarcaService {
	
	private static final String REST_URI = "http://localhost:8080/projeto-gestao-patrimonio/rest/";
	private static final String REST_ENTITY_MARCA = "marcas/";
	String a ="marcas/get/2";
	
	/* 
	 * 	Objetivo: Pesquisar marca por id
	 * */
	public static Marca getMarcaById(int idMarca) throws Exception   {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/marcas/" + idMarca;
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");
		int responseCode = conn.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputInline;
		StringBuffer response = new StringBuffer();
		while((inputInline= in.readLine()) != null) {
			response.append(inputInline);
		}
		in.close();
		
		Marca marca = new ObjectMapper().readValue(response.toString(), Marca.class);

		return marca;
	}
	/*
	 * Objetivo: Listar Marcas
	 * */
	public static List<Marca> listMarcas() throws Exception   {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/marcas";
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");
		int responseCode = conn.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputInline;
		StringBuffer response = new StringBuffer();
		while((inputInline= in.readLine()) != null) {
			response.append(inputInline);
		}
		in.close();
		List<Marca> arrayMarcas = new ObjectMapper().readValue(response.toString(), List.class);
		System.out.println(arrayMarcas);
		return arrayMarcas;		
	}
	
	/*
	 * Objetivo: Adicionar uma Marca
	 * */
	public static void addMarca (Marca marca) throws Exception {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/marcas";
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		
		con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		con.setRequestProperty("Accept", "application/json;charset=utf-8");
		con.setDoOutput(true);
		
		String jsonInputString = "{ \"nome\": \"" + marca.getNome() + "\" }";
		
		JsonObject jsonObject = new JsonParser().parse(jsonInputString).getAsJsonObject();
		
		System.out.println("Json para envio === " + jsonObject.toString());
		
		PrintStream printStream = new PrintStream(con.getOutputStream());
		printStream.println(jsonObject);
		con.connect();

		String jsonDeResposta = new Scanner(con.getInputStream(),"utf-8").nextLine(); 
	}
	/*
	 * Objetivo: Editar uma Marca
	 * */
	public static void editMarca (Marca marca) throws Exception {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/marcas/" + marca.getId();
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("PUT");
		
		con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		con.setRequestProperty("Accept", "application/json;charset=utf-8");
		con.setDoOutput(true);
		
		String jsonInputString = "{ \"nome\": \"" + marca.getNome() + "\" }";
		
		JsonObject jsonObject = new JsonParser().parse(jsonInputString).getAsJsonObject();
	
		PrintStream printStream = new PrintStream(con.getOutputStream());
		printStream.println(jsonObject);
		con.connect();

		String jsonDeResposta = new Scanner(con.getInputStream(),"utf-8").nextLine(); 
	}
	
	public static void deleteMarca (Marca marca) throws Exception {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/marcas/" + marca.getId();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("DELETE");
		
		con.setRequestProperty("Content-Type", "text/plain");
		con.setRequestProperty("Accept", "application/json;charset=utf-8");
		con.connect();

		String jsonDeResposta = new Scanner(con.getInputStream(),"utf-8").nextLine(); 
	}
	
	
}
