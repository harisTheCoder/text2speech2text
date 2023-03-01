package com.example.tourguidetest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class OpenAIClient {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/engines/davinci/jobs";

    public static void main(String[] args) throws IOException, JSONException {
        String message = "What is the capital of France?";
        String response = sendMessage(message);
        System.out.println("Response: " + response);
    }

    public static String sendMessage(String message) throws IOException, JSONException {
        URL url = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + API_KEY);

        JSONObject request = new JSONObject();
        request.put("prompt", message);
        request.put("max_tokens", 100);
        request.put("temperature", 0.5);

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(request.toString().getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            System.out.println("POST request failed. Response code: " + responseCode);
            return null;
        }
    }
}
