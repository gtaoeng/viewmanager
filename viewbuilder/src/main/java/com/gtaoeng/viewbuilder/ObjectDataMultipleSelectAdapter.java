package com.gtaoeng.viewbuilder;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by gtaoeng on 2018/4/9.
 */

public class ObjectDataMultipleSelectAdapter extends RecyclerView.Adapter<ObjectDataMultipleSelectAdapter.DataListViewHolder> {

    AlertDialog mDialog;
    private Context mContext;
    private List<?> baseData;

    private TextView textView;


    public ObjectDataMultipleSelectAdapter(AlertDialog dialog, Context context, TextView textView) {
        mContext = context;
        mDialog = dialog;
        this.textView = textView;
    }

    public void setDataList(List<?> baseDatas) {
        this.baseData = baseDatas;
        notifyDataSetChanged();
    }


    @Override
    public DataListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_list_multiple_item, parent, false);
        return new DataListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataListViewHolder holder, final int position) {

        final MXSelectCls item = (MXSelectCls) baseData.get(position);

        holder.tv_item_name.setText((position + 1) + "、" + item.getName());

        if (item.isChecked()) {
            holder.item_checkbox.setBackgroundResource(R.drawable.checked);
        } else {
            holder.item_checkbox.setBackgroundResource(R.color.transparent);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bCheck = item.isChecked();
                bCheck = !bCheck;
                item.setChecked(bCheck);
                if (bCheck) {
                    holder.item_checkbox.setBackgroundResource(R.drawable.checked);
                } else {
                    holder.item_checkbox.setBackgroundResource(R.color.transparent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return baseData != null ? baseData.size() : 0;
    }

    class DataListViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_name;
        ImageView item_checkbox;

        public DataListViewHolder(View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            item_checkbox = itemView.findViewById(R.id.item_checkbox);
        }
    }
}
