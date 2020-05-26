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
public class ShowingDao extends Dao<ShowingDao> {
    private static String table = "showings";
    
    @Column(type = "Number")
    private Number id;
    
    @Column(type = "Number")
    private Number room_id;
    
    @Column(type = "Number")
    private Number movie_id;
    
    @Column(type = "String")
    private String date;
    
    public ShowingDao () {
        super(table);
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Number room_id) {
        this.room_id = room_id;
    }

    public Number getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Number movie_id) {
        this.movie_id = movie_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
