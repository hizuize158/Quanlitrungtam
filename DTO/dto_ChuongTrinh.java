package DTO;

public class dto_ChuongTrinh {
    private int maCt;
    private String tenCt;
    private int maCc;
    private float diemDauVao;
    private float diemDauRa;
    private String noiDung;
    private int trangThai; // 0-đóng , 1-mở
    private int tinhNghe;
    private int tinhNoi;
    private int tinhDoc;
    private int tinhViet;
    private int cachTinhDiem;
    
    private dto_ChungChi chungChi;

    public dto_ChuongTrinh() {
    }

    public dto_ChungChi getChungChi() {
        return chungChi;
    }

    public int getMaCt() {
        return maCt;
    }

    public String getTenCt() {
        return tenCt;
    }

    public int getMaCc() {
        return maCc;
    }

    public float getDiemDauVao() {
        return diemDauVao;
    }

    public float getDiemDauRa() {
        return diemDauRa;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public int getTinhNghe() {
        return tinhNghe;
    }

    public int getTinhNoi() {
        return tinhNoi;
    }

    public int getTinhDoc() {
        return tinhDoc;
    }

    public int getTinhViet() {
        return tinhViet;
    }

    public int getCachTinhDiem() {
        return cachTinhDiem;
    }

//setter

    public void setMaCt(int maCt) {
        this.maCt = maCt;
    }

    public void setTenCt(String tenCt) {
        this.tenCt = tenCt;
    }

    public void setMaCc(int maCc) {
        this.maCc = maCc;
    }

    public void setDiemDauVao(float diemDauVao) {
        this.diemDauVao = diemDauVao;
    }

    public void setDiemDauRa(float diemDauRa) {
        this.diemDauRa = diemDauRa;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public void setTinhNghe(int tinhNghe) {
        this.tinhNghe = tinhNghe;
    }

    public void setTinhNoi(int tinhNoi) {
        this.tinhNoi = tinhNoi;
    }

    public void setTinhDoc(int tinhDoc) {
        this.tinhDoc = tinhDoc;
    }

    public void setTinhViet(int tinhViet) {
        this.tinhViet = tinhViet;
    }

    public void setCachTinhDiem(int cachTinhDiem) {
        this.cachTinhDiem = cachTinhDiem;
    }

    public void setChungChi(dto_ChungChi chungChi) {
        this.chungChi = chungChi;
    }
    
    //toString
    public String toString(){
        return this.maCt + " - "  + this.tenCt;
    }
    
}
