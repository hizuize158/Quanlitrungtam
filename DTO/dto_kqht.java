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
public class dto_kqht {
    private int maKh;
    private int maLop;
    private float nghe;
    private float noi;
    private float doc;
    private float viet;
    private float tong;
    
    private dto_KhachHang kh;

    public dto_kqht() {
    }

    public dto_KhachHang getKh() {
        return kh;
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

    public void setKh(dto_KhachHang kh) {
        this.kh = kh;
    }
    
    
}
