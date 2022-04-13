package BUS;

import DAL.dal_ChungChi;
import DAL.dal_ChuongTrinh;
import DAL.dal_GiaoVien;
import DAL.dal_Lich;
import DAL.dal_LopHoc;
import DAL.dal_Phong;
import DTO.dto_ChungChi;
import DTO.dto_ChuongTrinh;
import DTO.dto_GiaoVien;
import DTO.dto_Lich;
import DTO.dto_LopHoc;
import DTO.dto_Phong;
import DTO.dto_TaiKhoan;
import java.util.ArrayList;

public class bus_LopHoc {
    
    // HÀM LẤY DANH SÁCH LỚP
    public ArrayList<dto_LopHoc> layDsLop(boolean chonTatCa){
        
        ArrayList<dto_LopHoc> dsLopHoc = new dal_LopHoc().layDsLop(chonTatCa);
         
        return dsLopHoc;
    }
    
    // HÀM LẤY CHƯƠNG TRÌNH CHO DS LỚP
    public ArrayList<dto_LopHoc> layChuongTrinh(ArrayList<dto_LopHoc> dsLop){
        
         for(dto_LopHoc lop : dsLop){
            lop.setCt(new dal_LopHoc().layChuongTrinh(lop.getMaCt()));
        }
         
         return dsLop;
    }
    
    // HÀM LẤY CHƯƠNG TRÌNH CHO 1 LỚP
    public dto_ChuongTrinh layMotChuongTrinh(int maCt){
        
        return new dal_ChuongTrinh().layChuongTrinh(maCt);

    }
    
    // HÀM LẤY CHỨNG CHỈ
    public dto_ChungChi layMotChungChi(int maCc){
        
        return new dal_ChungChi().layChungChi(maCc);
    }
        // HÀM Sỉ Số
    public ArrayList<dto_LopHoc> laySiSo(ArrayList<dto_LopHoc> dsLop){
        
         for(dto_LopHoc lop : dsLop){
            //lop.setCt(new dal_LopHoc().layChuongTrinh(lop.getMaCt()));
            lop.setSiSo(new dal_LopHoc().laySiSo(lop.getMaLop()));
        }
         
         return dsLop;
    }
    
    // HÀM LẤY LỊCH HỌC
    public ArrayList<dto_Lich> layDsLich(int maLop){
        
        return new dal_Lich().layDsLichTheoLop(maLop);
    }
    
    // HÀM LẤY TÀI KHOẢN CHO LỚP
    public dto_TaiKhoan layTaiKhoan(int maTk){
        
        return new dal_LopHoc().layTaiKhoan(maTk);
    }
    
    // HÀM LẤY DS TÌM KIẾM
    public ArrayList<dto_LopHoc> layDsLopTim(String text){
        
        ArrayList<dto_LopHoc> dsLop = new dal_LopHoc().layDsLopTim(text);
        
        dsLop = layChuongTrinh(dsLop);
        dsLop = laySiSo(dsLop);
        
        return dsLop;
    }
    
    // HÀM LẤY DS CHƯƠNG TRÌNH
    public ArrayList<dto_ChuongTrinh> layDsChuongTrinh(){
        
        return new dal_ChuongTrinh().layDsChuongTrinh(false);
    }
    
    // HÀM LẤY DS GIÁO VIÊN
    public ArrayList<dto_GiaoVien> layDsGiaoVien(){
        
        return new dal_GiaoVien().layDsGiaoVien();
    }
    
    // HÀM LẤY DS PHÒNG
    public ArrayList<dto_Phong> layDsPhong(){
        
        return new dal_Phong().layDsPhong();
    }
    
    // HÀM THÊM LỚP MỚI
    public int themLopHoc(dto_LopHoc lop){
        
        return new dal_LopHoc().themLopHoc(lop);
    }
    
    // HÀM CẬP NHẬT LỚP
    public int capNhatLop(dto_LopHoc lop){
        
        return new dal_LopHoc().capNhatLop(lop);
    }
    
    // HÀM XÓA LỚP
    public int xoaLop(dto_LopHoc lop){
        
        return new dal_LopHoc().xoaLop(lop);
    }
    
    // HÀM LẤY DANH SÁCH LỊCH NGÀY TRONG HỆ THỐNG
    public ArrayList<dto_Lich> layDsLichNgay(){
        
        return new dal_Lich().layDsLichNgay();
    }
    
    // HÀM THÊM DANH SÁCH LỊCH HỌC
    public int themDsLich(ArrayList<dto_Lich> dsLich){
        
        return new dal_Lich().themLichHoc(dsLich);
    }
    
    //  HÀM XÓA LỊCH HỌC
    public int xoaDsLich(int maLop){
        
        return new dal_Lich().xoaDsLich(maLop);
    }
    
    // LẤY DANH SÁCH LỊCH THEO LỚP
    public ArrayList<dto_Lich> layDsLichTheoLop(int maLop){
        
        return new dal_Lich().layDsLichTheoLop(maLop);
    }
    
    // CẬP NHẬT THỜI GIAN HỌC
    public int capNhapThoiGianHoc(dto_LopHoc lop){
        
        return new dal_LopHoc().capNhatThoiGianHoc(lop);
    }
}
