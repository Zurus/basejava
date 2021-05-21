package com;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.urise.webapp.model.Resume;

/**
 * MainReflection.
 *
 * @author ADivaev
 */
public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r,"new_uuid");
        // TODO : invoke r.toString via reflection
        //System.out.println(r);
        Method m = r.getClass().getMethod("toString");
        m.invoke(r);
    }
}