package br.com.nogsantos.primeiroprojeto;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class Tela3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela3);
       /*
        * Adicionar fragment na activity dinamicamente
        */
        if(getIntent().hasExtra("id")){
            if (savedInstanceState == null) {
                FragmentManager fragmentManager         = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailBookFragment detailFragment       = new DetailBookFragment();
                fragmentTransaction.add(
                    R.id.fragment_container,
                    detailFragment,
                    "detailFragment"
                );
                fragmentTransaction.commit();
            }
        }else {
            finish();
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        int id = (int) getIntent().getExtras().getLong("id");
        DetailBookFragment detailFragment = (DetailBookFragment) getFragmentManager().findFragmentByTag("detailFragment");
        if(detailFragment != null) {
            detailFragment.loadBook((int) id);
        }
    }
}
