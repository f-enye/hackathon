package com.elvis.storyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Login extends Activity {

    public final ArrayList<String> jsonResult = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.loginButton);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.password);

                String email = username.getText().toString();
                String pass = password.getText().toString();

                Log.v("Creds", "email: " + email);
                Log.v("Creds", "pass: " + pass);
                String url = formURL(email, pass);
                String result = "";
                new GetLoginData().execute(url, result);
                Log.d("jsonResult", jsonResult.toString());
                if(Login.this.jsonResult.get(0).equals("true")){
                    new GetData().execute(url);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Login credentials invalid.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private class GetLoginData extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            String response;

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);

                HttpResponse httpResponse = httpclient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                response = EntityUtils.toString(httpEntity);
                Login.this.jsonResult.add(0, response);
                Log.d("response is", response);

                return new JSONObject(response);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result)
        {
            super.onPostExecute(result);
            try {
                JSONObject output = result.getJSONObject("valid");
                Log.d("GetLogin onPost", result.getString("valid"));
                Login.this.jsonResult.add(0, result.getString("valid"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class GetData extends AsyncTask<String, Void, JSONObject> {

        String JSONstring = "";
        @Override
        protected JSONObject doInBackground(String... params) {
            String response;

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                HttpResponse httpResponse = httpclient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                response = EntityUtils.toString(httpEntity);
                JSONstring = response;
                Log.d("response is", response);

                return new JSONObject(response);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result)
        {
            super.onPostExecute(result);
            Intent intent = new Intent(getBaseContext(), Inventory.class);
            intent.putExtra("JSON_RESULT", JSONstring);
            startActivity(intent);
        }
    }


    public String formURL(String email, String pass){
        StringBuilder url = new StringBuilder("http://76b334a8.ngrok.com/login/");
        url = url.append(email + "/" + pass);
        return url.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
