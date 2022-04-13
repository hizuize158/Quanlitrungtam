package BUS;

import DAL.dal_ChungChi;
import DAL.dal_ChuongTrinh;
import DTO.dto_ChungChi;
import DTO.dto_ChuongTrinh;
import UI.ChuongTrinh.UI_ChuongTrinh;
import java.util.ArrayList;

public class bus_ChuongTrinh {
    
    
    // HÀM LÂY GIÁ TRỊ HIỂN THỊ LÊN BẢNG.
    public ArrayList<dto_ChuongTrinh> layDsChuongTrinh(boolean layTatCa){
        
        ArrayList<dto_ChuongTrinh> dsChuongTrinh = new dal_ChuongTrinh().layDsChuongTrinh(layTatCa);
        
        for(dto_ChuongTrinh ct: dsChuongTrinh){
            
            ct.setChungChi(new dal_ChungChi().layChungChi(ct.getMaCc()));
        }
        
        return dsChuongTrinh;
    }
    
    // HÀM LẤY CHỨNG CHỈ
    public ArrayList<dto_ChungChi> layDsChungChi(){
        
        return new dal_ChungChi().layDsChungChi();
    }
    
    // HÀM LẤY MỘT CHỨNG CHỈ
    public dto_ChungChi layMotChungChi(int maCc){
        
        return new dal_ChungChi().layChungChi(maCc);
    }
    // HÀM THÊM CHƯƠNG TRÌNH
    public int themChuongTrinh(dto_ChuongTrinh ct){
        return new dal_ChuongTrinh().themChuongTrinh(ct);
    }
    
     // HÀM CẬP NHẬT CHƯƠNG TRÌNH
    public int capNhatChuongTrinh(dto_ChuongTrinh ct){
        
        return new dal_ChuongTrinh().capNhatChuongTrinh(ct);
    }
    
    // HÀM XÓA CHƯƠNG TRÌNH
    public int xoaChuongTrinh(int ma_ct){
        
        return new dal_ChuongTrinh().xoaChuongTrinh(ma_ct);
    }  
    
    // HÀM TÌM CHƯƠNG TRÌNH
    public ArrayList<dto_ChuongTrinh> layDsTimKiem(String text){
        
        ArrayList<dto_ChuongTrinh> dsChuongTrinh = new dal_ChuongTrinh().layDsTimKiem(text);
        
        for(dto_ChuongTrinh ct: dsChuongTrinh){
            
            ct.setChungChi(new dal_ChungChi().layChungChi(ct.getMaCc()));
        }
               return dsChuongTrinh;
    }
}
