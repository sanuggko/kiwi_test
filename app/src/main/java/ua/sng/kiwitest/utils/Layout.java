package ua.sng.kiwitest.utils;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by AlexLampa on 29.09.2017.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Layout {
    @LayoutRes int id();
}