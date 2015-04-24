package com.blinky.peestash.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.view.View.OnClickListener;


public class EditArtistProfilActivity extends Activity implements AdapterView.OnItemSelectedListener {

    String id_user = "", type="";
    private EditText editPseudo, editEmail, editConfirmEmail, editAdress, editCP, editNom, editPrenom, editVille, editPays, editTelMobile,
            editTelFixe, editSoundcloud, editSiteweb, editPassword, editConfirmMdp;
    TextView affichageEmail;
    int i;
    ImageView img;
    private Button btnSave;
    private String pseudo = "", nom = "", prenom = "", age = "", email = "", confirmEmail="", ville = "", adresse = "", cp = "", pays = "",
            telportable = "", telfixe = "", dispo = "", soundcloud = "", siteweb = "", imgUrl = "", genre_musical = "", password = "", confirmMdp="";
    private CheckBox rock, pop, metal, jazz, funk, electro, blues, rap, folk, classique, lundi, mardi, mercredi, jeudi, vendredi, samedi, dimanche;
    private String genremusical = "", disponibilites = "";

    private ArrayList<String> genrelist = new ArrayList<String>();
    private ArrayList<String> dispolist = new ArrayList<String>();
    Verify test = new Verify();
    String msg="";
    ProgressDialog progress;
    List<String> categories;
    ArrayAdapter<String> dataAdapter;
    Spinner spinnerAge;
    private WebView wv;

