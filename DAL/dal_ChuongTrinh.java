package DAL;

import DTO.dto_ChungChi;
import DTO.dto_ChuongTrinh;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class dal_ChuongTrinh extends DBConnect {

    // HÀM LẤY DỮ LIỆU TRONG BẢNG CHUONGTRINH CÓ THAM SỐ
    public ArrayList<dto_ChuongTrinh> layDsChuongTrinh(boolean layTatCa) {

        ArrayList<dto_ChuongTrinh> dsChuongTrinh = new ArrayList<dto_ChuongTrinh>();

        dto_ChuongTrinh ct = null;

        String sql = "";

        if (layTatCa == false) {
            sql = "SELECT "
                    + "ma_ct,"
                    + "ma_chung_chi,"
                    + "ten_ct,"
                    + "diem_dau_vao,"
                    + "diem_dau_ra,"
                    + "noi_dung,"
                    + "trang_thai,"
                    + "tinh_diem_nghe,"
                    + "tinh_diem_noi,"
                    + "tinh_diem_doc,"
                    + "tinh_diem_viet,"
                    + "cach_tinh_diem "
                    + "FROM chuong_trinh "
                    + "WHERE trang_thai = 1";
        } else {
            sql = "SELECT "
                    + "ma_ct,"
                    + "ma_chung_chi,"
                    + "ten_ct,"
                    + "diem_dau_vao,"
                    + "diem_dau_ra,"
                    + "noi_dung,"
                    + "trang_thai,"
                    + "tinh_diem_nghe,"
                    + "tinh_diem_noi,"
                    + "tinh_diem_doc,"
                    + "tinh_diem_viet,"
                    + "cach_tinh_diem "
                    + "FROM chuong_trinh "
                    + "ORDER BY trang_thai DESC";
        }
        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {
                ct = new dto_ChuongTrinh();

                ct.setMaCt(rs.getInt(1));
                ct.setMaCc(rs.getInt(2));
                ct.setTenCt(rs.getString(3));
                ct.setDiemDauVao(rs.getFloat(4));
                ct.setDiemDauRa(rs.getFloat(5));
                ct.setNoiDung(rs.getString(6));
                ct.setTrangThai(rs.getInt(7));
                ct.setTinhNghe(rs.getInt(8));
                ct.setTinhNoi(rs.getInt(9));
                ct.setTinhDoc(rs.getInt(10));
                ct.setTinhViet(rs.getInt(11));
                ct.setCachTinhDiem(rs.getInt(12));

                dsChuongTrinh.add(ct);
            }

            conn.close();
            return dsChuongTrinh;
        } catch (Exception e) {
            e.printStackTrace();
            return dsChuongTrinh;
        }

    }

    // HÀM TÌM KIẾM CHƯƠNG TRÌNH HỌC
    public ArrayList<dto_ChuongTrinh> layDsTimKiem(String text) {

        text = text.toLowerCase();
        ArrayList<dto_ChuongTrinh> dsChuongTrinh = new ArrayList<dto_ChuongTrinh>();
        dto_ChuongTrinh ct = null;

        String sql = "SELECT "
                + "ma_ct,"
                + "ma_chung_chi,"
                + "ten_ct,"
                + "diem_dau_vao,"
                + "diem_dau_ra,"
                + "noi_dung,"
                + "trang_thai,"
                + "tinh_diem_nghe,"
                + "tinh_diem_noi,"
                + "tinh_diem_doc,"
                + "tinh_diem_viet,"
                + "cach_tinh_diem "
                + "FROM chuong_trinh "
                + "WHERE LOWER(ten_ct) LIKE N'%" + text + "%' "
                + "OR LOWER(ma_ct) LIKE N'%" + text + "%'";


        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet rs = preStmt.executeQuery();

            while (rs.next()) {
                ct = new dto_ChuongTrinh();
                ct.setMaCt(rs.getInt(1));
                ct.setMaCc(rs.getInt(2));
                ct.setTenCt(rs.getString(3));
                ct.setDiemDauVao(rs.getFloat(4));
                ct.setDiemDauRa(rs.getFloat(5));
                ct.setNoiDung(rs.getString(6));
                ct.setTrangThai(rs.getInt(7));
                ct.setTinhNghe(rs.getInt(8));
                ct.setTinhNoi(rs.getInt(9));
                ct.setTinhDoc(rs.getInt(10));
                ct.setTinhViet(rs.getInt(11));
                ct.setCachTinhDiem(rs.getInt(12));

                dsChuongTrinh.add(ct);
            }
            conn.close();
            return dsChuongTrinh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsChuongTrinh;
    }

    // HÀM THÊM CHUONG TRÌNH
    public int themChuongTrinh(dto_ChuongTrinh ct) {

        try {
            String sql = "INSERT INTO chuong_trinh (ma_ct,"
                    + "ma_chung_chi,"
                    + "ten_ct,"
                    + "diem_dau_vao,"
                    + "diem_dau_ra,"
                    + "noi_dung,"
                    + "trang_thai,"
                    + "tinh_diem_nghe,"
                    + "tinh_diem_noi,"
                    + "tinh_diem_doc,"
                    + "tinh_diem_viet,"
                    + "cach_tinh_diem) "
                    + "VALUES (chuong_trinh_sequence.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, ct.getMaCc());
            preStmt.setString(2, ct.getTenCt());
            preStmt.setFloat(3, ct.getDiemDauVao());
            preStmt.setFloat(4, ct.getDiemDauRa());
            preStmt.setString(5, ct.getNoiDung());
            preStmt.setInt(6, ct.getTrangThai());
            preStmt.setInt(7, ct.getTinhNghe());
            preStmt.setInt(8, ct.getTinhNoi());
            preStmt.setInt(9, ct.getTinhDoc());
            preStmt.setInt(10, ct.getTinhViet());
            preStmt.setInt(11, ct.getCachTinhDiem());

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM CẬP NHẬT LẠI CHƯƠNG TRÌNH
    public int capNhatChuongTrinh(dto_ChuongTrinh ct) {

        try {
            String sql = "UPDATE chuong_trinh "
                    + "SET ten_ct = ?,"
                    + "diem_dau_vao = ?,"
                    + "diem_dau_ra = ?,"
                    + "trang_thai = ?,"
                    + "noi_dung=?,"
                    + "tinh_diem_nghe=?,"
                    + "tinh_diem_noi=?,"
                    + "tinh_diem_doc=?,"
                    + "tinh_diem_viet=?,"
                    + "cach_tinh_diem=? "
                    + "WHERE ma_ct=?";

            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, ct.getTenCt());
            preStmt.setFloat(2, ct.getDiemDauVao());
            preStmt.setFloat(3, ct.getDiemDauRa());
            preStmt.setInt(4, ct.getTrangThai());
            preStmt.setString(5, ct.getNoiDung());
            preStmt.setInt(6, ct.getTinhNghe());
            preStmt.setInt(7, ct.getTinhNoi());
            preStmt.setInt(8, ct.getTinhDoc());
            preStmt.setInt(9, ct.getTinhViet());
            preStmt.setInt(10, ct.getCachTinhDiem());
            preStmt.setInt(11, ct.getMaCt());

            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM XÓA CHƯƠNG TRÌNH
    public int xoaChuongTrinh(int ma_ct) {

        try {

            String sql = "DELETE FROM chuong_trinh "
                    + "WHERE ma_ct = " + ma_ct;

            PreparedStatement preStmt = conn.prepareStatement(sql);
            int rs = preStmt.executeUpdate();

            conn.close();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // HÀM LẤY CHƯƠNG TRÌNH BẰNG MÃ CHUONG TRINH
    public dto_ChuongTrinh layChuongTrinh(int ma_ct) {

        dto_ChuongTrinh ct = null;

        String sql = "SELECT "
                + "ma_ct,"
                + "ma_chung_chi,"
                + "ten_ct,"
                + "diem_dau_vao,"
                + "diem_dau_ra,"
                + "noi_dung,"
                + "trang_thai,"
                + "tinh_diem_nghe,"
                + "tinh_diem_noi,"
                + "tinh_diem_doc,"
                + "tinh_diem_viet,"
                + "cach_tinh_diem "
                + "FROM chuong_trinh "
                + "WHERE ma_ct =?";
        try {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, ma_ct);
            ResultSet rs = preStmt.executeQuery();

            if (rs.next()) {
                ct = new dto_ChuongTrinh();
                ct.setMaCt(rs.getInt(1));
                ct.setMaCc(rs.getInt(2));
                ct.setTenCt(rs.getString(3));
                ct.setDiemDauVao(rs.getFloat(4));
                ct.setDiemDauRa(rs.getFloat(5));
                ct.setNoiDung(rs.getString(6));
                ct.setTrangThai(rs.getInt(7));
                ct.setTinhNghe(rs.getInt(8));
                ct.setTinhNoi(rs.getInt(9));
                ct.setTinhDoc(rs.getInt(10));
                ct.setTinhViet(rs.getInt(11));
                ct.setCachTinhDiem(rs.getInt(12));
            }
            conn.close();
            return ct;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
