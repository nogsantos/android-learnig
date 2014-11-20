package br.com.nogsantos.primeiroprojeto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author nogsantos
 * @since 20/11/2014 9:34 AM
 */
public class BooksDbHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "treinaweb.db";
    static final int DB_VERSION = 1;
    static final String TABLE = "Livros";
    static final String B_ID = "_id";
    static final String B_TITULO = "titulo";
    static final String B_AUTOR = "autor";
    static final String B_EDITORA = "editora";
    static final String B_PAGINAS = "paginias";

    public BooksDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try{
            String sql = "create table " + TABLE + " (" + B_ID + " integer primary key autoincrement, "
                    + B_TITULO + " text, " + B_AUTOR + " text, "
                    + B_EDITORA + " text, " + B_PAGINAS + " integer)";
            db.execSQL(sql);
        }catch(Exception e){
            Log.e("Error DbHelper", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        try{
            db.execSQL("drop table if exists " + TABLE);
            onCreate(db);
        }catch(Exception e){
            Log.e("Error DbHelper", e.getMessage());
        }
    }

}
