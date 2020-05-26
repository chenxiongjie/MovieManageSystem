/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import annotations.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author caster
 */
public class ReflectClass {
    public Class c;
    
    public ReflectClass(Class c) {
        this.c = c;
    }
    
    public Map getColumns() {
        // 获取所有字段
        Map columns = new HashMap(); 
        for(Field f : this.c.getDeclaredFields()){
            // 判断这个字段是否有 Column 注解
            if(f.isAnnotationPresent(Column.class)){
                Column annotation = f.getAnnotation(Column.class);
                columns.put(f.getName(), annotation.type());
            }
        }
        return columns;
    }
    
    public Map<String, Method> getGetterMethods() {
        try {
            Method[] methods = this.c.getDeclaredMethods();
            Map<String, Method> getterResults = new HashMap<>();
            
            for (Method method : methods) {
                if (Modifier.isPublic(method.getModifiers()) && method.getName().startsWith("get")){
                    String name = method.getName().replace("get", "").toLowerCase();
                    getterResults.put(name, method);
                }
            }

            return getterResults;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public Map<String, Method> getSetterMethods() {
        try {
            Method[] methods = this.c.getDeclaredMethods();
            Map<String, Method> setterResults = new HashMap<>();
            
            for (Method method : methods) {
                if (method.getName().startsWith("set")){
                    String name = method.getName().replace("set", "").toLowerCase();
                    setterResults.put(name, method);
                }
            }

            return setterResults;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
