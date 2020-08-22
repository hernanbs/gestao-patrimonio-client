package br.com.gestao.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.Part;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.gestao.connectionHttp.ConnectionHttpRestServer;
import br.com.gestao.entity.Marca;
import br.com.gestao.utils.FileUtils;

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
	
	public static void uploadMarca(Part filePart ) {
		String nameFile = FileUtils.getSubmittedFileName(filePart);
		long sizeFile = filePart.getSize();
		String urlString = REST_ENDPOINT + "upload";
		
        HttpURLConnection con = null;
        DataOutputStream dataOutputStream = null;
        BufferedReader txtResponse = null;
        
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        
        try {
            InputStream fileInputStream = filePart.getInputStream();

            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setChunkedStreamingMode(maxBufferSize);
            
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            
            dataOutputStream = new DataOutputStream(con.getOutputStream());
            dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);

            dataOutputStream.writeBytes("Content-Disposition: form-data;"
						+ " name=\"file\";"
						+ "filename=\"" + nameFile + "\";"
						+ "size=\"" + sizeFile + "\";"
						+ lineEnd); 
            dataOutputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            
            while (bytesRead > 0) {
                dataOutputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            
            dataOutputStream.writeBytes(lineEnd);
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fileInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (MalformedURLException ex) {
        	System.out.println(ex.getMessage());
        	ex.printStackTrace();
        } catch (IOException ioe) {
        	System.out.println(ioe.getMessage());
        	ioe.printStackTrace();
        }
        
        try {
            txtResponse = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String str;
            while ((str = txtResponse.readLine()) != null) {
            	System.out.println("Response " + str);
            }
            txtResponse.close();
        } catch (IOException ioex) {
        	System.out.println(ioex.getMessage());
        	ioex.printStackTrace();
        }
	 }
	
	public static void downloadMarca(String nameFile, String save_path) throws IOException {
	    final int BUFFER_SIZE = 4096;
	    String filename = FileUtils.replaceWhitespace(nameFile);
    	String urlString = REST_ENDPOINT + "download/" + filename;
    	
        URL url = new URL(urlString);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = save_path + File.separator + nameFile;
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
	}
}
