package com.topBloc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class topBlocCodeC {
	public static final String dataFile_1 = "/Data1.xlsx";
	public static final String dataFile_2 = "/Data2.xlsx";
	
	public void readFiles() {
	try {
		
		//initialize workbook variables
		Workbook workbook1 = WorkbookFactory.create(new File(this.getClass().getResource(dataFile_1).toURI()));
		Workbook workbook2 = WorkbookFactory.create(new File(this.getClass().getResource(dataFile_2).toURI()));

		Sheet sheet1 = workbook1.getSheetAt(0);
		Sheet sheet2 = workbook2.getSheetAt(0);
		ArrayList<BigDecimal> dataSetOne = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> dataSetTwo = new ArrayList<BigDecimal>();
		ArrayList<String> dataString = new ArrayList<String>();

		int colNum = 0;
		
		DataFormatter dataFormatter = new DataFormatter();
		int rowNum = 0;

		//read Data1.xlsx into Arraylists
		for (Row row: sheet1) {
            for(Cell cell: row) {
            	if(rowNum == 0) {
            		continue;
            	}
                String cellValue = dataFormatter.formatCellValue(cell);
                switch(colNum) {
                	case 0:
                		dataSetOne.add(new BigDecimal(cellValue));
                		break;
                	case 1:
                		dataSetTwo.add(new BigDecimal(cellValue));
                		break;
                	case 2:
                		dataString.add(cellValue);
                		break;

                }
                colNum++;
            }
            rowNum++;
            colNum = 0;
        }
		
		rowNum = 0;
		
		//read and perform operations on Data2.xlsx then store results back into ArrayLists
		for (Row row: sheet2) {
            for(Cell cell: row) {
            	if(rowNum == 0) {
            		continue;
            	}
                String cellValue = dataFormatter.formatCellValue(cell);
                switch(colNum) {
                
                	case 0:
                		dataSetOne.set(rowNum-1, dataSetOne.get(rowNum-1).multiply(new BigDecimal(cellValue)));
                		 System.out.print(dataSetOne.get(rowNum-1).toString() + "\t");
                		 break;
                	case 1:
                		dataSetTwo.set(rowNum-1, dataSetTwo.get(rowNum-1).divide(new BigDecimal(cellValue)));
	               		 System.out.print(dataSetTwo.get(rowNum-1).toString() + "\t");	
	               		break;
                	case 2:
                		dataString.set(rowNum-1, dataString.get(rowNum-1) + " " + cellValue);
                		System.out.print(dataString.get(rowNum-1) + "\t");
                		break;
                }
                colNum++;
                System.out.println();
            }
            colNum = 0;
            rowNum++;
        }
		
		//Initialize json object
		String message;
		JSONObject json = new JSONObject();
		json.put("id", "markweizeorick@gmail.com");
		json.put("numberSetOne", dataSetOne.toString());
		json.put("numberSetTwo", dataSetTwo.toString());
		json.put("wordSetOne", String.join(" ", dataString));
		
		//Convert json Object to String and call method to post Json to URL
		message = json.toString();
	    DefaultHttpClient httpClient = new DefaultHttpClient();
		String result = postToURL("http://34.239.125.159:5000/challenge", message, httpClient);

		
		
		
		
	} catch(FileNotFoundException e) {
		System.out.print("EXCEPTION: File Note Found: " + e);
	} catch (IOException e) {
		System.out.print("EXCEPTION: IOException: " + e);
		e.printStackTrace();
	} catch (EncryptedDocumentException e) {
		System.out.print("EXCEPTION: EncryptedDocumentException: " + e);
		e.printStackTrace();
	} catch (InvalidFormatException e) {
		System.out.print("EXCEPTION: InvalidFormatException: " + e);
		e.printStackTrace();
	} catch (URISyntaxException e) {
		System.out.print("EXCEPTION: URISyntaxException: " + e);
		e.printStackTrace();
	}
	}
	
	 private static String postToURL(String url, String message, DefaultHttpClient httpClient) throws IOException, IllegalStateException, UnsupportedEncodingException, RuntimeException {
	        HttpPost postRequest = new HttpPost(url);
	 
	        StringEntity input = new StringEntity(message);
	        input.setContentType("application/json");
	        postRequest.setEntity(input);
	 
	        HttpResponse response = httpClient.execute(postRequest);
	 
	        if (response.getStatusLine().getStatusCode() != 200) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                    + response.getStatusLine().getStatusCode());
	        }
	 
	        BufferedReader br = new BufferedReader(
	                new InputStreamReader((response.getEntity().getContent())));
	 
	        String output;
	        StringBuffer totalOutput = new StringBuffer();
	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            System.out.println(output);
	            totalOutput.append(output);
	        }
	        return totalOutput.toString();
	    }
}

