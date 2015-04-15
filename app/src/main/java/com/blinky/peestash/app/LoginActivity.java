package com.blinky.peestash.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.view.View.OnClickListener;


public class LoginActivity extends Activity {

    private Button btArtist, btEtablissement, btConnect;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login);

        btArtist = (Button) findViewById(R.id.btnRegisterArtist);
        btEtablissement = (Button) findViewById(R.id.btnRegisterEtablissement);
        btConnect = (Button) findViewById(R.id.btnLogin);

        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);

        // On met un Listener sur le bouton Artist
        btArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(LoginActivity.this, RegisterArtistActivity.class);
                startActivity(intent);
            }
        });

        // On met un Listener sur le bouton Etablissement musical
        btEtablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(LoginActivity.this, RegisterEtablissementActivity.class);
                startActivity(intent);
            }
        });
        // On met un Listener sur le bouton Se Connecter
        btConnect.setOnClickListener(new OnClickListener() {

            InputStream is = null;

            @Override
            public void onClick(View arg0) {
                //put the editText value into string variables
                String email = ""+etEmail.getText().toString();
                String password = ""+etPassword.getText().toString();
                String result = null;
                //tag Connexion
                String tag = "connexion";

                //setting nameValuePairs
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                //adding string variables into the NameValuePairs
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                nameValuePairs.add(new BasicNameValuePair("tag", tag));

                //setting the connection to the database
                try{
                    //Setting up the default http client
                    HttpClient httpClient = new DefaultHttpClient();

                    //setting up the http post method
                    HttpPost httpPost = new HttpPost("http://peestash.peestash.fr/index.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    //getting the response
                    HttpResponse response = httpClient.execute(httpPost);

                    //setting up the entity
                    HttpEntity entity = response.getEntity();

                    //setting up the content inside the input stream reader
                    is = entity.getContent();

                }catch(ClientProtocolException e){

                    Log.e("ClientProtocole", "Log_tag");
                    String msg = "Erreur client protocole";

                }catch(IOException e)
                {
                    Log.e("Log_tag", "IOException");
                    e.printStackTrace();
                    String msg = "Erreur IOException";
                }
                try{
                    BufferedReader reader= new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    StringBuilder total = new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null) {
                        total.append(line);
                    }

                    is.close();

                    result= total.toString();


                    if(result.equals(null) || result.equals("[]"))
                    {
                        String msg="Erreur de connexion";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                    else {

                        String id = result.substring(7,11);
                        id = id.replace("\"","");
                        id = id.replace(",","");
                        id=id.trim();

                        if(result.indexOf("artiste")!=-1) {

                            String msg = "Vous êtes connecté en tant qu'artiste \n id =" + id + "\n";
                            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this, ArtistHomeActivity.class);
                            i.putExtra("id_user", id);
                            startActivity(i);

                        }
                        else if(result.indexOf("etablissement")!=-1) {

                         // String msg = "Vous êtes connecté en tant qu'établissement \n id =" + id + "\n";

                           // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this, EtablissementHomeActivity.class);
                            i.putExtra("id_user", id);
                            startActivity(i);
                        }


                    }
                } catch (Exception e) {
                    Log.i("tagconvertstr", "" + e.toString());
                }
            }
        });

    }

}
