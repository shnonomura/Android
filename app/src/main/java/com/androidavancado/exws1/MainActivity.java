package com.androidavancado.exws1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView textResultado;
    private TextView textCEP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResultado = findViewById(R.id.textResultado);
        textCEP = findViewById(R.id.textCEP);

    }

    public void acaoBusca(View v){
        String urlBusca = "https://viacep.com.br/ws/" + textCEP + "/json/";
        //String urlBusca = "https://viacep.com.br/ws/80230040/json/";
        Busca busca = new Busca();
        busca.execute(urlBusca);

    }

    class Busca extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];

            try {
                URL url = new URL(urlString);

                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linha = "";
                StringBuffer buffer = new StringBuffer();

                linha = reader.readLine();
                while (linha != null) {
                    buffer.append(linha + "\n");
                    linha = reader.readLine();
                }

                reader.close();
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String resultado = "";
            try {
                JSONObject principal = new JSONObject(s);
                String cidade = principal.getString( "localidade");
                String uf = principal.getString("uf");
                resultado = "CIDADE: " + cidade + "\n" + "UF: " + uf + "\n";

            } catch (JSONException e) {
                e.printStackTrace();
            }
            textResultado.setText(resultado);

        }
    }

}
