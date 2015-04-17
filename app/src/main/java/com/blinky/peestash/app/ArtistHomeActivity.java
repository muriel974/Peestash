package com.blinky.peestash.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ArtistHomeActivity extends Activity {

    private Button btn_editProfil;
    String id_user="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_home);

        btn_editProfil = (Button) findViewById(R.id.btneditProfil);

        Bundle var = this.getIntent().getExtras();
        id_user=var.getString("id_user");

        // On met un Listener sur le bouton Artist
        btn_editProfil.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                        Intent i = new Intent(ArtistHomeActivity.this, EditArtistProfilActivity.class);
                        i.putExtra("id_user", id_user);
                        startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_artist_home, menu);
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
}
