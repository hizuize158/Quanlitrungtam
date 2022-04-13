/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author USER
 */
public class dto_Phong {
    private int maPhong;
    private String tenPhong;

    public dto_Phong() {
    }

    public int getMaPhong() {
        return maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    } 
    
    //toString
    public String toString(){
        return this.tenPhong;
    }
}
