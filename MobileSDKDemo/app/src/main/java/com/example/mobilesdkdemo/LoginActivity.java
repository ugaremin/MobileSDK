package com.example.mobilesdkdemo;

import androidx.appcompat.app.AppCompatActivity;

import com.rbbn.cpaas.mobile.utilities.Globals;
import com.rbbn.cpaas.mobile.utilities.webrtc.ICEServers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rbbn.cpaas.mobile.authentication.api.ConnectionCallback;
import com.rbbn.cpaas.mobile.utilities.Configuration;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    String YOUR_ACCESS_TOKEN, YOUR_ID_TOKEN, username, password;
    EditText usernameField,passwordField;
    Button login_button;
    CheckBox remember;
    boolean isRemembered=false;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set router context
        Router.getInstance().setContext(getApplicationContext());

        login_button = findViewById(R.id.login_button);
        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        remember=findViewById(R.id.remember_check);

        Configuration.getInstance().setRestServerUrl("oauth-cpaas.att.com");
        Configuration.getInstance().setRestServerPort(443);

        //login config

        Configuration configuration = Configuration.getInstance();
        configuration.setRestServerUrl("oauth-cpaas.att.com");

        ICEServers iceServers = new ICEServers();
        iceServers.addICEServer("turns:turn-ucc-1.kandy:443?transport=tcp");
        iceServers.addICEServer("turns:turn-ucc-2.kandy.io:443?transport=tcp");
        iceServers.addICEServer("stun:turn-ucc-1.kandy.io:3478?transport=udp");
        iceServers.addICEServer("stun:turn-ucc-2.kandy.io:3478?transport=udp");
        configuration.setICEServers(iceServers);

        sharedPreferences=getSharedPreferences("SHARED_PREF",MODE_PRIVATE);
        isRemembered=sharedPreferences.getBoolean("check_remember", false); // default value
        /*
        if (isRemembered){
            Intent intent=new Intent(Router.getInstance().getContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Router.getInstance().getContext().startActivity(intent);
            finish();
        }

         */



        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                isActiveButton(false);

                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
                boolean checkRemember=remember.isChecked();
                boolean checkMail=isValidEmail(username);
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    isActiveButton(true);

                }

                else if (!checkMail){
                    Toast.makeText(LoginActivity.this, "Wrong email format", Toast.LENGTH_SHORT).show();
                    isActiveButton(true);

                }
                else {
                    Toast.makeText(LoginActivity.this, "Logging in", Toast.LENGTH_SHORT).show();
                }

                // shared preferences
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("username",username);
                editor.putString("password", password);
                editor.putBoolean("check_remember", checkRemember);
                editor.apply();

                // start get token

                HashMap<String,String> requestMap=new HashMap<>();
                requestMap.put("username",username);
                requestMap.put("password",password);
                requestMap.put("client_id","PUB-sdkmobile.ryfq.MSDK");
                requestMap.put("grant_type","password");
                requestMap.put("scope","");
                requestMap.put("client_secret","");

                new AccessAndIdTokenTask().execute(requestMap);

                Context context = getApplicationContext();
                Globals.setApplicationContext(context);

                // end get token
            }
        });

    }

    private class AccessAndIdTokenTask extends AsyncTask<HashMap<String,String>, Void, String> {


        @Override
        protected String doInBackground(HashMap<String,String>... params) {
            HashMap<String,String> _requestMap=params[0];
            String requestUrl = "https://oauth-cpaas.att.com/cpaas/auth/v1/token";

            return requestAccessAndIdToken(requestUrl, _requestMap.get("username"), _requestMap.get("password"), _requestMap.get("client_id"), _requestMap.get("client_secret"), _requestMap.get("scope"));
        }

        @Override
        protected void onPostExecute(String result) {

            if (result != null) {
                parseAccessAndIDTokenResult(result);

                // start login
                int lifetime = 3600; //in seconds

                List<ServiceInfo> services = new ArrayList<>();
                services.add(new ServiceInfo(ServiceType.CALL, true));
                services.add(new ServiceInfo(ServiceType.CHAT, true));

                CPaaS cPaaS = new CPaaS(services);
                CPaaSMeneger.getInstance().setCpaas(cPaaS);
                try {
                    cPaaS.getAuthentication().connect(YOUR_ID_TOKEN, YOUR_ACCESS_TOKEN, lifetime, new ConnectionCallback() {
                        @Override
                        public void onSuccess(String connectionToken) {

                            Log.i("CPaaS.Authentication","Connected to websocket successfully");

                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();


                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onFail(MobileError error) {
                            Log.i("CPaaS.Authentication","Connection to websocket failed");
                            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                            isActiveButton(true);

                        }
                    });
                } catch (MobileException e) {
                    e.printStackTrace();
                }

                // end login

            }

            else if (result==null && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                isActiveButton(true);
            }

        }
    }
    private String requestAccessAndIdToken(String requestUrl, String username, String password,
                                           String client_id, String client_secret, String scope) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(requestUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            urlConnection.setDoOutput(true);

            OutputStream outputStream = null;
            try {
                StringBuilder bodyBuilder = new StringBuilder();
                bodyBuilder.append("grant_type=password&");
                bodyBuilder.append("username=").append(username).append("&");
                bodyBuilder.append("password=").append(password).append("&");
                bodyBuilder.append("client_id=").append(client_id).append("&");
                bodyBuilder.append("client_secret=").append(client_secret).append("&");
                bodyBuilder.append("scope=").append(scope);

                outputStream = urlConnection.getOutputStream();
                outputStream.write(bodyBuilder.toString().getBytes());
                outputStream.flush();
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;
            if ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception exception) {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            return null;
        }
    }
    public  void parseAccessAndIDTokenResult(String result) {

        try {
            JSONObject tokenJSONObject = new JSONObject(result);
            YOUR_ACCESS_TOKEN=tokenJSONObject.getString("access_token");
            YOUR_ID_TOKEN=tokenJSONObject.getString("id_token");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Button active or inactive
    public void isActiveButton(boolean state){
        if (state==true){
            login_button.setEnabled(true);
            usernameField.setEnabled(true);
            passwordField.setEnabled(true);
        }

        else {
            login_button.setEnabled(false);
            usernameField.setEnabled(false);
            passwordField.setEnabled(false);
        }
    }

    // check mail format
    public boolean isValidEmail(String email)
    {
        String emailRegex ="^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        if(email.matches(emailRegex))
        {
            return true;
        }
        return false;
    }

}