package DAL;

import DTO.dto_Phong;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dal_Phong extends DBConnect {

    // HÀM LẤY DS PHÒNG
    public ArrayList<dto_Phong> layDsPhong() {

        ArrayList<dto_Phong> dsPhong = new ArrayList<dto_Phong>();
        dto_Phong phong = null;

        String sql = "SELECT ma_phong, ten_phong "
                + "FROM phong";

        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                phong = new dto_Phong();
                phong.setMaPhong(rs.getInt(1));
                phong.setTenPhong(rs.getString(2));

                dsPhong.add(phong);
            }

            conn.close();
            return dsPhong;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // HÀM THÊM PHÒNG HỌC
    public int themPhong(dto_Phong phong) {

        String sql = "INSERT INTO phong (ma_phong, ten_phong) "
                + "VALUES (phong_sequence.NEXTVAL, ?)";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, phong.getTenPhong());

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM CẬP NHẬT PHÒNG HỌC
    public int capNhatPhong(dto_Phong phong) {

        String sql = "UPDATE phong "
                + "SET ten_phong =" + phong.getTenPhong() + " "
                + "WHERE ma_phong =" + phong.getMaPhong();

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

    // HÀM XÓA PHÒNG
    public int xoaPhong(dto_Phong phong) {

        String sql = "DELETE FROM phong WHERE ma_phong=" + phong.getMaPhong();

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

    // HÀM TÌM KIẾM PHÒNG
    public ArrayList<dto_Phong> timPhong(String text) {

        text = text.toLowerCase();
        ArrayList<dto_Phong> dsPhong = new ArrayList<dto_Phong>();
        dto_Phong phong = null;

        String sql = "SELECT ma_phong, ten_phong "
                + "FROM phong "
                + "WHERE ma_phong LIKE N'" + text + "%' "
                + "OR LOWER(ten_phong) LIKE N'" + text + "%'";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);

            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                phong = new dto_Phong();
                phong.setMaPhong(rs.getInt(1));
                phong.setTenPhong(rs.getString(2));

                dsPhong.add(phong);
            }

            conn.close();
            return dsPhong;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
