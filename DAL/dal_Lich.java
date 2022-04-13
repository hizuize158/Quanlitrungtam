package DAL;

import DTO.dto_GiaoVien;
import DTO.dto_Lich;
import DTO.dto_Phong;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class dal_Lich extends DBConnect {

    // LẤY LỊCH NGÀY
    public ArrayList<dto_Lich> layDsLichNgay() {

        ArrayList<dto_Lich> dsLichNgay = new ArrayList<dto_Lich>();
        dto_Lich lich = null;

        try {

            String sql = "SELECT ma_lop, ma_gv, ma_phong, tg_bd, tg_kt "
                    + "FROM lich_ngay";

            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();
            while (rs.next()) {

                lich = new dto_Lich();

                lich.setMaLop(rs.getInt(1));
                lich.setMaGv(rs.getInt(2));
                lich.setMaPhong(rs.getInt(3));
                lich.setTgBd(rs.getDate(4));
                lich.setTgKt(rs.getDate(5));

                lich.setGv(layGiaoVien(lich.getMaGv()));
                lich.setPhong(layPhong(lich.getMaPhong()));

                dsLichNgay.add(lich);
            }

            return dsLichNgay;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return dsLichNgay;
        }

    }

    // LẤY LỊCH NGÀY THEO MÃ LỚP
    public ArrayList<dto_Lich> layDsLichTheoLop(int maLop) {

        ArrayList<dto_Lich> dsLich = new ArrayList<dto_Lich>();
        dto_Lich lich = null;

        try {

            String sql = "SELECT ma_lop, ma_gv, ma_phong, tg_bd, tg_kt "
                    + "FROM lich_ngay "
                    + "WHERE ma_lop = ? "
                    + "ORDER BY tg_bd";

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, maLop);
            ResultSet rs = preStmt.executeQuery();
            while (rs.next()) {

                lich = new dto_Lich();
                lich.setMaLop(rs.getInt(1));
                lich.setMaGv(rs.getInt(2));
                lich.setMaPhong(rs.getInt(3));
                lich.setTgBd(rs.getDate(4));
                lich.setTgKt(rs.getDate(5));

                lich.setGv(layGiaoVien(lich.getMaGv()));
                lich.setPhong(layPhong(lich.getMaPhong()));

                dsLich.add(lich);
            }

            return dsLich;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return dsLich;
        }

    }

    // HÀM LẤY GIÁO VIÊN TỪ MÃ LỚP - không đóng connect
    public dto_GiaoVien layGiaoVien(int maGv) {

        dto_GiaoVien gv = null;

        String sql = "SELECT ten_gv, quoc_tich "
                + "FROM giao_vien "
                + "WHERE ma_gv = ?";

        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, maGv);
            ResultSet rs = preStmt.executeQuery();

            if (rs.next()) {

                gv = new dto_GiaoVien();
                gv.setMaGv(maGv);
                gv.setTenGv(rs.getString(1));
                gv.setQuocTich(rs.getString(2));
            }
            return gv;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    // HÀM LẤY PHÒNG TỪ MÃ LỚP - không đóng connect
    public dto_Phong layPhong(int maPhong) {

        dto_Phong phong = null;

        String sql = "SELECT ten_phong "
                + "FROM phong "
                + "WHERE ma_phong=" + maPhong;

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            if (rs.next()) {
                phong = new dto_Phong();
                phong.setMaPhong(maPhong);
                phong.setTenPhong(rs.getString(1));
            }
            return phong;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // HÀM THÊM LỊCH HỌC
    public int themLichHoc(ArrayList<dto_Lich> dsLich) {

        try {
            String sql = "";
            PreparedStatement preStmt;

            for (dto_Lich lich : dsLich) {

                sql = "INSERT INTO lich_ngay(ma_lop,ma_gv,ma_phong, tg_bd, tg_kt) "
                        + "VALUES (?,?,?,?,?)";

                preStmt = conn.prepareStatement(sql);
                preStmt.setInt(1, lich.getMaLop());
                preStmt.setInt(2, lich.getMaGv());
                preStmt.setInt(3, lich.getMaPhong());
                preStmt.setDate(4, new java.sql.Date(lich.getTgBd().getTime()));
                preStmt.setDate(5, new java.sql.Date(lich.getTgKt().getTime()));

                preStmt.executeUpdate();
            }

            conn.close();
            return 1;

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // XÓA DANH SÁCH LỚP HỌC
    public int xoaDsLich(int ma_lop) {

        String sql = "DELETE FROM lich_ngay WHERE ma_lop = ?";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, ma_lop);

            int rs = preStmt.executeUpdate();

            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
