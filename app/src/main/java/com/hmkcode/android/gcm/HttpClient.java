package com.hmkcode.android.gcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ekondrashev on 2/11/15.
 */
public class HttpClient {

    private static final String URL = "http://10.0.2.2:8000";
    private static final String USER_AGENT = "Mozilla/5.0";
    private Charset utf8;

    public HttpClient() {
        this.utf8 = Charset.forName("utf8");
    }

    protected HttpURLConnection getConnection(String query, String method, Map<String, String> headers) throws IOException {
        URL url = new URL(URL + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method.toUpperCase());
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept-Charset", utf8.name());
        connection.setRequestProperty("Content-Type", String.format("application/json; charset=%s", utf8.name()));
        connection.setRequestProperty("Accept", "application/json");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        // Send post request
        if (method.equalsIgnoreCase("post")) {
            connection.setDoOutput(true);
        }
        return connection;
    }

    protected Result parseResult(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        StringBuffer response = new StringBuffer();
        try {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {

            }
        }

        return new Result(response.toString(), responseCode);
    }

    public Result post(String query, Map<String, String> data) {
        return null;
    }

    public Result get(String query) throws IOException {
        return get(query, new HashMap<String, String>());
    }

    public Result get(String query, Map<String, String> headers) throws IOException {

        HttpURLConnection c = getConnection(query, "get", headers);

        return parseResult(c);
    }


    public Result post(String query, String json) throws IOException {
        return post(query, json, new HashMap<String, String>());
    }

    public Result post(String query, String json, Map<String, String> headers) throws IOException {
        HttpURLConnection connection = getConnection(query, "post", headers);


        OutputStream outputStream = connection.getOutputStream();
        try {
            outputStream.write(json.getBytes(utf8));
            outputStream.flush();

            return parseResult(connection);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
    }


    class Result {

        private int responseCode;
        private String result;

        public Result(String result, int responseCode) {
            this.responseCode = responseCode;
            this.result = result;
        }

        public int resoponseCode() {
            return responseCode;
        }

        public String result(){
            return result;
        }
    }
}
