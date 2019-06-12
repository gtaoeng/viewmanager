package com.gtaoeng.viewbuilder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.LinearLayout.VERTICAL;

public class MXViewManager implements ObjectSelectItemOnClickListener {
    private Context context;
    private LinearLayout rootView;
    private HashMap<String, Object> textViews;


    private ItemOnClickListener listener;

    public MXViewManager(Context context, LinearLayout rootView) {
        this.context = context;
        this.rootView = rootView;
        UIUtils.mContext = context;
    }

    public void setListener(ItemOnClickListener listener) {
        this.listener = listener;
    }

    public void buildView(List<MXViewCls> viewFieldList) {
        this.rootView.removeAllViews();
        textViews = new HashMap<>();
        if (viewFieldList != null && viewFieldList.size() > 0) {
            for (int i = 0; i < viewFieldList.size(); i++) {
                MXViewCls viewCls = viewFieldList.get(i);
                switch (viewCls.getFieldType()) {
                    case MXViewCls.NormalType:
                    case MXViewCls.NumberType:
                    case MXViewCls.FloatType:
                    case MXViewCls.NoteType:
                    case MXViewCls.PhoneType:
                    case MXViewCls.PswType:
                        addTextView(viewCls);
                        break;
                    case MXViewCls.SelectType:
                    case MXViewCls.MultipleSelectType:
                        addComboxView(viewCls);
                        break;
                    case MXViewCls.DateType:
                        addDateView(viewCls);
                        break;
                    case MXViewCls.MapType:
                        addMapView(viewCls);
                        break;
                    default:
                        addTextView(viewCls);
                        break;
                }

            }
        }
    }

    private void addTextView(MXViewCls viewCls) {
        LinearLayout rowLayout = getRowLinearLayout();
        TextView lableName = getLabelTextView();
        lableName.setText(viewCls.getDisplayName());
        rowLayout.addView(lableName);

        EditText valueText = null;

        if (MXViewCls.NumberType == viewCls.getFieldType()) {
            valueText = getTextLayout(rowLayout);
            valueText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (MXViewCls.FloatType == viewCls.getFieldType()) {
            valueText = getTextLayout(rowLayout);
            valueText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (MXViewCls.NoteType == viewCls.getFieldType()) {
            valueText = getTextLongLayout(rowLayout);
        } else if (MXViewCls.PhoneType == viewCls.getFieldType()) {
            valueText = getTextLayout(rowLayout);
            valueText.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (MXViewCls.PswType == viewCls.getFieldType()) {
            valueText = getTextLayout(rowLayout);
            valueText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            valueText = getTextLayout(rowLayout);
        }
        valueText.setTag(viewCls.getFieldName());

        valueText.setEnabled(viewCls.getCanEdit());
        if (!viewCls.getCanEdit()) {
            valueText.setHint("");
            valueText.setTextColor(context.getResources().getColor(R.color.gray2));
        }
        valueText.setText(viewCls.getFieldValue());

        rootView.addView(rowLayout);


        textViews.put(viewCls.getFieldName(), valueText);
    }

    private void addComboxView(final MXViewCls viewCls) {
        LinearLayout rowLayout = getRowLinearLayout();
        TextView lableName = getLabelTextView();
        lableName.setText(viewCls.getDisplayName());
        rowLayout.addView(lableName);
        View.OnClickListener listener = null;

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewCls.getCanEdit()) {
                    TextView valueText = (TextView) v.getTag();
                    List<?> datas = (List<?>) valueText.getTag(R.id.tag_first);
                    if (viewCls.getFieldType() == MXViewCls.SelectType) {
                        showSelectDialog(datas, valueText);
                    } else if (viewCls.getFieldType() == MXViewCls.MultipleSelectType) {
                        showMultipleSelectDialog(datas, valueText);
                    }
                } else {
                    if (MXViewManager.this.listener != null) {
                        MXViewManager.this.listener.onSelectItemDisableListener(viewCls);
                    }
                }
            }
        };

        TextView valueText = getComboxLayout(rowLayout, listener);
        if (!viewCls.getCanEdit()) {
            valueText.setHint("");
            valueText.setTextColor(context.getResources().getColor(R.color.gray2));
        }

        valueText.setText(viewCls.getFieldValue());
        valueText.setTag(R.id.tag_first, viewCls.getSelectDatas());
        valueText.setTag(R.id.tag_three, viewCls);

        rootView.addView(rowLayout);

        textViews.put(viewCls.getFieldName(), valueText);

    }

