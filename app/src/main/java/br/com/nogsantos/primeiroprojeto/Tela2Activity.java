package br.com.nogsantos.primeiroprojeto;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author nogsantos
 * @since 20/11/2014 10:45 AM
 */
public class Tela2Activity extends Activity implements ListBookFragment.OnListBookSelectedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);
    }
    @Override
    public void onBookSelected(long id) {
        // TODO Auto-generated method stub
        DetailBookFragment detailFragment = (DetailBookFragment) getFragmentManager().findFragmentById(R.id.detail_book_fragment);
        if(detailFragment != null){
            detailFragment.loadBook((int) id);
        }
    }
}
