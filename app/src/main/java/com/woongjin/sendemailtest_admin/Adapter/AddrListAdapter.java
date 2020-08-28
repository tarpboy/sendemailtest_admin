package com.woongjin.sendemailtest_admin.Adapter;

import android.content.Context;
import android.icu.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;


import com.woongjin.sendemailtest_admin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by 77100658 on 2017-04-21.
 */

public class AddrListAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<HashMap<String, String>> mItemList;
    View.OnClickListener clickListener;
    private int selectPosition;



    private int color01;
    private int color15;




    public interface OnListItemClickListener {
        void onListItemClickListener(int position, View v);
    }

    public AddrListAdapter(Context context, ArrayList<HashMap<String, String>> list, View.OnClickListener listener) {
        mContext = context;
        mItemList = list;
        clickListener = listener;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectPosition = -1;

        color01 = context.getResources().getColor(R.color.black);
        color15 = context.getResources().getColor(R.color.colorAccent);

    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AddrItemHolder itemHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lay_addr_list_item, parent, false);

            itemHolder = new AddrItemHolder();
            itemHolder.layItem = (LinearLayout)convertView.findViewById(R.id.layAddrItem);
            itemHolder.tv_contract_name = (TextView) convertView.findViewById(R.id.tv_contract_name);
            itemHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            itemHolder.tv_loc = (TextView) convertView.findViewById(R.id.tv_loc);
            itemHolder.tv_from_to_date = (TextView) convertView.findViewById(R.id.tv_from_to_date);


            convertView.setTag(itemHolder);
            convertView.setOnClickListener(this);

        } else {
            itemHolder = (AddrItemHolder) convertView.getTag();
        }




        itemHolder.tv_contract_name.setText(mItemList.get(position).get("CONT_NAME") + "  ["  + mItemList.get(position).get("UPLD_DT") + "]");
        itemHolder.tv_name.setText(mItemList.get(position).get("NAME_PLACE") + " ( " + mItemList.get(position).get("TEL_PLACE") + " )"  +  "       인증번호 : " + mItemList.get(position).get("PASWD"));
        itemHolder.tv_loc.setText("근로장소 : " + mItemList.get(position).get("LOC_PLACE") );
        itemHolder.tv_from_to_date.setText("기간 : " + mItemList.get(position).get("CONT_FM_D_PLACE") + " ~ " + mItemList.get(position).get("CONT_TO_D_PLACE") );



        itemHolder.tv_name.setTextColor(color15);
        itemHolder.tv_contract_name.setTextColor(color15);
        if ("Y".equals(mItemList.get(position).get("SEND_YN"))){
            itemHolder.tv_name.setTextColor(color01);
            itemHolder.tv_contract_name.setTextColor(color01);
        }




        return convertView;
    }

    public void addList(ArrayList<HashMap<String, String>> list) {
        mItemList = list;
        selectPosition = -1;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        notifyDataSetChanged();
        clickListener.onClick(v);
    }

    public class AddrItemHolder {
        public TextView tv_contract_name;
        public TextView tv_name;
        public TextView tv_loc;
        public TextView tv_from_to_date;


        // 터치 레이아웃
        public LinearLayout layItem;
    }
}
