# viewmanager
根据字段类快速生成界面
 # 界面快速构建
#####  Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
##### Step 2. Add the dependency

```
	dependencies {
	        //系统基础框架
            implementation 'com.android.support:design:28.0.0'
            implementation 'com.android.support.constraint:constraint-layout:1.1.3'
            implementation 'com.android.support:support-v4:28.0.0'
            
            //构建包
            implementation 'com.github.gtaoeng:viewmanager:1.5' 
	}
```

## 代码示例
##### 字段类  DataBeanCls.xml

```
public class DataBeanCls {
    public final static String idCatchToolTag = "idCatchTool";
    private Long gid;
    @MXField(nameEn = "编号", fieldType = MXField.FeldType.NormalType, sort = 1)
    private String id;
    @MXField(nameEn = "水层", fieldType = MXField.FeldType.NormalType, sort = 2)
    private String layer;
    @MXField(nameEn = "深度", fieldType = MXField.FeldType.NumberType, sort = 3)
    private Integer depth;
    @MXField(nameEn = "水温", fieldType = MXField.FeldType.NumberType, sort = 4)
    private Integer temperature;
    @MXField(nameEn = "水位", fieldType = MXField.FeldType.NumberType, sort = 5)
    private Integer waterLevel;
    @MXField(nameEn = "流量", fieldType = MXField.FeldType.NumberType, sort = 6)
    private Integer velocity=0;
    @MXField(nameEn = "网具", fieldType = MXField.FeldType.SelectType, sort = 7)
    protected String idCatchTool;

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(Integer waterLevel) {
        this.waterLevel = waterLevel;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public String getIdCatchTool() {
        return idCatchTool;
    }

    public void setIdCatchTool(String idCatchTool) {
        this.idCatchTool = idCatchTool;
    }
}
```


##### activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F4F4F4">

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">


        <ScrollView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:scrollbars="none">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">

                <LinearLayout
                    android:id="@+id/view_content"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_marginBottom="50dp"
                    android:orientation="vertical"></LinearLayout>

            </LinearLayout>


        </ScrollView>

    </RelativeLayout>
</android.support.constraint.ConstraintLayout>
```
##### MainActivity.java

```
public class MainActivity extends AppCompatActivity {

    MXViewManager mxViewManager;
    DataBeanCls dataBeanCls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBeanCls = new DataBeanCls();
        String dataID = UUID.randomUUID().toString();
        dataBeanCls.setId(dataID);

        LinearLayout view_content = findViewById(R.id.view_content);
        List<MXViewCls> mxViewClsList = MXFieldsTools.loadField(dataBeanCls);
        for (MXViewCls mxViewCls : mxViewClsList) {
            if ("id".equals(mxViewCls.getFieldName())) {
                mxViewCls.setCanEdit(false);
            }
        }


        mxViewManager = new MXViewManager(this, view_content);
        mxViewManager.buildView(mxViewClsList);

        List<MXSelectCls> selectList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MXSelectCls mx = new MXSelectCls();
            String name = "名称"+(i+1);
            mx.setName(name);
            mx.setObject(name);
            selectList.add(mx);
        }
        mxViewManager.updateSelectDatas(DataBeanCls.idCatchToolTag, selectList);


        mxViewManager.setListener(new ItemOnClickListener() {
            @Override
            public void onSelectCreateListener(Object object) {

            }

            @Override
            public void onSelectItemListener(Object object) {
                if (object != null && object.getClass() == MXViewCls.class) {

                    MXViewCls mxViewCls = (MXViewCls) object;
                    Object objCtl = mxViewManager.getTextViewByFieldName(mxViewCls.getFieldName());

                }
            }

            @Override
            public void onSelectItemDisableListener(Object object) {

            }

            @Override
            public void onLocationListener(Object object) {
                if (object != null && object.getClass() == MXViewCls.class) {


                }
            }
        });


    }
}
```



