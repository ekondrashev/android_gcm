package com.hmkcode.android.gcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ekondrashev on 2/11/15.
 */
public class HttpClient {
    private static final String URL = "http://10.0.2.2:8000/android/regdev";
    private static final String USER_AGENT = "Mozilla/5.0";
    private URL url;
    private Charset utf8;

    public HttpClient() throws MalformedURLException {
        this.url = new URL(URL);
        this.utf8 = Charset.forName("utf8");
    }

    public Result postJson(String json) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept-Charset", utf8.name());
        connection.setRequestProperty("Content-Type", String.format("application/json; charset=%s", utf8.name()));
//        con.setDoOutput(true);
//
//        con.setDoInput(true);
//
//        con.setRequestProperty("Content-Type", "application/json");

        connection.setRequestProperty("Accept", "application/json");


        // Send post request
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        try {
            outputStream.write(json.getBytes(utf8));
            outputStream.flush();

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
    }
}
