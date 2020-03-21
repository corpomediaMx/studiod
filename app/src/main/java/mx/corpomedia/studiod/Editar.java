package mx.corpomedia.studiod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Editar extends AppCompatActivity {
    private final static String[] names = { "Masculino", "Femenino"};
    private static final int PICK_IMAGE_REQUEST = 1;


    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";


    EditText Nombre;
    EditText Apellidos;
    EditText Direccion;
    EditText Telefono;
    EditText Correo;
    EditText Password;
    EditText Password2;
    Button guardar;
    ImageView foto;

    private TextView registerErrorMsg;
    private Spinner spinner;
    TextView error;
    ArrayAdapter adapter;

    private MagicalPermissions magicalPermissions;
    private MagicalCamera magicalCamera;
    private static final int RESIZE_PHOTO_PIXELS_PERCENTAJE = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        getSupportActionBar().hide();

        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        magicalPermissions = new MagicalPermissions(this, permissions);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };

        magicalPermissions.askPermissions(runnable);

        magicalCamera = new MagicalCamera(this,RESIZE_PHOTO_PIXELS_PERCENTAJE,magicalPermissions);

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        Nombre = (EditText) findViewById(R.id.nombre);
        Apellidos = (EditText) findViewById(R.id.apellidos);
        Direccion = (EditText) findViewById(R.id.direccion);
        Telefono = (EditText) findViewById(R.id.telefono);
        Correo = (EditText) findViewById(R.id.correo);
        error = findViewById(R.id.error);
        guardar = findViewById(R.id.save);
        foto = findViewById(R.id.foto);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, names);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


        datos(sharedPreferences.getString("user_id", ""));


        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magicalCamera.selectedPicture("Selleccione su foto");
            }
        });

        if(sharedPreferences.getString("foto_user", "").equals("")){
            foto.setImageResource(R.drawable.usuario);
        }
        else {
        foto.setImageBitmap(decodeBase64(sharedPreferences.getString("foto_user", "")));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        magicalCamera.resultPhoto(requestCode,resultCode,data);

        String namefoto = ""+System.currentTimeMillis()/1000;

        String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(),"img_"+namefoto,"studiod", MagicalCamera.JPEG, true);

        if(path != null){

            Bitmap bitmap = magicalCamera.getPhoto();

            if (bitmap != null) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("foto_user", encodeTobase64(bitmap));
                editor.commit();

                foto.setImageBitmap(bitmap);
            }


        }else{
            Toast.makeText(Editar.this, "No se guardo la foto", Toast.LENGTH_SHORT).show();
        }

        switch (requestCode){

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        for (String permission : map.keySet()) {
            Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
        }
        //Following the example you could also
        //locationPermissions(requestCode, permissions, grantResults);
    }



    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public void datos(final String id)
    {


        final String tag = "User";

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

                            Log.d("Mundo NUMERO 1  ",success);

                            if (success.equals("1")) {
                                String nombre = jsonObject.getString("nombre");
                                String apellidos = jsonObject.getString("apellidos");
                                String sexo = jsonObject.getString("sexo");
                                String direccion = jsonObject.getString("direccion");
                                String celular = jsonObject.getString("celular");
                                String correo = jsonObject.getString("correo");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_id", user_id);
                                editor.putString("nombre_usuario", nombre+" "+apellidos);
                                editor.putString("email", correo);
                                editor.commit();

                                Nombre.setText(nombre);
                                Apellidos.setText(apellidos);
                                Correo.setText(correo);
                                Direccion.setText(direccion);
                                Telefono.setText(celular);
                                String compareValue = sexo;
                                if (compareValue != null) {

                                    spinner.setSelection(adapter.getPosition(compareValue));

                                }




                            }
                            if (success.equals("0")) {
                                error.setText(mensaje);
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
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Editar.this);
        requestQueue.add(stringRequest);
    }

    public void atras(View v){
        onBackPressed();
    }

    public void guardar(View v){

        if(Nombre.getText().toString().equals("")
                | Apellidos.getText().toString().equals("") | Direccion.getText().toString().equals("")
                | Telefono.getText().toString().equals("") | Correo.getText().toString().equals("")) {

            error.setText("!Llene todos los campos!");

        } else {





                final String sexo = (String) spinner.getSelectedItem();
                final String tag = "saveUser";

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
                                        String usuario = jsonObject.getString("username");
                                        String correo = jsonObject.getString("correo");
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user_id", user_id);
                                        editor.putString("nombre_usuario", usuario);
                                        editor.putString("email", correo);
                                        editor.commit();

                                        error.setText(mensaje);
                                        Intent itemintent = new Intent(Editar.this, menu.class);
                                        Editar.this.startActivity(itemintent);

                                    }
                                    if (success.equals("0")) {
                                        error.setText(mensaje);
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
                        params.put("nombre", Nombre.getText().toString());
                        params.put("apellidos", Apellidos.getText().toString());
                        params.put("sexo", sexo);
                        params.put("direccion", Direccion.getText().toString());
                        params.put("celular", Telefono.getText().toString());
                        params.put("correo", Correo.getText().toString());
                        params.put("id", sharedPreferences.getString("user_id", ""));
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Editar.this);
                requestQueue.add(stringRequest);
            }


    }


}
