package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;

public class newOrder extends AppCompatActivity {

    private CheckBox checkBox1,checkBox2;
    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";

    EditText paciente;
    RadioGroup grupo;
    String opciones, mor, ant;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);


        paciente = (EditText) findViewById(R.id.paciente);
        grupo = (RadioGroup) findViewById(R.id.grupo_entregas);
        checkBox1=(CheckBox)findViewById(R.id.mordida);
        checkBox2=(CheckBox)findViewById(R.id.antagonista);

        ImageButton button = (ImageButton) findViewById(R.id.btn_atras);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                onBackPressed();
            }
        });

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.radioButton){
                    AlertDialog.Builder builder = new AlertDialog.Builder(newOrder.this);
                    builder.setMessage("Entrega ordinaria: Apartir del cuarto día hábil")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    opciones = "Entrega ordinaria";
                }else if (checkedId == R.id.radioButton2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(newOrder.this);
                    builder.setMessage("Entrega urgente: De dos a tres días cargo extra del 40%")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    opciones = "Entrega urgente";
                }

            }
        });


    }



    public void next(View v){

        if (checkBox1.isChecked() == true) {
            mor = "Si";
        } else {
            mor = "No";
        }
        if (checkBox2.isChecked() == true) {
            ant = "Si";
        } else {
            ant = "No";
        }

        if(paciente.getText().toString().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(newOrder.this);
            builder.setMessage("Tiene que escribir un nombre de paciente")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("paciente", paciente.getText().toString());
            editor.putString("tipo_entrega", opciones);
            editor.putString("registro_mordida", mor);
            editor.putString("antagonista", ant);
            editor.commit();



            startActivity(new Intent(newOrder.this, indicaciones.class));
        }
    }


}
