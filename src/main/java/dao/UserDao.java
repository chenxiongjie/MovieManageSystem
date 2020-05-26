/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import annotations.Column;
import common.Hash;

/**
 *
 * @author caster
 */
public class UserDao extends Dao<UserDao> {
    private static String table = "users";
    
    @Column(type = "Number")
    private Number id;
    
    @Column(type = "String")
    private String name;
    
    @Column(type = "String")
    private String email;
    
    @Column(type = "String")
    private String password;
    
    @Column(type = "String")
    private String phone;
    
    @Column(type = "String")
    private String address;
    
    public UserDao () {
        super(table);
    }
    
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    private String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Hash.encrypt(password);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * check the password is equals with that in database
     * @param password
     * @return Boolean
     */
    public Boolean authorize(String password) {
        // 加密两次，解决从数据库取出时 setPassword 再次加密问题
        String encrypt = Hash.encrypt(Hash.encrypt(password));
        return encrypt.equals(this.getPassword());
    }
}
