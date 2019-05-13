package com.gtaoeng.demoapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gtaoeng.viewbuilder.MXFieldsTools;
import com.gtaoeng.viewbuilder.MXSelectCls;
import com.gtaoeng.viewbuilder.MXViewCls;
import com.gtaoeng.viewbuilder.MXViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MXViewManager mxViewManager;
    ClassModel monitorBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.rightButton).setVisibility(View.VISIBLE);
        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> datas = mxViewManager.getViewsValuse();
                // ClassUtil.buildClassData(monitorBean, datas);

            }
        });

        LinearLayout view_content = findViewById(R.id.view_content);
        monitorBean = new ClassModel();
        List<MXViewCls> mxViewClsList = MXFieldsTools.loadField(monitorBean);
        mxViewManager = new MXViewManager(this, view_content);

        List<String> yesOrNo = new ArrayList<>();
        yesOrNo.add("是");
        yesOrNo.add("否");

        List<User> users = new ArrayList<>();


        for (MXViewCls mxViewCls : mxViewClsList) {

            if ("bm".equals(mxViewCls.getFieldName())) {
                mxViewCls.setCanEdit(false);
            } else if ("name2".equals(mxViewCls.getFieldName())) {
                mxViewCls.setSelectDatas(yesOrNo);
            } else if ("name3".equals(mxViewCls.getFieldName())) {
                mxViewCls.setSelectDatas( getAllUsers());
            } else if ("isWarn".equals(mxViewCls.getFieldName())) {
                mxViewCls.setSelectDatas(yesOrNo);
            }


        }

        mxViewManager.buildView(mxViewClsList);
    }

    private List<MXSelectCls> getAllUsers() {
        int k = 0;
        List<MXSelectCls> selectCls = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            k++;
            User user = new User();
            user.setId(k);
            user.setName("user" + k);
            MXSelectCls mxSelectCls = new MXSelectCls();
            mxSelectCls.setName("user" + k);
            mxSelectCls.setObject(user);
            selectCls.add(mxSelectCls);
        }
        return selectCls;

    }
}
