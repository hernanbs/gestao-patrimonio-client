package br.com.gestao.utils;

import javax.servlet.http.Part;

public class FileUtils {
	
	public static String getSubmittedFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
	
	public static String replaceWhitespace(String str) {
	    if (str.contains(" ")) {
	        str = str.replace(" ", "%20");
	    }
	    return str;
	}

}
