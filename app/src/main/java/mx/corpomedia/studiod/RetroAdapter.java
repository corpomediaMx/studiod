package mx.corpomedia.studiod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mx.corpomedia.studiod.model.IndicacionesModel;

public class RetroAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<IndicacionesModel> dataModelArrayList;

    public RetroAdapter(Context context, ArrayList<IndicacionesModel> dataModelArrayList) {

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_layout, null, true);

            holder.iv = (ImageView) convertView.findViewById(R.id.imagen);
            holder.tvname = (TextView) convertView.findViewById(R.id.nombre);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        Picasso.with(context)
                .load("https://www.studiodlab.com.mx/admin/app/img/"+dataModelArrayList.get(position).getStrimagen())
                .placeholder(R.drawable.load)
                .into(holder.iv);

        holder.tvname.setText(""+dataModelArrayList.get(position).getNombre());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        protected ImageView iv;
    }

}

