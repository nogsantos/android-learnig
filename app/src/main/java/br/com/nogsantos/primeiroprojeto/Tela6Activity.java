package br.com.nogsantos.primeiroprojeto;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Tela6Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela6);

        Button btn = (Button) findViewById(R.id.buttonBuscar);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText editId = (EditText) findViewById(R.id.editTextId);
                if(editId.getText().length() > 0){
                    int id = Integer.parseInt(editId.getText().toString());
                    new AccessWebServiceAsyncTask().execute(id);
                }
            }
        });
    }
    /**
     *
     */
    private InputStream OpenHttpConnection(String urlString)throws IOException {
        InputStream is = null;
        int response   = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection)) {
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
        }catch (Exception ex){
            Log.i("HttpConnection", ex.getLocalizedMessage());
            throw new IOException("Erro ao se conectar");
        }
        return is;
    }
    /**
     *
     */
    private Map<String, String> getCliente(int id) {
        InputStream is = null;
        Map<String, String> cliente = new HashMap<String, String>();

        try {
            is = OpenHttpConnection(
                    "http://www.treinaweb.com.br/sandbox/android-avancado/clientes/clientes.php?id=" + id);
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;

            try {
                db = dbf.newDocumentBuilder();
                doc = db.parse(is);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            doc.getDocumentElement().normalize();
            NodeList definitionElements = doc.getElementsByTagName("cliente");

            for (int i = 0; i < definitionElements.getLength(); i++) {
                Node itemNode = definitionElements.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    NodeList clienteElements = itemNode.getChildNodes();
                    for(int j = 0; j < clienteElements.getLength(); j++){
                        Node clienteNode = clienteElements.item(j);
                        if(clienteNode.getNodeType() == Node.ELEMENT_NODE){
                            cliente.put(clienteNode.getNodeName(), clienteNode.getTextContent());
                        }
                    }
                }
            }
        } catch (IOException e1) {
            Log.i("HttpConnection", e1.getLocalizedMessage());
        }

        return cliente;
    }
    /**
     *
     */
    private class AccessWebServiceAsyncTask extends AsyncTask<Integer, Void, Map<String,String>> {
        @Override
        protected Map<String, String> doInBackground(Integer... params) {
            return getCliente(params[0]);
        }

        protected void onPostExecute(Map<String, String> result){
            TextView txtViewNome     = (TextView) findViewById(R.id.textViewNome);
            TextView txtViewIdade    = (TextView) findViewById(R.id.textViewIdade);
            TextView txtViewTelefone = (TextView) findViewById(R.id.textViewTelefone);
            TextView txtViewRua      = (TextView) findViewById(R.id.textViewRua);
            TextView txtViewCidade   = (TextView) findViewById(R.id.textViewCidade);
            TextView txtViewEstado   = (TextView) findViewById(R.id.textViewEstado);

            if(result.containsKey("nome")){
                txtViewNome.setText("Nome: "        + result.get("nome"));
                txtViewIdade.setText("Idade: "      + result.get("idade"));
                txtViewTelefone.setText("Telefone:" + result.get("telefone"));
                txtViewRua.setText("Rua: "          + result.get("rua"));
                txtViewCidade.setText("Cidade: "    + result.get("cidade"));
                txtViewEstado.setText("Estado: "    + result.get("estado"));

                findViewById(R.id.viewDados).setVisibility(View.VISIBLE);
            }else {
                Toast.makeText(getBaseContext(), "Cliente não encontrado!", Toast.LENGTH_LONG).show();
                findViewById(R.id.viewDados).setVisibility(View.INVISIBLE);
            }
        }
    }

}
