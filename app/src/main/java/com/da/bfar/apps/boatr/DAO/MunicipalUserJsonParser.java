package com.da.bfar.apps.boatr.DAO;

import android.util.Log;
import android.widget.Toast;

import com.da.bfar.apps.boatr.ConnectionStats;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by BFAR-PC on 3/5/2015.
 */
public class MunicipalUserJsonParser {

    static InputStream is = null;
    static String json = "";

    public MunicipalUserJsonParser() {
    }

    public String makeHttpRequest_StringResponse(String url, String method, List<NameValuePair> params) {

        // Making HTTP request
        try {

            if (method == "GET") {
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                Log.i("test json user get", "params-" + params);
                String paramString = URLEncodedUtils.format(params, "utf-8");
                Log.i("test json user get", "paramString-" + paramString);
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                    HttpResponse httpResponse = httpClient.execute(httpGet);

                    Log.e(this.getClass().getSimpleName(), String.valueOf(httpResponse.getStatusLine().getStatusCode()));

                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                    //httpResponse.getStatusLine().getStatusCode();
                    Log.e("http response", String.valueOf(httpResponse.getStatusLine().getStatusCode()));

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            System.out.println("from api: --"+json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // return JSON String
        return json;

    }

}
