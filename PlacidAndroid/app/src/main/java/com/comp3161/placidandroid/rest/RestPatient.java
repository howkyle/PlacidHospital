package com.comp3161.placidandroid.rest;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by stone on 4/3/17.
 */

public class RestPatient {



    public String registerPatient(String url,String json)
    {
        String response="";
        try {

            HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection(); //opens a connection to the server


            conn.setRequestMethod("POST");//sets the request method as a POST request

            conn.setDoOutput(true); //send request body
            conn.setDoInput(true); //expect input
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream()); //creates a new output stream to write data to server
            wr.write(json.toString()); //sends the request body as json
            wr.flush(); //flushes the output stream
            wr.close();  //close the output stream
            int responseCode=conn.getResponseCode();

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


    public String getPatients(String url)
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

    public String updatePatient(String url,String json)
    {
        String response="";
        try {

            HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection(); //opens a connection to the server


            conn.setRequestMethod("POST");//sets the request method as a POST request

            conn.setDoOutput(true); //send request body
            conn.setDoInput(true); //expect input
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream()); //creates a new output stream to write data to server
            wr.write(json.toString()); //sends the request body as json
            wr.flush(); //flushes the output stream
            wr.close();  //close the output stream
            int responseCode=conn.getResponseCode();

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
