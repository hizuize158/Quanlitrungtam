package DAL;

import DTO.dto_ChuongTrinh;
import DTO.dto_LopHoc;
import DTO.dto_TaiKhoan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dal_LopHoc extends DBConnect {

    // HÀM LẤY DANH SÁCH LỚP
    public ArrayList<dto_LopHoc> layDsLop(boolean chonTatCa) {

        ArrayList<dto_LopHoc> dsLop = new ArrayList<dto_LopHoc>();

        dto_LopHoc lop = null;

        String sql = "";

        if (chonTatCa == false) {
            sql = "SELECT ma_lop, ma_ct, ma_nv, ten_lop, ngay_bd, so_buoi, trang_thai, ngay_kt "
                    + "FROM lop "
                    + "WHERE trang_thai = 1";
        } else if (chonTatCa == true) {
            sql = "SELECT ma_lop, ma_ct, ma_nv, ten_lop, ngay_bd, so_buoi, trang_thai, ngay_kt "
                    + "FROM lop "
                    + "ORDER BY trang_thai DESC";
        }

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                lop = new dto_LopHoc();
                lop.setMaLop(rs.getInt(1));
                lop.setMaCt(rs.getInt(2));
                lop.setMaNv(rs.getInt(3));
                lop.setTenLop(rs.getString(4));
                lop.setNgayBd(rs.getDate(5));
                lop.setSoBuoi(rs.getInt(6));
                lop.setTrangThai(rs.getInt(7));
                lop.setNgayKt(rs.getDate(8));
                dsLop.add(lop);
            }
            conn.close();
            return dsLop;
        } catch (Exception ex) {
            ex.printStackTrace();
            return dsLop;
        }
    }

    // HÀM LẤY SỈ SỐ LỚP
    public int laySiSo(int maLop) {

        int siSo = 0;

        String sql = "SELECT COUNT(ma_kh) "
                + "FROM kqht "
                + "GROUP BY ma_lop "
                + "HAVING ma_lop =?";

        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, maLop);
            ResultSet rs = preStmt.executeQuery();

            if (!rs.next()) {

            } else {
                siSo = rs.getInt(1);
            }

            conn.close();
            return siSo;

        } catch (Exception ex) {
            ex.printStackTrace();
            return siSo;
        }
    }

    // HÀM TÌM LỚP THEO KÝ TỰ NHẬP VÀO
    public ArrayList<dto_LopHoc> layDsLopTim(String text) {

        text = text.toLowerCase();

        ArrayList<dto_LopHoc> dsLop = new ArrayList<dto_LopHoc>();
        dto_LopHoc lop = null;

        String sql = "SELECT ma_lop, ma_ct, ma_nv, ten_lop, ngay_bd, so_buoi, trang_thai "
                + "FROM lop "
                + "WHERE ma_lop LIKE '" + text + "%' "
                + "OR ma_ct LIKE '" + text + "%' "
                + "OR ma_nv LIKE '" + text + "%' "
                + "OR LOWER(ten_lop) LIKE N'%" + text + "%'";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {
                lop = new dto_LopHoc();
                lop.setMaLop(rs.getInt(1));
                lop.setMaCt(rs.getInt(2));
                lop.setMaNv(rs.getInt(3));
                lop.setTenLop(rs.getString(4));
                lop.setNgayBd(rs.getDate(5));
                lop.setSoBuoi(rs.getInt(6));
                lop.setTrangThai(rs.getInt(7));

                lop.setDsLich(new dal_Lich().layDsLichTheoLop(lop.getMaLop()));
                dsLop.add(lop);
            }
            conn.close();
            return dsLop;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // HÀM THÊM LỚP HỌC
    public int themLopHoc(dto_LopHoc lop) {

        String sql = "INSERT INTO lop (ma_lop, ma_ct, ma_nv, ten_lop,so_buoi, trang_thai) "
                + "VALUES (lop_sequence.NEXTVAL,?,?,?,?,1)";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, lop.getMaCt());
            preStmt.setInt(2, lop.getMaNv());
            preStmt.setString(3, lop.getTenLop());
            preStmt.setInt(4, lop.getSoBuoi());
            
            int rs = preStmt.executeUpdate();
            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM CẬP NHẬT LỚP HỌC
    public int capNhatLop(dto_LopHoc lop) {

        String sql = "UPDATE lop "
                + "SET ten_lop =?, "
                + "ma_ct=? "
                + "WHERE ma_lop=?";

        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1,lop.getTenLop());
            preStmt.setInt(2,lop.getMaCt());
            preStmt.setInt(3,lop.getMaLop());

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // XÓA LỚP
    public int xoaLop(dto_LopHoc lop) {

        String sql = "DELETE FROM lop WHERE ma_lop=" + lop.getMaLop();

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            int rs = preStmt.executeUpdate();
            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM LẤY THÔNG TIN LỚP BẰNG MÃ LỚP
    public dto_LopHoc layLopHoc(int maLop) {

        dto_LopHoc lop = null;

        String sql = "SELECT ma_lop, ma_ct, ma_nv, ten_lop, ngay_bd, so_buoi, trang_thai, ngay_kt "
                + "FROM lop "
                + "WHERE ma_lop=?";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, maLop);
            ResultSet rs = preStmt.executeQuery();

            if (rs.next()) {

                lop = new dto_LopHoc();
                lop.setMaLop(rs.getInt(1));
                lop.setMaCt(rs.getInt(2));
                lop.setMaNv(rs.getInt(3));
                lop.setTenLop(rs.getString(4));
                lop.setNgayBd(rs.getDate(5));
                lop.setSoBuoi(rs.getInt(6));
                lop.setTrangThai(rs.getInt(7));
                lop.setNgayKt(rs.getDate(8));

            }

            conn.close();
            return lop;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // HÀM LẤY CHƯƠNG TRÌNH BẰNG MÃ CHUONG TRINH
    public dto_ChuongTrinh layChuongTrinh(int ma_ct) {

        dto_ChuongTrinh ct = null;

        String sql = "SELECT ma_chung_chi, ten_ct "
                + "FROM chuong_trinh "
                + "WHERE ma_ct =?";
        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, ma_ct);
            ResultSet rs = preStmt.executeQuery();

            if (rs.next()) {
                ct = new dto_ChuongTrinh();
                ct.setMaCt(ma_ct);
                ct.setMaCc(rs.getInt(1));
                ct.setTenCt(rs.getString(2));
            }
            conn.close();
            return ct;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // HÀM LẤY TÀI KHOẢN BẰNG MÃ TK
    public dto_TaiKhoan layTaiKhoan(int ma_tk) {

        dto_TaiKhoan tk = null;

        String sql = "SELECT ten_nv,loai, ten_dang_nhap, mat_khau "
                + "FROM nhan_vien "
                + "WHERE ma_nv = ?";

        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, ma_tk);
            ResultSet rs = preStmt.executeQuery();

            if (rs.next()) {
                tk = new dto_TaiKhoan();
                tk.setMa(ma_tk);
                tk.setHoTen(rs.getString(1));
                tk.setLoai(rs.getInt(2));
                tk.setTenDangNhap(rs.getString(3));
                tk.setMatKhau(rs.getString(4));
            }
            conn.close();
            return tk;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // HÀM CẬP NHẬT NGÀY BẮT ĐẦU - NGÀY KẾT THÚC CỦA LỚP HỌC
    public int capNhatThoiGianHoc(dto_LopHoc lop){
        
        String sql = "UPDATE lop SET ngay_bd = ? , ngay_kt=?, so_buoi=? WHERE ma_lop=?";
        
        try{
            
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setDate(1, new java.sql.Date(lop.getNgayBd().getTime()));
            preStmt.setDate(2, new java.sql.Date(lop.getNgayKt().getTime()));
            preStmt.setInt(3, lop.getSoBuoi());
            preStmt.setInt(4, lop.getMaLop());
            
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
