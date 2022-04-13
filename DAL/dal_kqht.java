package DAL;

import DTO.dto_kqht;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dal_kqht extends DBConnect {

    // LẤY DANH SÁCH KQHT
    public ArrayList<dto_kqht> layDsKqht(int maLop) {

        ArrayList<dto_kqht> dsKqht = new ArrayList<dto_kqht>();
        dto_kqht kqht = null;

        String sql = "SELECT ma_kh, ma_lop, nghe, noi, doc, viet, tong "
                + "FROM kqht "
                + "WHERE ma_lop = ?";

        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, maLop);

            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                kqht = new dto_kqht();
                kqht.setMaKh(rs.getInt(1));
                kqht.setMaLop(rs.getInt(2));
                kqht.setNghe(rs.getFloat(3));
                kqht.setNoi(rs.getFloat(4));
                kqht.setDoc(rs.getFloat(5));
                kqht.setViet(rs.getFloat(6));
                kqht.setTong(rs.getFloat(7));

                dsKqht.add(kqht);
            }

            conn.close();
            return dsKqht;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    
    // HÀM CẬP NHẬT KẾT QUẢ HỌC TẬP
    public int capNhatKqht(dto_kqht kqht){
        
        String sql = "UPDATE kqht "
                + "SET "
                + "nghe=?,"
                + "noi=?,"
                + "doc=?,"
                + "viet=?,"
                + "tong=? "
                + "WHERE ma_lop=? AND ma_kh = ?";
        
        try{
            
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setFloat(1, kqht.getNghe());
            preStmt.setFloat(2, kqht.getNoi());
            preStmt.setFloat(3, kqht.getDoc());
            preStmt.setFloat(4, kqht.getViet());
            preStmt.setFloat(5, kqht.getTong());
            preStmt.setInt(6, kqht.getMaLop());
            preStmt.setInt(7, kqht.getMaKh());
            
            int rs = preStmt.executeUpdate();
            
            conn.close();
            return rs;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }
    
    // HÀM THÊM KHÁCH HÀNG VÀO LỚP
    public int themKhachHangVaoLop(dto_kqht kqht){
        
        String sql = "INSERT INTO kqht (ma_lop, ma_kh, nghe, noi, doc, viet, tong) "
                + "VALUES(?, ?, ? ,? ,? ,? ,?)";
        try{
          PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, kqht.getMaLop());
            preStmt.setInt(2, kqht.getMaKh());          
            preStmt.setFloat(3, kqht.getNghe());          
            preStmt.setFloat(4, kqht.getNoi());          
            preStmt.setFloat(5, kqht.getDoc());          
            preStmt.setFloat(6, kqht.getViet());          
            preStmt.setFloat(7, kqht.getTong());      
            
            int rs = preStmt.executeUpdate();
            conn.close();
            return rs;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return 0;
        }

    }
    
    public int capNhatLopChoKhachHang(dto_kqht kqht){
        
        String sql = "UPDATE khach_hang SET lop_dang_hoc=? WHERE ma_kh=?";
        
        try{
            
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, kqht.getKh().getTenLop());
            preStmt.setInt(2, kqht.getMaKh());
            
            int rs = preStmt.executeUpdate();
            
            conn.close();
            return rs;
        }
        
        catch(Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }
    
    // HÀM XÓA KHÁCH HÀNG KHỎI LÓP
    public int xoaKhachHangKhoiLop(dto_kqht kqht){
        
        String sql = "DELETE FROM kqht WHERE ma_kh=? AND ma_lop =?";
        
        try{
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, kqht.getMaKh());
            preStmt.setInt(2, kqht.getMaLop());
            
            int rs = preStmt.executeUpdate();
            
            conn.close();
            return rs;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }
    
    // HÀM XÓA TÊN LỚP TRONG KHÁCH HÀNG
    public int xoaLopDangHoc(dto_kqht kqht){
        
        String sql = "UPDATE khach_hang SET lop_dang_hoc = null WHERE ma_kh = ?";
        
        try{
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, kqht.getMaKh());
            
            int rs = preStmt.executeUpdate();
            
            conn.close();
            return rs;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }
}
