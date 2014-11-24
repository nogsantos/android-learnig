package br.com.nogsantos.primeiroprojeto;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class Tela5Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela5);

        Button btn = (Button) findViewById(R.id.buttonCarregar);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DownloadTextAsyncTask().execute("http://www.treinaweb.com.br/sandbox/android-avancado/clientes/clientes.xml");
            }
        });
    }
    /**
     *
     */
    private InputStream OpenHttpConnection(String urlString)throws IOException{
        InputStream is = null;
        int response   = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection)) {
            throw new IOException("Não é uma conexão HTTP");
        }
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                is = httpConn.getInputStream();
            }
        }catch (Exception ex){
            Log.i("HttpConnection", ex.getLocalizedMessage());
            throw new IOException("Erro ao se conectar");
        }
        return is;
    }
    /**
     * Download do conteúdo da url em formato texto
     */
    private String DownloadString(String url){
        int BUFFER_SIZE = 2000;
        InputStream is  = null;

        try {
            is = OpenHttpConnection(url);
        } catch (IOException e) {
            Log.d("HttpConnection", e.getLocalizedMessage());
            return "";
        }

        InputStreamReader isr = new InputStreamReader(is);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];

        try {

            while ((charRead = isr.read(inputBuffer))>0) {
                //convert char para string
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            is.close();
        } catch (IOException e) {
            Log.d("HttpConnection", e.getLocalizedMessage());
            return "";
        }
        return str;
    }
    /**
     * Thread
     */
    private class DownloadTextAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return DownloadString(params[0]);
        }

        protected void onPostExecute(String result){
            TextView txtConteudo = (TextView) findViewById(R.id.textViewConteudo);
            txtConteudo.setText(result);
        }

    }
 }
