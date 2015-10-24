package com.mayank.cognitio_submission;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 10/24/2015.
 */
public class data_fetch extends AsyncTask<Void, String, Void> {

    HttpURLConnection urlConnection = null;
    String JsonStr;
    BufferedReader reader = null;
    String[] name;
    String[] detail;

    @Override
    protected Void doInBackground(Void... params) {
        try {
            final String url_str = "http://mayank.byethost7.com/demo.php";
            URL url = new URL(url_str);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }

            JsonStr = buffer.toString();
            Log.v("rggf",JsonStr);



           JSONObject datJson = new JSONObject(JsonStr);
            JSONArray datJsonarray = datJson.getJSONArray("results");
            //JSONArray datJsonarray = new JSONArray(JsonStr);
           // JSONArray datJsonArray = JsonStr.getJsi
            int count = datJsonarray.length();
            for (int i =0;i<count;i++){
                JSONObject whl = datJsonarray.getJSONObject(i);
                name[i] = whl.getString("name");
                detail[i] = whl.getString("detail");
                publishProgress((String)name[i],(String) detail[i]);
            }
        }
        catch (IOException | JSONException e) {
            Log.e("mayank", "Error ", e);
            return null;
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("mayank", "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        MainActivity.name_arr.add(values[0]);
        MainActivity.detail_arr.add(values[1]);
        super.onProgressUpdate(values);
    }
}

