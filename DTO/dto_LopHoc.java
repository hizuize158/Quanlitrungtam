
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author USER
 */
public class dto_LopHoc {

    private int maLop;
    private int maCt;
    private int maNv;
    private String tenLop;
    private Date ngayBd;
    private Date ngayKt;
    private int soBuoi;
    private int trangThai;
    private int siSo;

    private dto_ChuongTrinh ct;
    private dto_TaiKhoan tk;
    private ArrayList<dto_Lich> dsLich;

    public dto_LopHoc() {
    }

    public int getSiSo() {
        return siSo;
    }

    public Date getNgayKt() {
        return ngayKt;
    }

    public dto_ChuongTrinh getCt() {
        return ct;
    }

    public dto_TaiKhoan getTk() {
        return tk;
    }

    public int getMaLop() {
        return maLop;
    }

    public int getMaCt() {
        return maCt;
    }

    public int getMaNv() {
        return maNv;
    }

    public String getTenLop() {
        return tenLop;
    }

    public Date getNgayBd() {
        return ngayBd;
    }

    public int getSoBuoi() {
        return soBuoi;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public ArrayList<dto_Lich> getDsLich() {
        return dsLich;
    }

    public void setMaLop(int maLop) {
        this.maLop = maLop;
    }

    public void setMaCt(int maCt) {
        this.maCt = maCt;
    }

    public void setMaNv(int maNv) {
        this.maNv = maNv;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public void setNgayBd(Date ngay) {
        this.ngayBd = ngay;
    }

    public void setSoBuoi(int soBuoi) {
        this.soBuoi = soBuoi;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public void setDsLich(ArrayList<dto_Lich> dsLich) {
        this.dsLich = dsLich;
    }

    public void setCt(dto_ChuongTrinh ct) {
        this.ct = ct;
    }

    public void setTk(dto_TaiKhoan tk) {
        this.tk = tk;
    }

    public void setSiSo(int siSo) {
        this.siSo = siSo;
    }

    public void setNgayKt(Date ngayKt) {
        this.ngayKt = ngayKt;
    }

    public String layNgayGioBd() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        if (this.ngayBd != null) {
            return sdf.format(this.ngayBd);
        } else {
            return "";
        }
    }

    public String layNgayBd() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        if (this.ngayBd != null) {
            return sdf.format(this.ngayBd);
        } else {
            return "";
        }
    }

    public String layNgayKt() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if (this.ngayKt != null) {
            return sdf.format(this.ngayKt);
        } else {
            return "";
        }

    }
}
