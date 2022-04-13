package DAL;

import DTO.dto_KhachHang;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dal_KhachHang extends DBConnect {

    // LẤY DS KHÁCH HÀNG
    public ArrayList<dto_KhachHang> layDsKhachHang() {

        ArrayList<dto_KhachHang> dsKhachHang = new ArrayList<dto_KhachHang>();
        dto_KhachHang kh = null;

        String sql = "SELECT ma_kh, ten_kh, gioi_tinh, ngay_sinh, sdt, dia_chi, diem_dau_vao, chung_chi_can_hoc, lop_dang_hoc "
                + "FROM khach_hang";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                kh = new dto_KhachHang();
                kh.setMaKh(rs.getInt(1));
                kh.setTenKh(rs.getString(2));
                kh.setGioiTinh(rs.getInt(3));
                kh.setNgaySinh(rs.getDate(4));
                kh.setSdt(rs.getString(5));
                kh.setDiaChi(rs.getString(6));
                kh.setDiemDauVao(rs.getFloat(7));
                kh.setMaChungChi(rs.getInt(8));
                kh.setTenLop(rs.getString(9));

                dsKhachHang.add(kh);
            }

            conn.close();
            return dsKhachHang;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // THÊM KHÁCH HÀNG
    public int themKhachHang(dto_KhachHang kh) {

        String sql = "INSERT INTO khach_hang (ma_kh, "
                + "ten_kh, "
                + "ngay_sinh, "
                + "gioi_tinh, "
                + "dia_chi, "
                + "sdt, "
                + "diem_dau_vao, "
                + "chung_chi_can_hoc)"
                + "values (khach_hang_sequence.NEXTVAL,?,?,?,?,?,?,?)";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, kh.getTenKh());
            preStmt.setDate(2, new java.sql.Date(kh.getNgaySinh().getTime()));
            preStmt.setInt(3, kh.getGioiTinh());
            preStmt.setString(4, kh.getDiaChi());
            preStmt.setString(5, kh.getSdt());
            preStmt.setFloat(6, kh.getDiemDauVao());
            preStmt.setInt(7, kh.getMaChungChi());

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM CẬP NHẬT KHÁCH HÀNG
    public int capNhatKhachHang(dto_KhachHang kh) {

        String sql = "UPDATE khach_hang "
                + "SET ten_kh = ?,"
                + "gioi_tinh = ?,"
                + "ngay_sinh=?,"
                + "dia_chi=?,"
                + "sdt=?,"
                + "diem_dau_vao=?,"
                + "chung_chi_can_hoc=? "
                + "WHERE ma_kh=?";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, kh.getTenKh());
            preStmt.setDate(3, new java.sql.Date(kh.getNgaySinh().getTime()));
            preStmt.setInt(2, kh.getGioiTinh());
            preStmt.setString(4, kh.getDiaChi());
            preStmt.setString(5, kh.getSdt());
            preStmt.setFloat(6, kh.getDiemDauVao());
            preStmt.setInt(7, kh.getMaChungChi());
            preStmt.setInt(8, kh.getMaKh());

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM XÓA KHÁCH HÀNG
    public int xoaKhachHang(dto_KhachHang kh) {

        String sql = "DELETE FROM khach_hang WHERE ma_kh=?";

        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, kh.getMaKh());

            int rs = preStmt.executeUpdate();
            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // LẤY KHÁCH HÀNG BẰNG MÃ KHÁCH HÀNG
    public dto_KhachHang layKhachHang(int maKh) {

        dto_KhachHang kh = null;

        String sql = "SELECT ma_kh, ten_kh, gioi_tinh, ngay_sinh, sdt, dia_chi, diem_dau_vao, chung_chi_can_hoc, lop_dang_hoc "
                + "FROM khach_hang "
                + "WHERE ma_kh=?";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, maKh);
            ResultSet rs = preStmt.executeQuery();

            if (rs.next()) {

                kh = new dto_KhachHang();
                kh.setMaKh(rs.getInt(1));
                kh.setTenKh(rs.getString(2));
                kh.setGioiTinh(rs.getInt(3));
                kh.setNgaySinh(rs.getDate(4));
                kh.setSdt(rs.getString(5));
                kh.setDiaChi(rs.getString(6));
                kh.setDiemDauVao(rs.getFloat(7));
                kh.setMaChungChi(rs.getInt(8));
                kh.setTenLop(rs.getString(9));
            }

            conn.close();
            return kh;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<dto_KhachHang> layDsTimKiem(String text){
        text = text.toLowerCase();
        
        ArrayList<dto_KhachHang> dsKhachHang = new ArrayList<dto_KhachHang>();
        dto_KhachHang kh = null;
        
        
        String sql = "SELECT ma_kh, ten_kh, gioi_tinh, ngay_sinh, sdt, dia_chi, diem_dau_vao, chung_chi_can_hoc, lop_dang_hoc "
                + "FROM khach_hang "
                + "WHERE ma_kh LIKE N'%" + text + "%' "
                + "OR LOWER(ten_kh) LIKE N'%" + text + "%' "
                + "OR LOWER(sdt) LIKE N'" + text +"%' "
                + "OR LOWER(dia_chi) LIKE N'%" + text + "%' ";
        try{
            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                kh = new dto_KhachHang();
                kh.setMaKh(rs.getInt(1));
                kh.setTenKh(rs.getString(2));
                kh.setGioiTinh(rs.getInt(3));
                kh.setNgaySinh(rs.getDate(4));
                kh.setSdt(rs.getString(5));
                kh.setDiaChi(rs.getString(6));
                kh.setDiemDauVao(rs.getFloat(7));
                kh.setMaChungChi(rs.getInt(8));
                kh.setTenLop(rs.getString(9));

                dsKhachHang.add(kh);
            }
            
            conn.close();
            return dsKhachHang;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
