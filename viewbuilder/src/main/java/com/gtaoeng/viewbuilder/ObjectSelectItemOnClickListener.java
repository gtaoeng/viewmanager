package com.gtaoeng.viewbuilder;

import android.app.AlertDialog;
import android.widget.TextView;

import java.util.List;

public interface ObjectSelectItemOnClickListener {
    void onDataSelectItemClickListener(AlertDialog dialog, List<?> datalist, int postion, TextView textView);
}
