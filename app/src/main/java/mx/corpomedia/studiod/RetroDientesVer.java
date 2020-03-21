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

public class RetroDientesVer extends BaseAdapter {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";
    private Context context;
    private ArrayList<Dientes> dataModelArrayList;

    public RetroDientesVer(Context context, ArrayList<Dientes> dataModelArrayList) {

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final RetroDientesVer.ViewHolder holder;

        if (convertView == null) {
            holder = new RetroDientesVer.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_dientes_ver, null, true);


            holder.tvname = (TextView) convertView.findViewById(R.id.nombre);
            holder.tvtipo = (TextView) convertView.findViewById(R.id.tipo);
            holder.material = (TextView) convertView.findViewById(R.id.material);
            holder.modelo = (TextView) convertView.findViewById(R.id.modelo);
            holder.precio = (TextView) convertView.findViewById(R.id.precio);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (RetroDientesVer.ViewHolder) convertView.getTag();
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



        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        protected TextView tvtipo, modelo, material, precio;
        protected Button cerrar, editar;
    }
}