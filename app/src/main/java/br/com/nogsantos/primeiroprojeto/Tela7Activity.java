package br.com.nogsantos.primeiroprojeto;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Tela7Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela7);

        Button btn = (Button) findViewById(R.id.buttonCarregar);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new ReadJsonAsyncTask().execute("http://www.treinaweb.com.br/sandbox/android-avancado/clientes.json");
            }
        });
    }
    /**
     * Carregar, baixar conteúdo via Json format
     */
    public String readJson(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet   = new HttpGet(URL);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode        = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } else {
                Log.e("Json", "Erro durante o download do arquivo");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    /**
     * Thread
     */
    private class ReadJsonAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return readJson(params[0]);
        }

        protected void onPostExecute(String result){
            ListView list = (ListView) findViewById(R.id.listViewClientes);
            try{
                JSONObject jObj = new JSONObject(result);
                JSONArray jArray = jObj.getJSONArray("clientes");
                String[] strArray = new String[jArray.length()];

                for(int i = 0; i < jArray.length(); i++){
                    JSONObject jObject = jArray.getJSONObject(i);
                    strArray[i] = jObject.getString("nome");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, strArray);

                list.setAdapter(adapter);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
