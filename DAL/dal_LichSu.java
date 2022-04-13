package DAL;

import DTO.dto_LichSu;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dal_LichSu extends DBConnect {

    // HÀM LẤY LỊCH SỬ CỦA 1 KHÁCH HÀNG
    public ArrayList<dto_LichSu> dsLichSu(int maKh) {

        ArrayList<dto_LichSu> dsLichSu = new ArrayList<dto_LichSu>();
        dto_LichSu ls = null;

        String sql = "SELECT ma_kh, ma_lop, nghe, noi, doc, viet, tong "
                + "FROM kqht "
                + "WHERE ma_kh = ?";

        try {

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, maKh);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {

                ls = new dto_LichSu();

                ls.setMaKh(rs.getInt(1));
                ls.setMaLop(rs.getInt(2));
                ls.setNghe(rs.getFloat(3));
                ls.setNoi(rs.getFloat(4));
                ls.setDoc(rs.getFloat(5));
                ls.setViet(rs.getFloat(6));
                ls.setTong(rs.getFloat(7));

                ls.setLop(new dal_LopHoc().layLopHoc(ls.getMaLop()));
                dsLichSu.add(ls);
            }
            conn.close();
            return dsLichSu;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
