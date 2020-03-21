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

import mx.corpomedia.studiod.model.OrdenesModelos;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;

public class RetroOrdenesVer extends BaseAdapter {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";
    private Context context;
    private ArrayList<OrdenesModelos> dataModelArrayList;

    public RetroOrdenesVer(Context context, ArrayList<OrdenesModelos> dataModelArrayList) {

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
        final RetroOrdenesVer.ViewHolder holder;

        if (convertView == null) {
            holder = new RetroOrdenesVer.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_ordenes_ver, null, true);


            holder.tvname = (TextView) convertView.findViewById(R.id.nombre);
            holder.tvtipo = (TextView) convertView.findViewById(R.id.tipo);
            holder.cerrar = (Button) convertView.findViewById(R.id.close);
            holder.ver = (Button) convertView.findViewById(R.id.ver);
            holder.material = (TextView) convertView.findViewById(R.id.material);
            holder.modelo = (TextView) convertView.findViewById(R.id.status);
            holder.total = (TextView) convertView.findViewById(R.id.total);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (RetroOrdenesVer.ViewHolder) convertView.getTag();
        }

        holder.tvname.setText("No. " + dataModelArrayList.get(position).getOrden_id());
        holder.tvtipo.setText(dataModelArrayList.get(position).getPaciente());
        holder.material.setText(dataModelArrayList.get(position).getFecha());
        holder.total.setText(dataModelArrayList.get(position).getTotal());
        holder.modelo.setText(dataModelArrayList.get(position).getStatus());


        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        protected TextView tvtipo, modelo, material, total;
        protected Button cerrar, ver;
    }
}