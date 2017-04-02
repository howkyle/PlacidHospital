package com.comp3161.placidandroid.rest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by stone on 3/29/17.
 */

public class RestLogin {



    public String loginNurse(String username,String password,String url)
    {

        String response="";
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password",password);



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

    public String loginSecretary(String username,String password,String url)
    {
        String response="";
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password",password);



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

    public String loginDoctor(String username,String password,String url)
    {
        String response="";
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password",password);



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
