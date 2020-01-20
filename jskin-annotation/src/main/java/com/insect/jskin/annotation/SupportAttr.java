package com.insect.jskin.annotation;


import androidx.annotation.AttrRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface SupportAttr {

   @AttrRes int[]  value();

}
