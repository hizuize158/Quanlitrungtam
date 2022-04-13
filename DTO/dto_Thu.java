/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author USER
 */
public class dto_Thu {
    private int dayOfWeek;
    private int gioBd;
    private int phutBd;
    private int gioKt;
    private int phutKt;
    private Date tgBd;
    private Date tgKt;
    private dto_GiaoVien giaoVien;
    private dto_Phong phong;

    public dto_Thu() {
    }

    public dto_Thu(int dayOfWeek,int gioBd, int phutBd, int gioKt, int phutKt,dto_GiaoVien giaoVien, dto_Phong phong) {
        this.dayOfWeek = dayOfWeek;
        this.gioBd = gioBd;
        this.phutBd = phutBd;
        this.gioKt = gioKt;
        this.phutKt = phutKt;      
        this.giaoVien = giaoVien;
        this.phong = phong;
    }
    public int getDayOfWeek(){
        return this.dayOfWeek;    
    }
    public int getGioBd() {
        return gioBd;
    }

    public int getPhutBd() {
        return phutBd;
    }

    public int getGioKt() {
        return gioKt;
    }

    public int getPhutKt() {
        return phutKt;
    }

    public Date getTgBd() {
        return tgBd;
    }

    public Date getTgKt() {
        return tgKt;
    }

    public dto_GiaoVien getGiaoVien() {
        return giaoVien;
    }

    public dto_Phong getPhong() {
        return phong;
    }

    public void setGioBd(int gioBd) {
        this.gioBd = gioBd;
    }

    public void setPhutBd(int phutBd) {
        this.phutBd = phutBd;
    }

    public void setGioKt(int gioKt) {
        this.gioKt = gioKt;
    }

    public void setPhutKt(int phutKt) {
        this.phutKt = phutKt;
    }

    public void setTgBd(Date tgBd) {
        this.tgBd = tgBd;
    }

    public void setTgKt(Date tgKt) {
        this.tgKt = tgKt;
    }

    public void setGiaoVien(dto_GiaoVien giaoVien) {
        this.giaoVien = giaoVien;
    }

    public void setPhong(dto_Phong phong) {
        this.phong = phong;
    }
    
    public Date layTgBd(String strDate){
        
        strDate = strDate + " " + this.gioBd + ":" + this.phutBd + ": 00";
        
        try{
            Date thoiGianBd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(strDate);
            this.tgBd = thoiGianBd;
            return thoiGianBd;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        
        
    }
    
    public Date layTgKt(String strDate){
        strDate = strDate + " " + this.gioKt + ":" + this.phutKt + ": 00";
        
        try{
            Date thoiGianKt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(strDate);
            this.tgKt = thoiGianKt;
            return thoiGianKt;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }  
    }
    
}
