package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.corpomedia.studiod.model.Dientes;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;

public class registro extends AppCompatActivity{

    private Button lblGotoLogin;
    private Button btnRegister;
    TextView error;
    private final static String[] names = { "Masculino", "Femenino"};


    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";


    EditText Nombre;
    EditText Apellidos;
    EditText Direccion;
    EditText Telefono;
    EditText Correo;
    EditText Password;
    EditText Password2;

    private TextView registerErrorMsg;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        Nombre = (EditText) findViewById(R.id.nombre);
        Apellidos = (EditText) findViewById(R.id.apellidos);
        Direccion = (EditText) findViewById(R.id.direccion);
        Telefono = (EditText) findViewById(R.id.telefono);
        Correo = (EditText) findViewById(R.id.correo);
        Password = (EditText) findViewById(R.id.password);
        Password2 = (EditText) findViewById(R.id.repassword);
        error = findViewById(R.id.error);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, names);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);



        btnRegister = (Button) findViewById(R.id.registrar);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(Nombre.getText().toString().equals("")
                        | Apellidos.getText().toString().equals("") | Direccion.getText().toString().equals("")
                        | Telefono.getText().toString().equals("") | Correo.getText().toString().equals("")
                        | Password.getText().toString().equals("")) {

                        error.setText("!Llene todos los campos!");

                } else {

                    if(Password.getText().toString().equals(Password2.getText().toString())) {



                        final String sexo = (String) spinner.getSelectedItem();
                        final String tag = "registerUser";

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
                                                Intent itemintent = new Intent(registro.this, menu.class);
                                                registro.this.startActivity(itemintent);

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
                                params.put("password", Password.getText().toString());
                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(registro.this);
                        requestQueue.add(stringRequest);
                    } else {
                        error.setText("!Las contraseñas deben coincidir!");
                    }

                }

            }
        });




    }

    public void atras(View v){
       onBackPressed();
    }




}
