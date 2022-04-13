package BUS;

import DAL.dal_GiaoVien;
import DTO.dto_GiaoVien;
import java.util.ArrayList;

public class bus_GiaoVien {
    
    // HÀM LẤY DANH SÁCH GIÁO VIÊN
    public ArrayList<dto_GiaoVien> layDsGiaoVien(){
        
        return new dal_GiaoVien().layDsGiaoVien();
    }
    
    // HÀM THÊM GIÁO VIÊN
    public int themGiaoVien(dto_GiaoVien gv){
        
        return new dal_GiaoVien().themGiaoVien(gv);
    }
    
    // HÀM CẬP NHẬT GIÁO VIÊN
    public int capNhatGiaoVien(dto_GiaoVien gv){
        
        return new dal_GiaoVien().capNhatGiaovien(gv);
    }
    
    // HÀM XÓA GIÁO VIÊN
    public int xoaGiaoVien(dto_GiaoVien gv){
        
        return new dal_GiaoVien().xoaGiaoVien(gv);
    }
    
    // HÀM TÌM GIÁO VIÊN
    public ArrayList<dto_GiaoVien> timGiaoVien(String text){
        
        return new dal_GiaoVien().timGiaoVien(text);
    }
    
}
