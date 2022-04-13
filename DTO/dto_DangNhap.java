/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * Đối tượng sử dụng cho đăng nhập
 */
public class dto_DangNhap {
    private String ma;
    private String hoTen;
    private int loai;
    private String tenDangNhap;
    private String matKhau;
    private String sdt;

    public dto_DangNhap() {
    }

    public dto_DangNhap(String tenDangNhap, String matKhau) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public dto_DangNhap(String ma, String hoTen, int loai, String tenDangNhap, String matKhau, String sdt) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.loai = loai;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.sdt = sdt;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }


    public String getMa() {
        return ma;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
