package BUS;

import DAL.dal_Phong;
import DTO.dto_Phong;
import java.util.ArrayList;

public class bus_Phong {
    
    // HÀM LẤY DS PHÒNG
    public ArrayList<dto_Phong> layDsPhong(){
        
       return new dal_Phong().layDsPhong();
    }
    
    // HÀM THÊM PHÒNG   
    public int themPhong(dto_Phong phong){
        
        return new dal_Phong().themPhong(phong);
    }
    
    // HÀM CẬP NHẬT PHÒNG
    public int capNhatPhong(dto_Phong phong){
        
        return new dal_Phong().capNhatPhong(phong);
    }
    
    // HÀM XÓA PHÒNG
    public int xoaPhong(dto_Phong phong){
        
        return new dal_Phong().xoaPhong(phong);
    }
   
    // HÀM TÌM PHÒNG
    public ArrayList<dto_Phong> timPhong(String text){
        
        return new dal_Phong().timPhong(text);
    }
}
