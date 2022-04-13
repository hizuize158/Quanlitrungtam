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
public class dto_LichSu {
    private int maKh;
    private int maLop;
    private float nghe;
    private float noi;
    private float doc;
    private float viet;
    private float tong;

    private dto_LopHoc lop;
    
    public dto_LichSu() {
    }

    public dto_LopHoc getLop() {
        return lop;
    }

    public int getMaKh() {
        return maKh;
    }

    public int getMaLop() {
        return maLop;
    }

    public float getNghe() {
        return nghe;
    }

    public float getNoi() {
        return noi;
    }

    public float getDoc() {
        return doc;
    }

    public float getViet() {
        return viet;
    }

    public float getTong() {
        return tong;
    }

    public void setMaKh(int maKh) {
        this.maKh = maKh;
    }

    public void setMaLop(int maLop) {
        this.maLop = maLop;
    }

    public void setNghe(float nghe) {
        this.nghe = nghe;
    }

    public void setNoi(float noi) {
        this.noi = noi;
    }

    public void setDoc(float doc) {
        this.doc = doc;
    }

    public void setViet(float viet) {
        this.viet = viet;
    }

    public void setTong(float tong) {
        this.tong = tong;
    }

    public void setLop(dto_LopHoc lop) {
        this.lop = lop;
    }

    public String layNgayBd(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if(this.lop.getNgayBd() !=null)
            return sdf.format(this.lop.getNgayBd());
        else
            return "";
    }
    
    public String layNgayKt(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if(this.lop.getNgayKt() != null)
            return sdf.format(this.lop.getNgayKt());
        else
            return "";
    }
}
