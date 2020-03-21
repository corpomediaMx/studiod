package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.corpomedia.studiod.model.DientesModelos;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;

public class seleccion extends AppCompatActivity {

    WebView myWebView;

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);
        getSupportActionBar().hide();

        myWebView = (WebView) this.findViewById(R.id.web);

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);


        WebSettings webSettings = myWebView.getSettings();
        myWebView.setInitialScale(50);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        String indicaciones_id = getIntent().getStringExtra("indicaciones_id");
        myWebView.loadUrl("https://www.studiodlab.com.mx/admin/app/dentadura.php?id="+indicaciones_id );
        myWebView.addJavascriptInterface(new IJavascriptHandler(seleccion.this), "corpomedia");


    }
    public void atras(View v){
        onBackPressed();
    }

    @Override
    protected void onResume() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(seleccion.this,"studiod",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        db.delete( Utilidades.TABLE_DIENTES, Utilidades.CAMPO_ORDEN_ID +" = 0 AND "+Utilidades.CAMPO_USER_ID+" = "+sharedPreferences.getString("user_id", ""),  null);


        super.onResume();
    }
}
final class IJavascriptHandler {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";
    Context mContext;
    String dientes_id;
    String orden_id;
    String tipos_indicaciones_id2;
    String dientes_text;
    String materiasles_id;
    String diseno_id;
    String color;
    String prueba;
    String observaciones;
    String user_id;

    IJavascriptHandler(Context c) {
        mContext = c;
        sharedPreferences = mContext.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
    }

    // This annotation is required in Jelly Bean and later:
    @JavascriptInterface
    public void sendToAndroid(String text) {

        Log.d("DATOS enviados",text+" "+sharedPreferences.getString("user_id", ""));



            if(text.equals("{\"datos\":[]}")){
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Debes seleccionar un diente!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {


                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(text);

                    final ArrayList<DientesModelos> modelListViewArrayList = new ArrayList<>();
                    JSONArray dataArray  = obj.getJSONArray("datos");

                    for (int i = 0; i < dataArray.length(); i++) {

                        DientesModelos modelListView = new DientesModelos();
                        JSONObject dataobj = dataArray.getJSONObject(i);

                        String dientes = dataobj.getString("diente");
                        String tipo_id = dataobj.getString("tipo");

                        Log.e("Aqui1 ",tipo_id);


                        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(mContext,"studiod",null,1);
                        SQLiteDatabase db=conn.getWritableDatabase();



                        String where = Utilidades.CAMPO_TIPOS_INDICACIONES_ID + " = "+tipo_id+" AND "+ Utilidades.CAMPO_ORDEN_ID +" = 0 AND "+Utilidades.CAMPO_USER_ID+" = "+sharedPreferences.getString("user_id", "");
                        String orden = Utilidades.CAMPO_TIPOS_INDICACIONES_ID;
                        String[] campos = new String[] {Utilidades.CAMPO_ID, Utilidades.CAMPO_ORDEN_ID, Utilidades.CAMPO_TIPOS_INDICACIONES_ID, Utilidades.CAMPO_DIENTES, Utilidades.CAMPO_MATERIALES_ID, Utilidades.CAMPO_DISENOS_ID, Utilidades.CAMPO_COLOR, Utilidades.CAMPO_PRUEBA, Utilidades.CAMPO_OBSERVACIONES, Utilidades.CAMPO_USER_ID};
                        Cursor miResultado = db.query(Utilidades.TABLE_DIENTES, campos, where, null, null, null, orden);


                        if (miResultado.moveToFirst()) {

                            do {
                                dientes_id = miResultado.getString(0);
                                orden_id= miResultado.getString(1);
                                tipos_indicaciones_id2 = miResultado.getString(2);
                                dientes_text = miResultado.getString(3);
                                materiasles_id = miResultado.getString(4);
                                diseno_id = miResultado.getString(5);
                                color= miResultado.getString(6);
                                prueba = miResultado.getString(7);
                                observaciones = miResultado.getString(8);
                                user_id = miResultado.getString(9);
                            } while(miResultado.moveToNext());
                        }

                        Log.e("Aqui2 ","id diente "+dientes_id+" orden id "+orden_id+" tipos id "+tipos_indicaciones_id2+" usuario_id"+user_id );

                        if (tipos_indicaciones_id2 == null){

                            Log.e("ENTRO DINERO ",sharedPreferences.getString("user_id", ""));

                            ContentValues values = new ContentValues();
                            values.put(Utilidades.CAMPO_ORDEN_ID,"0");
                            values.put(Utilidades.CAMPO_TIPOS_INDICACIONES_ID,tipo_id);
                            values.put(Utilidades.CAMPO_DIENTES,dientes);
                            values.put(Utilidades.CAMPO_MATERIALES_ID,"");
                            values.put(Utilidades.CAMPO_DISENOS_ID,"");
                            values.put(Utilidades.CAMPO_COLOR,"");
                            values.put(Utilidades.CAMPO_PRUEBA,"");
                            values.put(Utilidades.CAMPO_OBSERVACIONES,"");
                            values.put(Utilidades.CAMPO_USER_ID,sharedPreferences.getString("user_id", ""));
                            values.put(Utilidades.CAMPO_PRECIO,"0");



                            Long idResultante = db.insert(Utilidades.TABLE_DIENTES,Utilidades.CAMPO_ID,values);

                        } else {

                            Log.d("ESTO APARECE ",dientes);




                                ContentValues actualizardatos = new ContentValues();
                                actualizardatos.put(Utilidades.CAMPO_DIENTES, dientes);

                                db.update(Utilidades.TABLE_DIENTES, actualizardatos, Utilidades.CAMPO_ID + "=" + dientes_id, null);

                        }





                    }

                    Intent i = new Intent(mContext, OrdenDientes.class);
                    mContext.startActivity(i);



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

    }



}