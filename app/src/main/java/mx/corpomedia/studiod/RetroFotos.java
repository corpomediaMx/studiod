package mx.corpomedia.studiod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import mx.corpomedia.studiod.model.Audios;
import mx.corpomedia.studiod.model.Fotos;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;

public class RetroFotos extends BaseAdapter {


    private Context context;
    private ArrayList<Fotos> dataModelArrayList;

    public RetroFotos(Context context, ArrayList<Fotos> dataModelArrayList) {

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
        RetroFotos.ViewHolder holder;

        if (convertView == null) {
            holder = new RetroFotos.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_fotos, null, true);


            holder.delete = (Button) convertView.findViewById(R.id.delete);
            holder.foto = (ImageView) convertView.findViewById(R.id.foto);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (RetroFotos.ViewHolder) convertView.getTag();
        }

        holder.foto.setImageDrawable(Drawable.createFromPath(dataModelArrayList.get(position).getNombre()));



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context,"studiod",null,1);
                SQLiteDatabase db=conn.getWritableDatabase();

                db.delete( Utilidades.TABLE_FOTOS, Utilidades.CAMPO_ID+"="+dataModelArrayList.get(position).getId() ,  null);

                dataModelArrayList.remove(position);
                notifyDataSetChanged();

            }
        });



        return convertView;
    }
    // convert from byte array to bitmap

    private class ViewHolder {


        protected Button delete;
        protected ImageView foto;

    }
}