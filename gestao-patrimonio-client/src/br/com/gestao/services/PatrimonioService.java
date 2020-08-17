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
import br.com.gestao.entity.Patrimonio;

public class PatrimonioService {
	
	public static Patrimonio getPatrimonioById(int idPatrimonio) throws Exception   {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/patrimonios/get/" + idPatrimonio;
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
		
		Patrimonio patrimonio = new ObjectMapper().readValue(response.toString(), Patrimonio.class);

		return patrimonio;
	}
	
	public static List<Patrimonio> listPatrimonios() throws Exception   {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/patrimonios/list";
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
		List<Patrimonio> arrayPatrimonios = new ObjectMapper().readValue(response.toString(), List.class);
		System.out.println(arrayPatrimonios);
		return arrayPatrimonios;		
	}
	
	public static void addPatrimonio (Patrimonio patrimonio) throws Exception {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/patrimonios/add";
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		
		con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		con.setRequestProperty("Accept", "application/json;charset=utf-8");
		con.setDoOutput(true);
		
		String jsonInputString = "{ \"nome\": \"" + patrimonio.getNome() + "\","
								+ "\"idMarca\": \""+ patrimonio.getIdMarca() +"\","
								+ "\"descricao\": \"" + patrimonio.getDescricao() +"\" }";
		
		JsonObject jsonObject = new JsonParser().parse(jsonInputString).getAsJsonObject();
		
		System.out.println("Json para envio === " + jsonObject.toString());
		
		PrintStream printStream = new PrintStream(con.getOutputStream());
		printStream.println(jsonObject);
		con.connect();

		String jsonDeResposta = new Scanner(con.getInputStream(),"utf-8").nextLine(); 
	}
	
	public static void editPatrimonio (Patrimonio patrimonio) throws Exception {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/patrimonios/edit/" + patrimonio.getId();
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("PUT");
		
		con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		con.setRequestProperty("Accept", "application/json;charset=utf-8");
		con.setDoOutput(true);
		
		String jsonInputString = "{ \"nome\": \"" + patrimonio.getNome() + "\","
				+ "\"idMarca\": \""+ patrimonio.getIdMarca() +"\","
				+ "\"descricao\": \"" + patrimonio.getDescricao() +"\" }";
		
		JsonObject jsonObject = new JsonParser().parse(jsonInputString).getAsJsonObject();
		
		PrintStream printStream = new PrintStream(con.getOutputStream());
		printStream.println(jsonObject);
		con.connect();

		String jsonDeResposta = new Scanner(con.getInputStream(),"utf-8").nextLine(); 
	}
	public static void deletePatrimonio (Patrimonio patrimonio) throws Exception {
		String url = "http://localhost:8080/projeto-gestao-patrimonio/rest/patrimonios/delete/" + patrimonio.getId();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("DELETE");
		
		con.setRequestProperty("Content-Type", "text/plain");
		con.setRequestProperty("Accept", "application/json;charset=utf-8");
		con.connect();

		String jsonDeResposta = new Scanner(con.getInputStream(),"utf-8").nextLine(); 
	}
	
}
