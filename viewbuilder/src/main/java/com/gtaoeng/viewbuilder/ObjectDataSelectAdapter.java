package com.gtaoeng.viewbuilder;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by gtaoeng on 2018/4/9.
 */

public class ObjectDataSelectAdapter extends RecyclerView.Adapter<ObjectDataSelectAdapter.DataListViewHolder> {

    AlertDialog mDialog;
    private Context mContext;
    private List<?> baseData;
    private ObjectSelectItemOnClickListener mInterface;

    private TextView textView;


    public ObjectDataSelectAdapter(AlertDialog dialog, Context context, ObjectSelectItemOnClickListener mInterface, TextView textView) {
        mContext = context;
        mDialog = dialog;
        this.mInterface = mInterface;
        this.textView = textView;
    }

    public void setDataList(List<?> baseDatas) {
        this.baseData = baseDatas;
        notifyDataSetChanged();
    }


    @Override
    public DataListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_list_item, parent, false);
        return new DataListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataListViewHolder holder, final int position) {

        Object object = baseData.get(position);

        if (object.getClass() == String.class) {
            String item = (String) object;
            holder.tv_item_name.setText((position + 1) + "、" + item);
        } else if (object.getClass() == MXSelectCls.class) {
            MXSelectCls item = (MXSelectCls) object;
            holder.tv_item_name.setText((position + 1) + "、" + item.getName());
        }


        holder.tv_item_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.onDataSelectItemClickListener(mDialog, baseData, position, textView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return baseData != null ? baseData.size() : 0;
    }

    class DataListViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_name;

        public DataListViewHolder(View itemView) {
            super(itemView);
            tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
        }
    }
}
