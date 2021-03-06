package br.com.nogsantos.primeiroprojeto;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author nogsantos
 * @since 20/11/2014 10:30 AM
 */
public class DetailBookFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        return view;
    }
    /**
     * Query
     */
    public void loadBook(int id){
        TextView tViewTitulo  = (TextView) getView().findViewById(R.id.textViewTituloLivro);
        TextView tViewAutor   = (TextView) getView().findViewById(R.id.textViewAutor);
        TextView tViewEditora = (TextView) getView().findViewById(R.id.textViewEditora);
        TextView tViewPaginas = (TextView) getView().findViewById(R.id.textViewPaginas);


        String[] columns = {
            BooksDbHelper.B_ID,
            BooksDbHelper.B_TITULO,
            BooksDbHelper.B_AUTOR,
            BooksDbHelper.B_EDITORA,
            BooksDbHelper.B_PAGINAS
        };
        String selection       = BooksDbHelper.B_ID + "=?";
        String[] selectionArgs = { String.valueOf(id) };

        SQLiteDatabase db = null;
        try {
            BooksDbHelper dbHelper = new BooksDbHelper(getActivity());
            db                     = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(
                    BooksDbHelper.TABLE,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
                );
                if(cursor.moveToNext()){
                    tViewTitulo.setText(cursor.getString(1));
                    tViewAutor.setText(cursor.getString(2));
                    tViewEditora.setText(cursor.getString(3));
                    tViewPaginas.setText(cursor.getString(4));
                }
            }catch(Exception e){
                Log.e("error sqlite", e.getMessage());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("error sqlite", e.getMessage());
        }
        finally{
            db.close();
        }
    }
    /**
     *  Salvar o estado do app
     */
    @Override
    public void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);

        TextView tViewTitulo  = (TextView) getView().findViewById(R.id.textViewTituloLivro);
        TextView tViewAutor   = (TextView) getView().findViewById(R.id.textViewAutor);
        TextView tViewEditora = (TextView) getView().findViewById(R.id.textViewEditora);
        TextView tViewPaginas = (TextView) getView().findViewById(R.id.textViewPaginas);
        String titulo         = tViewTitulo.getText().toString();
        String autor          = tViewAutor.getText().toString();
        String editora        = tViewEditora.getText().toString();
        String paginas        = tViewPaginas.getText().toString();
        outState.putString("titulo", titulo);
        outState.putString("autor", autor);
        outState.putString("editora", editora);
        outState.putString("paginas", paginas);
    }
    /**
     * Recriar com o estado que foi salvo
     */
    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            TextView tViewTitulo  = (TextView) getView().findViewById(R.id.textViewTituloLivro);
            TextView tViewAutor   = (TextView) getView().findViewById(R.id.textViewAutor);
            TextView tViewEditora = (TextView) getView().findViewById(R.id.textViewEditora);
            TextView tViewPaginas = (TextView) getView().findViewById(R.id.textViewPaginas);
            String titulo         = savedInstanceState.getString("titulo");
            String autor          = savedInstanceState.getString("autor");
            String editora        = savedInstanceState.getString("editora");
            String paginas        = savedInstanceState.getString("paginas");
            tViewTitulo.setText(titulo);
            tViewAutor.setText(autor);
            tViewEditora.setText(editora);
            tViewPaginas.setText(paginas);
        }
    }
}
