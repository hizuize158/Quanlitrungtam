package BUS;

import DAL.dal_ChungChi;
import DAL.dal_KhachHang;
import DAL.dal_LichSu;
import DAL.dal_LopHoc;
import DTO.dto_ChungChi;
import DTO.dto_KhachHang;
import DTO.dto_LichSu;
import java.util.ArrayList;

public class bus_Khachhang {
    
    // LẤY DS KHÁCH HÀNG
    public ArrayList<dto_KhachHang> layDsKhachHang(){
         
         return new dal_KhachHang().layDsKhachHang();
    }
    
    // HÀM LẤY LỊCH SỬ KHÁCH HÀNG
    public ArrayList<dto_LichSu> dsLichSu(int maKh){
        
        ArrayList<dto_LichSu> dsLichSu =  new ArrayList<dto_LichSu>();
        dsLichSu = new dal_LichSu().dsLichSu(maKh);
        
        for(dto_LichSu ls : dsLichSu){
            
            ls.setLop(new dal_LopHoc().layLopHoc(ls.getMaLop()));
        }
        
        return dsLichSu;
    }
    
    // HÀM LẤY DTO CHỨNG CHỈ CHO KHÁCH HÀNG
    public dto_ChungChi layChungChi(int maCc){
        
        return new dal_ChungChi().layChungChi(maCc);
    }
    
      
    // HÀM THÊM KHÁCH HÀNG
    public int themKhachHang(dto_KhachHang kh){
        
        return new dal_KhachHang().themKhachHang(kh);
    }
    
    // HÀM CẬP NHẬT KHÁCH HÀNG
    public int capNhatKhachHang(dto_KhachHang kh){
        
        return new dal_KhachHang().capNhatKhachHang(kh);
    }
    
    // HÀM XÓA KHÁCH HÀNG
    public int xoaKhachHang(dto_KhachHang kh){
        
        return new dal_KhachHang().xoaKhachHang(kh);
    }
    
    // HÀM TÌM KHÁCH HÀNG
    public ArrayList<dto_KhachHang> layDsTimKiem(String text){
        
        return new dal_KhachHang().layDsTimKiem(text);
    }
}
