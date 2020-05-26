/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import common.ReflectClass;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author caster
 */
public abstract class Dao<T> extends Builder<T> {
    private String table;
    
    public Dao (String table) {
        super(table);
        this.table = table;
    }
    
    public String getTable () {
        return this.table;
    }
    
    public void print () {
        Map<String, Method> getterMethods = new ReflectClass(this.getClass()).getGetterMethods();
        try {
            for (Map.Entry<String, Method> getterMethod : getterMethods.entrySet()) {
                String name = getterMethod.getKey();
                Method method = getterMethod.getValue();

                System.out.println(name + ":" + method.invoke(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
