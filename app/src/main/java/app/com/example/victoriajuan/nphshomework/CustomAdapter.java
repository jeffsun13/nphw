package app.com.example.victoriajuan.nphshomework;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemname;
    private final List<String> itemdescr;
    private final List<Integer> imgid;

    public CustomAdapter(Activity context, List<String> itemname, List<String> itemdescr, List<Integer> imgid) {
        super(context, R.layout.list_item, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.itemdescr=itemdescr;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.list_item_forecast_textview);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.list_item_forecast_textview2);

        txtTitle.setText(itemname.get(position));
        imageView.setImageResource(imgid.get(position));
        extratxt.setText(itemdescr.get(position));
        return rowView;

    };


}