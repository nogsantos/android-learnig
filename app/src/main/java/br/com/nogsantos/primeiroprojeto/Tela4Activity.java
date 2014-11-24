package br.com.nogsantos.primeiroprojeto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class Tela4Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela4);

        Button btn = (Button) findViewById(R.id.buttonCarregar);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new DownloadImageAsyncTask().execute("https://treinaweb-cursos.s3.amazonaws.com/prod/107/arquivos/logo-treinaweb-500.png");
            }
        });
    }
    /**
     * Verifica se o dispositivo pode se conectar a internet
     */
    private InputStream OpenHttpConnection(String urlString)throws IOException{
        InputStream is     = null;
        int response       = -1;
        URL url            = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection)){
            throw new IOException("Não é uma conexão HTTP");
        }
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                is = httpConn.getInputStream();
            }
        }
        catch (Exception ex){
            Log.i("HttpConnection", ex.getLocalizedMessage());
            throw new IOException("Erro ao se conectar");
        }
        return is;
    }
    /**
     *
     */
    private Bitmap DownloadImage(String URL){
        Bitmap bitmap  = null;
        InputStream is = null;
        try {
            is     = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e1) {
            Log.d("HttpConnection", e1.getLocalizedMessage());
        }
        return bitmap;
    }
    /**
     *
     */
    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            return DownloadImage(params[0]);
        }

        protected void onPostExecute(Bitmap result) {
            ImageView img = (ImageView) findViewById(R.id.imageViewWeb);
            img.setImageBitmap(result);
        }
    }
}
