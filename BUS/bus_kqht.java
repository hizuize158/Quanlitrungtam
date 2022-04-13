package BUS;

import DAL.dal_ChungChi;
import DAL.dal_KhachHang;
import DAL.dal_kqht;
import DTO.dto_ChungChi;
import DTO.dto_kqht;
import java.util.ArrayList;

public class bus_kqht {
    
    // HÀM LẤY DANH SÁCH KQHT
    public ArrayList<dto_kqht> layDsKqht(int maLop){
        
        return new dal_kqht().layDsKqht(maLop);
    }
    
    // HÀM LẤY ĐỐI TƯỢNG KHÁCH HÀNG BẰNG MÃ KH
    public ArrayList<dto_kqht> layKhachHang(ArrayList<dto_kqht> dsKqht){
        
        int size = dsKqht.size();

        
        for(int i = 0; i < size; i++){
            dsKqht.get(i).setKh(new dal_KhachHang().layKhachHang(dsKqht.get(i).getMaKh()));
        }
        
        return dsKqht;
    }
    
    // HÀM LẤY CHỨNG CHỈ
    public dto_ChungChi layChungChi(int maChungChi){
        
        return new dal_ChungChi().layChungChi(maChungChi);
    }
    
    // HÀM CẬP NHẬT KQHT    
    public int capNhatKqht(dto_kqht kqht){
        
        return new dal_kqht().capNhatKqht(kqht);
    }
    
    // HÀM THÊM KHÁCH HÀNG VÀO LỚP
    public int themKhachHangVaoLop(dto_kqht kqht){
        
        int rs =  new dal_kqht().themKhachHangVaoLop(kqht);
        
        if(rs > 0){
            new dal_kqht().capNhatLopChoKhachHang(kqht);
        }
        return rs;
    }
    
    // HÀM XÓA KHÁCH HÀNG KHỎI LỚP
    public int xoaKhachHangKhoiLop(dto_kqht kqht){
        
        int rs = new dal_kqht().xoaKhachHangKhoiLop(kqht);
        
        if(rs >0){
            new dal_kqht().xoaLopDangHoc(kqht);
        }
        return rs;
    }
}
