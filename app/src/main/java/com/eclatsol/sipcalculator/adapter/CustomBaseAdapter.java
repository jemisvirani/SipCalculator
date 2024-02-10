package com.eclatsol.sipcalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eclatsol.sipcalculator.R;
import com.eclatsol.sipcalculator.model.OurOtherAppRowItemModel;
import java.util.List;


public abstract class CustomBaseAdapter extends BaseAdapter {
    Context context;
    List<OurOtherAppRowItemModel> rowItems;

    public CustomBaseAdapter(Context context, List<OurOtherAppRowItemModel> list) {
        this.context = context;
        this.rowItems = list;
    }

    @Override
    public int getCount() {
        return this.rowItems.size();
    }

    @Override 
    public Object getItem(int i) {
        return this.rowItems.get(i);
    }

    @Override 
    public long getItemId(int i) {
        return this.rowItems.indexOf(getItem(i));
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.icon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        OurOtherAppRowItemModel ourOtherAppRowItemModel = (OurOtherAppRowItemModel) getItem(i);
        viewHolder.txtTitle.setText(ourOtherAppRowItemModel.getTitle());
        viewHolder.imageView.setImageResource(ourOtherAppRowItemModel.getImageId());
        return view;
    }

    public abstract void onItemClick(AdapterView<?> adapterView, View view, int i2, long j);


    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;

        private ViewHolder() {
        }
    }
}
