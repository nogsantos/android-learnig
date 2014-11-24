package br.com.nogsantos.primeiroprojeto;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * A fragment representing a list of Items.
 */
public class ListBookFragment extends Fragment {
    /*
     */
    OnListBookSelectedListener mCallback;
    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        return view;
    }
    /**
     * Create informando o menu
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    /**
     * Estado inicial. Consulta os dados
     */
    @Override
    public void onStart(){
        super.onStart();
        SQLiteDatabase db = null;
        try {
            BooksDbHelper dbHelper = new BooksDbHelper(getActivity());
            String[] uiBindFrom    = {
                BooksDbHelper.B_ID,
                BooksDbHelper.B_TITULO,
                BooksDbHelper.B_AUTOR
            };
            int[] uiBindTo         = {
                R.id.textViewId,
                R.id.textViewTituloLivro,
                R.id.textViewAutor
            };
            db = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(
                    BooksDbHelper.TABLE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    BooksDbHelper.B_ID + " DESC"
                );
                SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(
                    getActivity(),
                    R.layout.book_item_list,
                    cursor,
                    uiBindFrom,
                    uiBindTo,
                    0
                );
                ListView list = (ListView) getView().findViewById(R.id.listViewLivros);
                list.setAdapter(mAdapter);
                list.setOnItemClickListener(mClickItemList);
            }catch(Exception e){
                Log.e("error sqlite", e.getMessage());
            }
        } catch (Exception e) {
            Log.i("error sqlite", e.getMessage());
        }
        finally{
            if(db.isOpen()) {
                db.close();
            }
        }
    }
    /**
     *
     */
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnListBookSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " deve implementar OnListBookSelectedListener");
        }
    }
    /**
     *
     */
    private AdapterView.OnItemClickListener mClickItemList = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long id) {
            mCallback.onBookSelected(id);
        }
    };
    /**
     * Id do item selecionado
     */
    public interface OnListBookSelectedListener {
        public void onBookSelected(long id);
    }
    /**
     * Menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_list_book, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    /**
     * Seleção do menu
     */
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.actionAdd:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                // Se outro item for selecionado, executa o padrão.
                return super.onOptionsItemSelected(item);
        }
    }
}
