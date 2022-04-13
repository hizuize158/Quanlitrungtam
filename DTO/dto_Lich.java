/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author USER
 */
public class dto_Lich {
    private int maLop;
    private int maGv;
    private int maPhong;
    private Date TgBd;
    private Date TgKt;
    
    private dto_GiaoVien gv;
    private dto_Phong phong;

    public dto_Lich() {
        this.TgBd = new Date();
        this.TgKt = new  Date();           
    }

    public int getMaLop() {
        return maLop;
    }

    public int getMaGv() {
        return maGv;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public Date getTgBd() {
        return TgBd;
    }

    public Date getTgKt() {
        return TgKt;
    }

    public dto_GiaoVien getGv() {
        return gv;
    }

    public dto_Phong getPhong() {
        return phong;
    }

    public void setMaLop(int maLop) {
        this.maLop = maLop;
    }

    public void setMaGv(int maGv) {
        this.maGv = maGv;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public void setTgBd(Date Bd) {
        
        this.TgBd = new java.util.Date(Bd.getTime());
    }

    public void setTgKt(Date Kt) {
        this.TgKt = new java.util.Date(Kt.getTime());
    }

    public void setGv(dto_GiaoVien gv) {
        this.gv = gv;
    }

    public void setPhong(dto_Phong phong) {
        this.phong = phong;
    }
    
    // HÀM LẤY NGÀY HỌC
    public String getNgayHoc(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(this.TgBd);
    }
    
    // HÀM LẤY GIỜ BẮT ĐẦU HỌC
    public String getGioBd(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(this.TgBd);
    }
    
    // HÀM LẤY GIỜ TAN HỌC
    public String getGioKt(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(this.TgKt);
    }
    
    public String getNgayGioBd(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sdf.format(this.TgBd);
    }
    
        public String layNgayGioBd(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(this.TgBd);
    }
    
    public String getNgayGioKt(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sdf.format(this.TgKt);
    }
    public String layNgayGioKt(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(this.TgKt);
    }
    
    // HÀM LẤY THỨ
    public int getThu(){
        Calendar c = Calendar.getInstance();
        c.setTime(this.TgBd);
        int thu = c.get(Calendar.DAY_OF_WEEK);
        
        return thu;
    }
    
    

}
