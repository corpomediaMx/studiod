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

public class RetroOrdenes extends BaseAdapter {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";
    private Context context;
    private ArrayList<OrdenesModelos> dataModelArrayList;

    public RetroOrdenes(Context context, ArrayList<OrdenesModelos> dataModelArrayList) {

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
        final RetroOrdenes.ViewHolder holder;

        if (convertView == null) {
            holder = new RetroOrdenes.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_ordenes, null, true);


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
            holder = (RetroOrdenes.ViewHolder) convertView.getTag();
        }

        holder.tvname.setText("No. " + dataModelArrayList.get(position).getOrden_id());
        holder.tvtipo.setText(dataModelArrayList.get(position).getPaciente());
        holder.material.setText(dataModelArrayList.get(position).getFecha());
        holder.total.setText(dataModelArrayList.get(position).getTotal());
        holder.modelo.setText(dataModelArrayList.get(position).getStatus());




        holder.cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context,"studiod",null,1);
                SQLiteDatabase db=conn.getWritableDatabase();

                db.delete( Utilidades.TABLE_ORDENES, Utilidades.CAMPO_ID+"="+dataModelArrayList.get(position).getId() ,  null);

                dataModelArrayList.remove(position);
                notifyDataSetChanged();

            }
        });

        holder.ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, DetalleOrden.class);
                i.putExtra("orden_id",dataModelArrayList.get(position).getOrden_id());
                i.putExtra("paciente",dataModelArrayList.get(position).getPaciente());
                i.putExtra("tipo_entrega",dataModelArrayList.get(position).getTipo_entrega());
                i.putExtra("registro_mordida",dataModelArrayList.get(position).getRegistro_mordida());
                i.putExtra("antagonista",dataModelArrayList.get(position).getAntagonista());
                context.startActivity(i);

            }
        });





        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        protected TextView tvtipo, modelo, material, total;
        protected Button cerrar, ver;
    }
}