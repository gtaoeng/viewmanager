package com.gtaoeng.viewbuilder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MXField {

    enum FeldType {NormalType, NumberType, FloatType, NoteType, PhoneType,
        SelectType,StaticSelectType, MultipleSelectType, DateType,TimeType, MapType,
        ScanType,SensorType,
        OcrCardType,OcrBankType}

    String nameEn() default "";

    FeldType fieldType() default FeldType.NormalType;

    boolean canCreate() default false;

    int sort() default 9999;

    boolean isMust() default  false;
}
