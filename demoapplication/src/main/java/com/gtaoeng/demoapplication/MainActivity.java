package com.gtaoeng.demoapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.gtaoeng.viewbuilder.ItemOnClickListener;
import com.gtaoeng.viewbuilder.MXFieldsTools;
import com.gtaoeng.viewbuilder.MXSelectCls;
import com.gtaoeng.viewbuilder.MXViewCls;
import com.gtaoeng.viewbuilder.MXViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ClassModel monitorBean;
    MXViewManager mxViewManager;
    List<MXViewCls> mxViewClsList;

    boolean bEdit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.rightButton).setVisibility(View.VISIBLE);
        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bEdit = !bEdit;

                mxViewManager.changeControlState(bEdit);

                saveTest();
            }
        });

        LinearLayout view_content = findViewById(R.id.view_content);
        monitorBean = new ClassModel();
        monitorBean.setName("撒旦撒旦");
        monitorBean.setName2("阿斯顿撒旦萨达萨达萨达萨达萨达萨达萨达萨达萨达是萨达萨达萨达萨达萨达飒飒打撒");

        // String uerId = data.getQueryParameter("userId");
        String uerId = this.getIntent().getStringExtra("arguments");

        if (!TextUtils.isEmpty(uerId)) {
            monitorBean.setName(uerId);
        }

        mxViewClsList = MXFieldsTools.loadField(monitorBean);
        mxViewManager = new MXViewManager(this, view_content);


        for (MXViewCls mxViewCls : mxViewClsList) {
            if ("select".equals(mxViewCls.getFieldName())) {
                User user = new User();
                user.setId(-1);
                user.setName("初始");
                mxViewCls.setSelectData(user);
            }
            if ("name3".equals(mxViewCls.getFieldName())) {
                mxViewCls.setSelectDatas(getAllUsers());
            }
            if ("name2".equals(mxViewCls.getFieldName())) {
                mxViewCls.setSelectDatas(getAllUsers());
            }

        }
        mxViewManager.setFontSize(18);
         mxViewManager.setViewOrientation(LinearLayout.HORIZONTAL);
        mxViewManager.buildView(mxViewClsList);


        mxViewManager.setListener(new ItemOnClickListener() {
            @Override
            public void onSelectCreateListener(Object object) {

            }

            @Override
            public void onSelectItemListener(Object object) {
                if (object instanceof MXViewCls) {
                    MXViewCls mxViewCls = (MXViewCls) object;
                    if (mxViewCls.getFieldName().equals("select")) {
                        User user = new User();
                        user.setId(100);
                        user.setName("修改");
                        mxViewCls.setFieldValue(user.getName());
                        mxViewCls.setSelectData(user);
                        mxViewManager.updateViewsFields(mxViewCls);
                    }
                }
            }

            @Override
            public void onSelectItemDisableListener(Object object) {

            }

            @Override
            public void onLocationListener(Object object) {

            }
        });
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

    private void saveTest() {

        if (!mxViewManager.checkMustFields(mxViewClsList)) {
            return;
        }

        Map<String, String> datas = mxViewManager.getViewsValuse();
        Object object1 = mxViewManager.getSelectViewValueByKey("select");
        if (object1 instanceof User) {
            datas.put("select", ((User) object1).getId() + "");
        }
    }


}