    private void addDateView(final MXViewCls viewCls) {
        LinearLayout rowLayout = getRowLinearLayout();
        TextView lableName = getLabelTextView();
        lableName.setText(viewCls.getDisplayName());
        rowLayout.addView(lableName);


        View.OnClickListener listener = null;
        if (viewCls.getCanEdit()) {
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TextView valueText = (TextView) v.getTag();
                    showSelectDate(context, valueText);
                }
            };
        }
        TextView valueText = getDateLayout(rowLayout, listener);
        valueText.setText(viewCls.getFieldValue());
        // valueText.setTag(viewCls.getFieldName());


        rootView.addView(rowLayout);

        textViews.put(viewCls.getFieldName(), valueText);

    }

    private void addMapView(final MXViewCls viewCls) {
        LinearLayout rowLayout = getRowLinearLayout();
        TextView lableName = getLabelTextView();
        lableName.setText(viewCls.getDisplayName());
        rowLayout.addView(lableName);


        View.OnClickListener listener = null;

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewCls.getCanEdit()) {
                    if (MXViewManager.this.listener != null) {
                        MXViewManager.this.listener.onLocationListener(viewCls);
                    }
                } else {
                    if (MXViewManager.this.listener != null) {
                        MXViewManager.this.listener.onSelectItemDisableListener(viewCls);
                    }
                }
            }
        };

        TextView valueText = getMapLayout(rowLayout, listener);
        if (!viewCls.getCanEdit()) {
            valueText.setHint("");
            valueText.setTextColor(context.getResources().getColor(R.color.gray2));
        }
        valueText.setText(viewCls.getFieldValue());


        rootView.addView(rowLayout);

        textViews.put(viewCls.getFieldName(), valueText);

    }


    private LinearLayout getRowLinearLayout() {
        LinearLayout oneLinearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams llsub = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llsub.setMargins(UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5));
        oneLinearLayout.setLayoutParams(llsub);
        oneLinearLayout.setBackground(context.getDrawable(R.drawable.gridground));
        oneLinearLayout.setPadding(UIUtils.dp2Px(2), UIUtils.dp2Px(2), UIUtils.dp2Px(2), UIUtils.dp2Px(2));
        oneLinearLayout.setOrientation(VERTICAL);
        return oneLinearLayout;
    }

    private TextView getLabelTextView() {
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams ll_value_tv = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ll_value_tv.addRule(RelativeLayout.CENTER_VERTICAL);
        ll_value_tv.setMargins(UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5));
        textView.setGravity(Gravity.LEFT);
        textView.setTextColor(context.getResources().getColor(R.color.black));
        textView.setTextSize(UIUtils.dp2sp(15));
        textView.setLayoutParams(ll_value_tv);
        return textView;
    }

    //文本类型
    private EditText getTextLayout(LinearLayout parentView) {
        RelativeLayout oneLayout = new RelativeLayout(context);
        LinearLayout.LayoutParams llsub = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                UIUtils.dp2Px(40));
        oneLayout.setLayoutParams(llsub);
        oneLayout.setBackground(context.getDrawable(R.drawable.textbackground_report));

        //内容
        EditText value_tv = new EditText(context);
        RelativeLayout.LayoutParams ll_value_tv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ll_value_tv.setMargins(UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5));
        ll_value_tv.addRule(RelativeLayout.CENTER_IN_PARENT);

        value_tv.setPadding(0, 0, 0, 0);
        value_tv.setLayoutParams(ll_value_tv);
        value_tv.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        value_tv.setTextColor(context.getResources().getColor(R.color.black));
        value_tv.setTextSize(UIUtils.dp2sp(12));
        value_tv.setHint("请输入");
        value_tv.setGravity(Gravity.LEFT);
        oneLayout.addView(value_tv);
        parentView.addView(oneLayout);
        return value_tv;
    }

    private EditText getTextLongLayout(LinearLayout parentView) {
        RelativeLayout oneLayout = new RelativeLayout(context);
        LinearLayout.LayoutParams llsub = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        oneLayout.setLayoutParams(llsub);
        oneLayout.setBackground(context.getDrawable(R.drawable.textbackground_report));

        //内容
        EditText value_tv = new EditText(context);
        RelativeLayout.LayoutParams ll_value_tv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                UIUtils.dp2Px(100));
        ll_value_tv.setMargins(UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5));
        ll_value_tv.addRule(RelativeLayout.CENTER_IN_PARENT);

        value_tv.setPadding(0, 0, 0, 0);
        value_tv.setLayoutParams(ll_value_tv);
        value_tv.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        value_tv.setTextColor(context.getResources().getColor(R.color.black));
        value_tv.setTextSize(UIUtils.dp2sp(12));
        value_tv.setHint("请输入");
        value_tv.setLines(3);
        value_tv.setGravity(Gravity.TOP);
        oneLayout.addView(value_tv);
        parentView.addView(oneLayout);
        return value_tv;
    }

    int minrRID = 0xaaabc;

    //选择器
    private TextView getComboxLayout(LinearLayout parentView, View.OnClickListener listener) {
        RelativeLayout oneLayout = new RelativeLayout(context);
        LinearLayout.LayoutParams llsub = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                UIUtils.dp2Px(40));
        oneLayout.setLayoutParams(llsub);
        oneLayout.setBackground(context.getDrawable(R.drawable.textbackground_report));

        //向下箭头
        int rid = minrRID;
        minrRID++;
        ImageView imageView = new ImageView(context);
        imageView.setId(rid);
        RelativeLayout.LayoutParams ll_image = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ll_image.addRule(RelativeLayout.CENTER_VERTICAL);
        ll_image.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ll_image.setMargins(0, 0, UIUtils.dp2Px(10), 0);
        imageView.setLayoutParams(ll_image);
        imageView.setBackground(context.getDrawable(R.drawable.icon_pull));
        //内容
        TextView value_tv = new TextView(context);
        RelativeLayout.LayoutParams ll_value_tv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                UIUtils.dp2Px(20));
        ll_value_tv.setMargins(UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5));
        ll_value_tv.addRule(RelativeLayout.CENTER_VERTICAL);
        value_tv.setLayoutParams(ll_value_tv);
        value_tv.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        value_tv.setTextColor(context.getResources().getColor(R.color.black));
        value_tv.setTextSize(UIUtils.dp2sp(12));
        value_tv.setHint("请选择");
        ll_value_tv.addRule(RelativeLayout.LEFT_OF, rid);

        oneLayout.addView(imageView);
        oneLayout.addView(value_tv);
        oneLayout.setTag(value_tv);
        oneLayout.setOnClickListener(listener);
        parentView.addView(oneLayout);

        return value_tv;
    }

    //时间选择
    private TextView getDateLayout(LinearLayout parentView, View.OnClickListener listener) {
        RelativeLayout oneLayout = new RelativeLayout(context);
        LinearLayout.LayoutParams llsub = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                UIUtils.dp2Px(40));
        oneLayout.setLayoutParams(llsub);
        oneLayout.setBackground(context.getDrawable(R.drawable.textbackground_report));

        //向下箭头
        int rid = minrRID;
        minrRID++;
        ImageView imageView = new ImageView(context);
        imageView.setId(rid);
        RelativeLayout.LayoutParams ll_image = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ll_image.addRule(RelativeLayout.CENTER_VERTICAL);
        ll_image.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ll_image.setMargins(0, 0, UIUtils.dp2Px(10), 0);
        imageView.setLayoutParams(ll_image);
        imageView.setBackground(context.getDrawable(R.drawable.icon_calendar_small));
        //内容
        TextView value_tv = new TextView(context);
        RelativeLayout.LayoutParams ll_value_tv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                UIUtils.dp2Px(20));
        ll_value_tv.setMargins(UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5));
        ll_value_tv.addRule(RelativeLayout.CENTER_VERTICAL);
        value_tv.setLayoutParams(ll_value_tv);
        value_tv.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        value_tv.setTextColor(context.getResources().getColor(R.color.black));
        value_tv.setTextSize(UIUtils.dp2sp(12));
        value_tv.setHint("请选择");
        ll_value_tv.addRule(RelativeLayout.LEFT_OF, rid);

        oneLayout.addView(imageView);
        oneLayout.addView(value_tv);
        oneLayout.setTag(value_tv);
        oneLayout.setOnClickListener(listener);
        parentView.addView(oneLayout);

        return value_tv;
    }

    private TextView getMapLayout(LinearLayout parentView, View.OnClickListener listener) {
        RelativeLayout oneLayout = new RelativeLayout(context);
        LinearLayout.LayoutParams llsub = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                UIUtils.dp2Px(40));
        oneLayout.setLayoutParams(llsub);
        oneLayout.setBackground(context.getDrawable(R.drawable.textbackground_report));

        //向下箭头
        int rid = minrRID;
        minrRID++;
        ImageView imageView = new ImageView(context);
        imageView.setId(rid);
        RelativeLayout.LayoutParams ll_image = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ll_image.addRule(RelativeLayout.CENTER_VERTICAL);
        ll_image.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ll_image.setMargins(0, 0, UIUtils.dp2Px(10), 0);
        imageView.setLayoutParams(ll_image);
        imageView.setBackground(context.getDrawable(R.drawable.icon_map_samll));
        //内容
        TextView value_tv = new TextView(context);
        RelativeLayout.LayoutParams ll_value_tv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                UIUtils.dp2Px(20));
        ll_value_tv.setMargins(UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5), UIUtils.dp2Px(5));
        ll_value_tv.addRule(RelativeLayout.CENTER_VERTICAL);
        value_tv.setLayoutParams(ll_value_tv);
        value_tv.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        value_tv.setTextColor(context.getResources().getColor(R.color.black));
        value_tv.setTextSize(UIUtils.dp2sp(12));
        value_tv.setHint("选择位置");
        ll_value_tv.addRule(RelativeLayout.LEFT_OF, rid);

        oneLayout.addView(imageView);
        oneLayout.addView(value_tv);
        oneLayout.setTag(value_tv);
        oneLayout.setOnClickListener(listener);
        parentView.addView(oneLayout);

        return value_tv;
    }

    //获取界面值
    public Map<String, String> getViewsValuse() {
        Map<String, String> datas = new HashMap<>();
        if (textViews != null && textViews.size() > 0) {

            for (String key : textViews.keySet()) {
                System.out.println("Key: " + key + " Value: " + textViews.get(key));

                Object object = textViews.get(key);
                if (object.getClass() == TextView.class) {
                    TextView tv = (TextView) object;
                    String val = tv.getText().toString();
                    datas.put(key, val);
                } else if (object.getClass() == EditText.class) {
                    EditText tv = (EditText) object;
                    String val = tv.getText().toString();
                    datas.put(key, val);
                }
            }
        }
        return datas;
    }

    /**
     * 批量更新界面值
     */
    public void updateViewsFields(List<MXViewCls> mxViewClsList) {
        if (textViews != null && textViews.size() > 0) {
            for (int i = 0; i < mxViewClsList.size(); i++) {
                String fieldName = mxViewClsList.get(i).getFieldName();
                String filedVal = mxViewClsList.get(i).getFieldValue();
                updateViewsFields(fieldName, filedVal);
            }
        }
    }

    /**
     * 单个更新界面值
     *
     * @param fieldName
     * @param filedVal
     */
    public void updateViewsFields(String fieldName, String filedVal) {

        if (textViews.containsKey(fieldName)) {
            Object object = textViews.get(fieldName);
            if (object.getClass() == TextView.class) {
                TextView tv = (TextView) object;
                tv.setText(filedVal);
            } else if (object.getClass() == EditText.class) {
                EditText tv = (EditText) object;
                tv.setText(filedVal);
            }
        }
    }

    public Object getTextViewByFieldName(String fieldName) {

        if (textViews.containsKey(fieldName)) {
            Object object = textViews.get(fieldName);
            return object;
        }
        return null;
    }

    /**
     * 赋值选择器的界面值
     *
     * @param fieldName
     * @param filedVal
     */
    public void updateViewsSelectFields(String fieldName, String filedVal) {

        if (textViews.containsKey(fieldName)) {
            Object object = textViews.get(fieldName);
            if (object.getClass() == TextView.class) {
                TextView tv = (TextView) object;
                tv.setText(filedVal);
            } else if (object.getClass() == EditText.class) {
                EditText tv = (EditText) object;
                tv.setText(filedVal);
            }
        }
    }

    /**
     * 更新选择器值
     *
     * @param mxViewClsList
     */
    public void updateSelectDatas(List<MXViewCls> mxViewClsList) {
        if (textViews != null && textViews.size() > 0) {
            for (int i = 0; i < mxViewClsList.size(); i++) {
                String fieldName = mxViewClsList.get(i).getFieldName();
                List<?> datas = mxViewClsList.get(i).getSelectDatas();
                updateSelectDatas(fieldName, datas);
            }
        }
    }

    public void changeControlState(boolean bEnable) {
        for (String key : textViews.keySet()) {
            Object object = textViews.get(key);
            if (object instanceof TextView) {
                TextView valueText = (TextView) object;
                View parentView = (View) valueText.getParent();

                if (bEnable) {
                    valueText.setTextColor(context.getResources().getColor(R.color.black));
                } else {
                    valueText.setTextColor(context.getResources().getColor(R.color.gray2));
                }
                valueText.setEnabled(bEnable);
                parentView.setEnabled(bEnable);

            }
        }
    }

    /**
     * 更新选择器值
     *
     * @param fieldName
     * @param datas
     */
    public void updateSelectDatas(String fieldName, List<?> datas) {

        if (textViews.containsKey(fieldName)) {
            Object object = textViews.get(fieldName);
            if (object.getClass() == TextView.class) {
                TextView tv = (TextView) object;
                tv.setTag(R.id.tag_first, datas);

            }
        }
    }

    /**
     * 根据字段名获取界面值
     *
     * @param fieldName
     * @return
     */
    public Object getSelectViewValueByKey(String fieldName) {
        if (textViews.containsKey(fieldName)) {
            Object object = textViews.get(fieldName);
            if (object.getClass() == TextView.class) {
                TextView tv = (TextView) object;
                String val = tv.getText().toString();
                Object tag = tv.getTag(R.id.tag_second);
                if (tag != null) {
                    return tag;
                }
                return val;
            }
        }
        return "";
    }

    public String getViewValueByKey(String fieldName) {
        if (textViews.containsKey(fieldName)) {
            Object object = textViews.get(fieldName);
            if (object.getClass() == TextView.class) {
                TextView tv = (TextView) object;
                String val = tv.getText().toString();

                return val;
            } else if (object.getClass() == EditText.class) {
                EditText tv = (EditText) object;
                String val = tv.getText().toString();
                return val;
            }
        }
        return "";
    }

    //选择框
    private LayoutInflater inflater;

    private void showSelectDialog(List<?> dialogData, TextView textView) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        View view = inflater.inflate(R.layout.dialog_select_list, null);
        AlertDialog flowersDialog = new AlertDialog.Builder(context).create();
        flowersDialog.setCancelable(true);
        flowersDialog.show();
        flowersDialog.getWindow().setContentView(view);
        flowersDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


        RecyclerView rlv_data_list = view.findViewById(R.id.rlv_data_list);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rlv_data_list.setLayoutManager(manager);
        rlv_data_list.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        final ObjectDataSelectAdapter varietiesAdapter = new ObjectDataSelectAdapter(flowersDialog, context, this, textView);
        rlv_data_list.setAdapter(varietiesAdapter);
        varietiesAdapter.setDataList(dialogData);
    }

    public static void showSelectDialog(Context context, List<?> dialogData, TextView textView, ObjectSelectItemOnClickListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_select_list, null);
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setContentView(view);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        RecyclerView rlv_data_list = view.findViewById(R.id.rlv_data_list);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rlv_data_list.setLayoutManager(manager);
        rlv_data_list.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        final ObjectDataSelectAdapter varietiesAdapter = new ObjectDataSelectAdapter(dialog, context, listener, textView);
        rlv_data_list.setAdapter(varietiesAdapter);
        varietiesAdapter.setDataList(dialogData);
    }

    private void showMultipleSelectDialog(final List<?> dialogData, final TextView textView) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        View view = inflater.inflate(R.layout.dialog_select_list_multiple, null);
        final AlertDialog flowersDialog = new AlertDialog.Builder(context).create();
        flowersDialog.setCancelable(false);
        flowersDialog.show();
        flowersDialog.getWindow().setContentView(view);
        flowersDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


        RecyclerView rlv_data_list = view.findViewById(R.id.rlv_data_list);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rlv_data_list.setLayoutManager(manager);
        rlv_data_list.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        final ObjectDataMultipleSelectAdapter varietiesAdapter = new ObjectDataMultipleSelectAdapter(flowersDialog, context, textView);
        rlv_data_list.setAdapter(varietiesAdapter);
        varietiesAdapter.setDataList(dialogData);
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flowersDialog != null && flowersDialog.isShowing()) {
                    flowersDialog.dismiss();
                }

                MXViewCls mxViewCls = (MXViewCls) textView.getTag(R.id.tag_three);

                String names = "";
                List<MXSelectCls> list = new ArrayList<>();
                for (Object object : dialogData) {
                    if (object.getClass() == MXSelectCls.class) {
                        MXSelectCls mxSelectCls = (MXSelectCls) object;
                        if (mxSelectCls.isChecked()) {
                            list.add(mxSelectCls);
                            if (!TextUtils.isEmpty(names)) {
                                names += ",";
                            }
                            names += mxSelectCls.getName();
                        }
                    }
                }

                textView.setText(names);
                textView.setTag(R.id.tag_second, list);
                if (listener != null) {
                    listener.onSelectItemListener(mxViewCls);
                }

            }
        });
    }

    private void showSelectDate(Context context, final TextView textView) {
        String dates = textView.getText().toString();
        String[] dds = dates.split("-");
        int year = 0, month = 0, day = 0;
        if (dds == null || dds.length != 3) {
            try {
                year = Integer.parseInt(dds[0]);
                month = Integer.parseInt(dds[1]) - 1;
                day = Integer.parseInt(dds[2]);
            } catch (Exception e) {

            }
        }
        if (year == 0 || month == 0 || day == 0) {

            Calendar cd = Calendar.getInstance();
            Date date = new Date();
            cd.setTime(date);
            year = cd.get(Calendar.YEAR);
            month = cd.get(Calendar.MONTH);
            day = cd.get(Calendar.DAY_OF_MONTH);

        }


        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String sMonth = (month + 1) + "";
                if (sMonth.length() == 1) {
                    sMonth = "0" + sMonth;
                }
                String sDay = dayOfMonth + "";
                if (sDay.length() == 1) {
                    sDay = "0" + sDay;
                }

                String ss = String.format("%d-%s-%s", year, sMonth, sDay);
                textView.setText(ss);
            }
        }, year, month, day).show();
    }

    @Override
    public void onDataSelectItemClickListener(AlertDialog dialog, List<?> townData, int position, TextView textView) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Object object = townData.get(position);

        MXViewCls mxViewCls = (MXViewCls) textView.getTag(R.id.tag_three);

        if (object.getClass() == String.class) {
            String item = (String) object;
            textView.setText(item);
            if (listener != null) {
                listener.onSelectItemListener(mxViewCls);
            }

        } else if (object.getClass() == MXSelectCls.class) {
            MXSelectCls mxSelectCls = (MXSelectCls) object;

            if (mxSelectCls.getObject() != null) {

                if (mxSelectCls.getObject().getClass() == String.class) {

                    if (MXSelectCls.createNew.equals(mxSelectCls.getName())
                            && listener != null) {
                        listener.onSelectCreateListener(mxSelectCls.getObject());
                    }
                } else {
                    textView.setText(mxSelectCls.getName());
                    textView.setTag(R.id.tag_second, mxSelectCls.getObject());
                    if (listener != null) {
                        listener.onSelectItemListener(mxViewCls);
                    }
                }
            }
        }
    }
}
