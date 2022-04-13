/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class dto_KhachHang {
    private int MaKh;
    private String tenKh;
    private Date ngaySinh;
    private int gioiTinh;
    private String diaChi;
    private String sdt;
    private float diemDauVao;
    private int maChungChi;
    private String tenLop;
    
    private dto_ChungChi chungChiCanHoc;
    private ArrayList<dto_LichSu> dsLichSu;

    public dto_KhachHang() {
    }

    public ArrayList<dto_LichSu> getDsLichSu() {
        return dsLichSu;
    }

    public int getMaChungChi() {
        return maChungChi;
    }

    public String getTenLop() {
        return tenLop;
    }

    public int getMaKh() {
        return MaKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public float getDiemDauVao() {
        return diemDauVao;
    }

    public dto_ChungChi getChungChiCanHoc() {
        return chungChiCanHoc;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setDiemDauVao(float diemDauVao) {
        this.diemDauVao = diemDauVao;
    }

    public void setChungChiCanHoc(dto_ChungChi chungChiCanHoc) {
        this.chungChiCanHoc = chungChiCanHoc;
    }

    public void setMaKh(int MaKh) {
        this.MaKh = MaKh;
    }

    public void setDsLichSu(ArrayList<dto_LichSu> dsLichSu) {
        this.dsLichSu = dsLichSu;
    }

    public void setMaChungChi(int maChungChi) {
        this.maChungChi = maChungChi;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String layNgaySinh(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(this.ngaySinh);
    }
}
