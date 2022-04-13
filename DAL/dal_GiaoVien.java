package DAL;

import DTO.dto_GiaoVien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dal_GiaoVien extends DBConnect {

    // HÀM LẤY DANH SÁCH GIÁO VIÊN
    public ArrayList<dto_GiaoVien> layDsGiaoVien() {

        ArrayList<dto_GiaoVien> dsGv = new ArrayList<dto_GiaoVien>();
        dto_GiaoVien gv = null;

        String sql = "SELECT ma_gv, ten_gv, gioi_tinh, sdt, quoc_tich "
                + "FROM giao_vien";

        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                gv = new dto_GiaoVien();
                gv.setMaGv(rs.getInt(1));
                gv.setTenGv(rs.getString(2));
                gv.setGioiTinh(rs.getInt(3));
                gv.setSdt(rs.getString(4));
                gv.setQuocTich(rs.getString(5));

                dsGv.add(gv);
            }
            conn.close();
            return dsGv;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // HÀM THÊM GIÁO VIÊN
    public int themGiaoVien(dto_GiaoVien gv) {

        String sql = "INSERT INTO giao_vien (ma_gv, ten_gv, gioi_tinh, sdt,quoc_tich) "
                + "VALUES (gv_sequence.NEXTVAL, ?, ?, ?, ?)";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, gv.getTenGv());
            preStmt.setInt(2, gv.getGioiTinh());
            preStmt.setString(3, gv.getSdt());
            preStmt.setString(4, gv.getQuocTich());

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM CẬP NHẬT GIÁO VIÊN
    public int capNhatGiaovien(dto_GiaoVien gv) {

        String sql = "UPDATE giao_vien SET "
                + "ten_gv=?,"
                + "gioi_tinh=?,"
                + "sdt=?,"
                + "quoc_tich=? "
                + "WHERE ma_gv=?";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, gv.getTenGv());
            preStmt.setInt(2, gv.getGioiTinh());
            preStmt.setString(3, gv.getSdt());
            preStmt.setString(4, gv.getQuocTich());
            preStmt.setInt(5, gv.getMaGv());

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM XÓA GIÁO VIÊN
    public int xoaGiaoVien(dto_GiaoVien gv) {

        String sql = "DELETE FROM giao_vien WHERE ma_gv= ?";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, gv.getMaGv());
            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public ArrayList<dto_GiaoVien> timGiaoVien(String text) {

        text = text.toLowerCase();

        ArrayList<dto_GiaoVien> dsGiaoVien = new ArrayList<dto_GiaoVien>();
        dto_GiaoVien gv = null;

        String sql = "SELECT ma_gv, ten_gv, gioi_tinh, sdt, quoc_tich "
                + "FROM giao_vien "
                + "WHERE ma_gv LIKE N'" + text + "%' "
                + "OR LOWER(ten_gv) LIKE N'%" + text + "%' "
                + "OR LOWER(sdt) LIKE N'%" + text +"%' "
                + "OR LOWER(quoc_tich) LIKE N'" + text + "'";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                gv = new dto_GiaoVien();
                gv.setMaGv(rs.getInt(1));
                gv.setTenGv(rs.getString(2));
                gv.setGioiTinh(rs.getInt(3));
                gv.setSdt(rs.getString(4));
                gv.setQuocTich(rs.getString(5));

                dsGiaoVien.add(gv);
            }

            conn.close();
            return dsGiaoVien;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
