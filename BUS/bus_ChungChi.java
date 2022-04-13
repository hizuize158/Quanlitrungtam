package BUS;

import DAL.dal_ChungChi;
import DTO.dto_ChungChi;
import java.util.ArrayList;

public class bus_ChungChi {
    
    // HÀM LẤY DANH SÁCH CHỨNG CHỈ
    public ArrayList<dto_ChungChi> layDsChungChi(){
        ArrayList<dto_ChungChi> dsChungChi = new ArrayList<dto_ChungChi>();
        
        dsChungChi = new dal_ChungChi().layDsChungChi();
        
        return dsChungChi;
    }
    
    // HÀM THÊM CHỨNG CHỈ
    public int themChungChi(dto_ChungChi cc){
        
        return new dal_ChungChi().themChungChi(cc);
    }
    
    // HÀM CẬT NHẬT CHỨNG CHỈ
    public int capNhatChungChi(dto_ChungChi cc){
        
        return new dal_ChungChi().capNhatChungChi(cc);
    }
    
    // HÀM XÓA CHỨNG CHỈ
    public int xoaChungChi(int ma_cc){
        
        return new dal_ChungChi().xoaChungChi(ma_cc);
    }
}
