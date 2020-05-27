/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import annotations.Column;

/**
 *
 * @author caster
 */
public class RoomDao extends Dao<RoomDao> {
    private static String table = "rooms";

    public static String getName(String name) {
        return name;
    }
    public static String getSeats(String seats) {
        return seats;
    }
    
    @Column(type = "Number")
    private Number id;
    
    @Column(type = "String")
    private String name;
    
    @Column(type = "int")
    private int seats;
    
    public RoomDao () {
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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setSeats(String seats) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
