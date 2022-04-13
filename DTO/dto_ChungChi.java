package DTO;

public class dto_ChungChi {
    private int maCc;
    private String tenCc;
    private float diemToiDa;
    private String noiDung;
    private String srcImg;
    
    //constructor

    public dto_ChungChi() {
    }
    
    //getter

    public String getSrcImg() {
        return srcImg;
    }
    
    
    public int getMaCc() {
        return maCc;
    }

    public String getTenCc() {
        return tenCc;
    }

    public float getDiemToiDa() {
        return diemToiDa;
    }

    public String getNoiDung() {
        return noiDung;
    }
    
    //setter

    public void setMaCc(int maCc) {
        this.maCc = maCc;
    }

    public void setTenCc(String tenCc) {
        this.tenCc = tenCc;
    }

    public void setDiemToiDa(float diemToiDa) {
        this.diemToiDa = diemToiDa;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setSrcImg(String srcImg) {
        this.srcImg = srcImg;
    }
    
    
    public String toString(){
        return tenCc;
    }
}
