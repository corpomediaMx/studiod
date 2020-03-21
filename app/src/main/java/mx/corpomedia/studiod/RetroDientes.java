package mx.corpomedia.studiod;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import mx.corpomedia.studiod.model.Dientes;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;

public class RetroDientes extends BaseAdapter {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";
    private Context context;
    private ArrayList<Dientes> dataModelArrayList;

    public RetroDientes(Context context, ArrayList<Dientes> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position,View convertView, ViewGroup parent) {
        final RetroDientes.ViewHolder holder;

        if (convertView == null) {
            holder = new RetroDientes.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_dientes, null, true);


            holder.tvname = (TextView) convertView.findViewById(R.id.nombre);
            holder.tvtipo = (TextView) convertView.findViewById(R.id.tipo);
            holder.cerrar = (Button) convertView.findViewById(R.id.close);
            holder.editar = (Button) convertView.findViewById(R.id.ver);
            holder.material = (TextView) convertView.findViewById(R.id.material);
            holder.modelo = (TextView) convertView.findViewById(R.id.modelo);
            holder.precio = (TextView) convertView.findViewById(R.id.precio);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (RetroDientes.ViewHolder) convertView.getTag();
        }

        holder.tvname.setText("Dientes " + dataModelArrayList.get(position).getDientes());
        holder.material.setText(dataModelArrayList.get(position).getMaterial_nombre());
        holder.modelo.setText(dataModelArrayList.get(position).getDiseno_nombre());
        holder.precio.setText(" $ "+dataModelArrayList.get(position).getPrecio());

        if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("1")){
            holder.tvtipo.setText("Corona");
        }
        if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("2")){
            holder.tvtipo.setText("Inlay / Onlay");
        }
        if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("3")){
            holder.tvtipo.setText("Carilla");
        }
        if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("4")){
            holder.tvtipo.setText("Super Estructura de Impl");
        }
        if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("5")){
            holder.tvtipo.setText("Puente");
        }
        if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("6")){
            holder.tvtipo.setText("Guarda 1");
        }
        if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("7")){
            holder.tvtipo.setText("Guarda 2");
        }
        if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("8")){
            holder.tvtipo.setText("Guarda 3");
        }
        if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("9")){
            holder.tvtipo.setText("Encerado diagnóstico");
        }


        holder.cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context,"studiod",null,1);
                SQLiteDatabase db=conn.getWritableDatabase();

                db.delete( Utilidades.TABLE_DIENTES, Utilidades.CAMPO_ID+"="+dataModelArrayList.get(position).getId() ,  null);

                dataModelArrayList.remove(position);
                notifyDataSetChanged();

            }
        });

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String tipo_id = "";
                String tipo_nombre = "";
                String tipo_img = "";
                if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("1")){
                    tipo_id = "1";
                    tipo_nombre = "Corona";
                    tipo_img = "corona.png";
                }
                if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("2")){
                    tipo_id = "2";
                    tipo_nombre = "Inlay / Onlay";
                    tipo_img = "inlay.png";
                }
                if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("3")){
                    tipo_id = "3";
                    tipo_nombre = "Carilla";
                    tipo_img = "carilla.png";
                }
                if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("4")){
                    tipo_id = "4";
                    tipo_nombre = "Super Estructura de Impl";
                    tipo_img = "super.png";

                }
                if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("5")){
                    tipo_id = "5";
                    tipo_nombre = "Puente";
                    tipo_img = "super.png";

                }
                if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("6")){
                    tipo_id = "6";
                    tipo_nombre = "Guarda 1";
                    tipo_img = "guardaFINAL.png";

                }
                if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("7")){
                    tipo_id = "7";
                    tipo_nombre = "Guarda 2";
                    tipo_img = "guardaFINAL.png";

                }
                if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("8")){
                    tipo_id = "8";
                    tipo_nombre = "Guarda 3";
                    tipo_img = "guardaFINAL.png";

                }
                if(dataModelArrayList.get(position).getTipos_indicaciones_id().equals("9")){
                    tipo_id = "9";
                    tipo_nombre = "Encerado diagnóstico";
                    tipo_img = "super.png";

                }


                sharedPreferences = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
                
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tipos_indicaciones_id",tipo_id );
                editor.putString("nombre_tipos",tipo_nombre);
                editor.putString("imagen_tipos",tipo_img);
                editor.commit();
                Intent i = new Intent(context, especificaciones.class);
                i.putExtra("tipos_indicaciones_id",tipo_id);
                i.putExtra("dientes",dataModelArrayList.get(position).getDientes());
                i.putExtra("dientes_id",dataModelArrayList.get(position).getId());
                context.startActivity(i);

            }
        });



        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        protected TextView tvtipo, modelo, material, precio;
        protected Button cerrar, editar;
    }
}