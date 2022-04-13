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
public class dto_TaiKhoan {
    
    private int ma;
    private String hoTen;
    private String sdt;
    private int loai;
    private String tenDangNhap;
    private String matKhau;
    private String srcImg;

    public dto_TaiKhoan() {
    }   
    
    public String getSrcImg(){
        return srcImg;
    }
    
    public int getMa() {
        return ma;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public int getLoai() {
        return loai;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
    public void setSrcImg(String srcImg){
        this.srcImg = srcImg;
    } 
}
