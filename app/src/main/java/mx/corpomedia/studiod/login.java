package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";

    EditText correo, password;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        correo = findViewById(R.id.correo);
        password = findViewById(R.id.password);
        error = findViewById(R.id.error);

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);


    }

    public void registro(View v){
        startActivity(new Intent(login.this, registro.class));
    }

    public void ingresar(View v){

        if(correo.getText().toString().equals("")){
            error.setText("Escriba su correo");
        } else {
            if (password.getText().toString().equals("")) {
                error.setText("Escriba su contraseña");
            } else {


                final String tag = "login";

                String server_url = "https://www.studiodlab.com.mx/admin/app/index.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    Log.i("RESPU 1", "onResponse: " + response);
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
                                        startActivity(new Intent(login.this, menu.class));

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
                        params.put("correo", correo.getText().toString());
                        params.put("password", password.getText().toString());
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(login.this);
                requestQueue.add(stringRequest);
            }
        }

    }

    @Override
    protected void onResume() {

        if(!sharedPreferences.getString("user_id", "").isEmpty()){
            if(!sharedPreferences.getString("user_id", "").equals("")){
                Intent in = new Intent(getApplicationContext(), menu.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(in);
            }
        }
        super.onResume();
    }
}
