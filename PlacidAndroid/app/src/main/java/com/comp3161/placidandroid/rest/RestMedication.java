package com.comp3161.placidandroid.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by stone on 4/4/17.
 */

public class RestMedication {



    public String getMedications(String url)
    {

        String response="";
        try {

            HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection(); //opens a connection to the server

            conn.setDoInput(true);
            conn.setRequestMethod("GET");//sets the request method as a POST request
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }else{
                response = "";
            }


        }catch(Exception e){
            response="";
        }


        return response;
    }
}
