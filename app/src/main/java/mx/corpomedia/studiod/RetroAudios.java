package mx.corpomedia.studiod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import mx.corpomedia.studiod.model.Audios;
import mx.corpomedia.studiod.model.IndicacionesModel;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;

public class RetroAudios extends BaseAdapter {


    private Context context;
    private ArrayList<Audios> dataModelArrayList;

    public RetroAudios(Context context, ArrayList<Audios> dataModelArrayList) {

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
        RetroAudios.ViewHolder holder;

        if (convertView == null) {
            holder = new RetroAudios.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_audios, null, true);


            holder.delete = (Button) convertView.findViewById(R.id.delete);
            holder.reproducir = (Button) convertView.findViewById(R.id.reproducir);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (RetroAudios.ViewHolder) convertView.getTag();
        }

        holder.reproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try{

                    mediaPlayer.setDataSource(dataModelArrayList.get(position).getNombre());
                    mediaPlayer.prepare();

                }catch (IOException e){

                }

                mediaPlayer.start();
                Toast.makeText(context,"Reproduciendo audio",Toast.LENGTH_SHORT).show();
            }

        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context,"studiod",null,1);
                        SQLiteDatabase db=conn.getWritableDatabase();

                        db.delete( Utilidades.TABLE_AUDIOS, Utilidades.CAMPO_ID+"="+dataModelArrayList.get(position).getId() ,  null);

                        dataModelArrayList.remove(position);
                        notifyDataSetChanged();

            }
        });



        return convertView;
    }

    private class ViewHolder {


        protected Button delete, reproducir;

    }
}
