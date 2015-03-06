package com.da.bfar.apps.boatr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.da.bfar.apps.boatr.DAO.BoatRegistrationDBConnection;
import com.da.bfar.apps.boatr.DAO.MunicipalUserJsonParser;
import com.da.bfar.apps.boatr.DAO.UseDBTables;
import com.da.bfar.apps.boatr.DAO.User;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GetMunicipalUserActivity extends ActionBarActivity {

    String temp_url = "http://192.168.1.106:82/api/user.php";
    String check_url = "http://192.168.1.106:82/api/sender.php";
    EditText municipalCode;
    Button button_submit;
    Button button_logout;
    Context context;
    ConnectionStats connectionStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_get_municipal_user);
        init();
        connectionStats = new ConnectionStats();
    }

    private void init(){
        municipalCode = (EditText) findViewById(R.id.EDITTEXT_municipalCode);
        button_submit = (Button) findViewById(R.id.BUTTON_submitMunicipalCode);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check internet connection if existing
                    if (isConnectingToInternet() == true) {
                        new CheckServerAsync().execute();
                    }
            }
        });

        button_logout = (Button) findViewById(R.id.BUTTON_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set Admin account disabled
                setAdminFlag();
                //call Activity Login
                intentLogin();
            }
        });
    }

    private boolean isConnectingToInternet() {

        Boolean internetCheck;

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.e("internet", "connected");
            internetCheck = true;
        } else {
            Log.e("internet", "not connected");
            internetCheck = false;
        }

        return internetCheck;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_municipal_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void intentLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void setAdminFlag(){
        UseDBTables dbTables = new UseDBTables(context);
        dbTables.UpdateAdminAccount();
        Log.e("Admin account","updated");
    }

    private class CheckServerAsync extends AsyncTask {

        public void CheckServer() {
            String ans;
            try {
                // Create a new HTTP Client
                DefaultHttpClient defaultClient = new DefaultHttpClient();
                // Setup the get request
                HttpGet httpGetRequest = new HttpGet(check_url);

                // Execute the request in the client
                HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
                // Grab the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                String json = reader.readLine();

                // Instantiate a JSON object from the request response
                JSONObject jsonObject = new JSONObject(json);

                ans = jsonObject.getString("message");
                if (ans != null) {
                    /*new GetToJsonAsync(context).execute();*/
                    connectionStats.setMsg("Success");

                }
                Log.e("Check Server", "jsonResponse " + ans);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Checking Internet connection...");
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            CheckServer();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(connectionStats.getMsg().equals("Success")){
                new GetToJsonAsync(context).execute();
            }
            progressDialog.dismiss();
            Toast.makeText(context, "Check internet connection done..", Toast.LENGTH_SHORT).show();
        }
    }

    private class GetToJsonAsync extends AsyncTask<User, String, String> {

        private static final String TAG = "Error!";
        private String JSON_USERNAME = "username";
        private String JSON_PASSWORD = "password";
        private String JSON_FULLNAME = "name";
        private String JSON_MUNICIPALCODE = "municipalid";
        private Context context;

        private GetToJsonAsync(Context context) {
            this.context = context;
        }

        ProgressDialog progressDialog;
        ArrayList<User> userArrayList = new ArrayList<>();
        MunicipalUserJsonParser jsonParser = new MunicipalUserJsonParser();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Syncing...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground (User... params) {
            getJson();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(context, "Syncing Done..", Toast.LENGTH_SHORT).show();
        }

        private void getJson() {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("municipalid", municipalCode.getText().toString()));
            String jobj = jsonParser.makeHttpRequest_StringResponse(temp_url, "GET", params);

            try {

                JSONArray temp_array = new JSONArray(jobj);
                Log.e(TAG, "temp_array-size:" + temp_array.length());

                UseDBTables dbTables = new UseDBTables(context);

                    for (int x = 0; x < temp_array.length(); x++) {
                        Log.e(TAG, "jsonparsing:***** ***** ******");
                        JSONObject temp_obj = temp_array.getJSONObject(x);
                        userArrayList.add(new User(temp_obj.getString(JSON_USERNAME),
                                temp_obj.getString(JSON_PASSWORD),
                                temp_obj.getString(JSON_FULLNAME),
                                temp_obj.getString(JSON_MUNICIPALCODE)));
                    }
                    dbTables.CreateUserTable(userArrayList.get(0));


                try {

                    long maxUnits = new BoatRegistrationDBConnection(context).getDaoUser().queryRawValue("Select COUNT(*) from tblUser");
                    Log.e("Count of User:",String.valueOf(maxUnits));

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                } catch (JSONException e) {
                 e.printStackTrace();
                }
        }

    }
}
