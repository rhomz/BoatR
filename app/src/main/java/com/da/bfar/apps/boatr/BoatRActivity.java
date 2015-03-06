package com.da.bfar.apps.boatr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.da.bfar.apps.boatr.DAO.BoatRegistrationDBConnection;
import com.da.bfar.apps.boatr.DAO.FtpSender;
import com.da.bfar.apps.boatr.DAO.MunicipalUserJsonParser;
import com.da.bfar.apps.boatr.DAO.UseDBTables;
import com.da.bfar.apps.boatr.fragment.FragmentAbout;
import com.da.bfar.apps.boatr.fragment.FragmentGearRegistration;
import com.da.bfar.apps.boatr.fragment.FragmentHelp;
import com.da.bfar.apps.boatr.fragment.FragmentHome;
import com.da.bfar.apps.boatr.fragment.FragmentLogout;
import com.da.bfar.apps.boatr.fragment.FragmentRegistration;
import com.da.bfar.apps.boatr.tabFragment.BasicInfoDone;
import com.da.bfar.apps.boatr.tabFragment.BoatRFormEngine;
import com.da.bfar.apps.boatr.tabFragment.BoatRegistrationFragment;
import com.da.bfar.apps.boatr.tabFragment.FishingGearsFragment;
import com.da.bfar.apps.boatr.tabFragment.FishingVesselDimension;
import com.da.bfar.apps.boatr.tabFragment.TabFragmentDone;
import com.j256.ormlite.stmt.QueryBuilder;
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

