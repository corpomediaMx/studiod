package mx.corpomedia.studiod;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Base64;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class menu extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";

    TextView nombre;
    ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = findViewById(R.id.nombre);
        foto = findViewById(R.id.foto);

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        nombre.setText(sharedPreferences.getString("nombre_usuario", ""));

        if(sharedPreferences.getString("foto_user", "").equals("")){
            foto.setImageResource(R.drawable.usuario);
        }

        else {
            foto.setImageBitmap(decodeBase64(sharedPreferences.getString("foto_user", "")));
        }
    }



    public void newOrder(View v){
        startActivity(new Intent(menu.this, newOrder.class));
    }
    public void ordenes(View v){
        startActivity(new Intent(menu.this, Ordenes.class));
    }
    public void cuentas(View v){
        startActivity(new Intent(menu.this, Cuentas.class));
    }
    public void editar(View v){
        startActivity(new Intent(menu.this, Editar.class));
    }

    public void salir(View v){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id","");
        editor.putString("nombre_usuario","");
        editor.putString("email","");
        editor.commit();

        startActivity(new Intent(menu.this, login.class));
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public void repartidor(View v){


        AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
        builder.setMessage("¿Confirma qué desea solicitar un repartidor?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        final String tag = "repartidorUser";

                        String server_url = "https://www.studiodlab.com.mx/admin/app/index.php";

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                                new com.android.volley.Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String success = jsonObject.getString("success");
                                            String user_id = jsonObject.getString("id");
                                            String mensaje = jsonObject.getString("error_msg");

                                            if (success.equals("1")) {


                                                    AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
                                                    builder.setMessage("Tu solicitud ha sido enviada con éxito")
                                                            .setCancelable(false)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                    AlertDialog alert = builder.create();
                                                    alert.show();


                                            }
                                            if (success.equals("0")) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
                                                builder.setMessage("Tu solicitud no ha sido enviada")
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                AlertDialog alert = builder.create();
                                                alert.show();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("tag", tag);
                                params.put("user_id", sharedPreferences.getString("user_id", ""));
                                params.put("mensaje", "Solicito repartidor");
                                params.put("fecha", "");
                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(menu.this);
                        requestQueue.add(stringRequest);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();



    }
}
