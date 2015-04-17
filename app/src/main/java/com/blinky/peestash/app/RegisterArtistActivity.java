package com.blinky.peestash.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import android.view.View.OnClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class RegisterArtistActivity extends Activity {

    EditText etPseudo, etEmail, etConfirmEmail, etPassword, etConfirmMdp;
    Button btSubmit;
    String pseudo, email, confirmEmail, password, confirmPassword, tag, msg;
    Verify test = new Verify();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_artist);

        etPseudo = (EditText) findViewById(R.id.editPseudo);
        etEmail = (EditText) findViewById(R.id.editEmail);
        etConfirmEmail = (EditText) findViewById(R.id.editConfirmEmail);
        etConfirmMdp = (EditText) findViewById(R.id.editConfirmMdp);
        etPassword = (EditText) findViewById(R.id.editPassword);
        btSubmit = (Button) findViewById(R.id.btregister);


        // On met un Listener sur le bouton
        btSubmit.setOnClickListener(new OnClickListener() {
            InputStream is = null;


            @Override
            public void onClick(View arg0) {
                //put the editText value into string variables
                pseudo = "" + etPseudo.getText().toString();
                email = "" + etEmail.getText().toString();
                confirmEmail = "" + etConfirmEmail.getText().toString();
                password = "" + etPassword.getText().toString();
                confirmPassword = "" + etConfirmMdp.getText().toString();
                //tag création d'un compte artiste
                tag = "artist_register";

                //setting nameValuePairs
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                //adding string variables into the NameValuePairs
                nameValuePairs.add(new BasicNameValuePair("pseudo", pseudo));
                //nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                nameValuePairs.add(new BasicNameValuePair("tag", tag));

                String emailvalid = "ok";

                    if(test.checkEmailWriting(email))
                    {
                        if(test.checkEmail(email, confirmEmail))
                        {
                            emailvalid = "ok";
                            nameValuePairs.add(new BasicNameValuePair("email", email));
                        } else
                        {
                            emailvalid = "no";
                            msg = "Veuillez écrire l'email et la confirmation d'email correctement";
                        }
                    } else
                    {
                        emailvalid = "no";
                        msg = "Veuillez écrire correctement votre email";
                    }

                if (emailvalid == "ok") {
                    //setting the connection to the database
                    try {
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

                        //displaying a toast message if the data is entered in the database
                         msg = "Données enregistrées en BDD artist";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                    } catch (ClientProtocolException e) {
                        Log.e("ClientProtocole", "Log_tag");
                        msg = "Erreur client protocole";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Log.e("Log_tag", "IOException");
                        e.printStackTrace();
                        msg = "Erreur IOException";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                }
            }

        });

    }

}