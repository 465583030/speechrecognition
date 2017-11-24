package com.example.spolti.speechrecognition;

import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class FetchData extends AsyncTask<Void, Void, Void> {

    String data = "";
    String dataParsed = "";
    String singleParsed = "";

    @Override
    protected Void doInBackground(Void... voids) {


        String url_2 = "";
        try {

            String query = URLEncoder.encode(
                    "%7B%22_id%22%3A%22A42BB0-TD%252DVG5612-A42BB0E14DC0%22%7D&projection=InternetGatewayDevice.DeviceInfo.ModelName,InternetGatewayDevice.DeviceInfo.Manufacturer,InternetGatewayDevice.WANDevice.1.WANConnectionDevice.3.WANPPPConnection.1.DNSServers", "utf-8");
            url_2 = "http://172.20.10.76:8080/api/devices?query=" + query;
            URL url = new URL(url_2);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray ja = new JSONArray(data);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                singleParsed = "ID:" + jo.get("_id") + "\n";
//                               "Password:" + jo.get("password") + "\n" +
//                               "Contact:" + jo.get("contact") + "\n" +
//                               "Country:" + jo.get("country") + "\n";
//                dataParsed = dataParsed + singleParsed + "\n";
//
//            }


//            JSONArray ja = new JSONArray(data);
//            for(int i = 0; i < ja.length(); i++){
//                JSONObject jo = (JSONObject) ja.get(i);
//                singleParsed = "Name:" + jo.get("name") + "\n" +
//                               "Password:" + jo.get("password") + "\n" +
//                               "Contact:" + jo.get("contact") + "\n" +
//                               "Country:" + jo.get("country") + "\n";
//                dataParsed = dataParsed + singleParsed + "\n";
//
//            }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

        @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.text.setText(singleParsed);
    }
}
