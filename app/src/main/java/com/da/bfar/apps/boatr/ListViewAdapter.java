package com.da.bfar.apps.boatr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BFAR-PC on 2/23/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    private String TAG = ListViewAdapter.class.getSimpleName();
    private Context context;
    private Holder hold;
    private LayoutInflater inflate;
    private int layout;
    private ArrayList<FisherFolk> list;
    private ListviewCallback listcallback;
    private Button btnView;
    public ListViewAdapter(Context c, ArrayList <FisherFolk> list,ListviewCallback callback) {
        context = c;
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.listcallback = callback;


    }


    @Override
    public int getCount() {
        return  list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.fisherfolk_layout, null);

            hold = new Holder();
            Init(convertView);
            convertView.setTag(hold);
        } else {
            hold = (Holder) convertView.getTag();
        }
        hold.tvCFRNo.setText(list.get(position).getCFRNO());
        hold.tvName.setText(list.get(position).getFullname());
        hold.tvAddress.setText(list.get(position).getAddress());

        return convertView;
    }
    public void getSelectedRow(int row){
        BoatrSingleton.getInstance().setFisherFolkName(list.get(row).getFullname().toString());
        BoatrSingleton.getInstance().setFisherFolkAddress(list.get(row).getAddress().toString());
        BoatrSingleton.getInstance().setBoatCFRNO(list.get(row).getCFRNO());

    }
    class Holder {
        TextView  tvName, tvAddress, tvCFRNo;

    }

    public void Init(View convertView){
        hold.tvName = (TextView) convertView.findViewById(R.id.FisherFolkName);
        hold.tvAddress=(TextView)convertView.findViewById(R.id.FisherFolkAddress);
        hold.tvCFRNo =(TextView)convertView.findViewById(R.id.CFRNo);
    /*    hold.btnDelete =(Button)convertView.findViewById(R.id.btnDelete);
        hold.tVFrid = (TextView)convertView.findViewById(R.id.tvFrid);
        hold.btnView = (Button)convertView.findViewById(R.id.btnView);*/
    }

}
