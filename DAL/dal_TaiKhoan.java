package DAL;

import DTO.dto_TaiKhoan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class dal_TaiKhoan extends DBConnect {

    // HÀM ĐĂNG NHẬP
    public dto_TaiKhoan dangNhap(dto_TaiKhoan tkNhap) {

        dto_TaiKhoan tkTraVe = null;

        try {

            String sql = "SELECT ma_nv, ten_nv, sdt, loai, ten_dang_nhap, mat_khau, src_img "
                    + "FROM nhan_vien "
                    + "WHERE ten_dang_nhap = ?";

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, tkNhap.getTenDangNhap());

            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {
                tkTraVe = new dto_TaiKhoan();

                tkTraVe.setMa(rs.getInt(1));
                tkTraVe.setHoTen(rs.getString(2));
                tkTraVe.setSdt(rs.getString(3));
                tkTraVe.setLoai(rs.getInt(4));
                tkTraVe.setTenDangNhap(rs.getString(5));
                tkTraVe.setMatKhau(rs.getString(6));
                tkTraVe.setSrcImg(rs.getString(7));
            }

            conn.close();
            return tkTraVe;
        } catch (Exception ex) {
            ex.printStackTrace();
            return tkTraVe;
        }

    }

    // HÀM LẤY DANH SÁCH TÀI KHOẢN
    public ArrayList<dto_TaiKhoan> layDsTaiKhoan() {

        ArrayList<dto_TaiKhoan> dsTaiKhoan = new ArrayList<dto_TaiKhoan>();
        dto_TaiKhoan tk = null;

        try {

            String sql = "SELECT ma_nv, ten_nv, sdt, loai, ten_dang_nhap, mat_khau, src_img "
                    + "FROM nhan_vien";

            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                tk = new dto_TaiKhoan();

                tk.setMa(rs.getInt(1));
                tk.setHoTen(rs.getString(2));
                tk.setSdt(rs.getString(3));
                tk.setLoai(rs.getInt(4));
                tk.setTenDangNhap(rs.getString(5));
                tk.setMatKhau(rs.getString(6));
                tk.setSrcImg(rs.getString(7));

                dsTaiKhoan.add(tk);
            }

            conn.close();
            return dsTaiKhoan;
        } catch (Exception ex) {
            ex.printStackTrace();
            return dsTaiKhoan;
        }
    }

    // HÀM THÊM TÀI KHOẢN
    public int themTaiKhoan(dto_TaiKhoan tk) {

        try {

            String sql = "INSERT INTO nhan_vien (ma_nv, ten_nv, sdt, loai, ten_dang_nhap, mat_khau, src_img) "
                    + "VALUES (nhan_vien_sequence.NEXTVAL,?,?,?,?,?,?)";

            PreparedStatement preStmt = conn.prepareStatement(sql);

            preStmt.setString(1, tk.getHoTen());
            preStmt.setString(2, tk.getSdt());
            preStmt.setInt(3, tk.getLoai());
            preStmt.setString(4, tk.getTenDangNhap());
            preStmt.setString(5, tk.getMatKhau());
            preStmt.setString(6, tk.getSrcImg());

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM CẬP NHẬT TÀI KHOẢN
    public int capNhatTaiKhoan(dto_TaiKhoan tk, boolean capNhatMatKhau) {

        try {
            String sql = "";
            if (capNhatMatKhau == true) {
                sql = "UPDATE nhan_vien "
                        + "SET ten_nv=?,"
                        + "sdt=?,"
                        + "loai=?,"
                        + "mat_khau=?,"
                        + "src_img=? "
                        + "WHERE ma_nv=?";
            } else {
                sql = "UPDATE nhan_vien "
                        + "SET ten_nv=?,"
                        + "sdt=?,"
                        + "loai=?,"
                        + "src_img=? "
                        + "WHERE ma_nv=?";
            }

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, tk.getHoTen());
            preStmt.setString(2, tk.getSdt());
            preStmt.setInt(3, tk.getLoai());

            if (capNhatMatKhau == true) {
                preStmt.setString(4, tk.getMatKhau());
                preStmt.setString(5, tk.getSrcImg());
                preStmt.setInt(6, tk.getMa());
            } else {
                preStmt.setString(4, tk.getSrcImg());
                preStmt.setInt(5, tk.getMa());
            }

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM XÓA MẬT KHẨU
    public int xoaTaiKhoan(dto_TaiKhoan tk) {

        try {

            String sql = "DELETE FROM nhan_vien WHERE ma_nv=" + tk.getMa();

            PreparedStatement preStmt = conn.prepareStatement(sql);
            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM TÌM KIẾM
    public ArrayList<dto_TaiKhoan> layDsTimKiem(String text) {

        text = text.toLowerCase();
        ArrayList<dto_TaiKhoan> dsTaiKhoan = new ArrayList<dto_TaiKhoan>();
        dto_TaiKhoan tk = null;

        try {

            String sql = "SELECT ma_nv, ten_nv, sdt, loai, ten_dang_nhap, mat_khau, src_img "
                    + "FROM nhan_vien "
                    + "WHERE LOWER(ma_nv) LIKE N'%" + text + "%' "
                    + "OR LOWER(ten_nv) LIKE N'%" + text + "%' "
                    + "OR sdt LIKE N'%" + text + "%' "
                    + "OR LOWER(ten_dang_nhap) LIKE N'%" + text + "%'";

            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                tk = new dto_TaiKhoan();

                tk.setMa(rs.getInt(1));
                tk.setHoTen(rs.getString(2));
                tk.setSdt(rs.getString(3));
                tk.setLoai(rs.getInt(4));
                tk.setTenDangNhap(rs.getString(5));
                tk.setMatKhau(rs.getString(6));
                tk.setSrcImg(rs.getString(7));

                dsTaiKhoan.add(tk);
            }

            conn.close();
            return dsTaiKhoan;
        } catch (Exception ex) {
            ex.printStackTrace();
            return dsTaiKhoan;
        }
    }

    // HÀM LẤY TÀI KHOẢN BẰNG MÃ TK
    public dto_TaiKhoan layTaiKhoan(int ma_tk) {

        dto_TaiKhoan tk = null;

        String sql = "SELECT ten_nv, sdt, loai, ten_dang_nhap, mat_khau, src_img "
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
                tk.setSdt(rs.getString(2));
                tk.setLoai(rs.getInt(3));
                tk.setTenDangNhap(rs.getString(4));
                tk.setMatKhau(rs.getString(5));
                tk.setSrcImg(rs.getString(6));
            }
            conn.close();
            return tk;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