    String html;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_artist_profil);

        // Spinner element
        spinnerAge = (Spinner) findViewById(R.id.spinnerAge);
        // Spinner Drop down elements
        categories = new ArrayList<String>();

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        //tag récupération des informations de profil artiste

        Bundle var = this.getIntent().getExtras();
        id_user = var.getString("id_user");
        affichageEmail = (TextView) findViewById(R.id.affichageEmail);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editConfirmEmail = (EditText) findViewById(R.id.editConfirmEmail);
        editPseudo = (EditText) findViewById(R.id.editPseudo);
        editAdress = (EditText) findViewById(R.id.editAdress);
        editCP = (EditText) findViewById(R.id.editCP);
        editNom = (EditText) findViewById(R.id.editNom);
        editPrenom = (EditText) findViewById(R.id.editPrenom);
        editVille = (EditText) findViewById(R.id.editVille);
        editPays = (EditText) findViewById(R.id.editPays);
        editTelMobile = (EditText) findViewById(R.id.editTelMobile);
        editTelFixe = (EditText) findViewById(R.id.editTelFixe);
        editSoundcloud = (EditText) findViewById(R.id.editSoundcloud);
        editSiteweb = (EditText) findViewById(R.id.editSiteweb);
        img = (ImageView) findViewById(R.id.img);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editConfirmMdp = (EditText) findViewById(R.id.editConfirmMdp);

        //récupération des checkbox genres musicaux
        rock = (CheckBox) findViewById(R.id.rock);
        pop = (CheckBox) findViewById(R.id.pop);
        metal = (CheckBox) findViewById(R.id.metal);
        rap = (CheckBox) findViewById(R.id.rap);
        funk = (CheckBox) findViewById(R.id.funk);
        classique = (CheckBox) findViewById(R.id.classique);
        blues = (CheckBox) findViewById(R.id.blues);
        electro = (CheckBox) findViewById(R.id.electro);
        folk = (CheckBox) findViewById(R.id.folk);
        jazz = (CheckBox) findViewById(R.id.jazz);

        //récupération des checkbox disponibilités
        lundi = (CheckBox) findViewById(R.id.dispolundi);
        mardi = (CheckBox) findViewById(R.id.dispomardi);
        mercredi = (CheckBox) findViewById(R.id.dispomercredi);
        jeudi = (CheckBox) findViewById(R.id.dispojeudi);
        vendredi = (CheckBox) findViewById(R.id.dispovendredi);
        samedi = (CheckBox) findViewById(R.id.disposamedi);
        dimanche = (CheckBox) findViewById(R.id.dispodimanche);

        btnSave = (Button) findViewById(R.id.btnSave);

        addListenerOnChkWindows();
        int id;
        id=Integer.parseInt(id_user);
        new ReadProfilTask().execute(id);

        wv = (WebView)findViewById(R.id.webView);

        // On met un Listener sur le bouton Artist
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {

                                //update datas in database
                                InputStream is = null;
                                String result = null;
                                String tag = "update_ArtistProfil";

                                nom = "" + editNom.getText().toString();
                                prenom = "" + editPrenom.getText().toString();
                                pseudo = "" + editPseudo.getText().toString();
                                email = "" + editEmail.getText().toString();
                                confirmEmail = "" + editConfirmEmail.getText().toString();
                                adresse = "" + editAdress.getText().toString();
                                cp = "" + editCP.getText().toString();
                                ville = "" + editVille.getText().toString();
                                pays = "" + editPays.getText().toString();
                                telfixe = "" + editTelFixe.getText().toString();
                                telportable = "" + editTelMobile.getText().toString();
                                dispo = "" + dispolist.toString();
                                soundcloud = "" + editSoundcloud.getText().toString();
                                siteweb = "" + editSiteweb.getText().toString();
                                age = "" + age;
                                genre_musical = "" + genrelist.toString();
                                password = "" + editPassword.getText().toString();
                                confirmMdp = "" + editConfirmMdp.getText().toString();

                                //setting nameValuePairs
                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                                //adding string variables into the NameValuePairs
                                nameValuePairs.add(new BasicNameValuePair("tag", tag));
                                nameValuePairs.add(new BasicNameValuePair("id", id_user));
                                nameValuePairs.add(new BasicNameValuePair("nom", nom));
                                nameValuePairs.add(new BasicNameValuePair("prenom", prenom));
                                nameValuePairs.add(new BasicNameValuePair("pseudo", pseudo));
                                nameValuePairs.add(new BasicNameValuePair("adresse", adresse));
                                nameValuePairs.add(new BasicNameValuePair("code_postal", cp));
                                nameValuePairs.add(new BasicNameValuePair("ville", ville));
                                nameValuePairs.add(new BasicNameValuePair("pays", pays));
                                nameValuePairs.add(new BasicNameValuePair("tel_fixe", telfixe));
                                nameValuePairs.add(new BasicNameValuePair("tel_portable", telportable));
                                nameValuePairs.add(new BasicNameValuePair("disponibilites", dispo));
                                nameValuePairs.add(new BasicNameValuePair("soundcloud", soundcloud));
                                nameValuePairs.add(new BasicNameValuePair("siteweb", siteweb));
                                nameValuePairs.add(new BasicNameValuePair("age", age));
                                nameValuePairs.add(new BasicNameValuePair("genre_musical", genre_musical));

                                String emailvalid="ok",  passwordvalid = "ok", msg= "";

                                if(email!="") {
                                    if(test.checkEmailWriting(email)) {

                                        if (test.checkEmail(email, confirmEmail)) {
                                            emailvalid="ok";
                                            nameValuePairs.add(new BasicNameValuePair("email", email));
                                        }
                                        else {
                                            emailvalid="no";
                                            msg = "Veuillez écrire correctement l'email et la confirmation d'e-mail.\n";
                                        }
                                    } else
                                    {
                                        emailvalid ="no";
                                        msg = "Veuillez écrire un email correct.\n";
                                    }

                                }else {
                                    email = "" + affichageEmail.getText().toString();
                                    nameValuePairs.add(new BasicNameValuePair("email", email));
                                }
                                if(password!="") {

                                    if (test.checkMdpWriting(password)) {
                                        if (test.checkMdp(password, confirmMdp)) {
                                            passwordvalid = "ok";
                                            nameValuePairs.add(new BasicNameValuePair("password", password));
                                        } else {
                                            passwordvalid = "no";
                                            msg = msg + "Veuillez écrire votre password et votre confirmation de password correctement\n";
                                        }

                                    } else {
                                        passwordvalid = "no";
                                        msg = msg + "Votre mot de passe doit contenir au minimum 3 caractères.\n";
                                    }
                                }else {
                                    password = "" + editPassword.getText().toString();
                                    nameValuePairs.add(new BasicNameValuePair("password", password));
                                }

                                if(emailvalid == "ok" && passwordvalid == "ok") {
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
                                        msg = "Données modifiées en BDD artist";
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
                                    msg ="";
                                }
                                      }
                        });

                    }
                }).start();

            }

        });

        // Spinner click listener
        spinnerAge.setOnItemSelectedListener(this);

        for (i = 16; i < 90; i++) {
            categories.add(String.valueOf(i));
        }
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Button btn = (Button) findViewById(R.id.btnImage);

        View.OnClickListener listnr = new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        type="artiste";
                        Intent i = new Intent(EditArtistProfilActivity.this, UploadActivity.class);
                        i.putExtra("id_user", id_user);
                        i.putExtra("type", type);
                        startActivity(i);

                    }
                }).start();
            }

        };

        btn.setOnClickListener(listnr);

    }

    public void addListenerOnChkWindows() {

        //listener sur les checkbox des genres musicaux
        rock.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "rock";
                    genrelist.add(genremusical);

                } else {
                    genremusical = "rock";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }
            }
        });
        pop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "pop";
                    genrelist.add(genremusical);
                } else {
                    genremusical = "pop";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }

            }
        });
        metal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "metal";
                    genrelist.add(genremusical);
                } else {
                    genremusical = "metal";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }

            }
        });
        classique.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "classique";
                    genrelist.add(genremusical);
                } else {
                    genremusical = "classique";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }

            }
        });
        folk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "folk";
                    genrelist.add(genremusical);
                } else {
                    genremusical = "folk";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }
            }
        });
        funk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "funk";
                    genrelist.add(genremusical);
                } else {
                    genremusical = "funk";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }
            }
        });
        jazz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "jazz";
                    genrelist.add(genremusical);
                } else {
                    genremusical = "jazz";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }
            }
        });
        rap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "rap";
                    genrelist.add(genremusical);
                } else {
                    genremusical = "rap";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }
            }
        });
        blues.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "blues";
                    genrelist.add(genremusical);
                } else {
                    genremusical = "blues";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }
            }
        });
        electro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    genremusical = "electro";
                    genrelist.add(genremusical);
                } else {
                    genremusical = "electro";
                    int pos = genrelist.indexOf(genremusical);
                    genrelist.remove(pos);
                }
            }
        });
        //listener sur les checkbox des jours de disponibilité
        lundi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    disponibilites = "lundi";
                    dispolist.add(disponibilites);
                } else {
                    disponibilites = "lundi";
                    int pos = dispolist.indexOf(disponibilites);
                    dispolist.remove(pos);
                }
            }
        });
        mardi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    disponibilites = "mardi";
                    dispolist.add(disponibilites);
                } else {
                    disponibilites = "mardi";
                    int pos = dispolist.indexOf(disponibilites);
                    dispolist.remove(pos);
                }
            }
        });
        mercredi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    disponibilites = "mercredi";
                    dispolist.add(disponibilites);
                } else {
                    disponibilites = "mercredi";
                    int pos = dispolist.indexOf(disponibilites);
                    dispolist.remove(pos);
                }
            }
        });
        jeudi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    disponibilites = "jeudi";
                    dispolist.add(disponibilites);
                } else {
                    disponibilites = "jeudi";
                    int pos = dispolist.indexOf(disponibilites);
                    dispolist.remove(pos);
                }
            }
        });
        vendredi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    disponibilites = "vendredi";
                    dispolist.add(disponibilites);
                } else {
                    disponibilites = "vendredi";
                    int pos = dispolist.indexOf(disponibilites);
                    dispolist.remove(pos);
                }
            }
        });
        samedi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    disponibilites = "samedi";
                    dispolist.add(disponibilites);
                } else {
                    disponibilites = "samedi";
                    int pos = dispolist.indexOf(disponibilites);
                    dispolist.remove(pos);
                }
            }
        });
        dimanche.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    disponibilites = "dimanche";
                    dispolist.add(disponibilites);
                } else {
                    disponibilites = "dimanche";
                    int pos = dispolist.indexOf(disponibilites);
                    dispolist.remove(pos);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        age = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        //   Toast.makeText(parent.getContext(), "Age: " + age, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class ReadProfilTask extends AsyncTask<Integer, Void, InputStream> {
        int i, pos;
        String result = null;
        String tag = "read_ArtistProfil";

        protected InputStream doInBackground(Integer... id) {

            InputStream is = null;
            //setting nameValuePairs
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            //adding string variables into the NameValuePairs
            nameValuePairs.add(new BasicNameValuePair("tag", tag));
            nameValuePairs.add(new BasicNameValuePair("id_user", id_user));

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

            } catch (ClientProtocolException e) {

                Log.e("ClientProtocole", "Log_tag");
                String msg = "Erreur client protocole";

            } catch (IOException e) {
                Log.e("Log_tag", "IOException");
                e.printStackTrace();
                String msg = "Erreur IOException";
            }

            return is;
        }

        protected void onProgressUpdate(Void params) {

        }
        protected void onPreExecute() {
            progress = new ProgressDialog(EditArtistProfilActivity.this);
            progress.setMessage("Chargement de vos informations de profil...");
            progress.show();
        }

        protected void onPostExecute(InputStream is) {

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder total = new StringBuilder();
                String json = reader.readLine();
                JSONTokener tokener = new JSONTokener(json);
                JSONArray finalResult = new JSONArray(tokener);
                Bitmap imgurl;

                // Spinner element
                spinnerAge = (Spinner) findViewById(R.id.spinnerAge);

                // Access by key : value
                for (i = 0; i < finalResult.length(); i++) {
                    JSONObject element = finalResult.getJSONObject(0);

                    pseudo = element.getString("pseudo");
                    email = element.getString("email");
                    adresse = element.getString("adresse");
                    cp = element.getString("code_postal");
                    nom = element.getString("nom");
                    prenom = element.getString("prenom");
                    ville = element.getString("ville");
                    pays = element.getString("pays");
                    telportable = element.getString("tel_portable");
                    telfixe = element.getString("tel_fixe");
                    dispo = element.getString("disponibilites");
                    soundcloud = element.getString("soundcloud");
                    siteweb = element.getString("siteweb");
                    imgUrl = element.getString("image_url");
                    genre_musical = element.getString("genre_musical");
                    age = element.getString("age");

                    InputStream in = new java.net.URL(imgUrl).openStream();
                    imgurl = BitmapFactory.decodeStream(in);
                    img.setImageBitmap(imgurl);

                    editNom.setText(nom);
                    editPrenom.setText(prenom);
                    editPseudo.setText(pseudo);
                    affichageEmail.setText(email);
                    editAdress.setText(adresse);
                    editCP.setText(cp);
                    editVille.setText(ville);
                    editPays.setText(pays);
                    editTelMobile.setText(telportable);
                    editTelFixe.setText(telfixe);
                    editSoundcloud.setText(soundcloud);
                    editSiteweb.setText(siteweb);
                    editPassword.setText(password);

                    //verification et affichage des genres musicaux en bdd
                    pos = genre_musical.indexOf("rock");
                    if (pos != -1) {
                        rock.setChecked(true);
                        genrelist.add("rock");
                    }
                    pos = genre_musical.indexOf("pop");
                    if (pos != -1) {
                        pop.setChecked(true);
                        genrelist.add("pop");
                    }
                    pos = genre_musical.indexOf("metal");
                    if (pos != -1) {
                        metal.setChecked(true);
                        genrelist.add("metal");
                    }
                    pos = genre_musical.indexOf("folk");
                    if (pos != -1) {
                        folk.setChecked(true);
                        genrelist.add("folk");
                    }
                    pos = genre_musical.indexOf("funk");
                    if (pos != -1) {
                        funk.setChecked(true);
                        genrelist.add("funk");
                    }
                    pos = genre_musical.indexOf("classique");
                    if (pos != -1) {
                        classique.setChecked(true);
                        genrelist.add("classique");
                    }
                    pos = genre_musical.indexOf("rap");
                    if (pos != -1) {
                        rap.setChecked(true);
                        genrelist.add("rap");
                    }
                    pos = genre_musical.indexOf("electro");
                    if (pos != -1) {
                        electro.setChecked(true);
                        genrelist.add("electro");
                    }
                    pos = genre_musical.indexOf("jazz");
                    if (pos != -1) {
                        jazz.setChecked(true);
                        genrelist.add("jazz");
                    }
                    pos = genre_musical.indexOf("blues");
                    if (pos != -1) {
                        blues.setChecked(true);
                        genrelist.add("blues");
                    }
                    //vérification et affichage des disponibilités en bdd
                    pos = dispo.indexOf("lundi");
                    if (pos != -1) {
                        lundi.setChecked(true);
                        dispolist.add("lundi");
                    }
                    pos = dispo.indexOf("mardi");
                    if (pos != -1) {
                        mardi.setChecked(true);
                        dispolist.add("mardi");
                    }
                    pos = dispo.indexOf("mercredi");
                    if (pos != -1) {
                        mercredi.setChecked(true);
                        dispolist.add("mercredi");
                    }
                    pos = dispo.indexOf("jeudi");
                    if (pos != -1) {
                        jeudi.setChecked(true);
                        dispolist.add("jeudi");
                    }
                    pos = dispo.indexOf("vendredi");
                    if (pos != -1) {
                        vendredi.setChecked(true);
                        dispolist.add("vendredi");
                    }
                    pos = dispo.indexOf("samedi");
                    if (pos != -1) {
                        samedi.setChecked(true);
                        dispolist.add("samedi");
                    }
                    pos = dispo.indexOf("dimanche");
                    if (pos != -1) {
                        dimanche.setChecked(true);
                        dispolist.add("dimanche");
                    }

                }
                html="<iframe width=\"100%\" height=\"450\" scrolling=\"yes\" frameborder=\"yes\" src=\"https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/playlists/"+soundcloud+"&amp;auto_play=false&amp;hide_related=true&amp;show_comments=false&amp;show_user=false&amp;show_reposts=false&amp;visual=false\"></iframe>";
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadDataWithBaseURL("", html , "text/html",  "UTF-8", "");
                // attaching data adapter to spinner
                spinnerAge.setAdapter(dataAdapter);

                int posi = categories.indexOf(age);
                spinnerAge.setSelection(posi);

                is.close();

                result = total.toString();

                if (result.equals(null) || result.equals("[]")) {
                    String msg = "Erreur de lecture";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Log.i("tagconvertstr", "" + e.toString());
            }
            if (progress.isShowing()) {
                progress.dismiss();
            }
        }
    }

}