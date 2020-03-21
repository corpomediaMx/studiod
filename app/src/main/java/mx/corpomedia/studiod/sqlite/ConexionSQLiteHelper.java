package mx.corpomedia.studiod.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import mx.corpomedia.studiod.model.Dientes;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {




    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidades.CREAR_TABLA_DIENTES);
        db.execSQL(Utilidades.CREAR_TABLA_ORDENES);
        db.execSQL(Utilidades.CREAR_TABLA_AUDIOS);
        db.execSQL(Utilidades.CREAR_TABLA_FOTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionantigua, int venrsionnueva) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLE_DIENTES);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLE_ORDENES);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLE_AUDIOS);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLE_FOTOS);
        onCreate(db);
    }


    public ArrayList<Dientes> getAllData(){
        ArrayList<Dientes> ranking = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor fila = db.rawQuery("select id, orden_id, tipos_indicaciones_id, dientes, materiales_id, materiales_nombre, disenos_id, disenos_nombre, color, prueba, observaciones, user_id  from dientes", null);

        while(fila.moveToNext()){

            String id =  fila.getString(0);
            String orden_id = fila.getString(1);
            String tipos_indicaciones_id = fila.getString(2);
            String dientes = fila.getString(3);
            String materiales_id = fila.getString(4);
            String materiales_nombre = fila.getString(5);
            String disenos_id = fila.getString(6);
            String disenos_nombre = fila.getString(7);
            String color = fila.getString(8);
            String prueba = fila.getString(9);
            String observaciones = fila.getString(10);
            String user_id = fila.getString(11);

            Dientes carritoadd = new Dientes(id,"",tipos_indicaciones_id,dientes,materiales_id, materiales_nombre, disenos_id, disenos_nombre, color,prueba,observaciones, user_id,"");


            ranking.add(carritoadd);

        }

        return ranking;

    }
}
