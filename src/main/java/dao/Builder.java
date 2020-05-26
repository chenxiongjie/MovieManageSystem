/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import common.ReflectClass;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author caster
 */
public abstract class Builder<T> {
    private Set<String> whereArray = new LinkedHashSet<>();
    private String order = "";
    private String paginate = "";
    private String table;
    
    public Builder (String table) {
        this.table = table;
    }
    
    /**
     * get the table name
     * @return String 
     */
    public String getTable () {
        return this.table;
    }
    
    /**
     * build where arrray
     * @param column
     * @param operator
     * @param value
     * @return Builder
     */
    public Builder where(String column, String operator, Object value) {
        String castValue = value instanceof String ? "'" + value + "'" : "" + value;
        
        String whereString = column + " " + operator + " " + castValue;
        
        if (!this.whereArray.isEmpty()) {
            whereString = "and " + whereString;
        }
        
        this.whereArray.add(whereString);
        
        return this;
    }
    
    /**
     * build orWhere arrray
     * @param column
     * @param operator
     * @param value
     * @return Builder
     */
    public Builder orWhere(String column, String operator, Object value) {
        String castValue = value instanceof String ? "'" + value + "'" : "" + value;
        
        String whereString = "or " + column + " " + operator + " " + castValue;
        this.whereArray.add(whereString);
        
        return this;
    }
    
    /**
     * build order sql
     * @param column
     * @param order
     * @return Builder
     */
    public Builder order(String column, String order) {
        this.order = " order by " + column + " " + order;
        return this;
    }
    
    /**
     * build paginate sql
     * @param page
     * @param paginate
     * @return Builder
     */
    public Builder paginate(int page, int paginate) {
        int from = (page - 1) * paginate;
        int to = page * paginate;        
        this.paginate = " limit " + from + "," + to;
        return this;
    }
    
    /**
     * create new instance
     * @return Builder
     */
    public Builder create () {
        try {
            ReflectClass c = new ReflectClass(this.getClass());
            Map<String, Method> getterMethods = c.getGetterMethods();
            
            ArrayList columns = new ArrayList();
            ArrayList values = new ArrayList();
            String sql = "insert into " + this.getTable();
            
            // get data form instance and build values array
            for (Map.Entry<String, Method> getterMethod : getterMethods.entrySet()) {
                String name = getterMethod.getKey();
                Method method = getterMethod.getValue();

                Object value = method.invoke(this);

                if (value != null) {
                    String castValue = value instanceof String ? "'" + value + "'" : "" + value;
                    columns.add(name);
                    values.add(castValue);
                }
            }

            sql += " (" + String.join(", ", columns) + ")";
            sql += " values (" + String.join(", ", values) + ")";

            long id = ConnectionFactory.queryReturnPrimaryKey(sql);

            if (id > 0) {
                c.getSetterMethods().get("id").invoke(this, id);
                return this;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * update a new extends
     * @return Builder
     */
    public Builder update () {
        try {
            ArrayList sets = new ArrayList();
            
            ReflectClass c = new ReflectClass(this.getClass());
            Map<String, Method> getterMethods = c.getGetterMethods();
            Object id = getterMethods.get("id").invoke(this);
            
            // get data from instance and build values array
            for (Map.Entry<String, Method> getterMethod : getterMethods.entrySet()) {
                String name = getterMethod.getKey();
                Method method = getterMethod.getValue();

                Object value = method.invoke(this);

                if (value != null) {
                    String castValue = value instanceof String ? "'" + value + "'" : "" + value;
                    sets.add(name + " = " + castValue);
                }
            }
            
            String sql = "update " + this.getTable() + " set" + " " + String.join(", ", sets) + " where id = " + id;
            
            long newId = ConnectionFactory.queryReturnPrimaryKey(sql);

            if (newId > 0) {
                return this;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * create instance if id is null else update instance
     * @return Builder
     */
    public Builder save () {
        try {
            ArrayList sets = new ArrayList();
            
            ReflectClass c = new ReflectClass(this.getClass());
            Map<String, Method> getterMethods = c.getGetterMethods();
            Object id = getterMethods.get("id").invoke(this);
            
            if (id != null) {
                return this.update();
            } else {
                return this.create();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * select all data from database
     * @return ArrayList
     */
    public ArrayList<T> get() {
        String table = this.getTable();
        
        // 构建 sql
        String sql = this.generateSelectSql();

        ReflectClass c = new ReflectClass(this.getClass());
        Map<String, Method> setterMethods = c.getSetterMethods();
        
        ArrayList results = new ArrayList();
        
        try {
            // 获取结果集
            ArrayList<Map> rows = ConnectionFactory.queryReturnArrayList(sql);
            
            for (Map<String, Object> row : rows) {
                T dao = (T) this.getClass().newInstance();
                
                // 将 row 转化为 DAO
                for (Map.Entry<String, Object> column : row.entrySet()) {
                    try {
                        setterMethods.get(column.getKey()).invoke(dao, column.getValue());
                    } catch (Exception e) {
                        continue;
                    }
                }
                
                results.add(dao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    /**
     * select the first data from database
     * @return *Dao
     */
    public T first () {
        String table = this.getTable();
        
        // 构建 sql
        String sql = this.generateSelectSql() + " limit 1";
        System.out.println(sql);
        
        ReflectClass c = new ReflectClass(this.getClass());
        Map<String, Method> setterMethods = c.getSetterMethods();
        try {
            ArrayList<Map> rows = ConnectionFactory.queryReturnArrayList(sql);
            
            for (Map<String, Object> row : rows) {
                T dao = (T) this.getClass().newInstance();
                
                // transfrom row to dao
                for (Map.Entry<String, Object> column : row.entrySet()) {
                    try {
                        setterMethods.get(column.getKey()).invoke(dao, column.getValue());
                    } catch (Exception e) {
                        continue;
                    }
                }
                
                return (T) dao;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * delete instance
     * @return Boolean
     */
    public Boolean delete () {
        try {
            ArrayList sets = new ArrayList();
            
            ReflectClass c = new ReflectClass(this.getClass());
            Map<String, Method> getterMethods = c.getGetterMethods();
            Object id = getterMethods.get("id").invoke(this);
            String sql = "delete from " + this.getTable() + " where id = " + id;

            return ConnectionFactory.queryReturnBoolean(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * generate select sql
     * @return String
     */
    public String generateSelectSql() {
        String table = this.getTable();
        String sql = "select * from " + table;
        
        if (!this.whereArray.isEmpty()) {
            sql += " where " + String.join(" ", this.whereArray);
        }
        
        if (this.order.length() > 0) {
            sql += this.order;
        }
        
        if (this.paginate.length() > 0) {
            sql += this.paginate;
        }
        
        return sql;
    }
}
