package br.com.gestao.connectionHttp;

import java.io.PrintStream;
import java.net.HttpURLConnection;

import java.net.URL;

import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ConnectionHttpRestServer {
	
	@SuppressWarnings("resource")
	private static String conexaoJsonPOSTPUT (String url, String requestMethod, String jsonBodyString) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod(requestMethod);
			
			con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			con.setRequestProperty("Accept", "application/json;charset=utf-8");
			con.setDoOutput(true);
			
			JsonObject jsonObject = new JsonParser().parse(jsonBodyString).getAsJsonObject();
			PrintStream printStream = new PrintStream(con.getOutputStream());
			printStream.println(jsonObject);
			con.connect();
			
			String jsonDeResposta = new Scanner(con.getInputStream(),"utf-8").nextLine();
			return jsonDeResposta;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("resource")
	private static String conexaoGETDELETE (String url, String requestMethod, String languageAccept) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod(requestMethod);
			con.setRequestProperty("Accept", languageAccept);
			con.setDoOutput(true);
			con.connect();
			
			String jsonDeResposta = new Scanner(con.getInputStream(),"utf-8").nextLine();
			
			return jsonDeResposta;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String connectionGET(String url, String languageAccept) {
		return conexaoGETDELETE(url, "GET", languageAccept);
	}
	public static String connectionDELETE(String url, String languageAccept) {
		return conexaoGETDELETE(url, "DELETE", languageAccept);
	}
	public static String connectionPOST(String url, String jsonBodyString) {
		return conexaoJsonPOSTPUT(url, "POST", jsonBodyString);
	}
	public static String connectionPUT(String url, String jsonBodyString) {
		return conexaoJsonPOSTPUT(url, "PUT", jsonBodyString);
	}
}