public class BoatRActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        FragmentGearRegistration.OnFragmentInteractionListener,
        FragmentRegistration.OnFragmentInteractionListener,
        FragmentHome.OnFragmentInteractionListener,
        FragmentLogout.OnFragmentInteractionListener,
        FragmentHelp.OnFragmentInteractionListener,
        FragmentAbout.OnFragmentInteractionListener,
        TabFragmentDone.OnFragmentInteractionListener,
        BoatRegistrationFragment.OnFragmentInteractionListener,
        BoatRFormEngine.OnFragmentInteractionListener,
        FishingVesselDimension.OnFragmentInteractionListener,
        viewFragment.OnFragmentInteractionListener,
        BasicInfoDone.OnFragmentInteractionListener,
        FishingGearsFragment.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    //url for check server
    String check_url = "http://192.168.1.122:82/api/sender.php";

    //url for fisherfolk data collection
    String temp_url = "http://192.168.1.122:82/api/fisherfolk.php";

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private BoatRegistration registration =new BoatRegistration();
    private  Uri fileUri;
    private String messageFromBoat;
    private String TAG = BoatRActivity.class.getSimpleName();
    private int typeOfRegistrationIndex, vesselTypeIndex;

    public int getVesselTypeIndex() {
        return vesselTypeIndex;
    }

    public void setVesselTypeIndex(int vesselTypeIndex) {
        this.vesselTypeIndex = vesselTypeIndex;
    }

    public int getTypeOfRegistrationIndex() {
        return typeOfRegistrationIndex;
    }

    public void setTypeOfRegistrationIndex(int typeOfRegistrationIndex) {
        this.typeOfRegistrationIndex = typeOfRegistrationIndex;
    }

    ConnectionStats connectionStats;
    Context context;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        connectionStats = new ConnectionStats();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_r);

        context = this;

        long maxUnits = 0;
        try {
            maxUnits = new BoatRegistrationDBConnection(context).getDaoFisherFolk().queryRawValue("Select COUNT(*) from tblFisherFolk");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.e("Count of FisherFolk:", String.valueOf(maxUnits));
        // new GlobalAsyncTask(registration,this).execute();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        long flagStat = 0;
        try {
            flagStat = new BoatRegistrationDBConnection(context).getDaoParticular().queryRawValue("SELECT Flag FROM tblParticular");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (flagStat==1) {
            if (checkInitialFisherFolk()==0){

                //Check internet connection if existing
                if (isConnectingToInternet()==true) {
                    new CheckServerAsync().execute();
                }
            }
            else {
                if (isConnectingToInternet()==true) {
                    new CheckFisherFolkServerAsync().execute();
                }
            }
        }
        UseDBTables useDBTables = new UseDBTables(context);
        useDBTables.setFlagByCondition("unset");
    }

    private int checkInitialFisherFolk(){
        int flag = 0;
        BoatRegistrationDBConnection orm = new BoatRegistrationDBConnection(context);

        try {
            if(orm.getDaoFisherFolk().queryForAll().isEmpty()){

                flag = 0;
                Log.e("Fisherfolk data","is empty");
            }
            else{
                flag = 1;
                Log.e("Fisherfolk data","is not empty");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
        //if(user list is empty){
        //create admin account;
        //call method for checking admin credentials

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

    private class CheckFisherFolkServerAsync extends AsyncTask {

        public void CheckServer() {
            String ans;
            try {
                // Create a new HTTP Client
                DefaultHttpClient defaultClient = new DefaultHttpClient();
                // Setup the get request
                HttpGet httpGetRequest = new HttpGet(check_url);

                // Execute the request in the client
                try {
                    HttpResponse httpResponse = defaultClient.execute(httpGetRequest);

                    //if connected
                    //if(httpResponse.getStatusLine().getStatusCode() == 404){
                        // Grab the response
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String json = reader.readLine();

                        // Instantiate a JSON object from the request response
                        JSONObject jsonObject = new JSONObject(json);

                        ans = jsonObject.getString("message");
                        if (ans != null) {
/*                    new GetToJsonAsync(context).execute();*/
                            connectionStats.setMsg("Success");

                        }
                        else
                            connectionStats.setMsg("Failed");
                        Log.e("Check Server", "jsonResponse " + ans);
                    //}

                }catch (Exception e){
                    connectionStats.setMsg("Failed");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Checking connection on BFAR-CO(Server)...");
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
            progressDialog.dismiss();
            Toast.makeText(context, "Checking internet connection done.", Toast.LENGTH_SHORT).show();
            if(connectionStats.getMsg().equals("Failed")|| connectionStats.getMsg().isEmpty()){
                Toast.makeText(context, "Error connecting to server. Your data will be saved on local database." + "", Toast.LENGTH_LONG).show();
            }
            else if(connectionStats.getMsg().equals("Success"))
                new UpdateFisherfolkDataAsync(context).execute();
        }
    }

    private class UpdateFisherfolkDataAsync extends AsyncTask<FisherFolk, String, String> {

        private static final String TAG = "Error!";
        private String JSON_CFRNO = "frno";
        private String JSON_Fullname = "fisherfolk";
        private String JSON_Address = "address";
        private String JSON_TaskSequence = "getid";
        private Context context;

        private UpdateFisherfolkDataAsync(Context context) {
            this.context = context;
        }


        ProgressDialog progressDialog;
        ArrayList<FisherFolk> FisherFolkArrayList = new ArrayList<>();
        MunicipalUserJsonParser jsonParser = new MunicipalUserJsonParser();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(FisherFolk... params) {
            getJson(getTopValue());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           FtpSender ftpSender = new FtpSender(context,new BoatRegistration());
            ftpSender.SendToFtp(1,null);

        }

        private String getTopValue(){
            String id = "";
            try {

                QueryBuilder<FisherFolk,String> qb = new BoatRegistrationDBConnection(context).getDaoFisherFolk().queryBuilder();
                qb.orderBy("TaskSequence", false);
                qb.limit(1);
                id  = qb.queryForFirst().getTaskSequence();
                Log.e("kmarx1 TAG",id);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return id;
        }



        private void getJson(String id) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("municipalid", BoatrSingleton.getInstance().getUserMunicipalCode()));
            params.add(new BasicNameValuePair("getid", id));

            String jobj = jsonParser.makeHttpRequest_StringResponse(temp_url, "GET", params);

            try {

                JSONArray temp_array = new JSONArray(jobj);
                Log.e(TAG, "temp_array-size:" + temp_array.length());

                UseDBTables dbTables = new UseDBTables(context);

                for (int x = 0; x < temp_array.length(); x++) {
                    Log.e(TAG, "jsonparsing:***** ***** ******");
                    JSONObject temp_obj = temp_array.getJSONObject(x);
                    FisherFolkArrayList.add(new FisherFolk(temp_obj.getString(JSON_CFRNO),
                            temp_obj.getString(JSON_Fullname),
                            temp_obj.getString(JSON_Address),
                            temp_obj.getString(JSON_TaskSequence)));
                    dbTables.CreateFisherFolkTable(FisherFolkArrayList.get(x));
                    Log.e("FisherFolk:", FisherFolkArrayList.get(x).getCFRNO() + ", " + FisherFolkArrayList.get(x).getFullname() + ", " + FisherFolkArrayList.get(x).getAddress() + ", " + FisherFolkArrayList.get(x).getTaskSequence());
                }

                try {

                    long maxUnits = new BoatRegistrationDBConnection(context).getDaoFisherFolk().queryRawValue("Select COUNT(*) from tblFisherFolk");
                    Log.e("Count of FisherFolk:",String.valueOf(maxUnits));

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }

    private class CheckServerAsync extends AsyncTask {

        public void CheckServer() {
            String ans;
            try {
                // Create a new HTTP Client
                DefaultHttpClient defaultClient = new DefaultHttpClient();
                // Setup the get request
                HttpGet httpGetRequest = new HttpGet(check_url);
                try{
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

                } else
                    connectionStats.setMsg("Failed");
                Log.e("Check Server", "jsonResponse " + ans);

                }catch (Exception e){
                    connectionStats.setMsg("Failed");
                }



            }catch (Exception e) {
                e.printStackTrace();
            }

        }

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Checking Internet Connection...");
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            Log.e(TAG,"CheckServerAsync - doInBackground");
            CheckServer();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            Toast.makeText(context, "Checking internet connection done.", Toast.LENGTH_SHORT).show();
            if(connectionStats.getMsg().equals("Failed")){
                Toast.makeText(context, "Error connecting to server. Your data will be saved on local database." + "", Toast.LENGTH_LONG).show();
            }
            else if(connectionStats.getMsg().equals("Success"))
                new GetToJsonAsync(context).execute();
        }
    }

    private class GetToJsonAsync extends AsyncTask<FisherFolk, String, String> {

        private static final String TAG = "Error!";
        private String JSON_CFRNO = "frno";
        private String JSON_Fullname = "fisherfolk";
        private String JSON_Address = "address";
        private String JSON_TaskSequence = "getid";
        private Context context;

        private GetToJsonAsync(Context context) {
            this.context = context;
        }


        ProgressDialog progressDialog;
        ArrayList<FisherFolk> FisherFolkArrayList = new ArrayList<>();
        MunicipalUserJsonParser jsonParser = new MunicipalUserJsonParser();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(this.context);
            progressDialog.setTitle("Downloading FisherFolk Data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground (FisherFolk... params) {
            getJson();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(context, "Downloading Done..", Toast.LENGTH_SHORT).show();

        }

        private void getJson() {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("municipalid", BoatrSingleton.getInstance().getUserMunicipalCode()));

            String jobj = jsonParser.makeHttpRequest_StringResponse(temp_url, "GET", params);

            try {

                JSONArray temp_array = new JSONArray(jobj);
                Log.e(TAG, "temp_array-size:" + temp_array.length());

                UseDBTables dbTables = new UseDBTables(context);

                for (int x = 0; x < temp_array.length(); x++) {
                    Log.e(TAG, "jsonparsing:***** ***** ******");
                    JSONObject temp_obj = temp_array.getJSONObject(x);
                    FisherFolkArrayList.add(new FisherFolk(temp_obj.getString(JSON_CFRNO),
                            temp_obj.getString(JSON_Fullname),
                            temp_obj.getString(JSON_Address),
                            temp_obj.getString(JSON_TaskSequence)));
                    dbTables.CreateFisherFolkTable(FisherFolkArrayList.get(x));
                    Log.e("FisherFolk:", FisherFolkArrayList.get(x).getCFRNO() + ", " + FisherFolkArrayList.get(x).getFullname() + ", " + FisherFolkArrayList.get(x).getAddress() + ", " + FisherFolkArrayList.get(x).getTaskSequence());
                }

                try {

                    long maxUnits = new BoatRegistrationDBConnection(context).getDaoFisherFolk().queryRawValue("Select COUNT(*) from tblFisherFolk");
                    Log.e("Count of FisherFolk:",String.valueOf(maxUnits));

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();*/
        selectItem(position);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.Home);
                break;
            case 1:
                mTitle = getString(R.string.Boat);
                break;
            case 2:
                mTitle = getString(R.string.Gear);
                break;
            case 3:
                mTitle = getString(R.string.Logout);
                break;
            case 4:
                mTitle = getString(R.string.Help);
                break;
            case 5:
                mTitle = getString(R.string.About);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.boat_r, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_boat_r, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((BoatRActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    private void selectItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FragmentHome.newInstance("","");
                break;

            case 1:
                fragment = viewFragment.newInstance("","");
                break;

            case 2:
                fragment = FragmentGearRegistration.newInstance("","");
                break;
            case 3:
                fragment = FragmentLogout.newInstance("","");
                toLogout();
                break;
            case 4:
                fragment = FragmentHelp.newInstance("","");
                break;
            case 5:
                fragment = FragmentAbout.newInstance("","");
                break;
            case 6:
                fragment = FragmentRegistration.newInstance("","");
                break;
            default:
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, "" + mTitle)
                .commit();

        onSectionAttached(position); //Change action bar title
    }

    private void toLogout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onInvalid(String message){
        this.messageFromBoat = message;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,1);
        alertDialog.setTitle("Please review:");
        alertDialog.setMessage(message);
        alertDialog.show();
    }
    public void onInvalid(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this,1);
        alertDialog.setTitle("Please review:");
        alertDialog.setMessage(this.messageFromBoat);

        alertDialog.show();
    }
    public BoatRegistration getBoat (){
        return registration;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri",fileUri);
    }
    protected  void onRestoreInstanceState(Bundle bundle){
        super.onRestoreInstanceState(bundle);
        fileUri = bundle.getParcelable("file_uri");
    }
    //TO close the touchpad in tablet
    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        final View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            if ( !isTouchInsideView(ev, currentFocus)) {
                ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isTouchInsideView(final MotionEvent ev, final View currentFocus) {
        final int[] loc = new int[2];
        currentFocus.getLocationOnScreen(loc);
        return ev.getRawX() > loc[0] && ev.getRawY() > loc[1] && ev.getRawX() < (loc[0] + currentFocus.getWidth())
                && ev.getRawY() < (loc[1] + currentFocus.getHeight());
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }


}
