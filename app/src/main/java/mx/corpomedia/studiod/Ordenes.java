package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import mx.corpomedia.studiod.model.OrdenesModelos;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;

public class Ordenes extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String myPreferences = "MyPrefs";

    private NonScrollListView listView;
    private RetroOrdenes retroAdapter;

    TextView paciente, entrega, mordida, antagonista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        listView = findViewById(R.id.lv);

        if(getCarritosDatos().size() == 0){


        } else {

            writeListView(getCarritosDatos());
        }




    }


    private void writeListView(ArrayList<OrdenesModelos> response) {


        retroAdapter = new RetroOrdenes(this, response);
        listView.setAdapter(retroAdapter);



    }

    public ArrayList<OrdenesModelos> getCarritosDatos() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "studiod", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ArrayList<OrdenesModelos> ranking = new ArrayList<>();

        Cursor fila = db.rawQuery("select id, orden_id, usuarios_id, paciente, tipo_entrega, registro_mordida, antagonista, total, status, fecha  from ordenes", null);

        while (fila.moveToNext()) {

            String id = fila.getString(0);
            String orden_id = fila.getString(1);
            String usuarios_id = fila.getString(2);
            String paciente = fila.getString(3);
            String tipo_entrega = fila.getString(4);
            String registro_mordida = fila.getString(5);
            String antagonista = fila.getString(6);
            String total = fila.getString(7);
            String status = fila.getString(8);
            String fecha = fila.getString(9);


                OrdenesModelos carritoadd = new OrdenesModelos(id, orden_id,usuarios_id ,paciente,tipo_entrega, registro_mordida, antagonista,total,status, fecha);
                ranking.add(carritoadd);


        }

        return ranking;

    }

    public void atras(View v) {
        onBackPressed();
    }


}