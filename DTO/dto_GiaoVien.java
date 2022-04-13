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
public class dto_GiaoVien {
    private int maGv;
    private String tenGv;
    private int gioiTinh;
    private String sdt;
    private String quocTich;

    public dto_GiaoVien() {
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public int getMaGv() {
        return maGv;
    }

    public String getTenGv() {
        return tenGv;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setMaGv(int maGv) {
        this.maGv = maGv;
    }

    public void setTenGv(String tenGv) {
        this.tenGv = tenGv;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    
    //toString
    public String toString(){
        return this.maGv + " - " +this.tenGv + " - " + this.quocTich;
                
    }
}
