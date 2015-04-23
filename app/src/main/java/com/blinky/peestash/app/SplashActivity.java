package com.blinky.peestash.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        Button btn = (Button) findViewById(R.id.btn);

        View.OnClickListener listnr = new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {

                                  Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                                  startActivity(i);

                    }
                }).start();
            }

        };

        btn.setOnClickListener(listnr);
    }

}
