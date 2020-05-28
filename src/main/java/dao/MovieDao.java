/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import annotations.Column;
import java.math.BigDecimal;

/**
 *
 * @author caster
 */
public class MovieDao extends Dao<MovieDao>{
    private static String table = "movies";
    
    @Column(type = "Number")
    private Number id;
    
    @Column(type = "String")
    private String name;
    
    @Column(type = "String")
    private String director;
    
    @Column(type = "String")
    private String starring;

    @Column(type = "String")
    private String cover;
    
    @Column(type = "String")
    private String type;
    
    @Column(type = "BigDecimal")
    private BigDecimal price;
    
    public MovieDao () {
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
