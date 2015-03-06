package com.da.bfar.apps.boatr.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.da.bfar.apps.boatr.BoatRegistration;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by BFAR-PC on 3/3/2015.
 */
public class FtpSender {
    private static final int BUFFER = 2048;
    String TAG;
    String PATH = Environment.getExternalStorageDirectory().getPath();
    private Context context;
    private Object object;
    UseDBTables useDBTables;


    //String JsonUrl = "http://122.55.5.206:83/api/"; //for testing bfar public api

    //local api ni aries
    String JsonUrl = "http://192.168.1.106/api/";

    public FtpSender(Context context, Object object) {
        this.context = context;
        this.object = object;
        this.useDBTables = new UseDBTables(this.context);
    }


    public void textWritter(String datafile, String fileName) {

        try {
            File myFile = new File(PATH + "/" + fileName);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append(datafile.toString());
            myOutWriter.close();
            fOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void zip(String[] _files, String _zipFile) {
        Log.d("asdasd", "asd" + _zipFile);
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(_zipFile);
            // File srcFile = new File();
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            for (int i = 0; i < _files.length; i++) {
                Log.d("add:", _files[i]);
                Log.v("Compress", "Adding: " + _files[i]);
                FileInputStream fi = new FileInputStream(_files[i]);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteData(String inputFolderPath) {
        File myFile = new File(inputFolderPath);
        myFile.delete();
    }

    public void SendToFtp(int num, String filename) {
        //check mo d2 kung my net riz
        FTPClient con = null;

        try {
            con = new FTPClient();
            //con.connect("122.55.5.206", 21); //122.55.5.206
                                             //ftpmanager
                                             //bf@rf+p

            //local api
            con.connect("192.168.1.106", 21);

            /*if (con.login("ftpmanager", "bf@rf+p")) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);*/

            //lcoal api
            if (con.login("bfar-pc", "abc123")) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);

                if (num == 1) {
                    File files[] = Environment.getExternalStorageDirectory().listFiles();
                    for (File f : files) {
                        String fullPath = f.getAbsolutePath();
                        int dot = fullPath.lastIndexOf(".");
                        int directory = fullPath.lastIndexOf("/");
                        String ext = fullPath.substring(dot + 1);
                        String xfilname = fullPath.substring(directory + 1);
                        int myid = fullPath.lastIndexOf("_");
                        // Log.e(TAG,"count "+myid);
                        String GetId = fullPath.substring(myid + 1);
                        // Log.e(TAG,"count "+GetId);
                        String finalID = GetId.substring(0, GetId.length() - 4); //getting the id for update :)
                        // Log.e(TAG,"count "+finalID);
                        if (ext.equals("zip")) {
                            Log.e("zip", "" + xfilname);
                            FileInputStream in = new FileInputStream(new File(fullPath));
                            boolean result = con.storeFile("/" + xfilname, in);
                            in.close();

                            if (result == true) {
                                Log.v("upload result", "succeeded" + GetId);
                                // DeleteData(PATH + "/" + xfilname);
                                Log.e("testID", "the id is " + finalID);
                                useDBTables.UpdateIsSendEqTrue(object,Integer.parseInt(finalID));
                                //DeleteData(PATH + "/" + xfilname);

                            }


                        }
                    }
                } else {

                    String data = PATH + "/" + filename;
                    FileInputStream in = new FileInputStream(new File(data));
                    boolean result = con.storeFile("/" + filename, in);
                    int myid = data.lastIndexOf("_");
                    String GetId = data.substring(myid + 1);
                    String finalID = GetId.substring(0, GetId.length() - 4);

                    in.close();
                    if (result == true) {
                        Log.e("upload result", "succeeded");
                        Log.e("test id", "the id is "+finalID);
                        useDBTables.UpdateIsSendEqTrue(object,Integer.parseInt(finalID));
                         //DeleteData(PATH+"/"+filename);
                    }

                }
                con.logout();
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  new asd().execute();
    }


    public void CheckServer(int num, String filename) {
        String ans;
        try {
            // Create a new HTTP Client
            DefaultHttpClient defaultClient = new DefaultHttpClient();
            // Setup the get request
            HttpGet httpGetRequest = new HttpGet(JsonUrl);

            // Execute the request in the client
            HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
            // Grab the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();

            // Instantiate a JSON object from the request response
            JSONObject jsonObject = new JSONObject(json);

            ans = jsonObject.getString("message");
            if (ans != null) {
                SendToFtp(num, filename);
            }
            Log.e(TAG, "jsonREsponse " + ans);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isConnectingToInternet() {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.e(TAG, "connected");
            return true;

        } else {
            Log.e(TAG, "not connected");
        }

        return false;

    }


}