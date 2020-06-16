package com.gtaoeng.viewbuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MXFieldsTools {
    public static List<MXViewCls> loadField(Object obj) {
        try {
            List<MXViewCls> list = new ArrayList<>();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(MXField.class)) {
                    MXField annotation = field.getAnnotation(MXField.class);
                    if (annotation == null) continue;
                    String nameEn = annotation.nameEn();
                    MXField.FeldType FieleTy = annotation.fieldType();
                    boolean canCreate = annotation.canCreate();
                    int sort = annotation.sort();
                    boolean isMust = annotation.isMust();
                    field.setAccessible(true);
                    Object valObj = field.get(obj);
                    MXViewCls mxViewCls = new MXViewCls();
                    mxViewCls.setDisplayName(nameEn);
                    mxViewCls.setFieldName(field.getName());
                    mxViewCls.setCanEdit(true);
                    mxViewCls.setSort(sort);
                    mxViewCls.setMust(isMust);
                    if (valObj != null) {
//                        if (valObj.getClass() == String.class) {
//                            mxViewCls.setFieldValue(valObj + "");
//                        } else if (valObj.getClass() == Double.class) {
//                            mxViewCls.setFieldValue(valObj + "");
//                        } else if (valObj.getClass() == String.class) {
//                            mxViewCls.setFieldValue(valObj + "");
//                        }
                        mxViewCls.setFieldValue(valObj + "");
                    }
                    switch (FieleTy) {
                        case NormalType:
                            mxViewCls.setFieldType(MXViewCls.NormalType);
                            break;
                        case FloatType:
                            mxViewCls.setFieldType(MXViewCls.FloatType);
                            break;
                        case DateType:
                            mxViewCls.setFieldType(MXViewCls.DateType);
                            break;
                        case NoteType:
                            mxViewCls.setFieldType(MXViewCls.NoteType);
                            break;
                        case PhoneType:
                            mxViewCls.setFieldType(MXViewCls.PhoneType);
                            break;
                        case NumberType:
                            mxViewCls.setFieldType(MXViewCls.NumberType);
                            break;
                        case MultipleSelectType: {
                            mxViewCls.setFieldType(MXViewCls.MultipleSelectType);
                        }
                        break;
                        case StaticSelectType: {
                            mxViewCls.setFieldType(MXViewCls.StaticSelectType);
                        }
                        break;
                        case SelectType: {
                            mxViewCls.setFieldType(MXViewCls.SelectType);
                            if (canCreate) {
                                MXSelectCls mxSelectCls = new MXSelectCls();
                                mxSelectCls.setName(MXSelectCls.createNew);
                                mxSelectCls.setObject(nameEn);
                                List<MXSelectCls> selectList = new ArrayList<>();
                                selectList.add(mxSelectCls);
                                mxViewCls.setSelectDatas(selectList);
                            }
                        }
                        break;
                        case MapType:
                            mxViewCls.setFieldType(MXViewCls.MapType);
                            break;
                        case ScanType:
                            mxViewCls.setFieldType(MXViewCls.ScanType);
                            break;
                        case SensorType:
                            mxViewCls.setFieldType(MXViewCls.SensorType);
                            break;
                        case OcrCardType:
                            mxViewCls.setFieldType(MXViewCls.OcrCardType);
                            break;
                        case OcrBankType:
                            mxViewCls.setFieldType(MXViewCls.OcrBankType);
                            break;
                        default:
                            mxViewCls.setFieldType(MXViewCls.NormalType);
                            break;
                    }

                    list.add(mxViewCls);


                }
            }

            //排版显示顺序
            MXViewCls[] arr = list.toArray(new MXViewCls[0]);
            MXViewCls temp = null;
            for (int i = 0; i < arr.length; i++) {
                for (int j = i + 1; j < arr.length; j++) {

                    if (arr[i].getSort() > arr[j].getSort()) {
                        temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                }
            }
            list.clear();
            list.addAll(Arrays.asList(arr));

            return list;

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return null;
    }
}
