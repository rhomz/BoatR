package com.da.bfar.apps.boatr;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.da.bfar.apps.boatr.DAO.BoatRegistrationDBConnection;
import com.da.bfar.apps.boatr.DAO.UseDBTables;
import com.da.bfar.apps.boatr.DAO.User;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginActivity extends ActionBarActivity {

    EditText EDITTEXT_password;
    EditText EDITTEXT_username;
    Button BUTTON_login;
    Admin admin;
    private String testing = "LN-KP-000537-2015";
    private String frNo;
    TextView substring;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        init();
        if(checkInitialAdmin() == 0){
            UseDBTables dbTables = new UseDBTables(context);
            dbTables.CreateAdminAccount();
            Log.e("Admin account", "created");
        }

    }

    private int checkInitialAdmin(){
        int flag = 0;
        BoatRegistrationDBConnection orm = new BoatRegistrationDBConnection(context);

        try {
            if(orm.getDaoAdmin().queryForAll().isEmpty()){

                flag = 0;
                Log.e("Admin account","does not exist");
            }
            else{
                flag = 1;
                Log.e("Admin account","exists");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
        //if(user list is empty){
        //create admin account;
        //call method for checking admin credentials

    }



    private void init(){

        EDITTEXT_username = (EditText) findViewById(R.id.EDITTEXT_username);
        EDITTEXT_password = (EditText) findViewById(R.id.EDITTEXT_password);
        BUTTON_login = (Button) findViewById(R.id.BUTTON_login);

        BUTTON_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAdminStatus() == 1)
                    checkAdminLogin(EDITTEXT_username.getText().toString(), EDITTEXT_password.getText().toString());
                else
                {
                    //check user credential
                    checkCredentials(EDITTEXT_username.getText().toString(), EDITTEXT_password.getText().toString());
                }

                UseDBTables useDBTables = new UseDBTables(context);
                useDBTables.setFlagByCondition("set");
                //Toast.makeText( context, "TEST DATA ADDED" + EDITTEXT_username.getText().toString() + " " + EDITTEXT_password.getText().toString(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    private int checkAdminStatus() {
        int flag = 0;
        BoatRegistrationDBConnection orm = new BoatRegistrationDBConnection(context);
        QueryBuilder queryBuilder = orm.getDaoAdmin().queryBuilder();
        try {
            queryBuilder.where().eq("isSetup", "1");
            //select * where setup = 1; means not yet synced
            PreparedQuery<Admin> preparedQuery = queryBuilder.prepare();
            if(orm.getDaoAdmin().query(preparedQuery).isEmpty()){
                flag = 0;
                Log.e("municipal user","synced");
            }
            else{
                flag = 1;
                Log.e("municipal user","not yet synced");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return flag;
    }

    private void checkAdminLogin(String adminUsername, String adminPassword){
        BoatRegistrationDBConnection orm = new BoatRegistrationDBConnection(context);
        QueryBuilder queryBuilder = orm.getDaoAdmin().queryBuilder();

        try {
            queryBuilder.where().eq("AdminUsername", adminUsername).and().eq("AdminPassword", adminPassword);
            PreparedQuery<Admin> preparedQuery = queryBuilder.prepare();
            if(!(orm.getDaoAdmin().query(preparedQuery).isEmpty())){
                intentSyncMunicipalUser();
                Log.e("Municipal User Syncing","Done");


            }
            else Toast.makeText(context, "WRONG USERNAME/PASSWORD", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void intentSyncMunicipalUser(){
        Intent intent = new Intent(this, GetMunicipalUserActivity.class);
        startActivity(intent);

    }

    private void checkCredentials(String username, String password){
        BoatRegistrationDBConnection orm = new BoatRegistrationDBConnection(context);
        BoatRegistrationDBConnection orm2 = new BoatRegistrationDBConnection(context);
        QueryBuilder queryBuilder = orm.getDaoUser().queryBuilder();
        QueryBuilder queryBuilder2 = orm.getDaoUser().queryBuilder();

        ArrayList<User> user = new ArrayList<User>();
        try {

            queryBuilder.where().eq("Username", username);
            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            if(!(orm.getDaoUser().query(preparedQuery).isEmpty())){
                queryBuilder2.where().eq("Username", username)
                        .and()
                        .eq("Password", password);

                PreparedQuery<User> preparedQuery2 = queryBuilder2.prepare();
                if(!(orm2.getDaoUser().query(preparedQuery2).isEmpty())){

                    //user = (ArrayList<User>) orm2.getDaoUser().query(preparedQuery2);
                    intentLogin();
                    // Log.e(user.get(0).getUsername() + " " + user.get(0).getPassword(), "CATCH");
                    BoatrSingleton.getInstance().setUserUsername(orm2.getDaoUser().query(preparedQuery2).get(0).getUsername());
                    BoatrSingleton.getInstance().setUserPassword(orm2.getDaoUser().query(preparedQuery2).get(0).getPassword());
                    BoatrSingleton.getInstance().setUserFullname(orm2.getDaoUser().query(preparedQuery2).get(0).getFullname());
                    BoatrSingleton.getInstance().setUserMunicipalCode(orm2.getDaoUser().query(preparedQuery2).get(0).getMunicipalityCode());
                    Toast.makeText(context, "LOGIN SUCCESS", Toast.LENGTH_LONG).show();

                }
                else Toast.makeText(context, "WRONG PASSWORD", Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(context, "USER DOES NOT EXIST", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

   /* private void createSampleData(){


        BoatRegistrationDBConnection ormLiteDbHelper = new BoatRegistrationDBConnection(context);
        try {
            //ormLiteDbHelper.getDaoUser().create(BoatrSingleton.getInstance()BoatrSingleton.getInstance().setUserUsername("admin"), BoatrSingleton.getInstance().setUserPassword("admin"), BoatrSingleton.getInstance().setUserFullname("Admin Dela Cruz"), BoatrSingleton.getInstance().setUserMunicipalCode("071242000"));
            ormLiteDbHelper.getDaoUser().create(new User("Admin", "Admin", "Admin", "071242000"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


    private void intentLogin(){
        Intent intent = new Intent(this, BoatRActivity.class);
        startActivity(intent);

        /*intent.putExtra("username", user.get(0).getUsername());
        intent.putExtra("password", user.get(0).getPassword());
        intent.putExtra("municipality", user.get(0).getMunicipalityCode());

        startActivity(intent);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
