package com.example.mobilesdkdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.authentication.api.DisconnectionCallback;
import com.rbbn.cpaas.mobile.call.api.CallService;
import com.rbbn.cpaas.mobile.utilities.Globals;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CallService callService = CPaaSMeneger.getInstance().getCpaas().getCallService();

        MyListener myListener = new MyListener();
        try {
            callService.setCallApplicationListener(myListener);
        } catch (MobileException e) {
            e.printStackTrace();
        }

        // create fragments
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.chattFragment, R.id.usersFragment, R.id.callFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        preferences=getSharedPreferences("SHARED_PREF", MODE_PRIVATE);

    }
    // logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Context context = getApplicationContext();
        Globals.setApplicationContext(context);

        // clear the shared preferences
        SharedPreferences.Editor editor= preferences.edit();
        editor.clear();
        editor.apply();

        // logout
        if (item.getItemId() == R.id.logout) {
            try {
                CPaaSMeneger.getInstance().getCpaas().getAuthentication().disconnect(new DisconnectionCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("CPaaS.Authentication", "Disconnected from websocket successfully");
                    }

                    @Override
                    public void onFail(MobileError mobileError) {
                        Log.i("CPaaS.Authentication", "Disconnection from websocket failed");
                    }
                });
            } catch (MobileException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return true;
        }
        return false;
    }

}