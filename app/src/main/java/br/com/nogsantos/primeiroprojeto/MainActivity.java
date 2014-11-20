package br.com.nogsantos.primeiroprojeto;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.buttonSalvar);
        btn.setOnClickListener(mOnClickButtonSave);
    }


    private View.OnClickListener mOnClickButtonSave = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            EditText editTitulo = (EditText) findViewById(R.id.editTextTituloLivro);
            EditText editAutor = (EditText) findViewById(R.id.editTextAutor);
            EditText editEditora = (EditText) findViewById(R.id.editTextEditora);
            EditText editPaginas = (EditText) findViewById(R.id.editTextPaginas);

            try{
                BooksDbHelper dbHelper = new BooksDbHelper(getBaseContext());

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String titulo = editTitulo.getText().toString();
                String autor = editAutor.getText().toString();
                String editora = editEditora.getText().toString();
                int paginas = Integer.parseInt(editPaginas.getText().toString());

                ContentValues values = new ContentValues();
                values.put(BooksDbHelper.B_TITULO, titulo);
                values.put(BooksDbHelper.B_AUTOR, autor);
                values.put(BooksDbHelper.B_EDITORA, editora);
                values.put(BooksDbHelper.B_PAGINAS, paginas);

                try{
                    db.insertOrThrow(BooksDbHelper.TABLE, null, values);
                    Toast.makeText(getBaseContext(), "Livro cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    //Limpando os campos
                    editTitulo.setText("");
                    editAutor.setText("");
                    editEditora.setText("");
                    editPaginas.setText("");
                }finally
                {
                    db.close();
                }
            }catch(Exception e)
            {
                Log.e("error sqlite", e.getMessage());
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
